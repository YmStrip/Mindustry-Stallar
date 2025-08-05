package st.mod.content;

import mindustry.content.Blocks;
import mindustry.world.meta.Attribute;


public class ST_ATTRIBUTE {
	public static Attribute BASIN;
	public static Attribute SHALLOW_ORE;
	public static Attribute DEEP_ORE;
	public static Attribute WATER;
	public static void load() {
		BASIN = Attribute.add("basin");
		SHALLOW_ORE = Attribute.add("SHALLOW_ORE");
		DEEP_ORE = Attribute.add("DEEP_ORE");
		WATER = Attribute.add("WATER");
		//shallow_layer_ore
		{
			Blocks.basalt.attributes.set(SHALLOW_ORE, 1f);
			Blocks.dacite.attributes.set(SHALLOW_ORE, 1f);
			Blocks.hotrock.attributes.set(SHALLOW_ORE, 1.25f);
			Blocks.magmarock.attributes.set(SHALLOW_ORE, 1.5f);
			Blocks.charr.attributes.set(SHALLOW_ORE, 1.25f);
			Blocks.salt.attributes.set(SHALLOW_ORE, 0.15f);
			Blocks.shale.attributes.set(SHALLOW_ORE, 0.2f);
			Blocks.dirt.attributes.set(SHALLOW_ORE, 0.15f);
			Blocks.snow.attributes.set(SHALLOW_ORE, 0.15f);
			Blocks.moss.attributes.set(SHALLOW_ORE, 0.25f);
			Blocks.craters.attributes.set(SHALLOW_ORE, 1.5f);
			Blocks.rhyolite.attributes.set(SHALLOW_ORE, 0.65f);
			Blocks.yellowStone.attributes.set(SHALLOW_ORE, 1.25f);
		}
		//deep_ore
		{
			Blocks.basalt.attributes.set(DEEP_ORE, 0.8f);
			Blocks.dacite.attributes.set(DEEP_ORE, 0.8f);
			Blocks.hotrock.attributes.set(DEEP_ORE, 1f);
			Blocks.magmarock.attributes.set(DEEP_ORE, 1.25f);
			Blocks.charr.attributes.set(DEEP_ORE, 1f);
			Blocks.salt.attributes.set(DEEP_ORE, 0.15f);
			Blocks.shale.attributes.set(DEEP_ORE, 0.2f);
			Blocks.dirt.attributes.set(DEEP_ORE, 0.15f);
			Blocks.snow.attributes.set(DEEP_ORE, 0.15f);
			Blocks.moss.attributes.set(DEEP_ORE, 0.25f);
			Blocks.craters.attributes.set(DEEP_ORE, 1.5f);
			Blocks.rhyolite.attributes.set(DEEP_ORE, 0.65f);
			Blocks.yellowStone.attributes.set(DEEP_ORE, 0.65f);
		}
		//basin
		{
			Blocks.hotrock.attributes.set(BASIN, 0.5f);
			Blocks.magmarock.attributes.set(BASIN, 1f);
			Blocks.craters.attributes.set(BASIN, 1.25f);
			Blocks.ferricCraters.attributes.set(BASIN, 1f);
			Blocks.charr.attributes.set(BASIN, 0.75f);
		}
		//water
		{
			Blocks.coreZone.attributes.set(WATER, 10f);
			Blocks.water.attributes.set(WATER, 1f);
			Blocks.sandWater.attributes.set(WATER, 2.5f);
			Blocks.taintedWater.attributes.set(WATER, 0.5f);
			Blocks.darksandWater.attributes.set(WATER, 2.5f);
			Blocks.deepTaintedWater.attributes.set(WATER, 0.5f);
			Blocks.deepwater.attributes.set(WATER, 3f);
		}
	}
}
