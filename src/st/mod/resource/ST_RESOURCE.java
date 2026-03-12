package st.mod.resource;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.BuildVisibility;
import st.mod.UtilTooltip;
import st.mod.content.*;
import st.mod.util.STooltipBuilder;
import st.mod.item.STItem;
import st.mod.content.STLiquid;
import st.mod.resource.entity.SMiner;


public class ST_RESOURCE {
	/*
	public static STooltipBuilder inject(Block t) {
		t.buildVisibility = BuildVisibility.shown;
		return UtilTooltip.Tooltip(t.stats);
	}

	public static STooltipBuilder inject(Block t, int level) {
		return inject(t).TechLevel(level);
	}
	//[T1]
	public static SMiner MinerShallow, DEEP_MINER, SPECIAL_MINER, CONCENTRATOR;
	//time = 240 / (size * speed)
	public static Drill SUPER_DRILL;
	//泵
	public static Pump SUPER_PUMP;
	public static SolidPump SUPER_SOLID_PUMP, CORE_EXTRACTOR, ATMOSPHERE_EXTRACTOR, OIL_EXTRACTOR;

	//[T2]
	public static Drill IMPACT_DRILL;
	public static Pump IMPACT_PUMP;

	//[T3]
	public static Separator RESOURCE_EXTRACTOR;
	public static GenericCrafter NANO_FACTORY;
	public static Drill MINI_ELEMENT_DRILL;

	public static void load() {
		//[T1]
		MinerShallow = new SMiner("SHALLOW_MINER") {{
			consumeLiquid(Liquids.water, 0.15f);
			consumePower(4f);
			size = 2;
			craftTime = 10;
			liquidCapacity = 250;
			itemCapacity = 250;
			results = ItemStack.with(Items.copper, 1, Items.lead, 1);
			requirements(Category.production, ItemStack.with(
				STItem.Nanotube, 100,
				STItem.Superconductor, 50,
				Items.silicon, 50,
				Items.titanium, 50
			));
			attribute = STAttribute.OreShallow;
			inject(this, 1);
		}};
		//
		DEEP_MINER = new SMiner("DEEP_MINER") {{
			consumeLiquid(Liquids.water, 0.15f);
			consumePower(6f);
			size = 2;
			craftTime = 10;
			liquidCapacity = 250;
			itemCapacity = 250;
			results = ItemStack.with(Items.titanium, 1, Items.thorium, 1);
			requirements(Category.production, ItemStack.with(
				STItem.Chromal, 50,
				STItem.Nanotube, 50,
				STItem.Superconductor, 50,
				Items.thorium, 25,
				Items.phaseFabric, 50
			));
			attribute = STAttribute.OreDeep;
			inject(this, 1);
		}};
		SPECIAL_MINER = new SMiner("SPECIAL_MINER") {{
			consumeLiquid(Liquids.water, 0.15f);
			consumePower(4f);
			size = 2;
			craftTime = 6;
			liquidCapacity = 250;
			itemCapacity = 250;
			results = ItemStack.with(Items.sand, 1, Items.coal, 1);
			requirements(Category.production, ItemStack.with(
				STItem.Nanotube, 50,
				STItem.Superconductor, 50,
				Items.silicon, 50,
				Items.titanium, 50,
				Items.metaglass, 50
			));
			attribute = STAttribute.OreShallow;
			inject(this, 1);
		}};
		CONCENTRATOR = new SMiner("CONCENTRATOR") {{
			consumeLiquid(Liquids.water, 0.5f);
			consumePower(16f);
			size = 2;
			craftTime = 10;
			liquidCapacity = 250;
			itemCapacity = 250;
			requirements(Category.production, ItemStack.with(
				STItem.Nanotube, 150,
				STItem.Superconductor, 50,
				STItem.Chromal, 250,
				STItem.Metrystal, 50,
				Items.plastanium, 150,
				Items.phaseFabric, 100
			));
			results = ItemStack.with(
				Items.metaglass, 2,
				Items.graphite, 2,
				Items.silicon, 2,
				Items.plastanium, 2,
				Items.phaseFabric, 2,
				Items.surgeAlloy, 2
			);
			attribute = STAttribute.Basin;
			inject(this, 1);
		}};

		SUPER_DRILL = new Drill("SUPER_DRILL") {{
			consumePower(3f);
			size = 2;
			tier = 2;
			drillTime = 240 / 3f;
			updateEffect = Fx.pulverizeRed;
			updateEffectChance = 0.03f;
			drillEffect = Fx.mineHuge;
			drawRim = true;
			hasPower = true;
			warmupSpeed = 0.07f;
			requirements(Category.production, ItemStack.with(
				STItem.Nanotube, 100,
				STItem.Superconductor, 25,
				Items.silicon, 50,
				Items.titanium, 50
			));
			inject(this, 1);
		}};

		SUPER_PUMP = new Pump("SUPER_PUMP") {{
			consumePower(6f);
			pumpAmount = 0.25f;
			liquidCapacity = 200f;
			hasPower = true;
			size = 2;
			requirements(Category.liquid, ItemStack.with(
				STItem.Nanotube, 100,
				STItem.Superconductor, 25,
				Items.metaglass, 150,
				Items.titanium, 50
			));
			inject(this, 1);
		}};
		SUPER_SOLID_PUMP = new SolidPump("SUPER_SOLID_PUMP") {{
			size = 2;
			consumePower(6f);
			pumpAmount = 1.25f;
			liquidCapacity = 150f;
			rotateSpeed = 1.4f;
			result = Liquids.water;
			requirements(Category.production, ItemStack.with(
				STItem.Nanotube, 150,
				STItem.Superconductor, 50,
				Items.silicon, 100,
				Items.metaglass, 100,
				Items.titanium, 100,
				Items.plastanium, 50
			));
			inject(this, 1);
		}};
		CORE_EXTRACTOR = new SolidPump("CORE_EXTRACTOR") {{
			size = 2;
			consumePower(8f);
			pumpAmount = 1f;
			liquidCapacity = 150f;
			rotateSpeed = 1.4f;
			result = Liquids.slag;
			requirements(Category.production, ItemStack.with(
				STItem.Nanotube, 175,
				Items.metaglass, 150,
				STItem.Superconductor, 25,
				Items.silicon, 200,
				STItem.Chromal, 50,
				Items.plastanium, 50
			));
			inject(this, 1);
		}};
		ATMOSPHERE_EXTRACTOR = new SolidPump("ATMOSPHERE_EXTRACTOR") {{
			size = 2;
			consumePower(8f);
			pumpAmount = 1f;
			liquidCapacity = 150f;
			rotateSpeed = 1.4f;
			result = Liquids.cryofluid;
			requirements(Category.production, ItemStack.with(
				STItem.Nanotube, 150,
				Items.metaglass, 150,
				STItem.Superconductor, 50,
				Items.silicon, 175,
				Items.titanium, 150,
				Items.plastanium, 100
			));
			inject(this, 1);
		}};
		OIL_EXTRACTOR = new SolidPump("OIL_EXTRACTOR") {{
			size = 2;
			consumePower(960f / 60f);
			pumpAmount = 1f;
			liquidCapacity = 150f;
			rotateSpeed = 1.4f;
			result = Liquids.oil;
			requirements(Category.production, ItemStack.with(
				STItem.Nanotube, 150,
				STItem.Superconductor, 50,
				STItem.Chromal, 50,
				Items.metaglass, 150,
				Items.silicon, 175,
				Items.plastanium, 100
			));
			inject(this, 1);
		}};

		//[T2]
		IMPACT_DRILL = new Drill("IMPACT_DRILL") {{
			consumePower(64f);
			size = 3;
			tier = 6;
			drillTime = 240 / 64f;
			updateEffect = Fx.pulverizeRed;
			updateEffectChance = 0.03f;
			drillEffect = Fx.mineHuge;
			drawRim = true;
			hasPower = true;
			warmupSpeed = 0.005f;
			requirements(Category.production, ItemStack.with(
				STItem.Nanotube, 50,
				STItem.Superconductor, 50,
				STItem.Chromal, 150,
				STItem.Metrystal, 50,
				STItem.Suspended, 50,
				Items.phaseFabric, 50
			));
			inject(this, 2);
		}};
		IMPACT_PUMP = new Pump("IMPACT_PUMP") {{
			consumePower(8f);
			pumpAmount = 1.5f;
			liquidCapacity = 200f;
			hasPower = true;
			size = 2;
			requirements(Category.liquid, ItemStack.with(
				STItem.Nanotube, 50,
				STItem.Superconductor, 50,
				STItem.Chromal, 150,
				STItem.Metrystal, 50,
				STItem.Suspended, 50,
				Items.metaglass, 150
			));
			inject(this, 2);
		}};

		//[T3]
		//2.56m power = 1 prod
		RESOURCE_EXTRACTOR = new SMiner("RESOURCE_EXTRACTOR") {
			{
				consumeLiquid(STLiquid.NanoFluid, 0.1f);
				consumePower(72000f / 60f);
				canOverdrive = true;
				size = 5;
				craftTime = 60 / 45f;
				liquidCapacity = 250;
				itemCapacity = 128;
				//offloadToCore = true;
				attributeAll = true;
				results = ItemStack.with(
					Items.sand, 5,
					Items.coal, 5,
					Items.copper, 4,
					Items.lead, 4,
					Items.scrap, 3,
					Items.thorium, 3,
					Items.titanium, 3,
					Items.silicon, 2,
					Items.sporePod, 2,
					Items.graphite, 2,
					Items.metaglass, 2,
					Items.surgeAlloy, 1,
					Items.phaseFabric, 1,
					Items.blastCompound, 1,
					Items.pyratite, 1,
					Items.plastanium, 1
				);
				researchCostMultiplier = 0.2f;
				requirements(Category.production, ItemStack.with(
					STItem.Chromal, 100,
					STItem.Metrystal, 50,
					STItem.Darkmatter, 1,
					STItem.LIGHT_ELEMENT, 1,
					STItem.Antimatter, 50
				));
				inject(this, 3);
			}
		};
		NANO_FACTORY = new GenericCrafter("NANO_FACTORY") {{
			//consumeLiquid(Liquids.water, 1f);
			consumePower(128000 / 60f);
			canOverdrive = true;
			size = 3;
			craftTime = 60f;
			liquidCapacity = 250;
			itemCapacity = 250;
			craftEffect = Fx.shieldBreak;
			researchCostMultiplier = 0.1f;
			requirements(Category.production, ItemStack.with(
				STItem.Chromal, 250,
				STItem.Superconductor, 50,
				STItem.Metrystal, 50,
				STItem.LIGHT_ELEMENT, 1,
				STItem.Darkmatter, 1,
				STItem.Antimatter, 5
			));
			outputLiquid = new LiquidStack(STLiquid.NanoFluid, 0.1f);
			inject(this, 3);
		}};
		MINI_ELEMENT_DRILL = new Drill("MINI_ELEMENT_DRILL") {{
			consumePower(1200 / 60f);
			size = 1;
			tier = 42;
			drillTime = 240 / (0.5f);
			requirements(Category.production, ItemStack.with(
				STItem.Chromal, 150,
				STItem.Superconductor, 50,
				STItem.Metrystal, 15,
				STItem.Antimatter, 25
			));
			inject(this, 1);
		}};
	}*/
}
