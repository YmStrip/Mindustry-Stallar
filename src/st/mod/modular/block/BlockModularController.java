package st.mod.modular.block;

import st.mod.modular.struct.StructModular;

public class BlockModularController extends BlockModular {
	public int CapacityBuilding = 6;
	public BlockModularController(String name) {
		super(name);
	}
	@Override
	public void HandleStructAdd(StructModular struct, BlockModularBuilding building) {
		struct.CapacityBuilding += CapacityBuilding;
	}
	@Override
	public void HandleStructRemove(StructModular struct, BlockModularBuilding building) {
		struct.CapacityBuilding -= CapacityBuilding;
	}
	public class BlockModularControllerBuilding extends BlockModularBuilding {

	}
}
