package st.mod.content;

import arc.graphics.Color;
import mindustry.type.Liquid;


public class ST_LIQUID {
	public static Liquid NANO_FLUID;
	public static void load() {
		NANO_FLUID = new Liquid("NANO_FLUID") {{
			heatCapacity = 1.64f;
			color = Color.rgb(255, 255, 255);
		}};
	}
}
