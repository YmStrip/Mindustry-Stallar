package st.mod.power;


import arc.Events;
import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.game.EventType;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.BuildVisibility;
import st.mod.STTech;
import st.mod.item.STItem;
import st.mod.UtilTooltip;

public class STPower {
	public static void Inject(Block b) {
		Inject(b, 1);
	}
	public static void Inject(Block b, int level) {
		b.category = Category.power;
		b.buildVisibility = BuildVisibility.shown;
		UtilTooltip
			.Tooltip(b.stats)
			.TechLevel(level);
	}
	public static BeamNode BeamNode;
	public static BeamNode BeamNodeExtra;
	public static void Init() {
		_initContent();
		_initEvent();
	}
	private static void _initContent() {
		BeamNode = new BeamNode("BeamNode") {{
			size = 1;
			laserColor2 = Color.yellow;
			consumesPower = outputsPower = true;
			range = 24;
			fogRadius = 5;
			consumePowerBuffered(4000f);
			requirements(Category.power, ItemStack.with(Items.lead, 15, Items.metaglass, 15));
			Inject(this, 1);
		}};
		BeamNodeExtra = new BeamNode("BeamNodeExtra") {{
			health = 1500;
			size = 1;
			laserColor2 = Color.red;
			consumesPower = outputsPower = true;
			range = 72;
			fogRadius = 5;
			consumePowerBuffered(6000f);
			requirements(Category.power, ItemStack.with(STItem.Chromal, 15, STItem.Superconductor, 15));
			Inject(this, 2);
		}};
	}
	private static void _initEvent() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			STTech.createTechNodeRoot(STPower.BeamNode)
				.Add(STPower.BeamNode, t -> t
					.Add(STPower.BeamNodeExtra))
			;
		});
	}
}
