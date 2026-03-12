package st.mod.qio;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.util.Log;
import mindustry.content.Items;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import org.json.JSONObject;
import st.ST;
import st.id.entity.IDEventEmitter;
import st.mod.STTech;
import st.mod.UtilTooltip;
import st.mod.distribution.entity.BlockIO;
import st.mod.item.STItem;
import st.mod.qio.block.BlockQIOItemInterface;
import st.mod.qio.block.BlockQIOLiquidInterface;
import st.mod.qio.block.BlockQIOPane;
import st.mod.qio.block.BlockQIOUnitInterface;
import st.mod.qio.event.EventQIOUnlockDrive;
import st.mod.qio.drive.QIODrive;
import st.mod.qio.entity.QIONetwork;
import st.mod.util.CountBuild;
import st.mod.util.STooltipBuilder;


public class STQIO {
	public static CountBuild<BlockIO.BlockIOBuild> Build = new CountBuild<>() {
		@Override
		public boolean Filter(Building build) {
			return (
				build instanceof BlockQIOItemInterface.BlockQIOItemInterfaceBuild ||
					build instanceof BlockQIOLiquidInterface.BlockQIOLiquidInterfaceBuild ||
					build instanceof BlockQIOUnitInterface.BlockQIOUnitInterfaceBuild
			);
		}
	};
	public static QIONetwork Network = new QIONetwork();
	public static IDEventEmitter Event = new IDEventEmitter();
	public static BlockQIOItemInterface QIOItemInterface;
	public static BlockQIOLiquidInterface QIOLiquidInterface;
	public static BlockQIOUnitInterface QIOUnitInterface;
	public static BlockQIOPane QIOPane;
	public static QIODrive QIODrive;
	public static QIODrive QIODriveExtra;
	public static void Write() {
		Core.settings.put(ST.Name("qio"), Network.Serialize().toString());
	}
	public static void Read() {
		try {
			var source = Core.settings.getString(ST.Name("qio"));
			if (!(source == null || source.isEmpty())) {
				var object = new JSONObject(source);
				Network.Configure(object);
			}
		} catch (Exception e) {
			Log.info("[QIO] Read Data Failed");
			Log.err(e);
		}
	}
	public static void UpdateCapacity() {
		var liquid = 1024f;
		var item = 256f;
		var unit = 8f;
		var payload = 8f;
		if (QIODrive.unlocked()) {
			liquid += QIODrive.CapacityLiquid;
			item += QIODrive.CapacityItem;
			unit += QIODrive.CapacityUnit;
			payload += QIODrive.CapacityPayload;
		}
		if (QIODriveExtra.unlocked()) {
			liquid += QIODriveExtra.CapacityLiquid;
			item += QIODriveExtra.CapacityItem;
			unit += QIODriveExtra.CapacityUnit;
			payload += QIODriveExtra.CapacityPayload;
		}
		Network.Item.Capacity(item);
		Network.Liquid.Capacity(liquid);
		Network.Unit.Capacity(unit);
		Network.Payload.Capacity(payload);
	}
	public static void Init() {
		_initContent();
		_initEvent();
	}
	private static void _initContent() {
		QIOItemInterface = new BlockQIOItemInterface("QIOItemInterface") {{
			requirements(Category.distribution, ItemStack.with(
				STItem.Chromal, 1500,
				STItem.Superconductor, 1000,
				STItem.Antimatter, 500
			));
			canOverdrive = false;
			DestroyItem = false;
			color = Color.orange;
			size = 5;
			InputAble = true;
			OutputAble = true;
			CanSelect = true;
			PalceMax = 8;
			InputRate = InputBufferMax = 2048;
			OutputRate = OutputBufferMax = 1024;
			consumePower(16000 / 60f);
			Inject(this, 3);
		}};
		QIOLiquidInterface = new BlockQIOLiquidInterface("QIOLiquidInterface") {{
			requirements(Category.liquid, ItemStack.with(
				STItem.Chromal, 1500,
				STItem.Superconductor, 1000,
				STItem.Antimatter, 500
			));
			canOverdrive = false;
			color = Color.cyan;
			size = 5;
			InputAble = true;
			OutputAble = true;
			CanSelect = true;
			PlaceMax = 8;
			InputRate = InputBufferMax = 2048 * 1.5f;
			OutputRate = OutputBufferMax = 2048 * 1.5f;
			consumePower(16000 / 60f);
			Inject(this, 3);
		}};
		QIOUnitInterface = new BlockQIOUnitInterface("QIOUnitInterface") {{
			requirements(Category.units, ItemStack.with(
				STItem.Chromal, 5000,
				STItem.Metrystal, 2500,
				STItem.Darkmatter, 500
			));
			canOverdrive = false;
			color = Color.green;
			size = 14;
			InputAble = true;
			OutputAble = true;
			CanSelect = true;
			PlaceMax = 8;
			InputRate = 1;
			InputBufferMax = 1;
			OutputRate = 1 / 4f;
			OutputBufferMax = 1;
			consumePower(64000 / 60f);
			Inject(this, 3);
		}};
		QIOPane = new BlockQIOPane("QIOPane") {{
			requirements(Category.logic, ItemStack.with(
				STItem.Antimatter, 25,
				Items.metaglass, 25,
				Items.silicon, 10
			));
			size = 2;
			consumePower(60 / 60f);
			Inject(this, 3);
		}};
		QIODrive = new QIODrive("QIODrive") {
			@Override
			public ItemStack[] researchRequirements() {
				return ItemStack.with(
					STItem.Antimatter, 1000,
					STItem.Chromal, 1000,
					STItem.Superconductor, 1000
				);
			}
			{
				CapacityItem = 8 * 1000;
				CapacityLiquid = 4 * 1000;
				CapacityPayload = 4;
				CapacityUnit = 4;
				Inject(this, 3);
			}
		};
		QIODriveExtra = new QIODrive("QIODriveExtra") {
			@Override
			public ItemStack[] researchRequirements() {
				return ItemStack.with(
					STItem.Darkmatter, 3000,
					STItem.Chromal, 3000,
					STItem.Metrystal, 3000
				);
			}
			{
				CapacityItem = 24 * 1000;
				CapacityLiquid = 12 * 1000;
				CapacityPayload = 8;
				CapacityUnit = 8;
				Inject(this, 3);
			}
		};
	}
	private static void _initEvent() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			Read();
			STTech
				.createTechNodeRoot(QIOItemInterface)
				.Add(QIODrive, t -> t
					.Add(QIODriveExtra)
				)
				.Add(QIOPane)
				.Children(QIOLiquidInterface)
				.Children(QIOUnitInterface)
			;
		});
		Event.On(EventQIOUnlockDrive.class, e -> {
			UpdateCapacity();
		});
		Events.on(EventType.ModContentLoadEvent.class, e -> {
			UpdateCapacity();
		});
		Events.on(EventType.SaveWriteEvent.class, e -> {
			Write();
		});
		Events.on(EventType.ResetEvent.class, e -> {
			Network.Item.Clear();
			Network.Liquid.Clear();
			Network.Payload.Clear();
			Network.Unit.Clear();
			UpdateCapacity();
			Write();
		});
	}
	public static STooltipBuilder Inject(Block t) {
		return Inject(t, 1);
	}
	public static STooltipBuilder Inject(Block t, int level) {
		var builder = UtilTooltip.Tooltip(t.stats).TechLevel(level);
		if (t instanceof BlockQIOItemInterface io) {
			if (io.InputAble) builder.Add("input_rate", io.InputRate);
			if (io.OutputAble) builder.Add("output_rate", io.OutputRate);
			builder.Add("max_place", io.PalceMax);
		}
		if (t instanceof BlockQIOLiquidInterface io) {
			if (io.InputAble) builder.Add("input_rate", io.InputRate);
			if (io.OutputAble) builder.Add("output_rate", io.OutputRate);
			builder.Add("max_place", io.PlaceMax);
		}
		if (t instanceof BlockQIOUnitInterface io) {
			if (io.InputAble) builder.Add("input_rate", io.InputRate);
			if (io.OutputAble) builder.Add("output_rate", io.OutputRate);
			builder.Add("max_place", io.PlaceMax);
		}
		return builder;
	}
	public static STooltipBuilder Inject(QIODrive t, int level) {
		var builder = UtilTooltip.Tooltip(t.stats).TechLevel(level);
		builder.Add("capacity_item", t.CapacityItem);
		builder.Add("capacity_liquid", t.CapacityLiquid);
		builder.Add("capacity_unit", t.CapacityUnit);
		builder.Add("capacity_payload", t.CapacityPayload);
		return builder;
	}
}
