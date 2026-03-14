package st.mod.multiblock;

import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Struct {
	public static class HistoryCheckpoint {
		public int Min = 0;
		public int Max = 0;
		public HashMap<HistoryCheckpoint, ArrayList<Integer>> Connect = new HashMap<>();
	}

	public Struct(mindustry.game.Team team) {
		Team = team;
	}
	public Team Team;
	/**
	 * view. current building
	 */
	public HashSet<mindustry.gen.Building> Building = new HashSet<>();
	/**
	 * view. block count
	 */
	public HashMap<Block, Integer> Block = new HashMap<>();
	/**
	 * todo optimize. connect history
	 */
	public ArrayList<Building> History = new ArrayList<>();
	public ArrayList<HistoryCheckpoint> HistoryCheckpoint = new ArrayList<>();

	//
	public boolean Add(Building building) {
		if (building.team != Team || Building.contains(building)) return false;
		Building.add(building);
		Block.putIfAbsent(building.block, 0);
		Block.put(building.block, Block.get(building.block) + 1);
		STMultiBlock.StructByBuilding.put(building, this);
		return true;
	}
	public boolean Remove(Building building) {
		if (building.team != Team || !Building.contains(building)) return false;
		Building.remove(building);
		Block.putIfAbsent(building.block, 0);
		Block.put(building.block, Block.get(building.block) - 1);
		if (Block.get(building.block) <= 0) Block.remove(building.block);
		STMultiBlock.StructByBuilding.remove(building);
		if (Building.isEmpty()) STMultiBlock.Struct.remove(this);
		return true;
	}
	public void Merge(Struct other) {
		for (var i : other.Building) {
			this.Add(i);
		}
	}
	public abstract Struct Split(HashSet<Building> building);
	public void Clear() {
		var ar = Building.toArray();
		for (Object object : ar) {
			Remove((mindustry.gen.Building) object);
		}
	}
	public void Destroy() {
		Clear();
		STMultiBlock.Struct.remove(this);
	}
	public void HandleTick() {

	}
}
