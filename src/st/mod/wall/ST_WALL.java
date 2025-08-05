package st.mod.wall;


import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BuildVisibility;
import st.mod.item.ST_ITEM;
import st.mod.UTIL_TOOLTIP;


public class ST_WALL {
	public static void inject(Wall b) {
		inject(b, 1);
	}
	public static void inject(Wall b, int level) {
		b.buildVisibility = BuildVisibility.shown;
		UTIL_TOOLTIP
			.tooltip(b.stats)
			.techLevel(level);
	}
	//
	public static Wall NANO_WALL;
	public static Wall NANO_WALL_LARGE;
	//
	public static Wall CHROMAL_WALL;
	public static Wall CHROMAL_WALL_LARGE;
	//
	public static Wall ELEMENT_WALL;
	public static Wall ELEMENT_WALL_LARGE;
	public static Wall AETHER_WALL;
	public static Wall AETHER_WALL_LARGE;
	public static void load() {
		NANO_WALL = new Wall("NANO_WALL") {{
			requirements(Category.defense, ItemStack.with(
				ST_ITEM.NANOTUBE, 6
			));
			size = 1;
			health = 750;
			insulated = true;
			chanceDeflect = 10.0F;
			inject(this, 1);
		}};
		NANO_WALL_LARGE = new Wall("NANO_WALL_LARGE") {{
			requirements(Category.defense, ItemStack.with(
				ST_ITEM.NANOTUBE, 4 * 6
			));
			size = 2;
			health = NANO_WALL.health * 4;
			insulated = true;
			chanceDeflect = 10.0F;
			inject(this, 1);
		}};
		//
		CHROMAL_WALL = new Wall("CHROMAL_WALL") {{
			requirements(Category.defense, ItemStack.with(
				ST_ITEM.CHROMAL, 6
			));
			size = 1;
			health = 1400;
			absorbLasers = true;
			insulated = true;
			chanceDeflect = 10.0F;
			inject(this, 1);
		}};
		CHROMAL_WALL_LARGE = new Wall("CHROMAL_WALL_LARGE") {{
			requirements(Category.defense, ItemStack.with(
				ST_ITEM.CHROMAL, 4 * 6
			));
			size = 2;
			health = CHROMAL_WALL.health * 4;
			absorbLasers = true;
			insulated = true;
			chanceDeflect = 10.0F;
			inject(this, 1);
		}};
		//
		ELEMENT_WALL = new Wall("ELEMENT_WALL") {{
			requirements(Category.defense, ItemStack.with(
				ST_ITEM.SUSPENDED, 24,
				ST_ITEM.ANTIMATTER, 24,
				ST_ITEM.GOLD_ELEMENT, 6,
				ST_ITEM.WOOD_ELEMENT, 6,
				ST_ITEM.WATER_ELEMENT, 6,
				ST_ITEM.FIRE_ELEMENT, 6,
				ST_ITEM.EARTH_ELEMENT, 6
			));
			size = 1;
			health = 3200;
			insulated = true;
			chanceDeflect = 10.0F;
			absorbLasers = true;
			schematicPriority = 10;
			flashHit = true;
			lightColor = ST_ITEM.LIGHT_ELEMENT.color;
			lightningChance = 0.05f;
			lightningDamage = 40f;
			inject(this, 3);
		}};
		ELEMENT_WALL_LARGE = new Wall("ELEMENT_WALL_LARGE") {{
			requirements(Category.defense, ItemStack.with(
				ST_ITEM.SUSPENDED, 24,
				ST_ITEM.ANTIMATTER, 24,
				ST_ITEM.GOLD_ELEMENT, 24,
				ST_ITEM.WOOD_ELEMENT, 24,
				ST_ITEM.WATER_ELEMENT, 24,
				ST_ITEM.FIRE_ELEMENT, 24,
				ST_ITEM.EARTH_ELEMENT, 24
			));
			size = 2;
			health = ELEMENT_WALL.health * 4;
			insulated = true;
			chanceDeflect = 10.0F;
			absorbLasers = true;
			schematicPriority = 10;
			flashHit = true;
			lightColor = ST_ITEM.LIGHT_ELEMENT.color;
			lightningChance = 0.05f;
			lightningDamage = 40f;
			inject(this, 3);
		}};
		AETHER_WALL = new Wall("AETHER_WALL") {{
			requirements(Category.defense, ItemStack.with(
				ST_ITEM.SUSPENDED, 6,
				ST_ITEM.ANTIMATTER, 6,
				ST_ITEM.LIGHT_ELEMENT, 6,
				ST_ITEM.DARK_ELEMENT, 6
			));
			size = 3;
			health = 12000;
			insulated = true;
			chanceDeflect = 10.0F;
			absorbLasers = true;
			schematicPriority = 10;
			flashHit = true;
			lightColor = ST_ITEM.LIGHT_ELEMENT.color;
			lightningChance = 0.15f;
			lightningDamage = 120f;
			inject(this, 3);
		}};
		AETHER_WALL_LARGE = new Wall("AETHER_WALL_LARGE") {{
			requirements(Category.defense, ItemStack.with(
				ST_ITEM.SUSPENDED, 24,
				ST_ITEM.ANTIMATTER, 24,
				ST_ITEM.LIGHT_ELEMENT, 24,
				ST_ITEM.DARK_ELEMENT, 24
			));
			size = 2;
			health = AETHER_WALL.health * 8;
			insulated = true;
			chanceDeflect = 10.0F;
			absorbLasers = true;
			schematicPriority = 10;
			flashHit = true;
			lightColor = ST_ITEM.LIGHT_ELEMENT.color;
			lightningChance = 0.15f;
			lightningDamage = 120f;
			inject(this, 3);
		}};
	}
}
