package st.mod.modular.struct;


import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import st.mod.modular.block.BlockModularFactory;
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
	public float CapacityTransLiquid = 60;
	public float CapacityTransItem = 10;
	public float CapacityTransUnit = 2 / 60f;
	public float StockTransLiquid = 0;
	public float StockTransItem = 0;
	public float StockTransUnit = 0;
	//view.readonly
	public HashMap<UnlockableContent, HashSet<BlockModularFactory.BlockModularFactoryBuilding>> Input = new HashMap<>();
	public HashMap<UnlockableContent, HashSet<BlockModularFactory.BlockModularFactoryBuilding>> Output = new HashMap<>();
	public HashMap<UnlockableContent, HashMap<BlockModular.BlockModularBuilding, Float>> Stocked = new HashMap<>();
	public HashMap<UnlockableContent, Float> Stock = new HashMap<>();
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
		if (!(building instanceof BlockModular.BlockModularBuilding b1)) {
			return false;
		}
		if (!super.Add(building)) return false;
		if (building.block instanceof BlockModular b) {
			b.HandleStructAdd(this, b1);
		}
		if (building instanceof BlockModularFactory.BlockModularFactoryBuilding fb) {
			AddViewIO(fb);
		}
		AddViewStorage(b1);
		OverloadMax();
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
		if (building.block instanceof BlockModular b) {
			b.HandleStructRemove(this, b1);
		}
		if (building instanceof BlockModularFactory.BlockModularFactoryBuilding fb) {
			RemoveViewIO(fb);
		}
		RemoveViewStorage(b1);
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
				return building instanceof BlockModular.BlockModularBuilding;
			}
		});
	}
	public boolean AddViewIO(BlockModularFactory.BlockModularFactoryBuilding building) {
		if (Building.contains(building)) return false;
		if (building.Recipe != null) {
			for (var i : building.Recipe.Input.keySet()) {
				Input.putIfAbsent(i, new HashSet<>());
				var def = Input.get(i);
				def.add(building);
			}
			for (var i : building.Recipe.Output.keySet()) {
				Output.putIfAbsent(i, new HashSet<>());
				var def = Output.get(i);
				def.add(building);
			}
		}
		return true;
	}
	public boolean RemoveViewIO(BlockModularFactory.BlockModularFactoryBuilding building) {
		if (!Building.contains(building)) return false;
		if (building.Recipe != null) {
			for (var i : building.Recipe.Input.keySet()) {
				var def = Input.get(i);
				if (def != null) {
					def.remove(building);
				}
				assert def != null;
				if (def.isEmpty()) Input.remove(i);
			}
			for (var i : building.Recipe.Output.keySet()) {
				var def = Output.get(i);
				if (def != null) {
					def.remove(building);
				}
				assert def != null;
				if (def.isEmpty()) Output.remove(i);
			}
		}
		return true;
	}
	public boolean AddViewStorage(BlockModular.BlockModularBuilding building) {
		if (Building.contains(building)) return false;
		for (var i : Vars.content.items()) {
			var value = building.items.get(i);
			if (value > 0) ChangeViewStorage(building, i, value);
		}
		for (var i : Vars.content.liquids()) {
			var value = building.liquids.get(i);
			if (value > 0) ChangeViewStorage(building, i, value);
		}
		for (var i : building.Unit.keySet()) {
			var value = building.Unit.get(i);
			if (value > 0) ChangeViewStorage(building, i, value);
		}
		return true;
	}
	public boolean RemoveViewStorage(BlockModular.BlockModularBuilding building) {
		if (!Building.contains(building)) return false;
		for (var i : Vars.content.items()) {
			var value = building.items.get(i);
			if (value > 0) ChangeViewStorage(building, i, -value);
		}
		for (var i : Vars.content.liquids()) {
			var value = building.liquids.get(i);
			if (value > 0) ChangeViewStorage(building, i, -value);
		}
		for (var i : building.Unit.keySet()) {
			var value = building.Unit.get(i);
			if (value > 0) ChangeViewStorage(building, i, -value);
		}
		return true;
	}
	public void ChangeViewStorage(BlockModular.BlockModularBuilding building, UnlockableContent type, float diffValue) {
		if (diffValue > 0) {
			Stock.put(type, Stock.getOrDefault(type, 0f) + diffValue);
			//
			Stocked.computeIfAbsent(type, k -> new HashMap<>());
			var stockedMap = Stocked.get(type);
			stockedMap.put(building, stockedMap.getOrDefault(building, 0f) + diffValue);
		} else {
			var newStockValue = Stock.getOrDefault(type, 0f) + diffValue;
			if (newStockValue <= 0) Stock.remove(type);
			else Stock.put(type, newStockValue);
			//
			var stockedMap = Stocked.get(type);
			if (stockedMap != null) {
				var newStockedMapValue = stockedMap.getOrDefault(building, 0f) + diffValue;
				if (newStockedMapValue <= 0) stockedMap.remove(building);
				else stockedMap.put(building, newStockedMapValue);
				if (stockedMap.keySet().isEmpty()) {
					Stocked.remove(type);
				}
			}
		}
	}
	@Override
	public void HandleTick() {
		super.HandleTick();
		//update trans stock , 1s = 60t
		StockTransItem = Math.min(StockTransItem + CapacityTransItem / 60f, CapacityTransItem);
		StockTransLiquid = Math.min(StockTransLiquid + CapacityTransLiquid / 60f, CapacityTransLiquid);
		StockTransUnit = Math.min(StockTransUnit + CapacityTransUnit / 60f, CapacityTransUnit);
	}
}
