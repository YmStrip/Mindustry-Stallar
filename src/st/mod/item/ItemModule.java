package st.mod.item;

import arc.Events;
import mindustry.game.EventType;
import st.mod.ST_TECH;
import st.mod.content.ST_LIQUID;


public class ItemModule {
	public ItemModule() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			ST_TECH.createTechNodeRoot(ST_ITEM.NANOTUBE)
				.add(ST_ITEM.SUPERCONDUCTOR, t -> t
					.add(ST_ITEM.GOLD_ELEMENT)
					.add(ST_ITEM.METRYSTAl, t4 -> t4
						.add(ST_ITEM.WATER_ELEMENT))
					.add(ST_ITEM.WOOD_ELEMENT)
				)
				.add(ST_ITEM.SUSPENDED, t3 -> t3
					.add(ST_ITEM.EARTH_ELEMENT)
					.add(ST_ITEM.CHROMAL, t2 -> t2
						.add(ST_ITEM.FIRE_ELEMENT)
						.add(ST_ITEM.LIGHT_ELEMENT)
						.add(ST_ITEM.DARK_ELEMENT)
						.add(ST_LIQUID.NANO_FLUID)));
		});
	}
}
