package st.mod.turret;


import mindustry.content.*;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.BuildVisibility;
import st.mod.attack.ST_BULLET;
import st.mod.attack.ST_EFFECT;
import st.mod.item.ST_ITEM;
import st.mod.UTIL_TOOLTIP;
import st.mod.turret.entity.DrawTurretShadow;


public class ST_TURRET {

	public static void inject(Turret t) {
		inject(t, 1);
	}

	public static void inject(Turret t, int level) {
		//Basic
		{
			t.buildVisibility = BuildVisibility.shown;
			t.researchCostMultiplier = 0.8f;
			t.buildCostMultiplier = 0.8f;
			t.reload = 60;
			//t.targetAir = true;
			//t.targetGround = true;
			if (t.chargeSound == null) t.chargeSound = Sounds.lasercharge;
			t.hasPower = true;
			t.hasLiquids = true;
			t.category = Category.turret;
		}
		{
			var m = 0f;
			var mc = 0f;
			for (var i : t.requirements) {
				++mc;
				m += (float) (1.01 + (1.99 - 1.01) * Math.pow(Math.E, (-i.amount / i.item.cost)));
			}
			if (mc == 0) mc = 1;
			if (m == 0) m = 1;
			var v = m / mc;
			t.consumeCoolant(v - 1);
			t.coolantMultiplier = v;
		}
		UTIL_TOOLTIP.tooltip(t.stats).techLevel(level);
	}

	public static PowerTurret SUPER_LASER;
	public static ItemTurret SUPER_GUN;
	public static ItemTurret STINGER_MISSILE;
	public static ItemTurret IMPACT;
	public static PowerTurret PULSE;
	public static ContinuousLiquidTurret ION_BEAM;
	public static PowerTurret PHOTON_GUN;
	//
	public static PowerTurret ELECTRONIC_WAVE;
	public static ItemTurret ARMSTRONG_GUN;
	public static ItemTurret ELECTRONIC_SPECTRE;
	public static ItemTurret MASS_GUN;
	//[T2]
	public static PowerTurret CERTAIN_RAIL_GUN;
	public static PowerTurret CERTAIN_CANNON;
	public static PowerTurret CERTAIN_GUN;
	//[T3]
	public static ItemTurret LIGHT_CANNON;
	public static ItemTurret AETHER_BLASTER;
	public static ItemTurret AETHER_DESTROYER;
	public static void load() {
		SUPER_LASER = new PowerTurret("SUPER_LASER") {{
			size = 2;
			range = 26 * 8;
			targetAir = false;
			consumePower(240 / 60f);
			shootSound = Sounds.laser;
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 80, ST_ITEM.SUPERCONDUCTOR, 50, Items.silicon, 50, Items.metaglass, 50, Items.graphite, 80);
			shootType = ST_BULLET.BULLET_LASER.build(140, ST_ITEM.SUPERCONDUCTOR, 26, 1.5f, 0.8f).bullet();
			inject(this);
		}};
		SUPER_GUN = new ItemTurret("SUPER_GUN") {{
			size = 2;
			range = 28 * 8;
			consumePower(180 / 60f);
			shootSound = Sounds.shoot;
			inaccuracy = 4;
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 100, ST_ITEM.SUPERCONDUCTOR, 50, Items.silicon, 50, Items.titanium, 50);
			ammoTypes.put(Items.graphite, ST_BULLET.BULLET_MACHINE_GUN.build(70 + 0.1f * 100f, ST_EFFECT.BLUE, 28, 0.4f, 1.5f).time(20).frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.WATER_ELEMENT, 8, 0.1f, 3f, 0.5f).bullet());
			ammoTypes.put(Items.pyratite, ST_BULLET.BULLET_MACHINE_GUN.build(70 + 0.1f * 200, ST_ITEM.LIGHT_ELEMENT, 30, 0.4f, 1.5f).time(20).frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.GOLD_ELEMENT, 8, 0.1f, 3f, 0.5f).bullet());
			ammoTypes.put(Items.blastCompound, ST_BULLET.BULLET_MACHINE_GUN.build(70 + 0.1f * 300, ST_ITEM.FIRE_ELEMENT, 30, 0.4f, 1.5f).time(20).frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.FIRE_ELEMENT, 8, 0.1f, 5f, 0.5f).bullet());
			inject(this);
		}};
		STINGER_MISSILE = new ItemTurret("STINGER_MISSILE") {{
			size = 2;
			range = 36 * 8;
			targetAir = true;
			consumePower(180 / 60f);
			shootSound = Sounds.missile;
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 80, ST_ITEM.SUPERCONDUCTOR, 50, Items.silicon, 50, Items.titanium, 50);
			ammoTypes.put(Items.pyratite, ST_BULLET.BULLET_MISSILE.build(80 + 0.25f * 100, ST_ITEM.GOLD_ELEMENT, 32, 0.5f, 2f).frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.GOLD_ELEMENT, 1, 0.35f, 13f, 0.5f).bullet());
			ammoTypes.put(Items.blastCompound, ST_BULLET.BULLET_MISSILE.build(80 + 0.25f * 150, ST_ITEM.FIRE_ELEMENT, 32, 0.5f, 2f).frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.GOLD_ELEMENT, 1, 0.85f, 13f, 0.5f).bullet());
			inject(this, 1);
		}};
		IMPACT = new ItemTurret("IMPACT") {{
			size = 2;
			range = 24 * 8;
			targetAir = false;
			//consumePower(780 / 60f);
			shootSound = Sounds.shoot;
			inaccuracy = 6;
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 100, ST_ITEM.SUPERCONDUCTOR, 50, Items.titanium, 50, Items.silicon, 50, Items.graphite, 50);
			ammoTypes.put(Items.copper, ST_BULLET.BULLET_MACHINE_GUN.build(130, ST_ITEM.FIRE_ELEMENT, 24, 7f, 0.8f).frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.GOLD_ELEMENT, 1, 0.25f, 6f, 0.5f).bullet(bullet -> bullet.homingRange = 16));
			ammoTypes.put(Items.lead, ST_BULLET.BULLET_TRIANGLE.build(90, ST_ITEM.WATER_ELEMENT, 24, 4f, 0.8f).frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.WATER_ELEMENT, 1, 0.25f, 6f, 0.5f).bullet(bullet -> bullet.homingRange = 16));
			inject(this, 1);
		}};
		PULSE = new PowerTurret("PULSE") {{
			size = 3;
			range = 34 * 8;
			targetAir = true;
			targetGround = false;
			consumePower(480 / 60f);
			shootSound = Sounds.missile;
			inaccuracy = 14;
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 150, ST_ITEM.SUPERCONDUCTOR, 50, ST_ITEM.CHROMAL, 50, Items.silicon, 50);
			shootType = ST_BULLET.BULLET_TRIANGLE
				.build(520, ST_ITEM.GOLD_ELEMENT, 34, 16f, 1f)
				.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.GOLD_ELEMENT, 1, 1f, 2, 0.5f)
				.bullet(bullet -> bullet.homingRange = 16);
			inject(this, 1);
		}};
		ION_BEAM = new ContinuousLiquidTurret("ION_BEAM") {{
			liquidCapacity = 240;
			size = 3;
			range = 20 * 8;
			consumePower(600 / 60f * 1.5f);
			loopSound = Sounds.beam;
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 120, ST_ITEM.SUPERCONDUCTOR, 50, ST_ITEM.CHROMAL, 50, Items.graphite, 60);
			ammo(Liquids.water, ST_BULLET.BULLET_CONTINUE_FLAME
				.build(420, ST_ITEM.WATER_ELEMENT, 20, 1, 1.5f)
				.ammoMultiplier(1 / 48f)
				.bullet())
			;
			inject(this, 1);
		}};
		PHOTON_GUN = new PowerTurret("PHOTON_GUN") {{
			size = 3;
			range = 36 * 8;
			consumePower(600 / 60f);
			shootSound = Sounds.laser;
			inaccuracy = 2;
			shootEffect = Fx.lightningShoot;
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 120, ST_ITEM.SUPERCONDUCTOR, 50, ST_ITEM.CHROMAL, 50, Items.graphite, 100);
			shootType = ST_BULLET.BULLET_ELEMENT
				.build(540, ST_ITEM.GOLD_ELEMENT, 36, 1, 1f)
				.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.GOLD_ELEMENT, 1, 0.5f, 10, 1)
				.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.GOLD_ELEMENT, 1, 0.2f, 10, 1)
				.bullet();
			inject(this, 1);
		}};
		//
		ELECTRONIC_WAVE = new PowerTurret("ELECTRONIC_WAVE") {{
			shoot = new ShootSpread(15, 4f);
			size = 3;
			range = 34 * 8;
			shootEffect = smokeEffect = Fx.lightningShoot;
			consumePower(600 / 60f);
			shootSound = Sounds.shotgun;
			shootEffect = Fx.shootTitan;
			inaccuracy = 1;
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 100, ST_ITEM.SUPERCONDUCTOR, 50, ST_ITEM.CHROMAL, 50, Items.graphite, 100, Items.metaglass, 50, Items.thorium, 80);
			shootType = ST_BULLET.BULLET_SPREAD.build(900 / 12f, ST_ITEM.GOLD_ELEMENT, 34, 1, 1f).bullet();
			inject(this, 1);
		}};
		ARMSTRONG_GUN = new ItemTurret("ARMSTRONG_GUN") {{
			size = 3;
			range = 32 * 8;
			shootSound = Sounds.shootBig;
			shootEffect = Fx.shootBig;
			requirements = ItemStack.with(ST_ITEM.SUPERCONDUCTOR, 50, ST_ITEM.NANOTUBE, 100, Items.titanium, 50, Items.silicon, 150);
			ammoTypes.put(Items.blastCompound, ST_BULLET.BULLET_CANNON.build(600, ST_ITEM.SUPERCONDUCTOR, 48, 0.75f, 0.5f).frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.CHROMAL, 1, 0.3f, 5, 1).frag(ST_BULLET.BULLET_ELEMENT_IMPACT, ST_ITEM.SUPERCONDUCTOR, 2, 0.5f, 2, 1f).bullet());
			ammoTypes.put(Items.pyratite, ST_BULLET.BULLET_CANNON.build(300, ST_ITEM.METRYSTAl, 48, 0.75f, 0.5f).frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.CHROMAL, 1, 0.8f, 5, 1).frag(ST_BULLET.SHELL_STAR, ST_ITEM.METRYSTAl, 4, 0.25f, 2, 1f).bullet());
			inject(this, 1);
		}};
		ELECTRONIC_SPECTRE = new ItemTurret("ELECTRONIC_SPECTRE") {{
			size = 4;
			range = 36 * 8;
			consumePower(360 / 60f);
			shootSound = Sounds.shootBig;
			shootEffect = Fx.shootSmokeTitan;
			inaccuracy = 5;
			requirements = ItemStack.with(ST_ITEM.SUPERCONDUCTOR, 50, ST_ITEM.NANOTUBE, 150, ST_ITEM.CHROMAL, 50, Items.silicon, 150);
			ammoTypes.put(Items.thorium, ST_BULLET.BULLET_MACHINE_GUN
				.build(1200, ST_ITEM.FIRE_ELEMENT, 36, 12, 1f)
				.bullet()
			);
			ammoTypes.put(Items.titanium, ST_BULLET.BULLET_MACHINE_GUN
				.build(940, ST_ITEM.WATER_ELEMENT, 36, 12, 1f).bullet()
			);
			ammoTypes.put(Items.metaglass, ST_BULLET.BULLET_MACHINE_GUN
				.build(1600, ST_ITEM.CHROMAL, 36, 14, 2f)
				.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, ST_ITEM.CHROMAL, 1, 0.8f, 1, 1)
				.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.CHROMAL, 1, 0.2f, 3, 1)
				.bullet()
			);
			ammoTypes.put(Items.pyratite, ST_BULLET.BULLET_MACHINE_GUN
				.build(1400, ST_ITEM.GOLD_ELEMENT, 36, 10, 1f)
				.status(StatusEffects.burning)
				.bullet()
			);
			ammoTypes.put(Items.blastCompound, ST_BULLET.BULLET_MACHINE_GUN
				.build(1200, ST_ITEM.FIRE_ELEMENT, 36, 4, 1.5f)
				.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.CHROMAL, 1, 0.5f, 3, 1)
				.bullet()
			);
			inject(this, 1);
		}};
		MASS_GUN = new ItemTurret("MASS_GUN") {{
			size = 3;
			range = 40 * 8;
			consumePower(105 / 60f);
			shootSound = Sounds.shootBig;
			shootEffect = Fx.shootBigSmoke;
			inaccuracy = 4;
			requirements = ItemStack.with(Items.lead, 125, Items.titanium, 125, Items.thorium, 50, Items.silicon, 75, ST_ITEM.NANOTUBE, 50);
			ammoTypes.put(Items.copper, ST_BULLET.BULLET_CANNON.build(120, ST_ITEM.SUPERCONDUCTOR, 40, 1, 0.8f).frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.SUPERCONDUCTOR, 1, 0.5f).bullet());
			ammoTypes.put(Items.lead, ST_BULLET.BULLET_CANNON.build(130, ST_ITEM.SUSPENDED, 40, 1, 0.8f).frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.SUSPENDED, 1, 0.5f).bullet());
			inject(this, 1);
		}};
		//[T2]
		CERTAIN_RAIL_GUN = new PowerTurret("CERTAIN_RAIL_GUN") {{
			size = 4;
			range = 63 * 8;
			consumePower(1200 / 60f);
			shootSound = Sounds.railgun;
			requirements = ItemStack.with(ST_ITEM.SUPERCONDUCTOR, 100, ST_ITEM.NANOTUBE, 150, ST_ITEM.SUSPENDED, 50, ST_ITEM.METRYSTAl, 50, ST_ITEM.CHROMAL, 50);
			shootType = ST_BULLET.BULLET_RAIL_GUN.build(3200 / 5f, ST_ITEM.METRYSTAl, 63, 1 / 5f, 1.5f).frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.METRYSTAl, 1, 0.2f, 4, 1).bullet();
			inject(this, 2);
		}};
		CERTAIN_CANNON = new PowerTurret("CERTAIN_CANNON") {{
			size = 3;
			range = 46 * 8;
			consumePower(1200 / 60f);
			shootSound = Sounds.laser;
			shootEffect = Fx.lightningShoot;
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 500, ST_ITEM.CHROMAL, 350, ST_ITEM.SUPERCONDUCTOR, 50, ST_ITEM.METRYSTAl, 50, ST_ITEM.ANTIMATTER, 10);
			shootType = ST_BULLET.BULLET_ELEMENT
				.build(700f / 4, ST_ITEM.WATER_ELEMENT, 46, 1f / 4, 1f)
				.frag(ST_BULLET.SHELL_LIGHT, ST_ITEM.WATER_ELEMENT, 4, 0.9f, 6f, 1)
				.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.WATER_ELEMENT, 1, 0.9f, 8f, 1)
				.bullet();
			inject(this, 2);
		}};
		CERTAIN_GUN = new PowerTurret("CERTAIN_GUN") {{
			shootSound = Sounds.lasershoot;
			shootEffect = Fx.lightningShoot;
			size = 3;
			range = 48 * 8;
			inaccuracy = 3;
			consumePower(1400 / 60f);
			requirements = ItemStack.with(ST_ITEM.NANOTUBE, 500, ST_ITEM.CHROMAL, 350, ST_ITEM.SUPERCONDUCTOR, 50, ST_ITEM.METRYSTAl, 75, ST_ITEM.ANTIMATTER, 10);
			shootType = ST_BULLET.BULLET_TRIANGLE
				.build(1300, ST_ITEM.WATER_ELEMENT, 48, 24, 1f)
				.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, ST_ITEM.WATER_ELEMENT, 1, 1f, 2f, 0.5f)
				.bullet();
			inject(this, 2);
		}};
		//[T3]
		LIGHT_CANNON = new ItemTurret("LIGHT_CANNON") {{
			consumePower(24000 / 60f);
			shootSound = Sounds.railgun;
			shootEffect = Fx.lightningShoot;
			size = 8;
			range = 76 * 8;
			requirements = ItemStack.with(
				ST_ITEM.LIGHT_ELEMENT, 1,
				ST_ITEM.NANOTUBE, 1200,
				ST_ITEM.CHROMAL, 500,
				ST_ITEM.SUPERCONDUCTOR, 400,
				ST_ITEM.METRYSTAl, 150
			);
			ammoTypes.put(ST_ITEM.GOLD_ELEMENT, ST_BULLET.LIGHT
				.build(4200 / 4f, ST_ITEM.WATER_ELEMENT, 76, 1 / 4f, 4)
				.frag(ST_BULLET.SHELL_LIGHT, ST_ITEM.LIGHT_ELEMENT, 20, 2, 18, 2)
				.frag(ST_BULLET.SHELL_STAR, ST_ITEM.LIGHT_ELEMENT, 1, 2, 16, 2)
				.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.LIGHT_ELEMENT, 1, 3, 16, 1)
				.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.WATER_ELEMENT, 1, 1, 5, 2f)
				.bullet()
			);
			inject(this, 3);
		}};
		AETHER_BLASTER = new ItemTurret("AETHER_BLASTER") {
			{
				drawer = new DrawTurretShadow();
				consumePower(14000 / 60f);
				ammoPerShot = 5;
				size = 8;
				range = 78 * 8;
				shootSound = Sounds.railgun;
				requirements = ItemStack.with(
					ST_ITEM.NANOTUBE, 1500,
					ST_ITEM.CHROMAL, 700,
					ST_ITEM.SUPERCONDUCTOR, 400,
					ST_ITEM.METRYSTAl, 200,
					ST_ITEM.SUSPENDED, 150
				);
				ammoTypes.put(ST_ITEM.CHROMAL, ST_BULLET.BULLET_RAIL_GUN
					.build(900f, ST_ITEM.CHROMAL, 75, 0.5f, 2.5f)
					.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.CHROMAL, 3, 0.2f, 3, 2f)
					.bullet()
				);
				ammoTypes.put(ST_ITEM.ANTIMATTER, ST_BULLET.BULLET_RAIL_GUN
					.build(1260f, ST_ITEM.ANTIMATTER, 75, 0.5f, 2.5f)
					.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.GOLD_ELEMENT, 3, 0.2f, 3, 2f)
					.bullet()
				);
				ammoTypes.put(Items.surgeAlloy, ST_BULLET.BULLET_RAIL_GUN
					.build(800, ST_ITEM.GOLD_ELEMENT, 75, 0.5f, 2.5f)
					.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.GOLD_ELEMENT, 3, 0.2f, 2, 2f)
					.bullet()
				);
				ammoTypes.put(ST_ITEM.GOLD_ELEMENT, ST_BULLET.BULLET_RAIL_GUN
					.build(1200, ST_ITEM.GOLD_ELEMENT, 75, 0.5f, 2.5f)
					.status(StatusEffects.shocked)
					.frag(ST_BULLET.SHELL_LIGHT, ST_ITEM.GOLD_ELEMENT, 2, 1f, 8, 2f)
					.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.GOLD_ELEMENT, 3, 1f, 4, 1f)
					.bullet());
				ammoTypes.put(ST_ITEM.WOOD_ELEMENT, ST_BULLET.BULLET_RAIL_GUN
					.build(1150, ST_ITEM.WOOD_ELEMENT, 120, 0.5f, 2.5f)
					.status(StatusEffects.corroded)
					.frag(ST_BULLET.SHELL_LIGHT, ST_ITEM.FIRE_ELEMENT, 2, 1f, 8, 2f)
					.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.FIRE_ELEMENT, 1, 0.1f, 8, 2f)
					.bullet())
				;
				ammoTypes.put(ST_ITEM.WATER_ELEMENT, ST_BULLET.BULLET_RAIL_GUN
					.build(950, ST_ITEM.WATER_ELEMENT, 120, 0.5f, 2.5f)
					.status(StatusEffects.wet)
					.frag(ST_BULLET.SHELL_LIGHT, ST_ITEM.WATER_ELEMENT, 2, 1f, 8, 2f)
					.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.WATER_ELEMENT, 1, 0.1f, 8, 2f)
					.bullet()
				);
				ammoTypes.put(ST_ITEM.FIRE_ELEMENT, ST_BULLET.BULLET_RAIL_GUN
					.build(1100, ST_ITEM.FIRE_ELEMENT, 120, 0.5f, 2.5f)
					.status(StatusEffects.blasted)
					.frag(ST_BULLET.SHELL_LIGHT, ST_ITEM.FIRE_ELEMENT, 2, 1f, 8, 2f)
					.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.FIRE_ELEMENT, 1, 0.1f, 8, 2f)
					.bullet()
				);
				ammoTypes.put(ST_ITEM.EARTH_ELEMENT, ST_BULLET.BULLET_RAIL_GUN
					.build(1000, ST_ITEM.EARTH_ELEMENT, 120, 0.5f, 2.5f)
					.status(StatusEffects.slow)
					.frag(ST_BULLET.SHELL_LIGHT, ST_ITEM.EARTH_ELEMENT, 2, 1f, 8, 2f)
					.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.EARTH_ELEMENT, 1, 0.1f, 8, 2f)
					.bullet()
				);
				ammoTypes.put(ST_ITEM.LIGHT_ELEMENT, ST_BULLET.BULLET_RAIL_GUN.build(2500, ST_ITEM.LIGHT_ELEMENT, 120, 0.5f, 2.5f).vectorShock().status(StatusEffects.disarmed)
					.frag(ST_BULLET.SHELL_LIGHT, ST_ITEM.LIGHT_ELEMENT, 2, 1f, 8, 2f)
					.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.LIGHT_ELEMENT, 1, 0.9f, 8f, 2)
					.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.LIGHT_ELEMENT, 3, 0.8f, 8, 2f)
					.bullet()
				);
				ammoTypes.put(ST_ITEM.DARK_ELEMENT, ST_BULLET.BULLET_RAIL_GUN
					.build(2500, ST_ITEM.DARK_ELEMENT, 120, 0.5f, 2.5f).status(StatusEffects.wet)
					.frag(ST_BULLET.SHELL_LIGHT, ST_ITEM.DARK_ELEMENT, 2, 1f, 8, 2f)
					.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.DARK_ELEMENT, 1, 0.9f, 8f, 2)
					.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.DARK_ELEMENT, 3, 0.8f, 8, 2f)
					.bullet()
				);
				inject(this, 3);
			}
		};
		AETHER_DESTROYER = new ItemTurret("AETHER_DESTROYER") {{
			drawer = new DrawTurretShadow();
			ammoPerShot = 25;
			range = 180 * 8;
			size = 14;
			consumePower(64000 / 60f);
			shootSound = Sounds.railgun;
			requirements = ItemStack.with(
				ST_ITEM.LIGHT_ELEMENT, 5,
				ST_ITEM.DARK_ELEMENT, 5,
				ST_ITEM.NANOTUBE, 2500,
				ST_ITEM.SUPERCONDUCTOR, 1000,
				ST_ITEM.CHROMAL, 2000,
				ST_ITEM.SUSPENDED, 500,
				ST_ITEM.METRYSTAl, 750
			);
			ammoTypes.put(ST_ITEM.LIGHT_ELEMENT, ST_BULLET.BULLET_RAIL_GUN
				.build(24000, ST_ITEM.LIGHT_ELEMENT, 180, 0.3f, 4f)
				.status(StatusEffects.slow)
				.status(StatusEffects.burning)
				.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.LIGHT_ELEMENT, 1, 0.4f, 14, 3)
				.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.LIGHT_ELEMENT, 1, 0.3f, 3, 3)
				.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.LIGHT_ELEMENT, 6, 0.2f, 8, 3)
				.bullet()
			);
			ammoTypes.put(ST_ITEM.DARK_ELEMENT, ST_BULLET.BULLET_RAIL_GUN
				.build(22000, ST_ITEM.DARK_ELEMENT, 180, 0.3f, 4f)
				.status(StatusEffects.corroded)
				.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.DARK_ELEMENT, 1, 0.4f, 14, 3)
				.status(StatusEffects.wet)
				.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.DARK_ELEMENT, 1, 0.2f, 8, 3)
				.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.DARK_ELEMENT, 6, 0.2f, 8, 3)
				.bullet()
			);
			inject(this, 3);
		}};
	}
}
