package st.mod.modular.struct;


import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import st.mod.modular.block.BlockModularFactory;
import st.mod.modular.block.BlockModularStorage;
import st.mod.modular.entity.Recipe;
import st.mod.multiblock.STMultiBlock;
import st.mod.multiblock.Struct;
import st.mod.modular.block.BlockModular;
import st.mod.modular.block.BlockModularController;

import java.util.*;


public class StructModular extends Struct {
	//view.
	public HashSet<BlockModularController.BlockModularControllerBuilding> Controller = new HashSet<>();
	public int CapacityBuilding = 6;
	public int CapacityController = 4;
	public int CapacityBuildingDefault = 6;
	public float CapacityTransLiquid = 60;
	public float CapacityTransItem = 10;
	public float CapacityTransUnit = 2 / 60f;
	public float CapacityBufferLiquid = 120f;
	public float CapacityBufferItem = 60f;
	public float CapacityBufferUnit = 1f;
	public float BufferLiquidInput = 0;
	public float BufferItemInput = 0;
	public float BufferUnitInput = 0;
	public float BufferLiquidOutput = 0;
	public float BufferItemOutput = 0;
	public float BufferUnitOutput = 0;

	public static class IO {
		public IO() {
		}
		public IO(Recipe recipe) {
			for (var i : recipe.Input.keySet()) {
				Input.put(i, recipe.Input.get(i).Value());
			}
			for (var i : recipe.Output.keySet()) {
				Output.put(i, recipe.Output.get(i).Value());
			}
		}
		public HashMap<UnlockableContent, Float> Input = new HashMap<>();
		public HashMap<UnlockableContent, Float> Output = new HashMap<>();
		public float Weight = 1;
	}
	public void Schedule() {
		//update weight
		//sc

	}
	//view.readonly
	public HashMap<BlockModularFactory.BlockModularFactoryBuilding, IO> IO = new HashMap<>();
	public HashSet<BlockModularStorage.BlockModularStorageBuild> Storage = new HashSet<>();
	public HashMap<UnlockableContent, Float> StorageCount = new HashMap<>();
	//view.state diff by add and remove
	public boolean Overload = false;
	public boolean OverloadCapacity = false;
	public boolean OverloadController = false;
	//get state
	protected void Overload() {
		Overload = OverloadController || OverloadCapacity;
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
		OverloadController = Controller.size() > CapacityController;
	}
	@Override
	public boolean Add(mindustry.gen.Building building) {
		if (!(building instanceof BlockModular.BlockModularBuilding b1)) {
			return false;
		}
		if (!super.Add(building)) return false;
		b1.Struct = this;
		if (building.block instanceof BlockModular b) {
			b.HandleStructAdd(this, b1);
		}
		OverloadCapacity();
		OverloadController();
		Overload();
		return true;
	}
	@Override
	public boolean Remove(mindustry.gen.Building building) {
		if (!(building instanceof BlockModular.BlockModularBuilding b1)) {
			return false;
		}
		if (!super.Remove(building)) return false;
		b1.Struct = null;
		if (building.block instanceof BlockModular b) {
			b.HandleStructRemove(this, b1);
		}
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
				return building instanceof BlockModular.BlockModularBuilding;
			}
		});
	}
	public boolean AddIO(BlockModularFactory.BlockModularFactoryBuilding building) {
		if (Building.contains(building)) return false;
		if (building.Recipe == null) return false;
		IO.put(building, new IO(building.Recipe));
		return true;
	}
	public boolean RemoveIO(BlockModularFactory.BlockModularFactoryBuilding building) {
		if (!Building.contains(building)) return false;
		IO.remove(building);
		return true;
	}
	public boolean AddStorage(BlockModularStorage.BlockModularStorageBuild building) {
		if (Building.contains(building)) return false;
		Storage.add(building);
		for (var i : Vars.content.items()) {
			var def = building.items.get(i);
			if (def > 0) {
				StorageCount.put(i, StorageCount.getOrDefault(i, 0f) + def);
			}
		}
		for (var i : Vars.content.liquids()) {
			var def = building.liquids.get(i);
			if (def > 0) {
				StorageCount.put(i, StorageCount.getOrDefault(i, 0f) + def);
			}
		}
		for (var i : building.Unit.keySet()) {
			var def = building.Unit.get(i);
			if (def > 0) {
				StorageCount.put(i, StorageCount.getOrDefault(i, 0f) + def);
			}
		}
		return true;
	}
	public boolean RemoveStorage(BlockModularStorage.BlockModularStorageBuild building) {
		if (!Building.contains(building)) return false;
		Storage.remove(building);
		for (var i : Vars.content.items()) {
			var def = building.items.get(i);
			if (def > 0) {
				StorageCount.put(i, Math.max(StorageCount.getOrDefault(i, 0f) - def, 0));
			}
		}
		for (var i : Vars.content.liquids()) {
			var def = building.liquids.get(i);
			if (def > 0) {
				StorageCount.put(i, Math.max(StorageCount.getOrDefault(i, 0f) - def, 0));
			}
		}
		for (var i : building.Unit.keySet()) {
			var def = building.Unit.get(i);
			if (def > 0) {
				StorageCount.put(i, Math.max(StorageCount.getOrDefault(i, 0f) - def, 0));
			}
		}
		return true;
	}
	public boolean HasRecourse(UnlockableContent type, float count) {
		return StorageCount.getOrDefault(type, 0f) >= count;
	}
	public float AddRecourse(UnlockableContent type, float count) {
		for (var i : Storage) {
			count -= i.AddRecourse(type, count);
			if (count <= 0) break;
		}
		return count;
	}
	public float RemoveRecourse(UnlockableContent type, float count) {
		var total = count;
		for (var i : Storage) {
			count -= i.RemoveRecourse(type, count);
			if (count <= 0) return total;
		}
		return total - count;
	}
	@Override
	public void HandleTick() {
		super.HandleTick();
		//update buffer
		BufferItemInput = Math.min(BufferItemInput + CapacityTransItem / 60f, CapacityBufferItem);
		BufferLiquidInput = Math.min(BufferLiquidInput + CapacityTransLiquid / 60f, CapacityBufferLiquid);
		BufferUnitInput = Math.min(BufferUnitInput + CapacityTransUnit / 60f, CapacityBufferUnit);
		BufferItemOutput = Math.min(BufferItemOutput + CapacityTransItem / 60f, CapacityBufferItem);
		BufferLiquidOutput = Math.min(BufferLiquidOutput + CapacityTransLiquid / 60f, CapacityBufferLiquid);
		BufferUnitOutput = Math.min(BufferUnitOutput + CapacityTransUnit / 60f, CapacityBufferUnit);
		//send recourse to IO Map
		Schedule();
	}
	private static Random Rand = new Random();
	private void Wrap(ArrayList list) {
		var size = list.size();
		if (size > 1) {
			Collections.swap(list, 0, Rand.nextInt(list.size()));
		}
	}
}
