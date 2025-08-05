package st.mod.product;


import arc.Events;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.game.EventType;
import st.mod.item.ST_ITEM;
import st.mod.ST_TECH;

public class ProductModule {
	public ProductModule() {
		Events.on(EventType.ContentInitEvent.class, t -> {
			ST_TECH.createTechNodeRoot(ST_PRODUCT.NANOTUBE_FACTORY)
				.add(ST_PRODUCT.SUPERCONDUCTOR_FACTORY, t1 -> t1
					.add(ST_PRODUCT.SUPER_GRAPHITE_FACTORY, t2 -> t2
						.require(Items.graphite))
					.add(ST_PRODUCT.SUPER_SILICON_FACTORY, t2 -> t2
						.require(Items.silicon))
					.add(ST_PRODUCT.SUPER_METAGLASS_FACTORY, t2 -> t2
						.require(Items.metaglass))
					.add(ST_PRODUCT.SUPER_PLASTANIUM_FACTORY, t2 -> t2
						.require(Items.plastanium))
					.add(ST_PRODUCT.SUPER_PHASE_FABRIC_FACTORY, t2 -> t2
						.require(Items.phaseFabric))
					.add(ST_PRODUCT.SUPER_PYRATITE_FACTORY, t2 -> t2
						.require(Items.pyratite))
					.add(ST_PRODUCT.SUPER_BLASE_COMPOUND_FACTORY, t2 -> t2
						.require(Items.blastCompound))
					.add(ST_PRODUCT.TRITITANIUM_ALLOW_FACTORY, t2 -> t2
						.require(Items.surgeAlloy))
					.add(ST_PRODUCT.SUPER_CRYOFLUID_FACTORY, t2 -> t2
						.require(Liquids.cryofluid)))
				.add(ST_PRODUCT.CHROMAL_FACTORY)
				.add(ST_PRODUCT.ANTIMATTER_FACTORY, t1 -> t1
					.add(ST_PRODUCT.SUSPENDED_FACTORY)
					.add(ST_PRODUCT.METCRYSTAL_FACTORY)
				)
				.add(ST_PRODUCT.GOLD_ELEMENT_FACTORY, e1 -> e1
					.require(ST_ITEM.ANTIMATTER)
					.add(ST_PRODUCT.WOOD_ELEMENT_FACTORY, e2 -> e2
						.require(ST_ITEM.GOLD_ELEMENT)
						.add(ST_PRODUCT.WATER_ELEMENT_FACTORY, e3 -> e3
							.require(ST_ITEM.WOOD_ELEMENT)
							.add(ST_PRODUCT.FIRE_ELEMENT_FACTORY, e4 -> e4
								.require(ST_ITEM.WATER_ELEMENT)
								.add(ST_PRODUCT.EARTH_ELEMENT_FACTORY, e5 -> e5
									.require(ST_ITEM.FIRE_ELEMENT)
									.add(ST_PRODUCT.NANO_FLUID_MIXER, n -> n
										.require(ST_ITEM.EARTH_ELEMENT)
										.add(ST_PRODUCT.LIGHT_ELEMENT_FACTORY)
										.add(ST_PRODUCT.DARK_ELEMENT_FACTORY)
									)
								)
							)
						)
					)
				);
		});
	}
}
