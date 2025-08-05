package st.mod.util;

import arc.Core;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;

import java.util.Locale;

public class StatNoLimit extends Stat {
	public StatNoLimit(String name, StatCat category) {
		super(name, category);
	}
	public StatNoLimit(String name) {
		super(name);
	}
	public String localized(){
		return Core.bundle.get("stat." + name.toLowerCase(Locale.ROOT));
	}
}
