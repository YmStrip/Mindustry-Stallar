package st.mod.functional;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.MendProjector;
import mindustry.world.blocks.defense.OverdriveProjector;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.meta.BuildVisibility;
import st.mod.item.ST_ITEM;
import st.mod.UTIL_TOOLTIP;
import st.mod.util.STooltipBuilder;
import st.mod.functional.entity.SForceProjector;
import st.mod.functional.entity.SCore;
import st.mod.unit.ST_UNIT;


public class ST_FUNCTIONAL {

	public static STooltipBuilder inject(Block b, int level) {
		b.buildVisibility = BuildVisibility.shown;
		b.category = Category.effect;
		var tool = UTIL_TOOLTIP
			.tooltip(b.stats)
			.techLevel(level);
		if (b instanceof SCore core) {
			tool.show("max_place", core.maxCore);
		}
		return tool;
	}

	public static STooltipBuilder inject(Block b) {
		return inject(b, 1);
	}
	//[T1]
	public static CoreBlock OUTPOST_CORE;
	public static CoreBlock NANO_CORE;
	//public static MendProjector NANO_MEND_PROJECTOR;
	//public static OverdriveProjector NANO_OVERDRIVE_PROJECTOR;
	//public static StorageBlock NANO_STORAGE;
	//[T2]
	public static CoreBlock MATRIX_CORE;
	public static MendProjector MATRIX_MEND_PROJECTOR;
	public static OverdriveProjector MATRIX_OVERDRIVE_PROJECTOR;
	public static SForceProjector MATRIX_FORCE_FIELD;
	//[T3]
	public static CoreBlock AETHER_CORE;
	public static StorageBlock BLACK_HOLE_DRIVE;
	public static OverdriveProjector SUPERCRITICAL_PROJECTOR;
	public static void load() {
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
			isFirstTier = true;
			replaceable = false;
			itemCapacity = 6000;
			unitCapModifier = 3;
			maxCore = 4;
			size = 4;
			researchCostMultiplier = 0.25f;
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 1000,
				ST_ITEM.SUPERCONDUCTOR, 100,
				Items.silicon, 500
			);
			unitType = ST_UNIT.MATRIX_BUILDING_UNIT;
			consumePowerBuffered(12);
			inject(this, 1);
		}};
		MATRIX_CORE = new SCore("MATRIX_CORE") {{
			alwaysUnlocked = true;
			isFirstTier = true;
			replaceable = false;
			itemCapacity = 12000;
			maxCore = 6;
			unitCapModifier = 3;
			researchCostMultiplier = 0.2f;
			size = 4;
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 500,
				ST_ITEM.SUPERCONDUCTOR, 250,
				ST_ITEM.CHROMAL, 150,
				ST_ITEM.SUSPENDED, 250,
				ST_ITEM.METRYSTAl, 150
			);
			unitType = ST_UNIT.MATRIX_BUILDING_UNIT_EXTRA;
			consumePowerBuffered(32);
			inject(this, 2);
		}};
		AETHER_CORE = new SCore("AETHER_CORE") {{
			alwaysUnlocked = true;
			isFirstTier = true;
			itemCapacity = 12000;
			unitCapModifier = 3;
			replaceable = false;
			hasPower = true;
			size = 5;
			maxCore = 8;
			researchCostMultiplier = 0.2f;
			requirements = ItemStack.with(
				ST_ITEM.CHROMAL, 1000,
				ST_ITEM.LIGHT_ELEMENT, 50,
				ST_ITEM.DARK_ELEMENT, 50,
				ST_ITEM.ANTIMATTER, 50
			);
			unitType = ST_UNIT.ZETA;
			consumePowerBuffered(128);
			inject(this, 3);
		}};
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
		MATRIX_MEND_PROJECTOR = new MendProjector("MATRIX_MEND_PROJECTOR") {{
			consumePower(10f);
			consumePower(8f);
			reload = 180f;
			range = 28 * 8f;
			healPercent = 15f;
			phaseBoost = 3f;
			size = 2;
			phaseRangeBoost = 5 * 8f;
			consumeItem(Items.phaseFabric).boost();
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 150,
				ST_ITEM.SUPERCONDUCTOR, 50,
				ST_ITEM.METRYSTAl, 50,
				ST_ITEM.SUSPENDED, 100
			);
			consumeItem(ST_ITEM.ANTIMATTER).boost();
			inject(this, 2);
		}};
		MATRIX_OVERDRIVE_PROJECTOR = new OverdriveProjector("MATRIX_OVERDRIVE_PROJECTOR") {{
			consumePower(10f);
			consumePower(8f);
			range = 28 * 8f;
			size = 2;
			speedBoostPhase = 0.5f;
			phaseRangeBoost = 5 * 8f;
			speedBoost = 3f;
			hasBoost = true;
			useTime = 300f;
			consumeItem(Items.phaseFabric).boost();
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 250,
				ST_ITEM.CHROMAL, 50,
				ST_ITEM.SUPERCONDUCTOR, 25,
				ST_ITEM.METRYSTAl, 25,
				ST_ITEM.ANTIMATTER, 5
			);
			consumeItem(ST_ITEM.ANTIMATTER).boost();
			inject(this, 2);
		}};
		MATRIX_FORCE_FIELD = new SForceProjector("MATRIX_FORCE_FIELD") {{
			consumePower(8f);
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
				ST_ITEM.NANOTUBE, 200,
				ST_ITEM.CHROMAL, 150,
				ST_ITEM.SUPERCONDUCTOR, 25,
				ST_ITEM.METRYSTAl, 25,
				ST_ITEM.SUSPENDED, 50,
				ST_ITEM.ANTIMATTER, 5
			);
			inject(this, 1);
		}};
		BLACK_HOLE_DRIVE = new StorageBlock("BLACK_HOLE_DRIVE") {{
			size = 1;
			itemCapacity = 60000;
			scaledHealth = 55;
			requirements = ItemStack.with(
				ST_ITEM.CHROMAL, 250,
				ST_ITEM.SUPERCONDUCTOR, 150,
				ST_ITEM.METRYSTAl, 50,
				ST_ITEM.DARK_ELEMENT, 3
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
				ST_ITEM.CHROMAL, 350,
				ST_ITEM.SUPERCONDUCTOR, 150,
				ST_ITEM.METRYSTAl, 50,
				ST_ITEM.DARK_ELEMENT, 5,
				ST_ITEM.LIGHT_ELEMENT, 5
			);
			consumeItem(ST_ITEM.ANTIMATTER).boost();
			inject(this, 3);
		}};
	}
}
