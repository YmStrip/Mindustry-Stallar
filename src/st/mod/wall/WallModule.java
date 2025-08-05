package st.mod.wall;

import arc.Events;
import mindustry.game.EventType;
import st.mod.ST_TECH;

public class WallModule {
	public WallModule() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			ST_TECH
				.createTechNodeRoot(ST_WALL.NANO_WALL)
				.add(ST_WALL.NANO_WALL_LARGE)
				.add(ST_WALL.CHROMAL_WALL, t -> t
					.add(ST_WALL.CHROMAL_WALL_LARGE
					)
				)
				.add(ST_WALL.ELEMENT_WALL, t -> t
					.add(ST_WALL.ELEMENT_WALL_LARGE)
				)
				.add(ST_WALL.AETHER_WALL, t -> t
					.add(ST_WALL.AETHER_WALL_LARGE)
				)
			;
		});
	}
}
