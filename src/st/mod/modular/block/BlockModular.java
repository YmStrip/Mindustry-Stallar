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
import st.ST;
import st.mod.modular.ui.DialogModular;
import st.mod.multiblock.STMultiBlock;
import st.mod.modular.struct.StructModular;
import st.mod.ui.UtilUI;
import st.mod.ui.ViewBool;
import st.mod.ui.ViewColor;
import st.mod.util.BlockCodec;


import java.util.HashMap;

public class BlockModular extends BlockCodec {
	public float CapacityUnit = 0;
	public TextureRegion IconBase;
	public TextureRegion IconHeat;
	public TextureRegion IconTop;
	public BlockModular(String name) {
		super(name);
		update = true;
		solid = true;
		sync = true;
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
	public void RenderConfig(BlockModularBuilding building, Table table, DialogModular dialog) {
		table.labelWrap(building.block.localizedName).expandX().fillX().row();
		table.labelWrap(building.block.displayDescription()).expandX().fillX().row();
	}
	public void RenderStyle(BlockModularBuilding building, Table table, DialogModular dialog) {
		table.labelWrap(ST.UI("modular_style")).left().fillX().expandX().row();
		var Light = new ViewBool(building.Light, ST.UI("modular_light")) {
			@Override
			public void Update() {
				building.Light = Model;
			}
		};
		var LightColor = new ViewColor(building.LightColor) {
			@Override
			public void Update() {
				super.Update();
				building.LightColor = Model;
			}
		};
		table.add(Light).expandX().fillX().left().row();
		table.add(LightColor).expandX().fillX().left().row();
	}
	public void RenderBar(BlockModularBuilding building, Table table, DialogModular dialog) {
		table.add(UtilUI.CreateIconButton(building.block.uiIcon, () -> {
		})).size(42);
		table.labelWrap(" " + building.block.localizedName).expandX().left();
	}
	public void HandleStructAdd(StructModular struct, BlockModularBuilding building) {
	}
	public void HandleStructRemove(StructModular struct, BlockModularBuilding building) {
	}
	public void HandleConfig(BlockModularBuilding building, JSONObject json) {
	}
	public class BlockModularBuilding extends BlockCodecBuilding {
		public HashMap<UnitType, Float> Unit = new HashMap<>();
		public boolean Light = false;
		public Color LightColor = Color.white;
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
			Draw.alpha(DrawHeatSmooth);
			Draw.rect(fullIcon, x, y, size, size);
			Draw.color(color, color, 0.3f);
			Draw.blend(Blending.additive);
			Draw.alpha(DrawHeatSmooth);
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
			if (IconHeat.found()) DrawHeat(Light ? LightColor : Color.white, power.status, 90);
		}
		/**
		 * configure data
		 *
		 * @param json      json value
		 * @param isBlueMap isBlueMap
		 */
		@Override
		public void Configure(JSONObject json, boolean isBlueMap) {
			var r = json.getFloat("LightColor.R");
			var g = json.getFloat("LightColor.G");
			var b = json.getFloat("LightColor.B");
			Light = json.getBoolean("Light");
			LightColor = new Color(r, g, b);
			//
			if (!isBlueMap) {
				try {
					var units = json.getJSONObject("Unit");
					if (units != null) {
						for (var i : units.keySet()) {
							var unit = Vars.content.unit(i);
							if (unit != null) {
								Unit.put(unit, units.getFloat(i));
							}
						}
					}
				} catch (Exception e) {
				}
			}
		}
		/**
		 * get serialize data
		 *
		 * @param isBlueMap
		 * @return
		 */
		@Override
		public JSONObject Serialize(boolean isBlueMap) {
			var json = new JSONObject();
			json.put("LightColor.R", LightColor.r);
			json.put("LightColor.G", LightColor.g);
			json.put("LightColor.B", LightColor.b);
			json.put("Light", Light);
			if (!isBlueMap) {
				var unit = new JSONObject();
				for (var i : Unit.keySet()) {
					unit.put(i.name, Unit.get(i));
				}
				json.put("Unit", unit);
			}
			return json;
		}
	}
}
