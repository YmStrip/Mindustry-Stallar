package st.mod.content;

import arc.graphics.Color;
import mindustry.type.Liquid;


public class STLiquid {
	public static Liquid NanoFluid;
	public static void Init() {
		NanoFluid = new Liquid("NanoFluid") {{
			heatCapacity = 1.64f;
			color = Color.rgb(255, 255, 255);
		}};
	}
}
