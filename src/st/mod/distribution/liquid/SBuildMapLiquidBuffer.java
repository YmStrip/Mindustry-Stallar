package st.mod.distribution.liquid;

import mindustry.gen.Building;
import mindustry.type.Liquid;
import st.mod.util.CountBuild;

public class SBuildMapLiquidBuffer extends CountBuild<BlockLiquidBuffer.BlockBufferLiquidBuild> {
	@Override
	public boolean Filter(Building build) {
		return build instanceof BlockLiquidBuffer.BlockBufferLiquidBuild;
	}
	public void AddAmount(Building building, Liquid liquid, float amount) {
		var liquids = Get(building.team);
		for (var i : liquids) {
			if (amount > 0 && !i.acceptLiquid(building, liquid)) continue;
			var canAdd = Math.min(amount, i.block.liquidCapacity - i.liquids.get(liquid));
			i.handleLiquid(building, liquid, canAdd);
			amount -= canAdd;
			if (amount <= 0) return;
		}
	}
	public float GetAmount(Building building, Liquid liquid) {
		var liquids = Get(building.team);
		var has = 0f;
		//System.out.println("liquids"+liquids.size());
		for (var i : liquids) {
			var buildAmount = i.liquids.get(liquid);
			//System.out.println("amount"+buildAmount+",name"+liquid.name);
			if (buildAmount <= 0) continue;
			has += buildAmount;
		}
		return has;
	}
	public float GetCapacity(Building building, Liquid liquid) {
		var liquids = Get(building.team);
		var capacity = 0f;
		for (var i : liquids) {
			var buildAmount = i.block.liquidCapacity;
			capacity += buildAmount;
		}
		return capacity;
	}
}
