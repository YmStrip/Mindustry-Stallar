package st.mod.distribution;

import arc.Events;
import mindustry.game.EventType;
import st.mod.ST_TECH;

public class DistributionModule {
	public DistributionModule() {
		Events.on(EventType.WorldLoadEndEvent.class, e -> {
			ST_TECH
				.createTechNodeRoot(ST_DISTRIBUTION.ITEM_INPUT)
				.add(ST_DISTRIBUTION.ITEM_INPUT_EXTRA, t -> t
					.children(ST_DISTRIBUTION.ITEM_OUTPUT_EXTRA)
					.children(ST_DISTRIBUTION.ITEM_IO_EXTRA)
				)
				.add(ST_DISTRIBUTION.LIQUID_BUFFER, t -> t
					.add(ST_DISTRIBUTION.LIQUID_BUFFER_EXTRA)
					.add(ST_DISTRIBUTION.LIQUID_INPUT_EXTRA, t1 -> t1
						.children(ST_DISTRIBUTION.LIQUID_OUTPUT_EXTRA)
						.children(ST_DISTRIBUTION.LIQUID_IO_EXTRA)
					)
					.children(ST_DISTRIBUTION.LIQUID_INPUT)
					.children(ST_DISTRIBUTION.LIQUID_OUTPUT)
					.children(ST_DISTRIBUTION.LIQUID_IO)
				)
				.children(ST_DISTRIBUTION.ITEM_OUTPUT)
				.children(ST_DISTRIBUTION.ITEM_IO)
			;
		});
	}
}
