package st.mod.distribution.liquid;

import mindustry.gen.Building;
import mindustry.type.Liquid;
import st.mod.util.SBuildMap;

public class SBuildMapLiquidBuffer extends SBuildMap<BlockLiquidBuffer.BlockBufferLiquidBuild> {
	public void addAmount(Building building, Liquid liquid, float amount) {
		var liquids = get(building.team);
		for (var i : liquids) {
			if (!i.acceptLiquid(building, liquid)) continue;
			var canAdd = Math.min(amount, i.block.liquidCapacity - i.liquids.get(liquid));
			i.handleLiquid(building, liquid, canAdd);
			amount -= canAdd;
			if (amount <= 0) return;
		}
	}
	public float getAmount(Building building, Liquid liquid) {
		var liquids = get(building.team);
		var has = 0f;
		for (var i : liquids) {
			var buildAmount = i.liquids.get(liquid);
			if (buildAmount <= 0) continue;
			has += buildAmount;
		}
		return has;
	}
	public float getCapacity(Building building, Liquid liquid) {
		var liquids = get(building.team);
		var capacity = 0f;
		for (var i : liquids) {
			var buildAmount = i.block.liquidCapacity;
			capacity += buildAmount;
		}
		return capacity;
	}
}
