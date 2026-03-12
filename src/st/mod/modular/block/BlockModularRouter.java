package st.mod.modular.block;

import st.mod.modular.struct.StructModular;

public class BlockModularRouter extends BlockModular {
	public int CapacityTransItem = 120;
	public int CapacityTransLiquid = 240;
	public int CapacityTransUnit = 4;
	public BlockModularRouter(String name) {
		super(name);
	}
	@Override
	public void HandleStructAdd(StructModular struct, BlockModulerBuilding building) {
		struct.CapacityTransItem += CapacityTransItem;
		struct.CapacityTransLiquid += CapacityTransLiquid;
		struct.CapacityTransUnit += struct.CapacityTransUnit;
	}
	@Override
	public void HandleStructRemove(StructModular struct, BlockModulerBuilding building) {
		struct.CapacityTransItem -= CapacityTransItem;
		struct.CapacityTransLiquid -= CapacityTransLiquid;
		struct.CapacityTransUnit -= struct.CapacityTransUnit;
	}
}
