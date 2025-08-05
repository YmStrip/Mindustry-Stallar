package st.mod.distribution;


import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import st.mod.UTIL_TOOLTIP;
import st.mod.distribution.entity.BlockIO;
import st.mod.distribution.item.BlockIOItem;
import st.mod.distribution.liquid.BlockIOLiquid;
import st.mod.distribution.liquid.BlockLiquidBuffer;
import st.mod.util.STooltipBuilder;
import st.mod.item.ST_ITEM;

public class ST_DISTRIBUTION {
	public static STooltipBuilder inject(Block t) {
		return inject(t, 1);
	}
	public static STooltipBuilder inject(Block t, int level) {
		var builder = UTIL_TOOLTIP.tooltip(t.stats).techLevel(level);
		if (t instanceof BlockIO<?> io) {
			if (io.canInput) builder.show("input_speed", io.speedInput);
			if (io.canOutput) builder.show("output_speed", io.speedOutput);
		}
		if (t instanceof BlockLiquidBuffer buffer) {
			builder.show("max_place", buffer.maxPlace);
		}
		return builder;
	}
	public static BlockLiquidBuffer LIQUID_BUFFER;
	public static BlockLiquidBuffer LIQUID_BUFFER_EXTRA;
	//
	public static BlockIOItem ITEM_INPUT;
	public static BlockIOItem ITEM_OUTPUT;
	public static BlockIOItem ITEM_IO;
	public static BlockIOItem ITEM_INPUT_EXTRA;
	public static BlockIOItem ITEM_OUTPUT_EXTRA;
	public static BlockIOItem ITEM_IO_EXTRA;
	//
	public static BlockIOLiquid LIQUID_INPUT;
	public static BlockIOLiquid LIQUID_OUTPUT;
	public static BlockIOLiquid LIQUID_IO;
	public static BlockIOLiquid LIQUID_INPUT_EXTRA;
	public static BlockIOLiquid LIQUID_OUTPUT_EXTRA;
	public static BlockIOLiquid LIQUID_IO_EXTRA;
	public static void load() {
		//ts: o(maxPlace * n + c) + s(c)
		LIQUID_BUFFER = new BlockLiquidBuffer("LIQUID_BUFFER") {{
			requirements(Category.liquid, ItemStack.with(
				ST_ITEM.CHROMAL, 150,
				ST_ITEM.ANTIMATTER, 5,
				ST_ITEM.METRYSTAl, 25
			));
			size = 2;
			maxPlace = 4;
			liquidCapacity = 3600;
			inject(this, 2);
		}};
		LIQUID_BUFFER_EXTRA = new BlockLiquidBuffer("LIQUID_BUFFER_EXTRA") {{
			requirements(Category.liquid, ItemStack.with(
				ST_ITEM.CHROMAL, 250,
				ST_ITEM.ANTIMATTER, 15,
				ST_ITEM.METRYSTAl, 75
			));
			size = 3;
			maxPlace = 4;
			liquidCapacity = 15000;
			inject(this, 3);
		}};
		//
		ITEM_INPUT = new BlockIOItem("ITEM_INPUT") {{
			canInput = true;
			speedInput = 8;
			requirements(Category.distribution, ItemStack.with(
				ST_ITEM.CHROMAL, 1,
				ST_ITEM.ANTIMATTER, 1
			));
			inject(this, 2);
		}};
		ITEM_OUTPUT = new BlockIOItem("ITEM_OUTPUT") {{
			canOutput = true;
			speedOutput = 8;
			canSelect = true;
			requirements(Category.distribution, ItemStack.with(
				ST_ITEM.CHROMAL, 1,
				ST_ITEM.ANTIMATTER, 1
			));
			inject(this, 2);
		}};
		ITEM_IO = new BlockIOItem("ITEM_IO") {{
			canOutput = true;
			canInput = true;
			speedInput = 9;
			speedOutput = 9;
			canSelect = true;
			requirements(Category.distribution, ItemStack.with(
				ST_ITEM.CHROMAL, 3,
				ST_ITEM.ANTIMATTER, 3
			));
			inject(this, 2);
		}};
		//
		ITEM_INPUT_EXTRA = new BlockIOItem("ITEM_INPUT_EXTRA") {{
			speedInput = 72;
			canInput = true;
			requirements(Category.distribution, ItemStack.with(
				ST_ITEM.DARK_ELEMENT, 1,
				ST_ITEM.LIGHT_ELEMENT, 1,
				ST_ITEM.CHROMAL, 1
			));
			inject(this, 3);
		}};
		ITEM_OUTPUT_EXTRA = new BlockIOItem("ITEM_OUTPUT_EXTRA") {{
			canInput = true;
			canSelect = true;
			speedOutput = 72;
			requirements(Category.distribution, ItemStack.with(
				ST_ITEM.DARK_ELEMENT, 1,
				ST_ITEM.LIGHT_ELEMENT, 1,
				ST_ITEM.CHROMAL, 1
			));
			inject(this, 3);
		}};
		ITEM_IO_EXTRA = new BlockIOItem("ITEM_IO_EXTRA") {{
			canOutput = true;
			canInput = true;
			speedInput = 80;
			speedOutput = 80;
			canSelect = true;
			requirements(Category.distribution, ItemStack.with(
				ST_ITEM.DARK_ELEMENT, 3,
				ST_ITEM.LIGHT_ELEMENT, 3,
				ST_ITEM.CHROMAL, 3
			));
			inject(this, 3);
		}};
		//
		LIQUID_INPUT = new BlockIOLiquid("LIQUID_INPUT") {{
			canInput = true;
			speedInput = 60;
			requirements(Category.liquid, ItemStack.with(
				ST_ITEM.ANTIMATTER, 1,
				ST_ITEM.CHROMAL, 1
			));
			inject(this, 2);
		}};
		LIQUID_OUTPUT = new BlockIOLiquid("LIQUID_OUTPUT") {{
			canOutput = true;
			canSelect = true;
			speedOutput = 60;
			requirements(Category.liquid, ItemStack.with(
				ST_ITEM.ANTIMATTER, 1,
				ST_ITEM.CHROMAL, 1
			));
			inject(this, 2);
		}};
		LIQUID_IO = new BlockIOLiquid("LIQUID_IO") {{
			canInput = true;
			canOutput = true;
			canSelect = true;
			speedInput = 60;
			speedOutput = 60;
			requirements(Category.liquid, ItemStack.with(
				ST_ITEM.ANTIMATTER, 3,
				ST_ITEM.CHROMAL, 3
			));
			inject(this, 2);
		}};
		//
		LIQUID_INPUT_EXTRA = new BlockIOLiquid("LIQUID_INPUT_EXTRA") {{
			canInput = true;
			speedInput = 540;
			requirements(Category.liquid, ItemStack.with(
				ST_ITEM.LIGHT_ELEMENT, 1,
				ST_ITEM.DARK_ELEMENT, 1,
				ST_ITEM.CHROMAL, 1
			));
			inject(this, 3);
		}};
		LIQUID_OUTPUT_EXTRA = new BlockIOLiquid("LIQUID_OUTPUT_EXTRA") {{
			canOutput = true;
			canSelect = true;
			speedOutput = 540;
			requirements(Category.liquid, ItemStack.with(
				ST_ITEM.LIGHT_ELEMENT, 1,
				ST_ITEM.DARK_ELEMENT, 1,
				ST_ITEM.CHROMAL, 1
			));
			inject(this, 3);
		}};
		LIQUID_IO_EXTRA = new BlockIOLiquid("LIQUID_IO_EXTRA") {{
			canInput = true;
			canOutput = true;
			canSelect = true;
			speedInput = 600;
			speedOutput = 600;
			requirements(Category.liquid, ItemStack.with(
				ST_ITEM.LIGHT_ELEMENT, 3,
				ST_ITEM.DARK_ELEMENT, 3,
				ST_ITEM.CHROMAL, 3
			));
			inject(this, 3);
		}};
	}
}
