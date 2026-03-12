package st.mod;

import mindustry.world.meta.StatCat;
import mindustry.world.meta.Stats;
import st.mod.util.STooltipBuilder;


public class UtilTooltip {
	public static StatCat Cat() {
		return new StatCat("st");
	}

	public static STooltipBuilder Tooltip(Stats stats, StatCat cat) {
		return new STooltipBuilder(stats, cat);
	}

	public static STooltipBuilder Tooltip(Stats stats) {
		return new STooltipBuilder(stats, Cat());
	}
}
