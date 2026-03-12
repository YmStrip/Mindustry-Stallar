package st.mod.multiblock;


import arc.Events;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.core.GameState;
import mindustry.game.EventType;
import mindustry.gen.Building;


import java.util.*;


public class STMultiBlock {
	//view maintain
	public static HashMap<Building, Struct> StructByBuilding = new HashMap<>();
	public static HashSet<Struct> Struct = new HashSet<>();
	//controller
	private static Seq<HashSet<Building>> FindClusters(HashSet<Building> buildings) {
		Seq<HashSet<Building>> clusters = new Seq<>();
		HashSet<Building> visited = new HashSet<>();
		for (Building b : buildings) {
			if (!visited.contains(b)) {
				HashSet<Building> cluster = new HashSet<>();
				FillCluster(b, buildings, visited, cluster);
				clusters.add(cluster);
			}
		}
		return clusters;
	}

	private static void FillCluster(Building start, HashSet<Building> allBuildings, HashSet<Building> visited, HashSet<Building> cluster) {
		Seq<Building> stack = new Seq<>();
		stack.add(start);
		visited.add(start);
		cluster.add(start);

		while (!stack.isEmpty()) {
			Building current = stack.pop();

			for (Building neighbor : current.proximity) {
				if (allBuildings.contains(neighbor) && !visited.contains(neighbor)) {
					visited.add(neighbor);
					cluster.add(neighbor);
					stack.add(neighbor);
				}
			}
		}
	}
	/**
	 * not optimize : remove build O(n)
	 *
	 * @param building
	 */
	public static void Remove(Building building) {
		var def = StructByBuilding.get(building);
		if (def != null) {
			def.Remove(building);
			if (def.Building.isEmpty()) return;
			var Cluster = FindClusters(def.Building);
			if (Cluster.size > 1) {
				Cluster.sort((o1, o2) -> Integer.compare(o2.size(), o1.size()));
				for (var i = 1; i < Cluster.size; ++i) {
					var o = def.Split(Cluster.get(i));
					Struct.add(o);
				}
			}
		}
	}
	/**
	 * try add in proximity struct
	 *
	 * @param building
	 */
	public static boolean Add(Building building) {
		var includes = new HashSet<Struct>();
		for (var i : building.proximity) {
			var def = StructByBuilding.get(i);
			if (def != null) {
				includes.add(def);
			}
		}
		System.out.println("near by count"+includes.size()+":,pri:"+building.proximity.size);
		if (includes.isEmpty()) return false;
		if (includes.size() == 1) {
			includes.iterator().next().Add(building);
			return true;
		} else {
			var sort = new ArrayList<>(includes);
			sort.sort((o1, o2) -> Integer.compare(o2.Building.size(), o1.Building.size()));
			var result = sort.get(0);
			for (int i = 1; i < sort.size(); i++) {
				var temp = sort.get(i);
				result.Merge(temp);
				temp.Destroy();
			}
			result.Add(building);
			return true;
		}
	}
	/**
	 * try add in proximity struct , if fail , create new struct
	 *
	 * @param building
	 */
	public static void Add(Building building, AddCallback Fn) {
		if (Add(building)) return;
		var str = Fn.Call();
		Struct.add(str);
		str.Add(building);
	}
	/**
	 * auto create struct when world load Map[BuildingClass,Callback]
	 */
	public static HashSet<StructBuilder> StructBuilder = new HashSet<>();
	public static void Init() {
		Events.on(EventType.WorldLoadEndEvent.class, e -> {
			Struct.clear();
			var weak = new HashSet<Building>();
			Vars.world.tiles.eachTile(tile -> {
				if (tile.build == null) return;
				if(weak.contains(tile.build)) return;
				weak.add(tile.build);
				for (STMultiBlock.StructBuilder i : StructBuilder) {
					var check = i.Check(tile.build);
					tile.build.updateProximity();
					if(check) {
						Add(tile.build,()->i.Struct(tile.build));
					}
				}
			});
		});
		Events.run(EventType.Trigger.update, () -> {
			if (!Vars.state.isMenu() && !Vars.state.isPaused()) {
				for (var i : Struct) {
					i.HandleTick();
				}
			}
		});
		Events.on(EventType.StateChangeEvent.class, event -> {
			if (event.to == GameState.State.menu) {
				Clear();
			}
		});
	}


	public static interface AddCallback {
		Struct Call();
	}

	public static abstract class StructBuilder {
		public abstract Struct Struct(Building building);
		public abstract boolean Check(Building building);
	}
	public static void Clear() {
		Struct.clear();
		StructByBuilding.clear();
	}
}
