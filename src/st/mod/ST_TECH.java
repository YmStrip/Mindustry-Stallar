package st.mod;

import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.type.Item;
import st.mod.util.STechBuilder;




public class ST_TECH {
	public static STechBuilder createTechNode(UnlockableContent item) {
		return new STechBuilder(item);
	}
	public static STechBuilder createTechNodeRoot(UnlockableContent item) {
		return new STechBuilder(item).parent(WELCOME);
	}
	public static UnlockableContent WELCOME;
	public static TechTree.TechNode ROOT;
	public static void load() {
		WELCOME = new Item("WELCOME") {{
			alwaysUnlocked = true;
		}};
		ROOT = TechTree.nodeRoot("st", WELCOME, true, () -> {

		});
	}
}
