package st.mod.product.entity;

import mindustry.content.Fx;
import mindustry.type.ItemStack;
import mindustry.world.blocks.production.GenericCrafter;
import st.mod.item.ST_ITEM;

public class ElementFactory extends GenericCrafter {
	public ElementFactory(String name) {
		super(name);
		requirements = (ItemStack.with(
			ST_ITEM.NANOTUBE, 50,
			ST_ITEM.SUPERCONDUCTOR, 100,
			ST_ITEM.SUSPENDED, 50,
			ST_ITEM.METRYSTAl, 50,
			ST_ITEM.ANTIMATTER, 5,
			ST_ITEM.CHROMAL, 250
		));
		size = 5;
		craftTime = 180;
		craftEffect = Fx.smeltsmoke;
		consumePower(1600 / 60f);
	}
}
