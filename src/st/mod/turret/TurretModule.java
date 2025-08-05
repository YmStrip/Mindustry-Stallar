package st.mod.turret;

import arc.Events;
import mindustry.game.EventType;
import st.mod.ST_TECH;

public class TurretModule {
	public TurretModule() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			ST_TECH
				.createTechNodeRoot(ST_TURRET.SUPER_LASER)
				.add(ST_TURRET.SUPER_GUN)
				.add(ST_TURRET.IMPACT, t -> t
					.add(ST_TURRET.PULSE, t1 -> t1
						.add(ST_TURRET.ELECTRONIC_WAVE)))
				.add(ST_TURRET.ION_BEAM)
				.add(ST_TURRET.ARMSTRONG_GUN)
				.add(ST_TURRET.STINGER_MISSILE)
				.add(ST_TURRET.PHOTON_GUN, t -> t
					.add(ST_TURRET.ELECTRONIC_SPECTRE)
					.add(ST_TURRET.MASS_GUN)
					.add(ST_TURRET.CERTAIN_RAIL_GUN, t1 -> t1
						.add(ST_TURRET.CERTAIN_GUN)
						.add(ST_TURRET.CERTAIN_CANNON)
						.add(ST_TURRET.LIGHT_CANNON)
						.add(ST_TURRET.AETHER_BLASTER)
						.add(ST_TURRET.AETHER_DESTROYER)
					)
				);
		});
	}
}