package st.mod.unit;


import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.abilities.EnergyFieldAbility;
import mindustry.entities.abilities.MoveLightningAbility;
import mindustry.entities.abilities.UnitSpawnAbility;
import mindustry.entities.pattern.ShootPattern;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.meta.BuildVisibility;
import st.mod.attack.ST_BULLET;
import st.mod.item.ST_ITEM;
import st.mod.UTIL_TOOLTIP;
import st.mod.util.STooltipBuilder;
import st.mod.unit.entity.SUnitType;
import st.mod.unit.entity.SWeapon;


public class ST_UNIT {
	public static STooltipBuilder inject(UnitType unit) {
		return UTIL_TOOLTIP.tooltip(unit.stats);
	}
	public static STooltipBuilder inject(Block b) {
		b.category = Category.units;
		b.buildVisibility = BuildVisibility.shown;
		return UTIL_TOOLTIP.tooltip(b.stats);
	}
	public static STooltipBuilder inject(Block b, int level) {
		return inject(b).techLevel(level);
	}
	public static STooltipBuilder inject(UnitType unit, int level) {
		return inject(unit).techLevel(level);
	}
	public static UnitType MATRIX_BUILDING_UNIT;
	public static UnitType MATRIX_MINER_UNIT;
	public static UnitType MATRIX_ATTACK_UNIT;
	public static UnitType MATRIX_BUILDING_UNIT_EXTRA;
	public static UnitType MATRIX_MINER_UNIT_EXTRA;
	public static UnitType MATRIX_A1;
	public static UnitType MATRIX_A2;
	public static UnitType MATRIX_A3;
	public static UnitType MATRIX_A4;
	public static UnitType ZETA;
	public static UnitFactory MATRIX_UNIT_FACTORY;
	public static UnitFactory MATRIX_UNIT_FACTORY_EXTRA;
	public static void load() {
		MATRIX_BUILDING_UNIT = new SUnitType("MATRIX_BUILDING_UNIT") {{
			health = 500;
			armor = 4;
			flying = true;
			speed = 22 / 8f;
			buildSpeed = 4;
			itemCapacity = 120;
			mineTier = 2;
			mineSpeed = 2;
			hitSize = 2 * 8;
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_LASER) {{
				x = 1;
				y = 1;
				bullet(
					ST_BULLET.BULLET_MACHINE_GUN
						.build(20, ST_ITEM.SUPERCONDUCTOR, 28, 5, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.GOLD_ELEMENT, 1, 0.2f, 6, 0.5f)
						.bullet()
				);
			}});
			inject(this, 1);
		}};
		MATRIX_MINER_UNIT = new SUnitType("MATRIX_MINER_UNIT") {{
			health = 500;
			armor = 1;
			flying = true;
			speed = 20f / 8f;
			itemCapacity = 240;
			mineTier = 3;
			mineSpeed = 4;
			hitSize = 2 * 8;
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_LASER) {{
				x = 1;
				y = 1;
				bullet(
					ST_BULLET.BULLET_MACHINE_GUN
						.build(10, ST_ITEM.SUPERCONDUCTOR, 26, 5, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.GOLD_ELEMENT, 1, 0.2f, 4, 0.5f)
						.bullet()
				);
			}});
			inject(this, 1);
		}};
		MATRIX_ATTACK_UNIT = new SUnitType("MATRIX_ATTACK_UNIT") {{
			health = 1000;
			armor = 2;
			flying = true;
			speed = 30 / 8f;
			itemCapacity = 120;
			hitSize = 3 * 8;
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_LASER_BIG) {{
				x = 0;
				y = 5;
				bullet(
					ST_BULLET.BULLET_MACHINE_GUN
						.build(20, ST_ITEM.SUPERCONDUCTOR, 26, 10, 0.4f)
						.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.GOLD_ELEMENT, 1, 0.2f, 5, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_MISSILE) {{
				x = 0;
				y = 0;
				bullet(
					ST_BULLET.BULLET_MISSILE
						.build(40, ST_ITEM.SUPERCONDUCTOR, 26, 0.5f, 0.5f)
						.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.GOLD_ELEMENT, 1, 0.45f, 8, 0.5f)
						.bullet()
				);
			}});
			inject(this, 1);
		}};
		MATRIX_BUILDING_UNIT_EXTRA = new SUnitType("MATRIX_BUILDING_UNIT_EXTRA") {{
			health = 1500;
			armor = 1;
			flying = true;
			speed = 40 / 8f;
			buildSpeed = 8;
			itemCapacity = 300;
			mineTier = 4;
			mineSpeed = 4;
			hitSize = 2 * 8;
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 0;
				y = 0;
				bullet = ST_BULLET.BULLET_RAIL_GUN
					.build(80, Color.green, 36, 1, 0.5f)
					.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, ST_ITEM.WOOD_ELEMENT, 1, 0.8f, 6, 0.5f)
					.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.WATER_ELEMENT, 1, 0.5f, 6)
					.bullet();
			}});
		}};
		MATRIX_MINER_UNIT_EXTRA = new SUnitType("MATRIX_MINER_UNIT_EXTRA") {{
			health = 1500;
			armor = 1;
			flying = true;
			speed = 40 / 8f;
			buildSpeed = -1;
			itemCapacity = 500;
			mineTier = 6;
			mineSpeed = 8;
			hitSize = 2 * 8;
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 0;
				y = 0;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(80, Color.green, 36, 1, 0.5f)
						.bullet()
				);
			}});
		}};
		MATRIX_A1 = new SUnitType("MATRIX_A1") {{
			this.constructor = UnitEntity::create;
			flying = true;
			hitSize = 5 * 8;
			health = 2400;
			armor = 4;
			speed = 30 / 8f;
			itemCapacity = 600;
			engines.add(new UnitEngine(0, -4 * 8, 12f, -90));
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 12;
				y = -16;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(150, Color.green, 40, 2f, 0.5f)
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, ST_ITEM.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 8;
				y = 8;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(150, Color.green, 40, 1f, 0.5f)
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, ST_ITEM.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
						.bullet()
				);
			}});
		}};
		MATRIX_A2 = new SUnitType("MATRIX_A2") {{
			this.constructor = UnitEntity::create;
			flying = true;
			hitSize = 8 * 8;
			health = 12000;
			armor = 8;
			speed = 15 / 8f;
			itemCapacity = 300;
			engines.add(new UnitEngine(0, -7 * 8, 14f, -90));
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 4;
				y = 8;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(200, Color.green, 50, 3f, 0.5f)
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, ST_ITEM.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 12;
				y = -16;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(200, Color.green, 50, 2f, 0.5f)
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, ST_ITEM.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 20;
				y = -40;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(200, Color.green, 50, 1f, 0.5f)
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, ST_ITEM.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
						.bullet()
				);
			}});
		}};
		MATRIX_A3 = new SUnitType("MATRIX_A3") {{
			this.constructor = UnitEntity::create;
			flying = true;
			hitSize = 10 * 8;
			health = 30000;
			armor = 12;
			speed = 10 / 8f;
			itemCapacity = 1200;
			engines.add(new UnitEngine(0, -9.5f * 8, 16f, 90));
			abilities.add(new EnergyFieldAbility(60.218F, 90.0F, 8 * 3) {
				{
					this.healPercent = 0.01F;
				}
			});
			abilities.add(new UnitSpawnAbility(MATRIX_A1, 16 * 60, 0, 0));
			abilities.add(new ST_ABILITY.SHIELD_FIELD_EXTRA(12 * 18, 30.0F, 1500f, 30 * 60) {{
				sides = 3;
				color = ST_ITEM.WOOD_ELEMENT.color;
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 4;
				y = 8;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(400, Color.green, 50, 8f, 0.5f)
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, ST_ITEM.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 4 + 2f * 8;
				y = 4f * 8;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(400, Color.green, 50, 4f, 0.5f)
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, ST_ITEM.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 4 - 2f * 8;
				y = -2f * 8;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(400, Color.green, 50, 3f, 0.5f)
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, ST_ITEM.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 4 - 4f * 8;
				y = -5f * 8;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(400, Color.green, 50, 2f, 0.5f)
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, ST_ITEM.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 0;
				y = 0;
				mirror = true;
				shootSound = Sounds.laser;
				inaccuracy = 25f;
				shoot = new ShootPattern() {{
					shots = 12;
					shotDelay = 6f;
				}};
				bullet(
					ST_BULLET.BULLET_TRIANGLE
						.build(250.0F, ST_ITEM.METRYSTAl, 60.0F, 1.0F, 2.0F)
						.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.METRYSTAl, 1, 1.0F, 8.0F, 1.0F)
						.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.METRYSTAl, 1, 4.0F, 8.0F, 1.0F)
						.bullet()
				);
			}});
		}};
		MATRIX_A4 = new SUnitType("MATRIX_A4") {{
			flying = true;
			hitSize = 13 * 8;
			health = 640000;
			armor = 64;
			speed = 8 / 8f;
			itemCapacity = 6000;
			engines.add(new UnitEngine(0, -12f * 8, 18f, -90));
			//
			abilities.add(new EnergyFieldAbility(150f, 30.0F, 8 * 5) {
				{
					this.healPercent = 0.01F;
				}
			});
			abilities.add(new MoveLightningAbility(50f, 8 * 4, 0.4F, 16.0F, 0.20000002F, 1.3333334F, ST_ITEM.WOOD_ELEMENT.color));
			abilities.add(new UnitSpawnAbility(MATRIX_A1, 24 * 60, 0, 0));
			abilities.add(new UnitSpawnAbility(MATRIX_A2, 15 * 60, 0, 0));
			abilities.add(new ST_ABILITY.SHIELD_FIELD_EXTRA(16 * 20, 30.0F, 10000.0F, 30 * 60) {{
				sides = 3;
				color = ST_ITEM.WOOD_ELEMENT.color;
			}});
			//
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 20;
				y = 32;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 12f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.WOOD_ELEMENT.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 36;
				y = 56;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 6f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.WOOD_ELEMENT.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 4;
				y = 8;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 2f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.WOOD_ELEMENT.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = -12.0F;
				y = -16.0F;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 1f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.WOOD_ELEMENT.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = -28.0F;
				y = -40.0F;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 1f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.WOOD_ELEMENT.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = -44.0F;
				y = -64.0F;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 0.4f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.WOOD_ELEMENT.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = -28.0F;
				y = -64.0F;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 0.6f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.WOOD_ELEMENT.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = -12.0F;
				y = -64.0F;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 1f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.WOOD_ELEMENT.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 0;
				y = 0;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 100f, 0.5f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.WOOD_ELEMENT.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 0;
				y = 0;
				mirror = true;
				shootSound = Sounds.laser;
				inaccuracy = 25f;
				shoot = new ShootPattern() {{
					shots = 24;
					shotDelay = 3f;
				}};
				bullet(
					ST_BULLET.BULLET_TRIANGLE
						.build(360, ST_ITEM.METRYSTAl, 120.0F, 1.0F, 2.0F)
						.frag(ST_BULLET.SHELL_IMPACT, ST_ITEM.METRYSTAl, 1, 1.0F, 8.0F, 1.0F)
						.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.METRYSTAl, 1, 4.0F, 8.0F, 1.0F)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 0;
				y = 0;
				mirror = true;
				inaccuracy = 20f;
				shootSound = Sounds.laser;
				shoot = new ShootPattern() {{
					shots = 3;
					shotDelay = 10f;
				}};
				bullet(
					ST_BULLET.BULLET_BLACK_HOLE
						.build(2500 / 10f, ST_ITEM.METRYSTAl, 80.0F, 1f / 10f, 4.0F)
						.interval(ST_BULLET.BULLET_TRIANGLE, ST_ITEM.METRYSTAl, 10, 3.0F, 80.0F, 2.0F, t -> t
							.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.METRYSTAl, 1, 1.0F, 4.0F, 1.0F)
						)
						.frag(ST_BULLET.SHELL_EXPLODE, ST_ITEM.METRYSTAl, 1, 1.0F, 4.0F, 2.0F).time(120.0F)
						.bullet()
				);
			}});
		}};
		//
		ZETA = new SUnitType("ZETA") {{
			flying = true;
			health = 1500;
			hitSize = 3 * 8;
			itemCapacity = 300;
			mineTier = 6;
			armor = 6;
			speed = 60 / 8f;
			engines.add(new UnitEngine(0, -6f, 4, -90));
			abilities.add(new ST_ABILITY.SHIELD_FIELD_EXTRA(8 * 20, 30.0F, 1200, 10 * 60f) {{
				sides = 3;
				color = ST_ITEM.LIGHT_ELEMENT.color;
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 0;
				y = 0;
				shootSound = Sounds.lasershoot;
				inaccuracy = 10f;
				buildSpeed = 10;
				mineSpeed = 10;
				shoot = new ShootPattern() {{
					shots = 2;
					shotDelay = 1f;
				}};
				bullet(
					ST_BULLET.BULLET_TRIANGLE
						.build(350f, ST_ITEM.LIGHT_ELEMENT, 32.0F, 6f, 1.5F)
						.frag(ST_BULLET.SHELL_LIGHTNING, ST_ITEM.LIGHT_ELEMENT, 1, 0.5F, 4.0F, 1.0F)
						.bullet(t -> {
							t.weaveMag = 4;
							t.weaveScale = 4;
						})
				);
			}});
			alwaysUnlocked = true;
		}};
		//
		MATRIX_UNIT_FACTORY = new UnitFactory("MATRIX_UNIT_FACTORY") {{
			size = 3;
			itemCapacity = 500;
			consumePower(480 / 60f);
			consumeLiquid(Liquids.water, 60 / 60f);
			//
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 250,
				ST_ITEM.SUPERCONDUCTOR, 50,
				Items.silicon, 150,
				Items.titanium, 150
			);
			plans.add(new UnitFactory.UnitPlan(MATRIX_BUILDING_UNIT, 60 * 10, ItemStack.with(
				ST_ITEM.NANOTUBE, 50,
				ST_ITEM.SUPERCONDUCTOR, 25,
				Items.silicon, 50,
				Items.titanium, 50
			)));
			plans.add(new UnitFactory.UnitPlan(MATRIX_MINER_UNIT, 60 * 10, ItemStack.with(
				ST_ITEM.NANOTUBE, 75,
				ST_ITEM.SUPERCONDUCTOR, 25,
				Items.silicon, 25,
				Items.graphite, 50
			)));
			plans.add(new UnitFactory.UnitPlan(MATRIX_ATTACK_UNIT, 60 * 15, ItemStack.with(
				ST_ITEM.NANOTUBE, 50,
				ST_ITEM.SUPERCONDUCTOR, 25,
				Items.silicon, 25,
				Items.titanium, 50
			)));
			plans.add(new UnitFactory.UnitPlan(MATRIX_MINER_UNIT_EXTRA, 60 * 30, ItemStack.with(
				ST_ITEM.NANOTUBE, 25,
				ST_ITEM.SUPERCONDUCTOR, 25,
				ST_ITEM.METRYSTAl, 25,
				ST_ITEM.CHROMAL, 50,
				ST_ITEM.SUSPENDED, 50,
				Items.phaseFabric, 50
			)));
			plans.add(new UnitFactory.UnitPlan(MATRIX_BUILDING_UNIT_EXTRA, 60 * 30, ItemStack.with(
				ST_ITEM.NANOTUBE, 50,
				ST_ITEM.SUPERCONDUCTOR, 25,
				ST_ITEM.METRYSTAl, 25,
				ST_ITEM.CHROMAL, 100,
				ST_ITEM.SUSPENDED, 75,
				Items.plastanium, 50
			)));
			plans.add(new UnitFactory.UnitPlan(MATRIX_A1, 60 * 30, ItemStack.with(
				ST_ITEM.CHROMAL, 150,
				ST_ITEM.SUSPENDED, 100,
				ST_ITEM.NANOTUBE, 50,
				ST_ITEM.SUPERCONDUCTOR, 25,
				ST_ITEM.METRYSTAl, 50
			)));
			plans.add(new UnitFactory.UnitPlan(MATRIX_A2, 60 * 30, ItemStack.with(
				ST_ITEM.CHROMAL, 300,
				ST_ITEM.SUSPENDED, 200,
				ST_ITEM.NANOTUBE, 150,
				ST_ITEM.SUPERCONDUCTOR, 100,
				ST_ITEM.METRYSTAl, 75
			)));
			inject(this, 2);
		}};
		MATRIX_UNIT_FACTORY_EXTRA = new UnitFactory("MATRIX_UNIT_FACTORY_EXTRA") {{
			size = 7;
			itemCapacity = 1500;
			consumePower(2400 / 60f);
			consumeLiquid(Liquids.cryofluid, 60 / 60f);
			//
			requirements = ItemStack.with(
				ST_ITEM.NANOTUBE, 500,
				ST_ITEM.SUPERCONDUCTOR, 500,
				ST_ITEM.CHROMAL, 750,
				ST_ITEM.METRYSTAl, 250,
				Items.phaseFabric, 500,
				Items.plastanium, 500
			);
			plans.add(new UnitFactory.UnitPlan(MATRIX_A3, 60 * 120, ItemStack.with(
				ST_ITEM.CHROMAL, 750,
				ST_ITEM.NANOTUBE, 300,
				ST_ITEM.SUPERCONDUCTOR, 500,
				ST_ITEM.SUSPENDED, 250,
				ST_ITEM.METRYSTAl, 150,
				ST_ITEM.ANTIMATTER, 25
			)));
			plans.add(new UnitFactory.UnitPlan(MATRIX_A4, 60 * 240, ItemStack.with(
				ST_ITEM.CHROMAL, 3500,
				ST_ITEM.NANOTUBE, 1000,
				ST_ITEM.SUPERCONDUCTOR, 1000,
				ST_ITEM.SUSPENDED, 3000,
				ST_ITEM.METRYSTAl, 500,
				ST_ITEM.ANTIMATTER, 500,
				ST_ITEM.LIGHT_ELEMENT, 100,
				ST_ITEM.DARK_ELEMENT, 100
			)));
			inject(this, 3);
		}};
	}
}