package st.mod.wall;


import arc.Events;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BuildVisibility;
import st.mod.STTech;
import st.mod.item.STItem;
import st.mod.UtilTooltip;


public class StWall {
	public static void Inject(Wall b) {
		Inject(b, 1);
	}
	public static void Inject(Wall b, int level) {
		b.buildVisibility = BuildVisibility.shown;
		UtilTooltip
			.Tooltip(b.stats)
			.TechLevel(level);
	}
	//
	public static Wall ChromalWall;
	public static Wall ChromalWallLarge;
	//
	public static Wall DarkWall;
	public static Wall DarkWallLarge;
	//
	public static void Init() {
		_initContent();
	}
	private static void _initEvent() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			STTech
				.createTechNodeRoot(StWall.ChromalWall)
				.Add(StWall.ChromalWallLarge, t -> t
					.Add(StWall.ChromalWallLarge
					)
				)
				.Add(StWall.DarkWall, t -> t
					.Add(StWall.DarkWallLarge)
				)
			;
		});
	}
	private static void _initContent() {
		ChromalWall = new Wall("ChromalWall") {{
			requirements(Category.defense, ItemStack.with(
				STItem.Chromal, 6
			));
			size = 1;
			health = 1200;
			absorbLasers = true;
			insulated = true;
			chanceDeflect = 10.0F;
			Inject(this, 1);
		}};
		ChromalWallLarge = new Wall("ChromalWallLarge") {{
			requirements(Category.defense, ItemStack.with(
				STItem.Chromal, 4 * 6
			));
			size = 2;
			health = ChromalWall.health * 4;
			absorbLasers = true;
			insulated = true;
			chanceDeflect = 10.0F;
			Inject(this, 1);
		}};
		DarkWall = new Wall("DarkWall") {{
			requirements(Category.defense, ItemStack.with(
				STItem.Darkmatter, 24,
				STItem.Antimatter, 24
			));
			size = 1;
			health = 2800;
			insulated = true;
			chanceDeflect = 10.0F;
			absorbLasers = true;
			schematicPriority = 10;
			flashHit = true;
			lightColor = STItem.Darkmatter.color;
			lightningChance = 0.05f;
			lightningDamage = 40f;
			Inject(this, 3);
		}};
		DarkWallLarge = new Wall("DarkWallLarge") {{
			requirements(Category.defense, ItemStack.with(
				STItem.Darkmatter, 96,
				STItem.Antimatter, 96
			));
			size = 2;
			health = DarkWall.health * 4;
			insulated = true;
			chanceDeflect = 10.0F;
			absorbLasers = true;
			schematicPriority = 10;
			flashHit = true;
			lightColor = STItem.Darkmatter.color;
			lightningChance = 0.05f;
			lightningDamage = 40f;
			Inject(this, 3);
		}};
	}
}
