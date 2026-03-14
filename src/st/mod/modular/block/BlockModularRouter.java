package st.mod.modular.block;

import st.mod.modular.struct.StructModular;

public class BlockModularRouter extends BlockModular {
	public float CapacityTransItem = 8;
	public float CapacityTransLiquid = 60;
	public float CapacityTransUnit = 2 / 60f;
	public float CapacityBufferItem = 120;
	public float CapacityBufferLiquid = 240;
	public float CapacityBufferUnit = 1f;
	public BlockModularRouter(String name) {
		super(name);
	}
	@Override
	public void HandleStructAdd(StructModular struct, BlockModularBuilding building) {
		struct.CapacityTransItem += CapacityTransItem;
		struct.CapacityTransLiquid += CapacityTransLiquid;
		struct.CapacityTransUnit += CapacityTransUnit;
		struct.CapacityBufferItem += CapacityBufferItem;
		struct.CapacityBufferLiquid += CapacityBufferLiquid;
		struct.CapacityBufferUnit += CapacityBufferUnit;
	}
	@Override
	public void HandleStructRemove(StructModular struct, BlockModularBuilding building) {
		struct.CapacityTransItem -= CapacityTransItem;
		struct.CapacityTransLiquid -= CapacityTransLiquid;
		struct.CapacityTransUnit -= struct.CapacityTransUnit;
	}
}
