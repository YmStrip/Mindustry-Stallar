package st.mod;

import mindustry.world.meta.StatCat;
import mindustry.world.meta.Stats;
import st.mod.util.STooltipBuilder;


public class UTIL_TOOLTIP {
	public static StatCat cat() {
		return new StatCat("st");
	}

	public static STooltipBuilder tooltip(Stats stats, StatCat cat) {
		return new STooltipBuilder(stats, cat);
	}

	public static STooltipBuilder tooltip(Stats stats) {
		return new STooltipBuilder(stats, cat());
	}
}
