package st.mod.unit;


import arc.Events;
import mindustry.game.EventType;
import st.mod.ST_TECH;


public class UnitModule {
	public UnitModule() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			ST_TECH.createTechNodeRoot(ST_UNIT.ZETA);
			ST_TECH
				.createTechNodeRoot(ST_UNIT.MATRIX_UNIT_FACTORY)
				.add(ST_UNIT.MATRIX_A1, t -> t
					.add(ST_UNIT.MATRIX_A2, t1 -> t1
						.add(ST_UNIT.MATRIX_A3, t2 -> t2
							.add(ST_UNIT.MATRIX_A4)
						)
					)
				)
				.add(ST_UNIT.MATRIX_MINER_UNIT, t -> t
					.add(ST_UNIT.MATRIX_MINER_UNIT_EXTRA)
				)
				.add(ST_UNIT.MATRIX_BUILDING_UNIT, t -> t
					.add(ST_UNIT.MATRIX_BUILDING_UNIT_EXTRA)
				)
				.add(ST_UNIT.MATRIX_UNIT_FACTORY_EXTRA);
		});
	}
}
