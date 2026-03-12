package st.mod.distribution;


import arc.Events;
import mindustry.content.Items;
import mindustry.game.EventType;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import st.mod.STTech;
import st.mod.UtilTooltip;
import st.mod.distribution.entity.BlockIO;
import st.mod.distribution.item.BlockIOItem;
import st.mod.distribution.liquid.BlockIOLiquid;
import st.mod.distribution.liquid.BlockLiquidBuffer;
import st.mod.distribution.liquid.SBuildMapLiquidBuffer;
import st.mod.util.STooltipBuilder;
import st.mod.item.STItem;

public class STDistribution {
	public static SBuildMapLiquidBuffer Build = new SBuildMapLiquidBuffer();
	public static STooltipBuilder Inject(Block t) {
		return Inject(t, 1);
	}
	public static STooltipBuilder Inject(Block t, int level) {
		var builder = UtilTooltip.Tooltip(t.stats).TechLevel(level);
		if (t instanceof BlockIO<?> io) {
			if (io.InputAble) builder.Add("input_rate", io.InputRate);
			if (io.OutputAble) builder.Add("output_rate", io.OutputRate);
		}
		if (t instanceof BlockLiquidBuffer buffer) {
			builder.Add("max_place", buffer.maxPlace);
		}
		return builder;
	}
	public static BlockLiquidBuffer LiquidBuffer;
	public static BlockLiquidBuffer LiquidBufferExtra;
	//
	public static BlockIOItem ItemInput;
	public static BlockIOItem ItemOutput;
	public static BlockIOItem ItemIO;
	public static BlockIOItem ItemInputExtra;
	public static BlockIOItem ItemOutputExtra;
	public static BlockIOItem ItemIOExtra;
	//
	public static BlockIOLiquid LiquidInput;
	public static BlockIOLiquid LiquidOutput;
	public static BlockIOLiquid LiquidIO;
	public static BlockIOLiquid LiquidInputExtra;
	public static BlockIOLiquid LiquidOutputExtra;
	public static BlockIOLiquid LiquidIOExtra;
	public static void Init() {
		_initContent();
		_initEvent();
	}
	private static void _initEvent() {
		Events.on(EventType.WorldLoadEndEvent.class, e -> {
			STTech
				.createTechNodeRoot(STDistribution.ItemInput)
				.Add(STDistribution.ItemInputExtra, t -> t
					.Children(STDistribution.ItemOutputExtra)
					.Children(STDistribution.ItemIOExtra)
				)
				.Add(STDistribution.LiquidBuffer, t -> t
					.Add(STDistribution.LiquidBufferExtra)
					.Add(STDistribution.LiquidInputExtra, t1 -> t1
						.Children(STDistribution.LiquidOutputExtra)
						.Children(STDistribution.LiquidIOExtra)
					)
					.Children(STDistribution.LiquidInput)
					.Children(STDistribution.LiquidOutput)
					.Children(STDistribution.LiquidIO)
				)
				.Children(STDistribution.ItemOutput)
				.Children(STDistribution.ItemIO)
			;
		});
	}
	private static void _initContent() {
		LiquidBuffer = new BlockLiquidBuffer("LiquidBuffer") {{
			requirements(Category.liquid, ItemStack.with(
				Items.titanium, 250,
				Items.silicon, 150,
				Items.metaglass, 200,
				Items.phaseFabric, 100,
				Items.copper, 250,
				Items.lead, 100
			));
			size = 2;
			maxPlace = 4;
			liquidCapacity = 800;
			Inject(this, 2);
		}};
		LiquidBufferExtra = new BlockLiquidBuffer("LiquidBufferExtra") {{
			requirements(Category.liquid, ItemStack.with(
				STItem.Chromal, 250,
				STItem.Superconductor, 150,
				STItem.Metrystal, 150,
				STItem.Antimatter, 50,
				Items.metaglass, 250
			));
			size = 3;
			maxPlace = 4;
			liquidCapacity = 1200;
			Inject(this, 3);
		}};
		//
		ItemInput = new BlockIOItem("ItemInput") {{
			InputAble = true;
			InputRate = InputBufferMax = 6;
			requirements(Category.distribution, ItemStack.with(
				STItem.Superconductor, 25,
				Items.silicon, 25,
				Items.phaseFabric, 25
			));
			Inject(this, 2);
		}};
		ItemOutput = new BlockIOItem("ItemOutput") {{
			OutputAble = true;
			OutputRate = OutputBufferMax = 6;
			CanSelect = true;
			requirements(Category.distribution, ItemStack.with(
				STItem.Superconductor, 25,
				Items.silicon, 25,
				Items.phaseFabric, 25
			));
			Inject(this, 2);
		}};
		ItemIO = new BlockIOItem("ItemIO") {{
			OutputAble = true;
			InputAble = true;
			InputRate = InputBufferMax = 8;
			OutputRate = OutputBufferMax = 8;
			CanSelect = true;
			requirements(Category.distribution, ItemStack.with(
				STItem.Superconductor, 60,
				Items.silicon, 60,
				Items.phaseFabric, 60
			));
			Inject(this, 2);
		}};
		//
		ItemInputExtra = new BlockIOItem("ItemInputExtra") {{
			InputRate = InputBufferMax = 40;
			InputAble = true;
			requirements(Category.distribution, ItemStack.with(
				STItem.Metrystal, 25,
				STItem.Antimatter, 25,
				STItem.Chromal, 25
			));
			Inject(this, 3);
		}};
		ItemOutputExtra = new BlockIOItem("ItemOutputExtra") {{
			InputAble = true;
			CanSelect = true;
			OutputRate = OutputBufferMax = 40;
			requirements(Category.distribution, ItemStack.with(
				STItem.Metrystal, 25,
				STItem.Antimatter, 25,
				STItem.Chromal, 25
			));
			Inject(this, 3);
		}};
		ItemIOExtra = new BlockIOItem("ItemIOExtra") {{
			OutputAble = true;
			InputAble = true;
			InputRate = InputBufferMax = 46;
			OutputRate = OutputBufferMax = 46;
			CanSelect = true;
			requirements(Category.distribution, ItemStack.with(
				STItem.Metrystal, 60,
				STItem.Antimatter, 60,
				STItem.Chromal, 60
			));
			Inject(this, 3);
		}};
		//
		LiquidInput = new BlockIOLiquid("LiquidInput") {{
			InputAble = true;
			InputRate = InputBufferMax = 45;
			requirements(Category.liquid, ItemStack.with(
				STItem.Superconductor, 25,
				Items.metaglass, 25,
				Items.phaseFabric, 25
			));
			Inject(this, 2);
		}};
		LiquidOutput = new BlockIOLiquid("LiquidOutput") {{
			OutputAble = true;
			CanSelect = true;
			OutputRate = OutputBufferMax = 45;
			requirements(Category.liquid, ItemStack.with(
				STItem.Superconductor, 25,
				Items.metaglass, 25,
				Items.phaseFabric, 25
			));
			Inject(this, 2);
		}};
		LiquidIO = new BlockIOLiquid("LiquidIO") {{
			InputAble = true;
			OutputAble = true;
			CanSelect = true;
			InputRate = InputBufferMax = 60;
			OutputRate = OutputBufferMax = 60;
			requirements(Category.liquid, ItemStack.with(
				STItem.Superconductor, 60,
				Items.metaglass, 60,
				Items.phaseFabric, 60
			));
			Inject(this, 2);
		}};
		//
		LiquidInputExtra = new BlockIOLiquid("LiquidInputExtra") {{
			InputAble = true;
			InputRate = InputBufferMax = 800;
			requirements(Category.liquid, ItemStack.with(
				STItem.Metrystal, 25,
				Items.metaglass, 25,
				STItem.Antimatter, 25,
				STItem.Chromal, 25
			));
			Inject(this, 3);
		}};
		LiquidOutputExtra = new BlockIOLiquid("LiquidOutputExtra") {{
			OutputAble = true;
			CanSelect = true;
			OutputRate = OutputBufferMax = 800;
			requirements(Category.liquid, ItemStack.with(
				STItem.Metrystal, 25,
				Items.metaglass, 25,
				STItem.Antimatter, 25,
				STItem.Chromal, 25
			));
			Inject(this, 3);
		}};
		LiquidIOExtra = new BlockIOLiquid("LiquidIOExtra") {{
			InputAble = true;
			OutputAble = true;
			CanSelect = true;
			InputRate = InputBufferMax = 1200;
			OutputRate = OutputBufferMax = 1200;
			requirements(Category.liquid, ItemStack.with(
				STItem.Metrystal, 60,
				Items.metaglass, 60,
				STItem.Antimatter, 60,
				STItem.Chromal, 60
			));
			Inject(this, 3);
		}};
	}
}
