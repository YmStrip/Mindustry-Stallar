package st.mod.power;

import arc.Events;
import mindustry.game.EventType;
import st.mod.ST_TECH;


public class PowerModule {
	public PowerModule() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			ST_TECH.createTechNodeRoot(ST_POWER.BIOMASS_GENERATOR)
				.add(ST_POWER.BEAM_NODE, t -> t
					.add(ST_POWER.BEAM_NODE_EXTRA))
				.add(ST_POWER.OIL_GENERATOR)
				.add(ST_POWER.PHASE_FABRIC_GENERATOR, t -> t
					.add(ST_POWER.ANTIMATTER_GENERATOR, t1 -> t1
						.add(ST_POWER.GRAVITY_GENERATOR)))
				.add(ST_POWER.HYDROPOWER_STATION, t -> t
					.add(ST_POWER.WATER_FUSION_GENERATOR))
				.add(ST_POWER.ELEMENT_REACTOR, t -> t
					.add(ST_POWER.VACUUM_BATTERY, t1 -> t1
						.add(ST_POWER.VACUUM_MATRIX)))
			;
		});
	}
}
