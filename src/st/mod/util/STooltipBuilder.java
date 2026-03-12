package st.mod.util;

import arc.Core;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.StatValue;
import mindustry.world.meta.Stats;
import st.ST;

public class STooltipBuilder {
	public Stats Stats;
	public StatCat Cat;

	public STooltipBuilder(Stats stats, StatCat cat) {
		this.Stats = stats;
		this.Cat = cat;
	}
	public String GetSuffixName(String name) {
		return Core.bundle.has(ST.Name + "-" + name + ".right") ? " " + Core.bundle.get(ST.Name + "-" + name + ".right") : "";
	}


	public STooltipBuilder TechLevel(int level) {
		int v = level;
		if (v < 1) v = 1;
		if (v > 4) v = 4;
		Stat Left = new Stat(ST.Name + "-level", Cat);
		String Right = new Stat(ST.Name + "-level_" + level).localized();
		Stats.add(Left, Right);
		return this;
	}

	public STooltipBuilder Add(String name, StatValue call) {
		var st = new Stat(ST.Name + "-" + name, Cat);
		Stats.add(st, call);
		return this;
	}

	public STooltipBuilder Add(String name, float value) {
		var Left = new Stat(ST.Name + "-" + name, Cat);
		Stats.add(Left, ((long) value + GetSuffixName(name)));
		return this;
	}
	public STooltipBuilder Add(String name, String value) {
		var Left = new Stat(ST.Name + "-" + name, Cat);
		Stats.add(Left, (value + GetSuffixName(name)));

		return this;
	}

	public STooltipBuilder Add(String name, boolean value) {
		var Left = new Stat(ST.Name + "-" + name, Cat);
		var v = value ? Core.bundle.get(ST.Name + "-TRUE") : Core.bundle.get(ST.Name + "-FALSE");
		Stats.add(Left, v);
		return this;
	}
}
