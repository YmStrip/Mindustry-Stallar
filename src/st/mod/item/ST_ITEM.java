package st.mod.item;

import arc.graphics.Color;
import mindustry.type.Item;
import st.mod.UTIL_TOOLTIP;


public class ST_ITEM {
	public static void inject(Item item) {
		inject(item, 1);
	}
	public static void inject(Item item, int level) {
		UTIL_TOOLTIP
			.tooltip(item.stats)
			.techLevel(level);
	}
	public static Item NANOTUBE;
	public static Item SUPERCONDUCTOR;
	public static Item CHROMAL;
	public static Item ANTIMATTER;
	public static Item SUSPENDED;
	//public static Item RADIA;
	public static Item METRYSTAl;
	public static Item GOLD_ELEMENT;
	public static Item WOOD_ELEMENT;
	public static Item WATER_ELEMENT;
	public static Item FIRE_ELEMENT;
	public static Item EARTH_ELEMENT;
	public static Item LIGHT_ELEMENT;
	public static Item DARK_ELEMENT;
	public static void load() {
		NANOTUBE = new Item("NANOTUBE") {{
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
			inject(this, 1);
		}};
		SUPERCONDUCTOR = new Item("SUPERCONDUCTOR") {{
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
			inject(this, 1);
		}};
		CHROMAL = new Item("CHROMAL") {{
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
			inject(this, 1);
		}};
		ANTIMATTER = new Item("ANTIMATTER") {{
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
			inject(this, 2);
		}};
		SUSPENDED = new Item("SUSPENDED") {{
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
			inject(this, 2);
		}};
		/*RADIA = new Item("RADIA") {{
			color = Color.rgb(243, 156, 207);
			explosiveness = 2.5f;
			charge = 1f;
			cost = 1.5f;
			inject(this, 2);
		}};*/
		METRYSTAl = new Item("METRYSTAl") {{
			color = Color.rgb(116, 243, 149);
			explosiveness = 2.5f;
			charge = 1f;
			cost = 1.5f;
			inject(this, 2);
		}};
		GOLD_ELEMENT = new Item("GOLD_ELEMENT") {{
			hardness = 42;
			color = Color.rgb(255, 215, 0);
			radioactivity = 1.5f;
			explosiveness = 1.5f;
			charge = 1.5f;
			flammability = 1.5f;
			cost = 2;
			inject(this, 3);
		}};
		WOOD_ELEMENT = new Item("WOOD_ELEMENT") {
			{
				hardness = 42;
				color = Color.rgb(0, 255, 0);
				radioactivity = 1.5f;
				explosiveness = 1.5f;
				charge = 1.5f;
				flammability = 1.5f;
				cost = 2;
				inject(this, 3);
			}
		};
		WATER_ELEMENT = new Item("WATER_ELEMENT") {{
			hardness = 42;
			radioactivity = 1.5f;
			explosiveness = 1.5f;
			charge = 1.5f;
			flammability = 1.5f;
			cost = 2;
			color = Color.rgb(30, 144, 255);
			inject(this, 3);
		}};
		FIRE_ELEMENT = new Item("FIRE_ELEMENT") {{
			hardness = 42;
			radioactivity = 1.5f;
			explosiveness = 1.5f;
			charge = 1.5f;
			flammability = 1.5f;
			cost = 2;
			color = Color.rgb(220, 20, 60);
			inject(this, 3);
		}};
		EARTH_ELEMENT = new Item("EARTH_ELEMENT") {{
			hardness = 42;
			radioactivity = 1.5f;
			explosiveness = 1.5f;
			charge = 1.5f;
			flammability = 1.5f;
			cost = 2;
			color = Color.rgb(255, 0, 255);
			inject(this, 3);
		}};
		LIGHT_ELEMENT = new Item("LIGHT_ELEMENT") {{
			hardness = 42;
			color = Color.rgb(148, 243, 243);
			radioactivity = 4f;
			explosiveness = 4f;
			charge = 4f;
			flammability = 4f;
			cost = 4;
			inject(this, 3);
		}};
		DARK_ELEMENT = new Item("DARK_ELEMENT") {{
			hardness = 42;
			color = Color.rgb(0, 0, 128);
			radioactivity = 2.5f;
			explosiveness = 2.5f;
			charge = 2.5f;
			flammability = 2.5f;
			cost = 2.5f;
			inject(this, 3);
		}};
	}
}
