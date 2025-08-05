package st;


import arc.Core;
import arc.graphics.g2d.TextureRegion;

public class ST {
	public static String name = "st";

	public static String name(String n) {
		return Core.bundle.get(name + "-" + n);
	}

	public static String name(String type, String n) {
		return Core.bundle.get(type + "." + name + "-" + n);
	}
	public static String stat(String name) {
		return name("stat", name);
	}
	public static String bar(String name) {
		return name("bar", name);
	}
	public static String ui(String name) {
		return name("ui", name);
	}
	public static String item(String name) {
		return name("item", name);
	}
	public static String stage(String name) {
		return name("stage", name);
	}
	public static String block(String name) {
		return name("block", name);
	}
	public static TextureRegion atlas(String name) {
		return Core.atlas.find(atlasName(name));
	}
	public static String atlasName(String name) {
		return ST.name + "-" + name;
	}
}
