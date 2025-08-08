package st.mod.product;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.BuildVisibility;
import st.mod.item.ST_ITEM;
import st.mod.content.ST_LIQUID;
import st.mod.UTIL_TOOLTIP;
import st.mod.product.entity.ElementFactory;

import static mindustry.type.ItemStack.with;

public class ST_PRODUCT {
	//[T1]
	public static void inject(GenericCrafter b, int techLevel) {
		b.category = Category.crafting;
		b.buildVisibility = BuildVisibility.shown;
		UTIL_TOOLTIP
			.tooltip(b.stats)
			.techLevel(techLevel)
		;
	}
	public static void inject(GenericCrafter b) {
		inject(b, 1);
	}
	public static GenericCrafter NANOTUBE_FACTORY;
	public static GenericCrafter SUPERCONDUCTOR_FACTORY;
	public static GenericCrafter CHROMAL_FACTORY;
	public static GenericCrafter METCRYSTAL_FACTORY;
	//upgrade
	public static GenericCrafter SUPER_METAGLASS_FACTORY;
	public static GenericCrafter SUPER_SILICON_FACTORY;
	public static GenericCrafter SUPER_GRAPHITE_FACTORY;
	public static GenericCrafter SUPER_PYRATITE_FACTORY;
	public static GenericCrafter SUPER_BLASE_COMPOUND_FACTORY;
	public static GenericCrafter SUPER_CRYOFLUID_FACTORY;
	//[T2]
	public static GenericCrafter ANTIMATTER_FACTORY;
	public static GenericCrafter SUSPENDED_FACTORY;
	public static GenericCrafter TRITITANIUM_ALLOW_FACTORY;
	public static GenericCrafter SUPER_PHASE_FABRIC_FACTORY;
	public static GenericCrafter SUPER_PLASTANIUM_FACTORY;
	//[T3]
	public static ElementFactory GOLD_ELEMENT_FACTORY;
	public static ElementFactory WOOD_ELEMENT_FACTORY;
	public static ElementFactory WATER_ELEMENT_FACTORY;
	public static ElementFactory FIRE_ELEMENT_FACTORY;
	public static ElementFactory EARTH_ELEMENT_FACTORY;
	public static GenericCrafter NANO_FLUID_MIXER;
	public static GenericCrafter DARK_ELEMENT_FACTORY;
	public static GenericCrafter LIGHT_ELEMENT_FACTORY;
	public static void load() {
		NANOTUBE_FACTORY = new GenericCrafter("NANOTUBE_FACTORY") {{
			size = 3;
			consumePower(6f);
			craftTime = 45;
			requirements = ItemStack.with(
				Items.copper, 100,
				Items.lead, 80,
				Items.silicon, 50,
				Items.metaglass, 80
			);
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumeItems(with(Items.graphite, 1, Items.sand, 1));
			outputItem = new ItemStack(ST_ITEM.NANOTUBE, 1);
			inject(this);
		}};
		SUPERCONDUCTOR_FACTORY = new GenericCrafter("SUPERCONDUCTOR_FACTORY") {{
			size = 3;
			consumePower(6f);
			craftTime = 45;
			requirements = ItemStack.with(
				Items.copper, 150,
				Items.lead, 100,
				Items.silicon, 50,
				Items.titanium, 50,
				Items.metaglass, 100
			);
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumeItems(with(Items.copper, 1, Items.lead, 1));
			outputItem = new ItemStack(ST_ITEM.SUPERCONDUCTOR, 1);
			inject(this);
		}};
		METCRYSTAL_FACTORY = new GenericCrafter("METCRYSTAL_FACTORY") {{
			size = 4;
			craftTime = 60;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(6);
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 150,
				ST_ITEM.SUPERCONDUCTOR, 100,
				Items.silicon, 200,
				Items.phaseFabric, 50,
				Items.plastanium, 50
			);
			consumeItems(ItemStack.with(Items.silicon, 2, Items.metaglass, 1, Items.thorium, 1));
			consumeLiquid(Liquids.cryofluid, 1);
			outputItem = new ItemStack(ST_ITEM.METRYSTAl, 2);
			inject(this, 2);
		}};
		CHROMAL_FACTORY = new GenericCrafter("CHROMAL_FACTORY") {{
			size = 4;
			craftTime = 30;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(24f);
			requirements = ItemStack.with(
				Items.lead, 150,
				Items.silicon, 100,
				Items.titanium, 50,
				Items.plastanium, 50
			);
			consumeItems(ItemStack.with(Items.surgeAlloy, 1, Items.thorium, 1));
			outputItem = new ItemStack(ST_ITEM.CHROMAL, 1);
			inject(this, 1);
		}};
		//upgrade
		SUPER_METAGLASS_FACTORY = new GenericCrafter("SUPER_METAGLASS_FACTORY") {{
			size = 3;
			craftTime = 30;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(5f);
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 100, ST_ITEM.SUPERCONDUCTOR, 50, Items.silicon, 50, Items.sand, 50);
			consumeItems(ItemStack.with(Items.sand, 1, Items.lead, 1));
			outputItem = new ItemStack(Items.metaglass, 1);
			inject(this, 1);
		}};
		SUPER_SILICON_FACTORY = new GenericCrafter("SUPER_SILICON_FACTORY") {{
			size = 3;
			craftTime = 30;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(6f);
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 150, ST_ITEM.SUPERCONDUCTOR, 50, Items.silicon, 100, Items.sand, 50);
			consumeItems(ItemStack.with(Items.sand, 1));
			outputItem = new ItemStack(Items.silicon, 1);
			inject(this, 1);
		}};
		SUPER_GRAPHITE_FACTORY = new GenericCrafter("SUPER_GRAPHITE_FACTORY") {{
			size = 3;
			craftTime = 30;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(3f);
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 50, ST_ITEM.SUPERCONDUCTOR, 50, Items.silicon, 50, Items.coal, 50);
			consumeItems(ItemStack.with(Items.coal, 1));
			outputItem = new ItemStack(Items.graphite, 1);
			inject(this, 1);
		}};
		SUPER_PYRATITE_FACTORY = new GenericCrafter("SUPER_PYRATITE_FACTORY") {{
			size = 3;
			craftTime = 30;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(3f);
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 180, ST_ITEM.SUPERCONDUCTOR, 50, Items.silicon, 100, Items.sporePod, 50);
			consumeItems(ItemStack.with(Items.coal, 1, Items.sand, 1));
			outputItem = new ItemStack(Items.pyratite, 1);
			inject(this, 1);
		}};
		SUPER_BLASE_COMPOUND_FACTORY = new GenericCrafter("SUPER_BLASE_COMPOUND_FACTORY") {{
			size = 3;
			craftTime = 30;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(6f);
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 180, ST_ITEM.SUPERCONDUCTOR, 50, Items.silicon, 100, Items.sporePod, 50);
			consumeItems(ItemStack.with(Items.sporePod, 1, Items.coal, 1, Items.sand, 1, Items.lead, 1));
			outputItem = new ItemStack(Items.blastCompound, 1);
			inject(this, 1);
		}};
		SUPER_CRYOFLUID_FACTORY = new GenericCrafter("SUPER_CRYOFLUID_FACTORY") {{
			size = 2;
			craftTime = 30;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(3f);
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 180,
				ST_ITEM.SUPERCONDUCTOR, 50,
				Items.silicon, 100,
				Items.titanium, 50
			);
			consumeItems(ItemStack.with(Items.titanium, 2));
			consumeLiquid(Liquids.water, 1);
			outputLiquid = new LiquidStack(Liquids.cryofluid, 4);
			inject(this, 1);
		}};
		//[T2]
		ANTIMATTER_FACTORY = new GenericCrafter("ANTIMATTER_FACTORY") {{
			//40000 / per
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 150,
				ST_ITEM.SUPERCONDUCTOR, 50,
				ST_ITEM.CHROMAL, 50,
				Items.silicon, 50,
				Items.metaglass, 50
			);
			itemCapacity = 16;
			size = 4;
			craftTime = 360f;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(40000 / 60f);
			consumeItems(with(Items.copper, 6, Items.lead, 6));
			outputItem = new ItemStack(ST_ITEM.ANTIMATTER, 6);
			inject(this, 2);
		}};
		SUSPENDED_FACTORY = new GenericCrafter("SUSPENDED_FACTORY") {{
			size = 4;
			craftTime = 60 * 8f;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(8f);
			itemCapacity = 16;
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 200,
				ST_ITEM.SUPERCONDUCTOR, 75,
				ST_ITEM.CHROMAL, 70,
				Items.silicon, 50,
				Items.plastanium, 50
			);
			consumeItems(ItemStack.with(ST_ITEM.ANTIMATTER, 1, Items.thorium, 8));
			outputItem = new ItemStack(ST_ITEM.SUSPENDED, 8);
			inject(this, 2);
		}};
		TRITITANIUM_ALLOW_FACTORY = new GenericCrafter("TRITITANIUM_ALLOW_FACTORY") {{
			size = 4;
			craftTime = 30;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(22f);
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 150,
				ST_ITEM.CHROMAL, 200,
				ST_ITEM.SUPERCONDUCTOR, 100,
				ST_ITEM.SUSPENDED, 50,
				ST_ITEM.METRYSTAl, 50
			);
			consumeLiquid(Liquids.slag, 24 / 16f);
			consumeItems(ItemStack.with(Items.titanium, 3));
			outputItem = new ItemStack(Items.surgeAlloy, 2);
			inject(this, 1);
		}};
		SUPER_PHASE_FABRIC_FACTORY = new GenericCrafter("SUPER_PHASE_FABRIC_FACTORY") {{
			size = 3;
			craftTime = 30;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(8f);
			requirements = ItemStack.with(
				ST_ITEM.CHROMAL, 150,
				ST_ITEM.NANOTUBE, 50,
				ST_ITEM.METRYSTAl, 50,
				ST_ITEM.SUPERCONDUCTOR, 50,
				Items.phaseFabric, 250
			);
			consumeItems(ItemStack.with(Items.sand, 2, Items.thorium, 1));
			outputItem = new ItemStack(Items.phaseFabric, 1);
			inject(this, 1);
		}};
		SUPER_PLASTANIUM_FACTORY = new GenericCrafter("SUPER_PLASTANIUM_FACTORY") {{
			size = 3;
			craftTime = 30;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(6f);
			requirements = ItemStack.with(
				ST_ITEM.CHROMAL, 150,
				ST_ITEM.NANOTUBE, 50,
				ST_ITEM.METRYSTAl, 50,
				ST_ITEM.SUPERCONDUCTOR, 50,
				Items.plastanium, 250
			);
			consumeItems(ItemStack.with(Items.titanium, 1, Items.coal, 1));
			outputItem = new ItemStack(Items.plastanium, 1);
			inject(this, 1);
		}};
		//[T3]
		GOLD_ELEMENT_FACTORY = new ElementFactory("GOLD_ELEMENT_FACTORY") {{
			consumeItems(ItemStack.with(ST_ITEM.ANTIMATTER, 2, Items.surgeAlloy, 1));
			outputItem = new ItemStack(ST_ITEM.GOLD_ELEMENT, 1);
			consumeLiquid(Liquids.cryofluid, 1);
			inject(this, 3);
		}};
		WOOD_ELEMENT_FACTORY = new ElementFactory("WOOD_ELEMENT_FACTORY") {{
			consumeItems(ItemStack.with(ST_ITEM.ANTIMATTER, 2, Items.sporePod, 1));
			outputItem = new ItemStack(ST_ITEM.WOOD_ELEMENT, 1);
			consumeLiquid(Liquids.water, 1);
			inject(this, 3);
		}};
		WATER_ELEMENT_FACTORY = new ElementFactory("WATER_ELEMENT_FACTORY") {{
			consumeItems(ItemStack.with(ST_ITEM.ANTIMATTER, 2, Items.titanium, 1));
			outputItem = new ItemStack(ST_ITEM.WATER_ELEMENT, 1);
			consumeLiquid(Liquids.water, 1);
			inject(this, 3);
		}};
		FIRE_ELEMENT_FACTORY = new ElementFactory("FIRE_ELEMENT_FACTORY") {{
			consumeItems(ItemStack.with(ST_ITEM.ANTIMATTER, 2, Items.thorium, 1));
			outputItem = new ItemStack(ST_ITEM.FIRE_ELEMENT, 1);
			consumeLiquid(Liquids.slag, 1);
			inject(this, 3);
		}};
		EARTH_ELEMENT_FACTORY = new ElementFactory("EARTH_ELEMENT_FACTORY") {{
			consumeItems(ItemStack.with(ST_ITEM.ANTIMATTER, 2, Items.sand, 1));
			outputItem = new ItemStack(ST_ITEM.EARTH_ELEMENT, 1);
			consumeLiquid(Liquids.oil, 1);
			inject(this, 3);
		}};
		NANO_FLUID_MIXER = new GenericCrafter("NANO_FLUID_MIXER") {{
			size = 5;
			craftTime = 30;
			craftEffect = Fx.smeltsmoke;
			itemCapacity = 12;
			hasPower = true;
			consumePower(640000 / 60f);
			requirements = ItemStack.with(
				ST_ITEM.CHROMAL, 300,
				ST_ITEM.NANOTUBE, 50,
				ST_ITEM.SUSPENDED, 50,
				ST_ITEM.SUPERCONDUCTOR, 100,
				ST_ITEM.METRYSTAl, 50,
				ST_ITEM.ANTIMATTER, 50
			);
			consumeItems(with(
				ST_ITEM.GOLD_ELEMENT, 1,
				ST_ITEM.WOOD_ELEMENT, 1,
				ST_ITEM.WATER_ELEMENT, 1,
				ST_ITEM.FIRE_ELEMENT, 1,
				ST_ITEM.EARTH_ELEMENT, 1
			));
			outputLiquid = new LiquidStack(ST_LIQUID.NANO_FLUID, 1);
			inject(this, 3);
		}};
		DARK_ELEMENT_FACTORY = new GenericCrafter("DARK_ELEMENT_FACTORY") {{
			size = 8;
			craftTime = 60 * 4f;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(8.65f * 1000f * 1000f / 8f / 60f);
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 250,
				ST_ITEM.CHROMAL, 150,
				ST_ITEM.SUPERCONDUCTOR, 50,
				ST_ITEM.METRYSTAl, 50,
				ST_ITEM.ANTIMATTER, 50,
				ST_ITEM.SUSPENDED, 50
			);
			outputItem = new ItemStack(ST_ITEM.DARK_ELEMENT, 1);
			consumeLiquid(ST_LIQUID.NANO_FLUID, 1 / 4f);
			inject(this, 3);
		}};
		LIGHT_ELEMENT_FACTORY = new GenericCrafter("LIGHT_ELEMENT_FACTORY") {{
			size = 8;
			craftTime = 60 * 4f;
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumePower(8.65f * 1000f * 1000f / 8f / 60f);
			requirements = ItemStack.with(
				ST_ITEM.CHROMAL, 250,
				ST_ITEM.SUPERCONDUCTOR, 150,
				ST_ITEM.METRYSTAl, 100,
				ST_ITEM.ANTIMATTER, 100
			);
			outputItem = new ItemStack(ST_ITEM.LIGHT_ELEMENT, 1);
			consumeLiquid(ST_LIQUID.NANO_FLUID, 1 / 4f);
			inject(this, 3);
		}};
	}
}
