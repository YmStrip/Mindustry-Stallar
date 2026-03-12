package st.mod.modular.block;



import arc.scene.ui.layout.Table;
import mindustry.gen.Building;
import mindustry.world.Block;
import st.mod.modular.ui.DialogModular;
import st.mod.multiblock.STMultiBlock;
import st.mod.modular.struct.StructModular;

public class BlockModular extends Block {
	public int MaxCount = -1;
	public BlockModular(String name) {
		super(name);
		update = true;
		solid = true;
		sync = true;
	}
	public void ViewConfig(Building building, Table table, DialogModular dialog) {

	}
	public void ViewBar(Building building, Table table, DialogModular dialog) {

	}
	public void HandleStructAdd(StructModular struct,BlockModulerBuilding building) {}
	public void HandleStructRemove(StructModular struct,BlockModulerBuilding building) {}
	public class BlockModulerBuilding extends Building {
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
	}
}
