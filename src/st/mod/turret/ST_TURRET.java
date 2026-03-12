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
import st.mod.item.STItem;
import st.mod.UtilTooltip;
import st.mod.turret.entity.DrawTurretShadow;


public class ST_TURRET {
/*
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
		UtilTooltip.Tooltip(t.stats).TechLevel(level);
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
			requirements = ItemStack.with(STItem.Nanotube, 80, STItem.Superconductor, 50, Items.silicon, 50, Items.metaglass, 50, Items.graphite, 80);
			shootType = ST_BULLET.BULLET_LASER.build(140, STItem.Superconductor, 26, 1.5f, 0.8f).bullet();
			inject(this);
		}};
		SUPER_GUN = new ItemTurret("SUPER_GUN") {{
			size = 2;
			range = 28 * 8;
			consumePower(180 / 60f);
			shootSound = Sounds.shoot;
			inaccuracy = 4;
			requirements = ItemStack.with(STItem.Nanotube, 100, STItem.Superconductor, 50, Items.silicon, 50, Items.titanium, 50);
			ammoTypes.put(Items.graphite, ST_BULLET.BULLET_MACHINE_GUN.build(70 + 0.1f * 100f, ST_EFFECT.BLUE, 28, 0.4f, 1.5f).time(20).frag(ST_BULLET.SHELL_EXPLODE, STItem.WATER_ELEMENT, 8, 0.1f, 3f, 0.5f).bullet());
			ammoTypes.put(Items.pyratite, ST_BULLET.BULLET_MACHINE_GUN.build(70 + 0.1f * 200, STItem.LIGHT_ELEMENT, 30, 0.4f, 1.5f).time(20).frag(ST_BULLET.SHELL_EXPLODE, STItem.GOLD_ELEMENT, 8, 0.1f, 3f, 0.5f).bullet());
			ammoTypes.put(Items.blastCompound, ST_BULLET.BULLET_MACHINE_GUN.build(70 + 0.1f * 300, STItem.FIRE_ELEMENT, 30, 0.4f, 1.5f).time(20).frag(ST_BULLET.SHELL_EXPLODE, STItem.FIRE_ELEMENT, 8, 0.1f, 5f, 0.5f).bullet());
			inject(this);
		}};
		STINGER_MISSILE = new ItemTurret("STINGER_MISSILE") {{
			size = 2;
			range = 36 * 8;
			targetAir = true;
			consumePower(180 / 60f);
			shootSound = Sounds.missile;
			requirements = ItemStack.with(STItem.Nanotube, 80, STItem.Superconductor, 50, Items.silicon, 50, Items.titanium, 50);
			ammoTypes.put(Items.pyratite, ST_BULLET.BULLET_MISSILE.build(80 + 0.25f * 100, STItem.GOLD_ELEMENT, 32, 0.5f, 2f).frag(ST_BULLET.SHELL_EXPLODE, STItem.GOLD_ELEMENT, 1, 0.35f, 13f, 0.5f).bullet());
			ammoTypes.put(Items.blastCompound, ST_BULLET.BULLET_MISSILE.build(80 + 0.25f * 150, STItem.FIRE_ELEMENT, 32, 0.5f, 2f).frag(ST_BULLET.SHELL_EXPLODE, STItem.GOLD_ELEMENT, 1, 0.85f, 13f, 0.5f).bullet());
			inject(this, 1);
		}};
		IMPACT = new ItemTurret("IMPACT") {{
			size = 2;
			range = 24 * 8;
			targetAir = false;
			//consumePower(780 / 60f);
			shootSound = Sounds.shoot;
			inaccuracy = 6;
			requirements = ItemStack.with(STItem.Nanotube, 100, STItem.Superconductor, 50, Items.titanium, 50, Items.silicon, 50, Items.graphite, 50);
			ammoTypes.put(Items.copper, ST_BULLET.BULLET_MACHINE_GUN.build(130, STItem.FIRE_ELEMENT, 24, 7f, 0.8f).frag(ST_BULLET.SHELL_IMPACT, STItem.GOLD_ELEMENT, 1, 0.25f, 6f, 0.5f).bullet(bullet -> bullet.homingRange = 16));
			ammoTypes.put(Items.lead, ST_BULLET.BULLET_TRIANGLE.build(90, STItem.WATER_ELEMENT, 24, 4f, 0.8f).frag(ST_BULLET.SHELL_IMPACT, STItem.WATER_ELEMENT, 1, 0.25f, 6f, 0.5f).bullet(bullet -> bullet.homingRange = 16));
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
			requirements = ItemStack.with(STItem.Nanotube, 150, STItem.Superconductor, 50, STItem.Chromal, 50, Items.silicon, 50);
			shootType = ST_BULLET.BULLET_TRIANGLE
				.build(520, STItem.GOLD_ELEMENT, 34, 16f, 1f)
				.frag(ST_BULLET.SHELL_LIGHTNING, STItem.GOLD_ELEMENT, 1, 1f, 2, 0.5f)
				.bullet(bullet -> bullet.homingRange = 16);
			inject(this, 1);
		}};
		ION_BEAM = new ContinuousLiquidTurret("ION_BEAM") {{
			liquidCapacity = 240;
			size = 3;
			range = 20 * 8;
			consumePower(600 / 60f * 1.5f);
			loopSound = Sounds.beam;
			requirements = ItemStack.with(STItem.Nanotube, 120, STItem.Superconductor, 50, STItem.Chromal, 50, Items.graphite, 60);
			ammo(Liquids.water, ST_BULLET.BULLET_CONTINUE_FLAME
				.build(420, STItem.WATER_ELEMENT, 20, 1, 1.5f)
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
			requirements = ItemStack.with(STItem.Nanotube, 120, STItem.Superconductor, 50, STItem.Chromal, 50, Items.graphite, 100);
			shootType = ST_BULLET.BULLET_ELEMENT
				.build(540, STItem.GOLD_ELEMENT, 36, 1, 1f)
				.frag(ST_BULLET.SHELL_EXPLODE, STItem.GOLD_ELEMENT, 1, 0.5f, 10, 1)
				.frag(ST_BULLET.SHELL_LIGHTNING, STItem.GOLD_ELEMENT, 1, 0.2f, 10, 1)
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
			requirements = ItemStack.with(STItem.Nanotube, 100, STItem.Superconductor, 50, STItem.Chromal, 50, Items.graphite, 100, Items.metaglass, 50, Items.thorium, 80);
			shootType = ST_BULLET.BULLET_SPREAD.build(900 / 12f, STItem.GOLD_ELEMENT, 34, 1, 1f).bullet();
			inject(this, 1);
		}};
		ARMSTRONG_GUN = new ItemTurret("ARMSTRONG_GUN") {{
			size = 3;
			range = 32 * 8;
			shootSound = Sounds.shootBig;
			shootEffect = Fx.shootBig;
			requirements = ItemStack.with(STItem.Superconductor, 50, STItem.Nanotube, 100, Items.titanium, 50, Items.silicon, 150);
			ammoTypes.put(Items.blastCompound, ST_BULLET.BULLET_CANNON.build(600, STItem.Superconductor, 48, 0.75f, 0.5f).frag(ST_BULLET.SHELL_EXPLODE, STItem.Chromal, 1, 0.3f, 5, 1).frag(ST_BULLET.BULLET_ELEMENT_IMPACT, STItem.Superconductor, 2, 0.5f, 2, 1f).bullet());
			ammoTypes.put(Items.pyratite, ST_BULLET.BULLET_CANNON.build(300, STItem.Metrystal, 48, 0.75f, 0.5f).frag(ST_BULLET.SHELL_EXPLODE, STItem.Chromal, 1, 0.8f, 5, 1).frag(ST_BULLET.SHELL_STAR, STItem.Metrystal, 4, 0.25f, 2, 1f).bullet());
			inject(this, 1);
		}};
		ELECTRONIC_SPECTRE = new ItemTurret("ELECTRONIC_SPECTRE") {{
			size = 4;
			range = 36 * 8;
			consumePower(360 / 60f);
			shootSound = Sounds.shootBig;
			shootEffect = Fx.shootSmokeTitan;
			inaccuracy = 5;
			requirements = ItemStack.with(STItem.Superconductor, 50, STItem.Nanotube, 150, STItem.Chromal, 50, Items.silicon, 150);
			ammoTypes.put(Items.thorium, ST_BULLET.BULLET_MACHINE_GUN
				.build(1200, STItem.FIRE_ELEMENT, 36, 12, 1f)
				.bullet()
			);
			ammoTypes.put(Items.titanium, ST_BULLET.BULLET_MACHINE_GUN
				.build(940, STItem.WATER_ELEMENT, 36, 12, 1f).bullet()
			);
			ammoTypes.put(Items.metaglass, ST_BULLET.BULLET_MACHINE_GUN
				.build(1600, STItem.Chromal, 36, 14, 2f)
				.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, STItem.Chromal, 1, 0.8f, 1, 1)
				.frag(ST_BULLET.SHELL_LIGHTNING, STItem.Chromal, 1, 0.2f, 3, 1)
				.bullet()
			);
			ammoTypes.put(Items.pyratite, ST_BULLET.BULLET_MACHINE_GUN
				.build(1400, STItem.GOLD_ELEMENT, 36, 10, 1f)
				.status(StatusEffects.burning)
				.bullet()
			);
			ammoTypes.put(Items.blastCompound, ST_BULLET.BULLET_MACHINE_GUN
				.build(1200, STItem.FIRE_ELEMENT, 36, 4, 1.5f)
				.frag(ST_BULLET.SHELL_EXPLODE, STItem.Chromal, 1, 0.5f, 3, 1)
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
			requirements = ItemStack.with(Items.lead, 125, Items.titanium, 125, Items.thorium, 50, Items.silicon, 75, STItem.Nanotube, 50);
			ammoTypes.put(Items.copper, ST_BULLET.BULLET_CANNON.build(120, STItem.Superconductor, 40, 1, 0.8f).frag(ST_BULLET.SHELL_EXPLODE, STItem.Superconductor, 1, 0.5f).bullet());
			ammoTypes.put(Items.lead, ST_BULLET.BULLET_CANNON.build(130, STItem.Suspended, 40, 1, 0.8f).frag(ST_BULLET.SHELL_EXPLODE, STItem.Suspended, 1, 0.5f).bullet());
			inject(this, 1);
		}};
		//[T2]
		CERTAIN_RAIL_GUN = new PowerTurret("CERTAIN_RAIL_GUN") {{
			size = 4;
			range = 63 * 8;
			consumePower(1200 / 60f);
			shootSound = Sounds.railgun;
			requirements = ItemStack.with(STItem.Superconductor, 100, STItem.Nanotube, 150, STItem.Suspended, 50, STItem.Metrystal, 50, STItem.Chromal, 50);
			shootType = ST_BULLET.BULLET_RAIL_GUN.build(3200 / 5f, STItem.Metrystal, 63, 1 / 5f, 1.5f).frag(ST_BULLET.SHELL_EXPLODE, STItem.Metrystal, 1, 0.2f, 4, 1).bullet();
			inject(this, 2);
		}};
		CERTAIN_CANNON = new PowerTurret("CERTAIN_CANNON") {{
			size = 3;
			range = 46 * 8;
			consumePower(1200 / 60f);
			shootSound = Sounds.laser;
			shootEffect = Fx.lightningShoot;
			requirements = ItemStack.with(STItem.Nanotube, 500, STItem.Chromal, 350, STItem.Superconductor, 50, STItem.Metrystal, 50, STItem.Antimatter, 10);
			shootType = ST_BULLET.BULLET_ELEMENT
				.build(700f / 4, STItem.WATER_ELEMENT, 46, 1f / 4, 1f)
				.frag(ST_BULLET.SHELL_LIGHT, STItem.WATER_ELEMENT, 4, 0.9f, 6f, 1)
				.frag(ST_BULLET.SHELL_EXPLODE, STItem.WATER_ELEMENT, 1, 0.9f, 8f, 1)
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
			requirements = ItemStack.with(STItem.Nanotube, 500, STItem.Chromal, 350, STItem.Superconductor, 50, STItem.Metrystal, 75, STItem.Antimatter, 10);
			shootType = ST_BULLET.BULLET_TRIANGLE
				.build(1300, STItem.WATER_ELEMENT, 48, 24, 1f)
				.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, STItem.WATER_ELEMENT, 1, 1f, 2f, 0.5f)
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
				STItem.LIGHT_ELEMENT, 1,
				STItem.Nanotube, 1200,
				STItem.Chromal, 500,
				STItem.Superconductor, 400,
				STItem.Metrystal, 150
			);
			ammoTypes.put(STItem.GOLD_ELEMENT, ST_BULLET.LIGHT
				.build(4200 / 4f, STItem.WATER_ELEMENT, 76, 1 / 4f, 4)
				.frag(ST_BULLET.SHELL_LIGHT, STItem.LIGHT_ELEMENT, 20, 2, 18, 2)
				.frag(ST_BULLET.SHELL_STAR, STItem.LIGHT_ELEMENT, 1, 2, 16, 2)
				.frag(ST_BULLET.SHELL_EXPLODE, STItem.LIGHT_ELEMENT, 1, 3, 16, 1)
				.frag(ST_BULLET.SHELL_LIGHTNING, STItem.WATER_ELEMENT, 1, 1, 5, 2f)
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
					STItem.Nanotube, 1500,
					STItem.Chromal, 700,
					STItem.Superconductor, 400,
					STItem.Metrystal, 200,
					STItem.Suspended, 150
				);
				ammoTypes.put(STItem.Chromal, ST_BULLET.BULLET_RAIL_GUN
					.build(900f, STItem.Chromal, 75, 0.5f, 2.5f)
					.frag(ST_BULLET.SHELL_LIGHTNING, STItem.Chromal, 3, 0.2f, 3, 2f)
					.bullet()
				);
				ammoTypes.put(STItem.Antimatter, ST_BULLET.BULLET_RAIL_GUN
					.build(1260f, STItem.Antimatter, 75, 0.5f, 2.5f)
					.frag(ST_BULLET.SHELL_EXPLODE, STItem.GOLD_ELEMENT, 3, 0.2f, 3, 2f)
					.bullet()
				);
				ammoTypes.put(Items.surgeAlloy, ST_BULLET.BULLET_RAIL_GUN
					.build(800, STItem.GOLD_ELEMENT, 75, 0.5f, 2.5f)
					.frag(ST_BULLET.SHELL_LIGHTNING, STItem.GOLD_ELEMENT, 3, 0.2f, 2, 2f)
					.bullet()
				);
				ammoTypes.put(STItem.GOLD_ELEMENT, ST_BULLET.BULLET_RAIL_GUN
					.build(1200, STItem.GOLD_ELEMENT, 75, 0.5f, 2.5f)
					.status(StatusEffects.shocked)
					.frag(ST_BULLET.SHELL_LIGHT, STItem.GOLD_ELEMENT, 2, 1f, 8, 2f)
					.frag(ST_BULLET.SHELL_LIGHTNING, STItem.GOLD_ELEMENT, 3, 1f, 4, 1f)
					.bullet());
				ammoTypes.put(STItem.WOOD_ELEMENT, ST_BULLET.BULLET_RAIL_GUN
					.build(1150, STItem.WOOD_ELEMENT, 120, 0.5f, 2.5f)
					.status(StatusEffects.corroded)
					.frag(ST_BULLET.SHELL_LIGHT, STItem.FIRE_ELEMENT, 2, 1f, 8, 2f)
					.frag(ST_BULLET.SHELL_LIGHTNING, STItem.FIRE_ELEMENT, 1, 0.1f, 8, 2f)
					.bullet())
				;
				ammoTypes.put(STItem.WATER_ELEMENT, ST_BULLET.BULLET_RAIL_GUN
					.build(950, STItem.WATER_ELEMENT, 120, 0.5f, 2.5f)
					.status(StatusEffects.wet)
					.frag(ST_BULLET.SHELL_LIGHT, STItem.WATER_ELEMENT, 2, 1f, 8, 2f)
					.frag(ST_BULLET.SHELL_LIGHTNING, STItem.WATER_ELEMENT, 1, 0.1f, 8, 2f)
					.bullet()
				);
				ammoTypes.put(STItem.FIRE_ELEMENT, ST_BULLET.BULLET_RAIL_GUN
					.build(1100, STItem.FIRE_ELEMENT, 120, 0.5f, 2.5f)
					.status(StatusEffects.blasted)
					.frag(ST_BULLET.SHELL_LIGHT, STItem.FIRE_ELEMENT, 2, 1f, 8, 2f)
					.frag(ST_BULLET.SHELL_LIGHTNING, STItem.FIRE_ELEMENT, 1, 0.1f, 8, 2f)
					.bullet()
				);
				ammoTypes.put(STItem.EARTH_ELEMENT, ST_BULLET.BULLET_RAIL_GUN
					.build(1000, STItem.EARTH_ELEMENT, 120, 0.5f, 2.5f)
					.status(StatusEffects.slow)
					.frag(ST_BULLET.SHELL_LIGHT, STItem.EARTH_ELEMENT, 2, 1f, 8, 2f)
					.frag(ST_BULLET.SHELL_LIGHTNING, STItem.EARTH_ELEMENT, 1, 0.1f, 8, 2f)
					.bullet()
				);
				ammoTypes.put(STItem.LIGHT_ELEMENT, ST_BULLET.BULLET_RAIL_GUN.build(2500, STItem.LIGHT_ELEMENT, 120, 0.5f, 2.5f).vectorShock().status(StatusEffects.disarmed)
					.frag(ST_BULLET.SHELL_LIGHT, STItem.LIGHT_ELEMENT, 2, 1f, 8, 2f)
					.frag(ST_BULLET.SHELL_EXPLODE, STItem.LIGHT_ELEMENT, 1, 0.9f, 8f, 2)
					.frag(ST_BULLET.SHELL_LIGHTNING, STItem.LIGHT_ELEMENT, 3, 0.8f, 8, 2f)
					.bullet()
				);
				ammoTypes.put(STItem.Darkmatter, ST_BULLET.BULLET_RAIL_GUN
					.build(2500, STItem.Darkmatter, 120, 0.5f, 2.5f).status(StatusEffects.wet)
					.frag(ST_BULLET.SHELL_LIGHT, STItem.Darkmatter, 2, 1f, 8, 2f)
					.frag(ST_BULLET.SHELL_EXPLODE, STItem.Darkmatter, 1, 0.9f, 8f, 2)
					.frag(ST_BULLET.SHELL_LIGHTNING, STItem.Darkmatter, 3, 0.8f, 8, 2f)
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
				STItem.LIGHT_ELEMENT, 5,
				STItem.Darkmatter, 5,
				STItem.Nanotube, 2500,
				STItem.Superconductor, 1000,
				STItem.Chromal, 2000,
				STItem.Suspended, 500,
				STItem.Metrystal, 750
			);
			ammoTypes.put(STItem.LIGHT_ELEMENT, ST_BULLET.BULLET_RAIL_GUN
				.build(24000, STItem.LIGHT_ELEMENT, 180, 0.3f, 4f)
				.status(StatusEffects.slow)
				.status(StatusEffects.burning)
				.frag(ST_BULLET.SHELL_IMPACT, STItem.LIGHT_ELEMENT, 1, 0.4f, 14, 3)
				.frag(ST_BULLET.SHELL_EXPLODE, STItem.LIGHT_ELEMENT, 1, 0.3f, 3, 3)
				.frag(ST_BULLET.SHELL_LIGHTNING, STItem.LIGHT_ELEMENT, 6, 0.2f, 8, 3)
				.bullet()
			);
			ammoTypes.put(STItem.Darkmatter, ST_BULLET.BULLET_RAIL_GUN
				.build(22000, STItem.Darkmatter, 180, 0.3f, 4f)
				.status(StatusEffects.corroded)
				.frag(ST_BULLET.SHELL_IMPACT, STItem.Darkmatter, 1, 0.4f, 14, 3)
				.status(StatusEffects.wet)
				.frag(ST_BULLET.SHELL_EXPLODE, STItem.Darkmatter, 1, 0.2f, 8, 3)
				.frag(ST_BULLET.SHELL_LIGHTNING, STItem.Darkmatter, 6, 0.2f, 8, 3)
				.bullet()
			);
			inject(this, 3);
		}};
	}*/
}
