package st.mod.content;

import mindustry.world.blocks.environment.OreBlock;
import st.mod.item.ST_ITEM;


public class ST_FLOOR {
	public static OreBlock GOLD_CRYSTAL;
	public static OreBlock WOOD_CRYSTAL;
	public static OreBlock WATER_CRYSTAL;
	public static OreBlock FIRE_CRYSTAL;
	public static OreBlock EARTH_CRYSTAL;
	public static OreBlock LIGHT_CRYSTAL;
	public static OreBlock DARK_CRYSTAL;
	public static void load() {
		GOLD_CRYSTAL = new OreBlock("GOLD_CRYSTAL", ST_ITEM.GOLD_ELEMENT) {{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 3.380953f;
			alwaysUnlocked = true;
		}};
		WOOD_CRYSTAL = new OreBlock("WOOD_CRYSTAL", ST_ITEM.WOOD_ELEMENT) {{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 2.380953f;
			alwaysUnlocked = true;
		}};
		WATER_CRYSTAL = new OreBlock("WATER_CRYSTAL", ST_ITEM.WATER_ELEMENT) {{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 1.80953f;
			alwaysUnlocked = true;
		}};
		FIRE_CRYSTAL = new OreBlock("FIRE_CRYSTAL", ST_ITEM.FIRE_ELEMENT) {{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 1.180953f;
			alwaysUnlocked = true;
		}};
		EARTH_CRYSTAL = new OreBlock("EARTH_CRYSTAL", ST_ITEM.EARTH_ELEMENT) {{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 4.380953f;
			alwaysUnlocked = true;
		}};
		LIGHT_CRYSTAL = new OreBlock("LIGHT_CRYSTAL", ST_ITEM.LIGHT_ELEMENT) {{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 4.380953f;
			alwaysUnlocked = true;
		}};
		DARK_CRYSTAL = new OreBlock("DARK_CRYSTAL", ST_ITEM.DARK_ELEMENT) {{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 4.380953f;
			alwaysUnlocked = true;
		}};
	}
}
