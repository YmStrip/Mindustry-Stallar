package st.mod.util;

import arc.Events;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;

import java.util.HashMap;
import java.util.HashSet;

public abstract class CountBuild<T extends Building> {
	/**
	 * if pass filter , then put build when init
	 * @param build
	 * @return
	 */
	public abstract boolean Filter(Building build);
	public HashMap<Team, HashSet<T>> builds = new HashMap<>();
	public CountBuild() {
		Events.on(EventType.WorldLoadEndEvent.class, e -> {
			builds.clear();
			Vars.world.tiles.eachTile(tile -> {
				if (tile.build == null) return;
				if (!Filter(tile.build)) return;
				Add((T) tile.build);
			});
		});
	}
	public void Add(T building) {
		builds.computeIfAbsent(building.team, k -> new HashSet<>());
		builds.get(building.team).add(building);
	}
	public void Remove(T building) {
		builds.computeIfAbsent(building.team, k -> new HashSet<>());
		builds.get(building.team).remove(building);
	}
	public HashSet<T> Get(T building) {
		return Get(building.team);
	}
	public HashSet<T> Get(Team team) {
		builds.computeIfAbsent(team, k -> new HashSet<>());
		return builds.get(team);
	}
	public int Count(T building) {
		return Count(building.team);
	}
	public int Count(Team team) {
		return this.Get(team).size();
	}
}
