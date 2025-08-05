package st.mod.resource;

import arc.Events;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import st.mod.ST_TECH;


public class ResourceModule {
	public ResourceModule() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			ST_TECH.createTechNodeRoot(ST_RESOURCE.SUPER_PUMP)
				.add(ST_RESOURCE.IMPACT_PUMP)
			;
			ST_TECH.createTechNodeRoot(ST_RESOURCE.SUPER_DRILL)
				.add(ST_RESOURCE.IMPACT_DRILL,t->t
					.add(ST_RESOURCE.MINI_ELEMENT_DRILL)
				)
				.add(ST_RESOURCE.NANO_FACTORY)
				.add(ST_RESOURCE.RESOURCE_EXTRACTOR)
				.add(ST_RESOURCE.SHALLOW_MINER, t -> t
					.add(ST_RESOURCE.DEEP_MINER, t1 -> t1
						.add(ST_RESOURCE.SPECIAL_MINER)
					)
				)
				.add(ST_RESOURCE.CONCENTRATOR);
			ST_TECH.createTechNode(ST_RESOURCE.SUPER_SOLID_PUMP)
				.parent(Blocks.waterExtractor)
				.add(ST_RESOURCE.ATMOSPHERE_EXTRACTOR)
				.add(ST_RESOURCE.CORE_EXTRACTOR);
		});
	}
}
