package st.mod.functional;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.MendProjector;
import mindustry.world.blocks.defense.OverdriveProjector;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.meta.BuildVisibility;
import st.mod.UtilTooltip;
import st.mod.util.STooltipBuilder;
import st.mod.functional.entity.SForceProjector;
import st.mod.functional.entity.SCore;


public class STFunctional {

	public static STooltipBuilder inject(Block b, int level) {
		b.buildVisibility = BuildVisibility.shown;
		b.category = Category.effect;
		var tool = UtilTooltip
			.Tooltip(b.stats)
			.TechLevel(level);
		if (b instanceof SCore core) {
			tool.Add("max_place", core.maxCore);
		}
		return tool;
	}

	public static STooltipBuilder inject(Block b) {
		return inject(b, 1);
	}
	//[T1]
	public static CoreBlock OUTPOST_CORE;
	public static CoreBlock NANO_CORE;
	//[T2]
	public static CoreBlock MATRIX_CORE;
	public static MendProjector MATRIX_MEND_PROJECTOR;
	public static OverdriveProjector MATRIX_OVERDRIVE_PROJECTOR;
	public static SForceProjector MATRIX_FORCE_FIELD;
	//[T3]
	public static CoreBlock AETHER_CORE;
	public static StorageBlock BLACK_HOLE_DRIVE;
	public static OverdriveProjector SUPERCRITICAL_PROJECTOR;
	public static void load() {/*
		OUTPOST_CORE = new SCore("OUTPOST_CORE") {{
			maxCore = 4;
			size = 2;
			isFirstTier = true;
			canBreak = true;
			unitCapModifier = 1;
			itemCapacity = 250;
			researchCostMultiplier = 0.15f;
			requirements = ItemStack.with(
				Items.silicon, 250,
				Items.copper, 500,
				Items.lead, 500
			);
			inject(this, 1);
		}};
		NANO_CORE = new SCore("NANO_CORE") {{
			alwaysUnlocked = false;
			isFirstTier = false;
			replaceable = false;
			itemCapacity = 6000;
			unitCapModifier = 3;
			maxCore = 4;
			size = 3;
			researchCostMultiplier = 0.25f;
			requirements = ItemStack.with(
				STItem.Nanotube, 1000,
				STItem.Superconductor, 100,
				Items.silicon, 500
			);
			unitType = STUnit.MATRIX_BUILDING_UNIT;
			consumePowerBuffered(12);
			inject(this, 1);
		}};
		MATRIX_CORE = new SCore("MATRIX_CORE") {{
			alwaysUnlocked = false;
			isFirstTier = false;
			replaceable = false;
			itemCapacity = 12000;
			maxCore = 6;
			unitCapModifier = 3;
			researchCostMultiplier = 0.2f;
			size = 4;
			requirements = ItemStack.with(
				STItem.Nanotube, 500,
				STItem.Superconductor, 250,
				STItem.Chromal, 150,
				STItem.Suspended, 250,
				STItem.Metrystal, 150
			);
			unitType = STUnit.MATRIX_BUILDING_UNIT_EXTRA;
			consumePowerBuffered(32);
			inject(this, 2);
		}};
		AETHER_CORE = new SCore("AETHER_CORE") {{
			alwaysUnlocked = false;
			isFirstTier = false;
			itemCapacity = 12000;
			unitCapModifier = 3;
			replaceable = false;
			hasPower = true;
			size = 5;
			maxCore = 8;
			researchCostMultiplier = 0.2f;
			requirements = ItemStack.with(
				STItem.Chromal, 1000,
				STItem.LIGHT_ELEMENT, 50,
				STItem.Darkmatter, 50,
				STItem.Antimatter, 50
			);
			unitType = STUnit.ZETA;
			consumePowerBuffered(128);
			inject(this, 3);
		}};*/
		/*NANO_MEND_PROJECTOR = new MendProjector("NANO_MEND_PROJECTOR") {{
			consumePower(4f);
			reload = 180f;
			range = 24 * 8f;
			healPercent = 15f;
			phaseBoost = 5f;
			size = 2;
			phaseRangeBoost = 5 * 8f;
			consumeItem(Items.phaseFabric).boost();
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 150,
				ST_ITEM.SUPERCONDUCTOR, 25,
				Items.silicon, 50,
				Items.plastanium, 50
			);
			inject(this, 1);
		}};
		NANO_OVERDRIVE_PROJECTOR = new OverdriveProjector("NANO_OVERDRIVE_PROJECTOR") {{
			consumePower(10f);
			range = 24 * 8f;
			size = 2;
			speedBoostPhase = 0.5f;
			speedBoost = 2f;
			hasBoost = true;
			phaseRangeBoost = 5 * 8f;
			useTime = 300f;
			consumeItem(Items.phaseFabric).boost();
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 150,
				ST_ITEM.SUPERCONDUCTOR, 25,
				Items.silicon, 50,
				Items.plastanium, 50
			);
			inject(this, 1);
		}};*/
		//[T2]
		/*NANO_STORAGE = new StorageBlock("NANO_STORAGE") {{
			size = 1;
			itemCapacity = 3000;
			scaledHealth = 55;
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 100,
				Items.titanium, 50
			);
			inject(this, 1);
		}};*/
		/*MATRIX_MEND_PROJECTOR = new MendProjector("MATRIX_MEND_PROJECTOR") {{
			consumePower(800f / 60f);
			reload = 180f;
			range = 28 * 8f;
			healPercent = 15f;
			phaseBoost = 3f;
			size = 2;
			phaseRangeBoost = 5 * 8f;
			consumeItem(Items.phaseFabric).boost();
			requirements = ItemStack.with(
				STItem.Nanotube, 150,
				STItem.Superconductor, 50,
				STItem.Metrystal, 50,
				STItem.Suspended, 100
			);
			consumeItem(STItem.Antimatter).boost();
			inject(this, 2);
		}};
		MATRIX_OVERDRIVE_PROJECTOR = new OverdriveProjector("MATRIX_OVERDRIVE_PROJECTOR") {{
			consumePower(1200f / 60f);
			range = 28 * 8f;
			size = 2;
			speedBoostPhase = 0.5f;
			phaseRangeBoost = 5 * 8f;
			speedBoost = 3f;
			hasBoost = true;
			useTime = 300f;
			consumeItem(Items.phaseFabric).boost();
			requirements = ItemStack.with(
				STItem.Nanotube, 250,
				STItem.Chromal, 50,
				STItem.Superconductor, 25,
				STItem.Metrystal, 25,
				STItem.Antimatter, 5
			);
			consumeItem(STItem.Antimatter).boost();
			inject(this, 2);
		}};
		MATRIX_FORCE_FIELD = new SForceProjector("MATRIX_FORCE_FIELD") {{
			consumePower(800f / 60f);
			canOverdrive = false;
			size = 2;
			radius = 20 * 8;
			sides = 6;
			cooldownNormal = 0.5f;
			shieldHealth = 72000f;
			backColor = Color.cyan;
			category = Category.effect;
			phaseRadiusBoost = 4 * 8;
			phaseShieldBoost = 12000;
			requirements = ItemStack.with(
				STItem.Nanotube, 200,
				STItem.Chromal, 150,
				STItem.Superconductor, 25,
				STItem.Metrystal, 25,
				STItem.Suspended, 50,
				STItem.Antimatter, 5
			);
			inject(this, 1);
		}};
		BLACK_HOLE_DRIVE = new StorageBlock("BLACK_HOLE_DRIVE") {{
			size = 1;
			itemCapacity = 64000;
			scaledHealth = 55;
			requirements = ItemStack.with(
				STItem.Chromal, 250,
				STItem.Superconductor, 150,
				STItem.Metrystal, 50,
				STItem.Darkmatter, 5
			);
			inject(this, 3);
		}};
		SUPERCRITICAL_PROJECTOR = new OverdriveProjector("SUPERCRITICAL_PROJECTOR") {{
			consumePower(15f);
			range = 28 * 8f;
			size = 2;
			speedBoostPhase = 0.5f;
			speedBoost = 4.5f;
			hasBoost = true;
			phaseRangeBoost = 4f * 8f;
			useTime = 3000f;
			requirements = ItemStack.with(
				STItem.Chromal, 350,
				STItem.Superconductor, 150,
				STItem.Metrystal, 50,
				STItem.Darkmatter, 5,
				STItem.LIGHT_ELEMENT, 5
			);
			consumeItem(STItem.Antimatter).boost();
			inject(this, 3);
		}};*/
	}
}
