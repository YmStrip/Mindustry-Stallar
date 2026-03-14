package st.mod.modular.block;


import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
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


import java.util.HashMap;

public class BlockModular extends Block {
	public float CapacityUnit = 0;
	public BlockModular(String name) {
		super(name);
		update = true;
		solid = true;
		sync = true;
		configurable = true;
	}
	public void ViewConfig(Building building, Table table, DialogModular dialog) {

	}
	public void ViewBar(Building building, Table table, DialogModular dialog) {

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
	}
}
