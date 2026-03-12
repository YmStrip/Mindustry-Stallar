package st.mod.modular.struct;


import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;
import st.mod.multiblock.STMultiBlock;
import st.mod.multiblock.Struct;
import st.mod.modular.block.BlockModular;
import st.mod.modular.block.BlockModularController;

import java.util.HashMap;
import java.util.HashSet;

public class StructModular extends Struct {
	//view.
	public HashSet<BlockModularController.BlockModularControllerBuilding> Controller = new HashSet<>();
	public int CapacityBuilding = 6;
	public int CapacityController = 4;
	public int CapacityBuildingDefault = 6;
	public float CapacityTransLiquid = 0;
	public float CapacityTransItem = 0;
	public float CapacityTransUnit = 0;
	public float CountTransLiquid = 0;
	public float CountTransItem = 0;
	public float CountTransUnit = 0;
	//view.io map
	public HashMap<UnlockableContent, Building> Input = new HashMap<>();
	public HashMap<UnlockableContent, Building> Output = new HashMap<>();
	//view.state diff by add and remove
	public boolean Overload = false;
	public boolean OverloadMax = false;
	public boolean OverloadCapacity = false;
	public boolean OverloadController = false;
	//get state
	protected void Overload() {
		Overload = OverloadController || OverloadCapacity || OverloadMax;
	}
	protected void OverloadMax() {
		var p = false;
		for (var i : Block.keySet()) {
			if (i instanceof BlockModular b && b.MaxCount > 0) {
				if (Block.getOrDefault(i, 0) > b.MaxCount) {
					OverloadMax = true;
					return;
				}
			}
		}
		OverloadMax = false;
	}
	protected void OverloadCapacity() {
		var capacity = this.CapacityBuildingDefault;
		for (var i : Controller) {
			capacity += ((BlockModularController) i.block).CapacityBuilding;
		}
		OverloadCapacity = Building.size() > capacity;
	}
	//find the max and check
	protected void OverloadController() {
		if (Controller.isEmpty()) {
			OverloadController = false;
			CapacityController = 4;
		}
		var max = 0;
		for (var i : Controller) {
			if (i.block instanceof BlockModularController b) {
				if (max < b.MaxCount) max = b.MaxCount;
			}
		}
		OverloadController = Controller.size() > max;
		CapacityController = max;
	}
	@Override
	public boolean Add(mindustry.gen.Building building) {
		if (!(building instanceof BlockModular.BlockModulerBuilding b1)) {
			return false;
		}
		if (!super.Add(building)) return false;
		if (building.block instanceof BlockModular b) {
			b.HandleStructAdd(this, b1);
		}
		OverloadMax();
		OverloadCapacity();
		OverloadController();
		Overload();
		return true;
	}
	@Override
	public boolean Remove(mindustry.gen.Building building) {
		if (!(building instanceof BlockModular.BlockModulerBuilding b1)) {
			return false;
		}
		if (!super.Remove(building)) return false;
		if (building.block instanceof BlockModular b) {
			b.HandleStructRemove(this, b1);
		}
		OverloadMax();
		OverloadCapacity();
		OverloadController();
		Overload();
		return true;
	}
	@Override
	public Struct Split(HashSet<mindustry.gen.Building> building) {
		var str = new StructModular(building.iterator().next().team);
		for (var i : building) {
			Remove(i);
			str.Add(i);
		}
		return str;
	}
	public StructModular(mindustry.game.Team team) {
		super(team);
	}
	public static void Init() {
		STMultiBlock.StructBuilder.add(new STMultiBlock.StructBuilder() {
			@Override
			public Struct Struct(mindustry.gen.Building building) {
				return new StructModular(building.team);
			}
			@Override
			public boolean Check(mindustry.gen.Building building) {
				return building instanceof BlockModular.BlockModulerBuilding;
			}
		});
	}
}
