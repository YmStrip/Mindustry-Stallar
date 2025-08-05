package st.mod.util;

import arc.Events;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;

import java.util.HashMap;
import java.util.HashSet;

public class SBuildMap<T extends Building> {
	public HashMap<Team, HashSet<T>> builds = new HashMap<>();
	public SBuildMap() {
		Events.on(EventType.WorldLoadEndEvent.class, e -> builds.clear());
	}
	public void add(T building) {
		builds.computeIfAbsent(building.team, k -> new HashSet<>());
		builds.get(building.team).add(building);
	}
	public void remove(T building) {
		builds.computeIfAbsent(building.team, k -> new HashSet<>());
		builds.get(building.team).remove(building);
	}
	public HashSet<T> get(T building) {
		return get(building.team);
	}
	public HashSet<T> get(Team team) {
		builds.computeIfAbsent(team, k -> new HashSet<>());
		return builds.get(team);
	}
	public int count(T building) {
		return count(building.team);
	}
	public int count(Team team) {
		return this.get(team).size();
	}
}
