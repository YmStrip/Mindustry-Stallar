package st.mod.util;

import arc.Core;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.StatValue;
import mindustry.world.meta.Stats;
import st.ST;

public class STooltipBuilder {
	public Stats stats;
	public StatCat cat;

	public STooltipBuilder(Stats stats, StatCat cat) {
		this.stats = stats;
		this.cat = cat;
	}
	public String getSuffixName(String name) {
		System.out.println(Core.bundle.has(ST.name + "-" + name + ".right") ? " " + Core.bundle.get(ST.name + "-" + name + ".right") : " " + " ,, " + (ST.name + "-" + name + ".right"));
		return Core.bundle.has(ST.name + "-" + name + ".right") ? " " + Core.bundle.get(ST.name + "-" + name + ".right") : "";
	}


	public STooltipBuilder techLevel(int level) {
		int v = level;
		if (v < 1) v = 1;
		if (v > 4) v = 4;
		Stat Left = new Stat(ST.name + "-level", cat);
		String Right = new Stat(ST.name + "-level_" + level).localized();
		stats.add(Left, Right);
		return this;
	}

	public STooltipBuilder show(String name, StatValue call) {
		var st = new Stat(ST.name + "-" + name, cat);
		stats.add(st, call);
		return this;
	}

	public STooltipBuilder show(String name, float value) {
		if (value == 0) return this;
		var Left = new Stat(ST.name + "-" + name, cat);
		stats.add(Left, ((long) value + getSuffixName(name)));

		return this;
	}
	public STooltipBuilder show(String name, String value) {
		var Left = new Stat(ST.name + "-" + name, cat);
		stats.add(Left, (value + getSuffixName(name)));

		return this;
	}

	public STooltipBuilder show(String name, boolean value) {
		var Left = new Stat(ST.name + "-" + name, cat);
		var v = value ? Core.bundle.get(ST.name + "-TRUE") : Core.bundle.get(ST.name + "-FALSE");
		stats.add(Left, v);
		return this;
	}
}
