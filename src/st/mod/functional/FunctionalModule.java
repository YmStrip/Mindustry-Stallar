package st.mod.functional;


import arc.Events;
import mindustry.game.EventType;
import st.mod.ST_TECH;

public class FunctionalModule {
	public FunctionalModule() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			ST_TECH
				.createTechNodeRoot(ST_FUNCTIONAL.OUTPOST_CORE)
				.add(ST_FUNCTIONAL.NANO_CORE, t -> t
					.add(ST_FUNCTIONAL.MATRIX_CORE, t1 -> t1
						.add(ST_FUNCTIONAL.AETHER_CORE)
					)
				);
			ST_TECH
				.createTechNodeRoot(ST_FUNCTIONAL.BLACK_HOLE_DRIVE);
			ST_TECH
				.createTechNodeRoot(ST_FUNCTIONAL.MATRIX_MEND_PROJECTOR)
				.add(ST_FUNCTIONAL.MATRIX_FORCE_FIELD)
				.add(ST_FUNCTIONAL.MATRIX_OVERDRIVE_PROJECTOR, x_1 -> x_1
					.add(ST_FUNCTIONAL.SUPERCRITICAL_PROJECTOR));
		});
	}
}
