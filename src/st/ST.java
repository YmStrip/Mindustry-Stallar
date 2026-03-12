package st;


import arc.Core;
import arc.graphics.g2d.TextureRegion;

public class ST {
	public static String Name = "st";

	public static String Name(String n) {
		return Core.bundle.get(Name + "-" + n);
	}

	public static String Name(String type, String n) {
		return Core.bundle.get(type + "." + Name + "-" + n);
	}
	public static String Stat(String name) {
		return Name("stat", name);
	}
	public static String Bar(String name) {
		return Name("bar", name);
	}
	public static String UI(String name) {
		return Name("ui", name);
	}
	public static String Item(String name) {
		return Name("item", name);
	}
	public static String Stage(String name) {
		return Name("stage", name);
	}
	public static String Block(String name) {
		return Name("block", name);
	}
	public static TextureRegion Atlas(String name) {
		return Core.atlas.find(AtlasName(name));
	}
	public static String AtlasName(String name) {
		return ST.Name + "-" + name;
	}
}
