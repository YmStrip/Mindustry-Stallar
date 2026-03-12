package st.mod.functional;


import arc.Events;
import mindustry.game.EventType;
import st.mod.STTech;

public class FunctionalModule {
	public FunctionalModule() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			STTech
				.createTechNodeRoot(STFunctional.OUTPOST_CORE)
				.Add(STFunctional.NANO_CORE, t -> t
					.Add(STFunctional.MATRIX_CORE, t1 -> t1
						.Add(STFunctional.AETHER_CORE)
					)
				);
			STTech
				.createTechNodeRoot(STFunctional.BLACK_HOLE_DRIVE);
			STTech
				.createTechNodeRoot(STFunctional.MATRIX_MEND_PROJECTOR)
				.Add(STFunctional.MATRIX_FORCE_FIELD)
				.Add(STFunctional.MATRIX_OVERDRIVE_PROJECTOR, x_1 -> x_1
					.Add(STFunctional.SUPERCRITICAL_PROJECTOR));
		});
	}
}
