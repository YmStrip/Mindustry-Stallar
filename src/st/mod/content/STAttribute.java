package st.mod.content;

import mindustry.content.Blocks;
import mindustry.world.meta.Attribute;


public class STAttribute {
	public static Attribute Basin;
	public static Attribute OreShallow;
	public static Attribute OreDeep;
	public static Attribute Water;
	public static void Init() {
		Basin = Attribute.add("Basin");
		OreShallow = Attribute.add("OreShallow");
		OreDeep = Attribute.add("OreDeep");
		Water = Attribute.add("Water");
		//shallow_layer_ore
		{
			Blocks.basalt.attributes.set(OreShallow, 1f);
			Blocks.dacite.attributes.set(OreShallow, 1f);
			Blocks.hotrock.attributes.set(OreShallow, 1.25f);
			Blocks.magmarock.attributes.set(OreShallow, 1.5f);
			Blocks.charr.attributes.set(OreShallow, 1.25f);
			Blocks.salt.attributes.set(OreShallow, 0.15f);
			Blocks.shale.attributes.set(OreShallow, 0.2f);
			Blocks.dirt.attributes.set(OreShallow, 0.15f);
			Blocks.snow.attributes.set(OreShallow, 0.15f);
			Blocks.moss.attributes.set(OreShallow, 0.25f);
			Blocks.craters.attributes.set(OreShallow, 1.5f);
			Blocks.rhyolite.attributes.set(OreShallow, 0.65f);
			Blocks.yellowStone.attributes.set(OreShallow, 1.25f);
		}
		//deep_ore
		{
			Blocks.basalt.attributes.set(OreDeep, 0.8f);
			Blocks.dacite.attributes.set(OreDeep, 0.8f);
			Blocks.hotrock.attributes.set(OreDeep, 1f);
			Blocks.magmarock.attributes.set(OreDeep, 1.25f);
			Blocks.charr.attributes.set(OreDeep, 1f);
			Blocks.salt.attributes.set(OreDeep, 0.15f);
			Blocks.shale.attributes.set(OreDeep, 0.2f);
			Blocks.dirt.attributes.set(OreDeep, 0.15f);
			Blocks.snow.attributes.set(OreDeep, 0.15f);
			Blocks.moss.attributes.set(OreDeep, 0.25f);
			Blocks.craters.attributes.set(OreDeep, 1.5f);
			Blocks.rhyolite.attributes.set(OreDeep, 0.65f);
			Blocks.yellowStone.attributes.set(OreDeep, 0.65f);
		}
		//basin
		{
			Blocks.hotrock.attributes.set(Basin, 0.5f);
			Blocks.magmarock.attributes.set(Basin, 1f);
			Blocks.craters.attributes.set(Basin, 1.25f);
			Blocks.ferricCraters.attributes.set(Basin, 1f);
			Blocks.charr.attributes.set(Basin, 0.75f);
		}
		//water
		{
			Blocks.coreZone.attributes.set(Water, 10f);
			Blocks.water.attributes.set(Water, 1f);
			Blocks.sandWater.attributes.set(Water, 2.5f);
			Blocks.taintedWater.attributes.set(Water, 0.5f);
			Blocks.darksandWater.attributes.set(Water, 2.5f);
			Blocks.deepTaintedWater.attributes.set(Water, 0.5f);
			Blocks.deepwater.attributes.set(Water, 3f);
		}
	}
}
