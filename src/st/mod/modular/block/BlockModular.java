package st.mod.modular.block;


import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.Eachable;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.payloads.Payload;
import org.json.JSONObject;
import st.mod.modular.ui.DialogModular;
import st.mod.multiblock.STMultiBlock;
import st.mod.modular.struct.StructModular;
import st.mod.ui.UtilUI;


import java.util.HashMap;

public class BlockModular extends Block {
	public float CapacityUnit = 0;
	public TextureRegion IconBase;
	public TextureRegion IconHeat;
	public TextureRegion IconTop;
	public BlockModular(String name) {
		super(name);
		update = true;
		solid = true;
		sync = true;
		configurable = true;
		hasPower = true;
		outputsPower = true;
	}
	@Override
	public void loadIcon() {
		super.loadIcon();
		IconBase = Core.atlas.find(this.name + "-base");
		IconTop = Core.atlas.find(this.name + "-top");
		IconHeat = Core.atlas.find(this.name + "-heat");
	}
	@Override
	public void drawPlan(BuildPlan plan, Eachable<BuildPlan> list, boolean valid) {
		var drawSize = plan.block.size * Vars.tilesize;
		Draw.rect(region, plan.drawx(), plan.drawy(), drawSize, drawSize);
	}
	public void ViewConfig(Building building, Table table, DialogModular dialog) {
		table.labelWrap(building.block.localizedName).expandX().fillX().row();
		table.labelWrap(building.block.displayDescription()).expandX().fillX().row();
	}
	public void ViewBar(Building building, Table table, DialogModular dialog) {
		table.add(UtilUI.CreateIconButton(building.block.uiIcon, () -> {
		})).size(42);
		table.labelWrap(" " + building.block.localizedName).expandX().left();
	}
	public void HandleStructAdd(StructModular struct, BlockModularBuilding building) {
	}
	public void HandleStructRemove(StructModular struct, BlockModularBuilding building) {
	}
	public class BlockModularBuilding extends Building {
		public HashMap<UnitType, Float> Unit = new HashMap<>();
		/**
		 * view.optimize maintain by StructModular [Add|Remove]
		 */
		public StructModular Struct;
		@Override
		public void placed() {
			super.placed();
			STMultiBlock.Add(this, () -> new StructModular(team));
		}
		@Override
		public void onRemoved() {
			super.onRemoved();
			STMultiBlock.Remove(this);
		}
		@Override
		public void write(Writes write) {
			super.write(write);
			var json = new JSONObject();
			for (var i : Unit.keySet()) {
				json.put(i.name, Unit.get(i));
			}
			var jsonStr = json.toString();
			write.i(jsonStr.length());
			write.b(jsonStr.getBytes());
		}
		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);
			var str = new String(read.b(read.i()));
			try {
				var json = new JSONObject(str);
				for (var i : json.keySet()) {
					var unit = Vars.content.unit((String) i);
					if (unit != null) {
						Unit.put(unit, json.getFloat(i));
					}
				}
			} catch (Exception e) {
			}
		}
		@Override
		public boolean acceptItem(Building source, Item item) {
			return false;
		}
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return false;
		}
		@Override
		public boolean acceptPayload(Building source, Payload payload) {
			return false;
		}
		@Override
		public void buildConfiguration(Table table) {
			new DialogModular(this).show().exited(this::deselect);
		}
		public float DrawHeatSmooth = 0f;
		public void DrawHeat(Color color, float alpha, float smoothTime) {
			DrawHeatSmooth = Mathf.lerpDelta(DrawHeatSmooth, alpha, 1 / smoothTime);
			var size = block.size * Vars.tilesize;
			Draw.color(color, Color.white, 0.1f);
			Draw.alpha(DrawHeatSmooth);
			Draw.blend(Blending.additive);
			Draw.rect(IconHeat, x, y, size, size);
			Draw.blend();
			Draw.reset();
		}
		@Override
		public void draw() {
			var size = block.size * Vars.tilesize;
			if (IconBase.found()) {
				Draw.rect(IconBase, x, y, size, size);
			} else {
				Draw.rect(fullIcon, x, y, size, size);
			}
			if (IconTop.found()) Draw.rect(IconTop, x, y, size, size);
			if (IconHeat.found()) DrawHeat(Color.white, power.status, 90);
		}
	}
}
