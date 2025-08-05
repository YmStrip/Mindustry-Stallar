package st.mod.qio;


import arc.graphics.Color;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import st.mod.UTIL_TOOLTIP;
import st.mod.item.ST_ITEM;
import st.mod.qio.drive.QIODrive;
import st.mod.qio.block.*;
import st.mod.util.STooltipBuilder;

public class ST_QIO {
	public static STooltipBuilder inject(Block t) {
		return inject(t, 1);
	}
	public static STooltipBuilder inject(Block t, int level) {
		var builder = UTIL_TOOLTIP.tooltip(t.stats).techLevel(level);
		if (t instanceof BlockQIOItemInterface io) {
			if (io.canInput) builder.show("input_speed", io.speedInput);
			if (io.canOutput) builder.show("output_speed", io.speedOutput);
			builder.show("max_place", io.maxPlace);
		}
		if (t instanceof BlockQIOLiquidInterface io) {
			if (io.canInput) builder.show("input_speed", io.speedInput);
			if (io.canOutput) builder.show("output_speed", io.speedOutput);
			builder.show("max_place", io.maxPlace);
		}
		if (t instanceof BlockQIOUnitInterface io) {
			if (io.canInput) builder.show("input_speed", io.speedInput);
			if (io.canOutput) builder.show("output_speed", io.speedOutput);
			builder.show("max_place", io.maxPlace);
		}
		return builder;
	}
	public static STooltipBuilder inject(QIODrive t, int level) {
		var builder = UTIL_TOOLTIP.tooltip(t.stats).techLevel(level);
		builder.show("ITEM_CAPACITY", t.itemCapacity);
		builder.show("LIQUID_CAPACITY", t.liquidCapacity);
		builder.show("UNIT_CAPACITY", t.unitCapacity);
		builder.show("PAYLOAD_CAPACITY", t.payloadCapacity);
		return builder;
	}
	public static BlockQIOItemInterface QIO_ITEM_INTERFACE;
	public static BlockQIOLiquidInterface QIO_LIQUID_INTERFACE;
	public static BlockQIOUnitInterface QIO_UNIT_INTERFACE;
	public static BlockQIOPane QIO_PANE;
	public static QIODrive QIO_DRIVE;
	public static QIODrive QIO_DRIVE_EXTRA;
	public static void load() {
		QIO_ITEM_INTERFACE = new BlockQIOItemInterface("QIO_ITEM_INTERFACE") {{
			requirements(Category.distribution, ItemStack.with(
				ST_ITEM.CHROMAL, 300,
				ST_ITEM.SUPERCONDUCTOR, 150,
				ST_ITEM.METRYSTAl, 100,
				ST_ITEM.LIGHT_ELEMENT, 25,
				ST_ITEM.DARK_ELEMENT, 25,
				ST_ITEM.ANTIMATTER, 100
			));
			canOverdrive = false;
			destroyItem = false;
			color = Color.orange;
			size = 5;
			canInput = true;
			canOutput = true;
			canSelect = true;
			maxPlace = 8;
			speedInput = 1024;
			speedOutput = 512;
			consumePower(16000 / 60f);
			inject(this, 3);
		}};
		QIO_LIQUID_INTERFACE = new BlockQIOLiquidInterface("QIO_LIQUID_INTERFACE") {{
			requirements(Category.liquid, ItemStack.with(
				ST_ITEM.CHROMAL, 300,
				ST_ITEM.SUPERCONDUCTOR, 150,
				ST_ITEM.METRYSTAl, 100,
				ST_ITEM.LIGHT_ELEMENT, 25,
				ST_ITEM.DARK_ELEMENT, 25,
				ST_ITEM.ANTIMATTER, 100
			));
			canOverdrive = false;
			color = Color.cyan;
			size = 5;
			canInput = true;
			canOutput = true;
			canSelect = true;
			maxPlace = 8;
			speedInput = 256 * 60;
			speedOutput = 128 * 60;
			consumePower(16000 / 60f);
			inject(this, 3);
		}};
		QIO_UNIT_INTERFACE = new BlockQIOUnitInterface("QIO_UNIT_INTERFACE") {{
			requirements(Category.units, ItemStack.with(
				ST_ITEM.CHROMAL, 500,
				ST_ITEM.SUPERCONDUCTOR, 250,
				ST_ITEM.SUSPENDED, 450,
				ST_ITEM.METRYSTAl, 150,
				ST_ITEM.LIGHT_ELEMENT, 50,
				ST_ITEM.DARK_ELEMENT, 50,
				ST_ITEM.ANTIMATTER, 250
			));
			canOverdrive = false;
			color = Color.green;
			size = 12;
			canInput = true;
			canOutput = true;
			canSelect = true;
			maxPlace = 8;
			speedInput = 4 / 60f;
			speedOutput = 4 / 60f;
			consumePower(64000 / 60f);
			inject(this, 3);
		}};
		QIO_PANE = new BlockQIOPane("QIO_PANE") {{
			requirements(Category.logic, ItemStack.with(
				ST_ITEM.ANTIMATTER, 1,
				ST_ITEM.METRYSTAl, 1
			));
			size = 2;
			consumePower(60 / 60f);
			inject(this, 3);
		}};
		QIO_DRIVE = new QIODrive("QIO_DRIVE") {
			@Override
			public ItemStack[] researchRequirements() {
				return ItemStack.with(
					ST_ITEM.ANTIMATTER, 1000,
					ST_ITEM.SUPERCONDUCTOR, 250,
					ST_ITEM.METRYSTAl, 250
				);
			}
			{
				itemCapacity = 32 * 1000;
				liquidCapacity = 256 * 1000;
				payloadCapacity = 8;
				unitCapacity = 8;
				inject(this, 3);
			}
		};
		QIO_DRIVE_EXTRA = new QIODrive("QIO_DRIVE_EXTRA") {
			@Override
			public ItemStack[] researchRequirements() {
				return ItemStack.with(
					ST_ITEM.ANTIMATTER, 16000,
					ST_ITEM.SUPERCONDUCTOR, 2500,
					ST_ITEM.METRYSTAl, 2500
				);
			}
			{
				itemCapacity = 3600 * 1000;
				liquidCapacity = 7200 * 1000;
				payloadCapacity = 16;
				unitCapacity = 16;
				inject(this, 3);
			}
		};
	}
}
