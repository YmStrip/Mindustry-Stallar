package st.mod.content;

import mindustry.world.blocks.environment.OreBlock;
import st.mod.item.STItem;


public class STFloor {
	public static OreBlock Radia;
	public static void Init() {
		Radia = new OreBlock("Radia", STItem.Radia) {{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 3.380953f;
			alwaysUnlocked = true;
		}};
	}
}
