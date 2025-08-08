package st.mod.power;


import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.power.*;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawPlasma;
import mindustry.world.draw.DrawWarmupRegion;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BuildVisibility;
import st.mod.content.ST_ATTRIBUTE;
import st.mod.item.ST_ITEM;
import st.mod.UTIL_TOOLTIP;

public class ST_POWER {
	public static void inject(Block b) {
		inject(b, 1);
	}
	public static void inject(Block b, int level) {
		b.category = Category.power;
		b.buildVisibility = BuildVisibility.shown;
		UTIL_TOOLTIP
			.tooltip(b.stats)
			.techLevel(level);
	}
	//[T1]
	public static ConsumeGenerator BIOMASS_GENERATOR;
	public static ConsumeGenerator OIL_GENERATOR;
	public static ConsumeGenerator PHASE_FABRIC_GENERATOR;
	public static ThermalGenerator HYDROPOWER_STATION;
	public static BeamNode BEAM_NODE;
	//[T2]
	public static ConsumeGenerator ANTIMATTER_GENERATOR;
	public static ImpactReactor WATER_FUSION_GENERATOR;
	// 100k / per
	public static ConsumeGenerator GRAVITY_GENERATOR;
	public static BeamNode BEAM_NODE_EXTRA;
	//[T3]
	public static ImpactReactor ELEMENT_REACTOR;
	public static ThermalGenerator VACUUM_MATRIX;
	public static Battery VACUUM_BATTERY;
	public static void load() {
		BIOMASS_GENERATOR = new ConsumeGenerator("BIOMASS_GENERATOR") {{
			requirements(Category.power, ItemStack.with(Items.copper, 100, Items.lead, 120, Items.silicon, 50));
			size = 2;
			powerProduction = 240 / 60f;
			itemDuration = 90f;
			ambientSound = Sounds.smelter;
			ambientSoundVolume = 0.03f;
			generateEffect = Fx.generatespark;
			consumeItem(Items.sporePod);
			drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
			inject(this, 1);
		}};
		OIL_GENERATOR = new ConsumeGenerator("OIL_GENERATOR") {{
			requirements(Category.power, ItemStack.with(Items.copper, 300, Items.graphite, 300, Items.lead, 150, Items.metaglass, 150, Items.titanium, 125, Items.silicon, 150, Items.plastanium, 150));
			size = 2;
			powerProduction = 720 / 60f;
			ambientSound = Sounds.smelter;
			ambientSoundVolume = 0.03f;
			generateEffect = Fx.generatespark;
			consumeLiquid(Liquids.oil, 0.1f);
			drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
			inject(this, 1);
		}};
		PHASE_FABRIC_GENERATOR = new ConsumeGenerator("PHASE_FABRIC_GENERATOR") {{
			requirements(Category.power, ItemStack.with(Items.lead, 250, Items.metaglass, 50, Items.graphite, 100, Items.silicon, 50));
			size = 2;
			powerProduction = 1800 / 60f;
			itemDuration = 180f;
			ambientSound = Sounds.smelter;
			ambientSoundVolume = 0.03f;
			generateEffect = Fx.generatespark;
			consumeItem(Items.phaseFabric);
			consumeLiquid(Liquids.cryofluid, 6 / 16f);
			drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
			inject(this, 1);
		}};
		HYDROPOWER_STATION = new ThermalGenerator("HYDROPOWER_STATION") {{
			requirements(Category.power, ItemStack.with(
				Items.copper, 500,
				Items.lead, 350,
				Items.titanium, 250,
				Items.silicon, 250,
				Items.graphite, 250,
				Items.metaglass, 200
			));
			powerProduction = 180 / 60f;
			generateEffect = Fx.redgeneratespark;
			effectChance = 0.011f;
			size = 2;
			floating = true;
			ambientSound = Sounds.hum;
			ambientSoundVolume = 0.06f;
			attribute = Attribute.water;
			inject(this, 1);
		}};
		BEAM_NODE = new BeamNode("BEAM_NODE") {{
			size = 1;
			laserColor2 = Color.yellow;
			consumesPower = outputsPower = true;
			range = 24;
			fogRadius = 5;
			consumePowerBuffered(4000f);
			requirements(Category.power, ItemStack.with(Items.metaglass, 15, Items.copper, 15));
			inject(this, 1);
		}};
		//[T2]
		ANTIMATTER_GENERATOR = new ConsumeGenerator("ANTIMATTER_GENERATOR") {{
			//lost=4000
			health = 1500;
			size = 3;
			powerProduction = 4800 / 60f;
			itemDuration = 60f * 8;
			ambientSound = Sounds.pulse;
			ambientSoundVolume = 0.07f;
			//consumePower(60 / 60f);
			//consumeLiquid(Liquids.water, 0.25f);
			drawer = new DrawMulti(new DrawPlasma(), new DrawDefault(), new DrawWarmupRegion());
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 100,
				ST_ITEM.SUPERCONDUCTOR, 50
			);
			consumeItem(ST_ITEM.ANTIMATTER);
			inject(this, 2);
		}};
		WATER_FUSION_GENERATOR = new ImpactReactor("WATER_FUSION_GENERATOR") {{
			health = 1500;
			canOverdrive = false;
			size = 4;
			powerProduction = 128000 / 60f;
			ambientSound = Sounds.pulse;
			ambientSoundVolume = 0.07f;
			consumePower(2400 / 60f);
			consumeLiquid(Liquids.water, 180 / 60f);
			drawer = new DrawMulti(new DrawPlasma(), new DrawDefault(), new DrawWarmupRegion());
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 250,
				ST_ITEM.SUPERCONDUCTOR, 300,
				ST_ITEM.CHROMAL, 450,
				ST_ITEM.SUSPENDED, 250,
				ST_ITEM.METRYSTAl, 250,
				Items.metaglass, 250,
				Items.graphite, 500
			);
			inject(this, 2);
		}};
		// 24k / per
		GRAVITY_GENERATOR = new ConsumeGenerator("GRAVITY_GENERATOR") {{
			health = 6000;
			size = 2;
			powerProduction = 5714.24f / 60f;
			itemDuration = 60 * 4.5f;
			ambientSound = Sounds.smelter;
			ambientSoundVolume = 0.03f;
			generateEffect = Fx.generatespark;
			drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 150,
				ST_ITEM.SUPERCONDUCTOR, 50,
				ST_ITEM.CHROMAL, 100,
				ST_ITEM.SUSPENDED, 50
			);
			consumeItem(ST_ITEM.SUSPENDED, 1);
			inject(this, 2);
		}};
		BEAM_NODE_EXTRA = new BeamNode("BEAM_NODE_EXTRA") {{
			health = 1500;
			size = 1;
			laserColor2 = Color.red;
			consumesPower = outputsPower = true;
			range = 72;
			fogRadius = 5;
			consumePowerBuffered(6000f);
			requirements(Category.power, ItemStack.with(ST_ITEM.CHROMAL, 15, ST_ITEM.SUPERCONDUCTOR, 15));
			inject(this, 2);
		}};
		//[T3]
		ELEMENT_REACTOR = new ImpactReactor("ELEMENT_REACTOR") {{
			health = 6000;
			size = 4;
			powerProduction = 180000 * 5 / 60f;
			consumePower(1024 / 60f);
			itemDuration = 60f * 12;
			ambientSound = Sounds.smelter;
			ambientSoundVolume = 0.03f;
			drawer = new DrawMulti(new DrawPlasma(), new DrawDefault(), new DrawWarmupRegion());
			requirements = ItemStack.with(
				ST_ITEM.CHROMAL, 300,
				ST_ITEM.NANOTUBE, 150,
				ST_ITEM.SUPERCONDUCTOR, 100,
				ST_ITEM.SUSPENDED, 150,
				ST_ITEM.ANTIMATTER, 25
			);
			consumeItem(ST_ITEM.GOLD_ELEMENT, 1);
			consumeItem(ST_ITEM.WOOD_ELEMENT, 1);
			consumeItem(ST_ITEM.WATER_ELEMENT, 1);
			consumeItem(ST_ITEM.FIRE_ELEMENT, 1);
			consumeItem(ST_ITEM.EARTH_ELEMENT, 1);
			inject(this, 3);
		}};
		VACUUM_MATRIX = new ThermalGenerator("VACUUM_MATRIX") {{
			health = 6000;
			canOverdrive = false;
			powerProduction = 256000 / 60f;
			generateEffect = Fx.redgeneratespark;
			effectChance = 0.011f;
			size = 1;
			floating = true;
			ambientSound = Sounds.hum;
			ambientSoundVolume = 0.06f;
			requirements = ItemStack.with(
				ST_ITEM.LIGHT_ELEMENT, 10,
				ST_ITEM.DARK_ELEMENT, 10,
				ST_ITEM.ANTIMATTER, 50,
				ST_ITEM.CHROMAL, 250
			);
			attribute = ST_ATTRIBUTE.WATER;
			inject(this, 3);
		}};
		VACUUM_BATTERY = new Battery("VACUUM_BATTERY") {{
			health = 10000;
			size = 1;
			consumePowerBuffered(500000000f);
			baseExplosiveness = 0f;
			requirements = ItemStack.with(
				ST_ITEM.LIGHT_ELEMENT, 1,
				ST_ITEM.DARK_ELEMENT, 1,
				ST_ITEM.ANTIMATTER, 5,
				ST_ITEM.CHROMAL, 50
			);
			inject(this, 3);
		}};
	}
}
