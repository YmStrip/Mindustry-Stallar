package st.mod.item;

import arc.Events;
import arc.graphics.Color;
import mindustry.game.EventType;
import mindustry.type.Item;
import st.mod.STTech;
import st.mod.UtilTooltip;
import st.mod.content.STLiquid;


public class STItem {
	public static void Inject(Item item) {
		Inject(item, 1);
	}
	public static void Inject(Item item, int level) {
		UtilTooltip
			.Tooltip(item.stats)
			.TechLevel(level);
	}
	public static Item Superconductor;
	public static Item Chromal;
	public static Item Antimatter;
	public static Item Radia;
	public static Item Metrystal;
	public static Item Darkmatter;
	public static void Init() {
		_initContent();
		_initEvent();
	}
	private static void _initEvent() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			STTech.createTechNodeRoot(STItem.Chromal)
				.Add(STItem.Antimatter, t1 -> t1
					.Add(STLiquid.NanoFluid)
					.Add(STItem.Darkmatter)
				);
			STTech.createTechNodeRoot(Radia)
				.Add(STItem.Superconductor, t -> t
					.Add(STItem.Metrystal)
				);
		});
	}
	private static void _initContent() {
		/*Nanotube = new Item("Nanotube") {{
			color = Color.rgb(12, 8, 69);
			explosiveness = 0;
			flammability = 0;
			radioactivity = 0;
			charge = 0;
			//
			cost = 1.5f;
			hardness = 3;
			healthScaling = 1.5f;
			lowPriority = false;
			//
			Inject(this, 1);
		}};*/
		Superconductor = new Item("Superconductor") {{
			color = Color.rgb(235, 243, 196);
			explosiveness = 0;
			flammability = 0;
			radioactivity = 1.5f;
			charge = 2.5f;
			//
			cost = 1f;
			hardness = 1;
			healthScaling = 1f;
			lowPriority = false;
			Inject(this, 1);
		}};
		Chromal = new Item("Chromal") {{
			color = Color.rgb(154, 207, 243);
			explosiveness = 0;
			flammability = 0;
			radioactivity = 0;
			charge = 0.75f;
			//
			cost = 1.5f;
			hardness = 4;
			healthScaling = 2f;
			lowPriority = false;
			Inject(this, 1);
		}};
		Antimatter = new Item("Antimatter") {{
			color = Color.rgb(153, 50, 204);
			explosiveness = 2f;
			flammability = 2f;
			radioactivity = 2f;
			charge = 2f;
			//
			cost = 1f;
			hardness = 1;
			healthScaling = 1f;
			lowPriority = false;
			Inject(this, 2);
		}};
		Radia = new Item("Radia") {{
			color = Color.rgb(207, 95, 134);
			explosiveness = 0.5f;
			flammability = 0.5f;
			radioactivity = 1f;
			charge = 0;
			//
			cost = 1.5f;
			hardness = 1;
			healthScaling = 1f;
			lowPriority = false;
			Inject(this, 2);
		}};
		/*Suspended = new Item("Suspended") {{
			color = Color.rgb(65, 199, 198);
			explosiveness = 0;
			flammability = 0;
			radioactivity = 0;
			charge = 0;
			//
			cost = 1.5f;
			hardness = 1;
			healthScaling = 1f;
			lowPriority = false;
			Inject(this, 2);
		}};*/
		Metrystal = new Item("Metrystal") {{
			color = Color.rgb(116, 243, 149);
			explosiveness = 2.5f;
			charge = 1f;
			cost = 1.5f;
			Inject(this, 2);
		}};
		Darkmatter = new Item("Darkmatter") {{
			hardness = 42;
			color = Color.rgb(0, 0, 128);
			radioactivity = 0f;
			explosiveness = 0f;
			charge = 0f;
			flammability = 0f;
			cost = 1f;
			Inject(this, 3);
		}};
	}
}
