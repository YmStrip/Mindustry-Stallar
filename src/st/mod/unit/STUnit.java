package st.mod.unit;


import arc.Events;
import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.entities.abilities.EnergyFieldAbility;
import mindustry.entities.abilities.MoveLightningAbility;
import mindustry.entities.pattern.ShootPattern;
import mindustry.game.EventType;
import mindustry.gen.Sounds;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.meta.BuildVisibility;
import st.mod.STTech;
import st.mod.attack.ST_BULLET;
import st.mod.item.STItem;
import st.mod.UtilTooltip;
import st.mod.util.STooltipBuilder;
import st.mod.unit.entity.SUnitType;
import st.mod.unit.entity.SWeapon;


public class STUnit {
	public static STooltipBuilder Inject(UnitType unit) {
		return UtilTooltip.Tooltip(unit.stats);
	}
	public static STooltipBuilder Inject(Block b) {
		b.category = Category.units;
		b.buildVisibility = BuildVisibility.shown;
		return UtilTooltip.Tooltip(b.stats);
	}
	public static STooltipBuilder Inject(Block b, int level) {
		return Inject(b).TechLevel(level);
	}
	public static STooltipBuilder Inject(UnitType unit, int level) {
		return Inject(unit).TechLevel(level);
	}
	public static UnitType Destroyer;
	public static UnitType ZETA;
	public static void Init() {
		_initContent();
		_initEvent();
	}
	private static void _initContent() {
		ZETA = new SUnitType("ZETA") {{
			flying = true;
			health = 5000;
			hitSize = 3 * 8;
			itemCapacity = 300;
			mineTier = 15;
			buildSpeed = 25;
			armor = 6;
			speed = 60 / 8f;
			engines.add(new UnitEngine(0, -6f, 4, -90));
			abilities.add(new STAbility.SHIELD_FIELD_EXTRA(8 * 20, 1F, 1200, 10 * 60f) {{
				sides = 3;
				color = STItem.Darkmatter.color;
			}});
			weapons.add(new SWeapon(StWeaponAtlas.SPRITE_TURBO_LASER) {{
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
						.build(350f, STItem.Darkmatter, 32.0F, 6f, 1.5F)
						.frag(ST_BULLET.SHELL_LIGHTNING, STItem.Darkmatter, 1, 0.5F, 4.0F, 1.0F)
						.bullet(t -> {
							t.weaveMag = 4;
							t.weaveScale = 4;
						})
				);
			}});
			alwaysUnlocked = true;
		}};
		Destroyer = new SUnitType("MATRIX_A4") {{
			flying = true;
			hitSize = 13 * 8;
			health = 900000;
			armor = 24;
			speed = 8 / 8f;
			itemCapacity = 6000;
			engines.add(new UnitEngine(0, -12f * 8, 18f, -90));
			//
			abilities.add(new EnergyFieldAbility(150f, 30.0F, 8 * 5) {
				{
					this.healPercent = 0.01F;
				}
			});
			abilities.add(new MoveLightningAbility(50f, 8 * 4, 0.4F, 16.0F, 0.20000002F, 1.3333334F, Items.plastanium.color));
			//abilities.add(new UnitSpawnAbility(MATRIX_A1, 24 * 60, 0, 0));
			//abilities.add(new UnitSpawnAbility(MATRIX_A2, 15 * 60, 0, 0));
			abilities.add(new STAbility.SHIELD_FIELD_EXTRA(16 * 20, 1.5F, 10000.0F, 30 * 60) {{
				sides = 3;
				color = Items.plastanium.color;
			}});
			//
			weapons.add(new SWeapon(StWeaponAtlas.SPRITE_TURBO_LASER) {{
				x = 20;
				y = 32;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 12f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, Items.plastanium.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(StWeaponAtlas.SPRITE_TURBO_LASER) {{
				x = 36;
				y = 56;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 6f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, Items.plastanium.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(StWeaponAtlas.SPRITE_TURBO_LASER) {{
				x = 4;
				y = 8;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 2f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, Items.plastanium.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(StWeaponAtlas.SPRITE_TURBO_LASER) {{
				x = -12.0F;
				y = -16.0F;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 1f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, Items.plastanium.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(StWeaponAtlas.SPRITE_TURBO_LASER) {{
				x = -28.0F;
				y = -40.0F;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 1f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, Items.plastanium.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(StWeaponAtlas.SPRITE_TURBO_LASER) {{
				x = -44.0F;
				y = -64.0F;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 0.4f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, Items.plastanium.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(StWeaponAtlas.SPRITE_TURBO_LASER) {{
				x = -28.0F;
				y = -64.0F;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 0.6f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, Items.plastanium.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(StWeaponAtlas.SPRITE_TURBO_LASER) {{
				x = -12.0F;
				y = -64.0F;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 80f, 1f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, Items.plastanium.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(StWeaponAtlas.SPRITE_TURBO_LASER) {{
				x = 0;
				y = 0;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(500, Color.green, 100f, 0.5f, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, Items.plastanium.color, 1, 0.5f, 6f, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(StWeaponAtlas.SPRITE_TURBO_LASER) {{
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
						.build(360, STItem.Metrystal, 120.0F, 1.0F, 2.0F)
						.frag(ST_BULLET.SHELL_IMPACT, STItem.Metrystal, 1, 1.0F, 8.0F, 1.0F)
						.frag(ST_BULLET.SHELL_EXPLODE, STItem.Metrystal, 1, 4.0F, 8.0F, 1.0F)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(StWeaponAtlas.SPRITE_TURBO_LASER) {{
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
						.build(2500 / 10f, STItem.Metrystal, 80.0F, 1f / 10f, 4.0F)
						.interval(ST_BULLET.BULLET_TRIANGLE, STItem.Metrystal, 10, 3.0F, 80.0F, 2.0F, t -> t
							.frag(ST_BULLET.SHELL_EXPLODE, STItem.Metrystal, 1, 1.0F, 4.0F, 1.0F)
						)
						.frag(ST_BULLET.SHELL_EXPLODE, STItem.Metrystal, 1, 1.0F, 4.0F, 2.0F).time(120.0F)
						.bullet()
				);
			}});
		}};
	}
	private static void _initEvent() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			STTech.createTechNodeRoot(STUnit.ZETA);
			STTech
				.createTechNodeRoot(STUnit.ZETA)
				.Add(STUnit.Destroyer);
		});
	}
	private static void load() {
		/*MATRIX_BUILDING_UNIT = new SUnitType("MATRIX_BUILDING_UNIT") {{
			health = 500;
			armor = 4;
			flying = true;
			speed = 22 / 8f;
			buildSpeed = 5;
			itemCapacity = 120;
			mineTier = 2;
			mineSpeed = 1;
			hitSize = 2 * 8;
			defaultCommand = UnitCommand.assistCommand;
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_LASER) {{
				displayDraw = false;
				x = 1;
				y = 1;
				bullet(
					ST_BULLET.BULLET_MACHINE_GUN
						.build(20, STItem.Superconductor, 28, 5, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, STItem.GOLD_ELEMENT, 1, 0.2f, 6, 0.5f)
						.bullet()
				);
			}});
			Inject(this, 1);
		}};
		MATRIX_MINER_UNIT = new SUnitType("MATRIX_MINER_UNIT") {{
			health = 500;
			armor = 1;
			flying = true;
			speed = 20f / 8f;
			itemCapacity = 240;
			mineTier = 3;
			mineSpeed = 5;
			hitSize = 2 * 8;
			defaultCommand = UnitCommand.mineCommand;
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_LASER) {{
				displayDraw = false;
				x = 1;
				y = 1;
				bullet(
					ST_BULLET.BULLET_MACHINE_GUN
						.build(10, STItem.Superconductor, 26, 5, 0.5f)
						.frag(ST_BULLET.SHELL_IMPACT, STItem.GOLD_ELEMENT, 1, 0.2f, 4, 0.5f)
						.bullet()
				);
			}});
			Inject(this, 1);
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
				displayDraw = false;
				bullet(
					ST_BULLET.BULLET_MACHINE_GUN
						.build(20, STItem.Superconductor, 26, 10, 0.4f)
						.frag(ST_BULLET.SHELL_IMPACT, STItem.GOLD_ELEMENT, 1, 0.2f, 5, 0.5f)
						.bullet()
				);
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_MISSILE) {{
				x = 0;
				y = 0;
				displayDraw = false;
				bullet(
					ST_BULLET.BULLET_MISSILE
						.build(40, STItem.Superconductor, 26, 0.5f, 0.5f)
						.frag(ST_BULLET.SHELL_EXPLODE, STItem.GOLD_ELEMENT, 1, 0.45f, 8, 0.5f)
						.bullet()
				);
			}});
			Inject(this, 1);
		}};
		MATRIX_BUILDING_UNIT_EXTRA = new SUnitType("MATRIX_BUILDING_UNIT_EXTRA") {{
			health = 1500;
			armor = 1;
			flying = true;
			speed = 40 / 8f;
			buildSpeed = 10;
			itemCapacity = 300;
			mineTier = 4;
			mineSpeed = 4;
			hitSize = 2 * 8;
			defaultCommand = UnitCommand.assistCommand;
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 0;
				y = 0;
				displayDraw = false;
				bullet = ST_BULLET.BULLET_RAIL_GUN
					.build(80, Color.green, 36, 1, 0.5f)
					.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, STItem.WOOD_ELEMENT, 1, 0.8f, 6, 0.5f)
					.frag(ST_BULLET.SHELL_EXPLODE, STItem.WATER_ELEMENT, 1, 0.5f, 6)
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
			mineTier = 15;
			mineSpeed = 8;
			hitSize = 2 * 8;
			defaultCommand = UnitCommand.mineCommand;
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				displayDraw = false;
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
			health = 6000;
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
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, STItem.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
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
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, STItem.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
						.bullet()
				);
			}});
		}};
		MATRIX_A2 = new SUnitType("MATRIX_A2") {{
			this.constructor = UnitEntity::create;
			flying = true;
			hitSize = 8 * 8;
			health = 32000;
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
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, STItem.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
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
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, STItem.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
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
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, STItem.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
						.bullet()
				);
			}});
		}};
		MATRIX_A3 = new SUnitType("MATRIX_A3") {{
			this.constructor = UnitEntity::create;
			flying = true;
			hitSize = 10 * 8;
			health = 120000;
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
			abilities.add(new ST_ABILITY.SHIELD_FIELD_EXTRA(12 * 18, 1.0F, 1500f, 30 * 60) {{
				sides = 3;
				color = STItem.WOOD_ELEMENT.color;
			}});
			weapons.add(new SWeapon(ST_WEAPON.SPRITE_TURBO_LASER) {{
				x = 4;
				y = 8;
				mirror = true;
				shootSound = Sounds.laser;
				bullet(
					ST_BULLET.BULLET_RAIL_GUN
						.build(400, Color.green, 50, 8f, 0.5f)
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, STItem.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
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
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, STItem.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
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
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, STItem.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
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
						.frag(ST_BULLET.BULLET_ELEMENT_IMPACT, STItem.WOOD_ELEMENT, 1, 0.5f, 6, 0.5f)
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
						.build(250.0F, STItem.Metrystal, 60.0F, 1.0F, 2.0F)
						.frag(ST_BULLET.SHELL_IMPACT, STItem.Metrystal, 1, 1.0F, 8.0F, 1.0F)
						.frag(ST_BULLET.SHELL_EXPLODE, STItem.Metrystal, 1, 4.0F, 8.0F, 1.0F)
						.bullet()
				);
			}});
		}};

		//*/
		//
		/*MATRIX_UNIT_FACTORY = new UnitFactory("MATRIX_UNIT_FACTORY") {{
			size = 3;
			itemCapacity = 500;
			consumePower(480 / 60f);
			consumeLiquid(Liquids.water, 60 / 60f);
			//
			requirements = ItemStack.with(
				STItem.Nanotube, 250,
				STItem.Superconductor, 50,
				Items.silicon, 150,
				Items.titanium, 150
			);
			plans.add(new UnitFactory.UnitPlan(MATRIX_BUILDING_UNIT, 60 * 10, ItemStack.with(
				STItem.Nanotube, 50,
				STItem.Superconductor, 25,
				Items.silicon, 50,
				Items.titanium, 50
			)));
			plans.add(new UnitFactory.UnitPlan(MATRIX_MINER_UNIT, 60 * 10, ItemStack.with(
				STItem.Nanotube, 75,
				STItem.Superconductor, 25,
				Items.silicon, 25,
				Items.graphite, 50
			)));
			plans.add(new UnitFactory.UnitPlan(MATRIX_ATTACK_UNIT, 60 * 15, ItemStack.with(
				STItem.Nanotube, 50,
				STItem.Superconductor, 25,
				Items.silicon, 25,
				Items.titanium, 50
			)));
			plans.add(new UnitFactory.UnitPlan(MATRIX_MINER_UNIT_EXTRA, 60 * 30, ItemStack.with(
				STItem.Nanotube, 25,
				STItem.Superconductor, 25,
				STItem.Metrystal, 25,
				STItem.Chromal, 50,
				STItem.Suspended, 50,
				Items.phaseFabric, 50
			)));
			plans.add(new UnitFactory.UnitPlan(MATRIX_BUILDING_UNIT_EXTRA, 60 * 30, ItemStack.with(
				STItem.Nanotube, 50,
				STItem.Superconductor, 25,
				STItem.Metrystal, 25,
				STItem.Chromal, 100,
				STItem.Suspended, 75,
				Items.plastanium, 50
			)));
			plans.add(new UnitFactory.UnitPlan(MATRIX_A1, 60 * 30, ItemStack.with(
				STItem.Chromal, 150,
				STItem.Suspended, 100,
				STItem.Nanotube, 50,
				STItem.Superconductor, 25,
				STItem.Metrystal, 50
			)));
			plans.add(new UnitFactory.UnitPlan(MATRIX_A2, 60 * 30, ItemStack.with(
				STItem.Chromal, 1000,
				STItem.Suspended, 750,
				STItem.Nanotube, 750,
				STItem.Superconductor, 500,
				STItem.Metrystal, 255,
				STItem.Antimatter, 50
			)));
			Inject(this, 2);
		}};
		MATRIX_UNIT_FACTORY_EXTRA = new UnitFactory("MATRIX_UNIT_FACTORY_EXTRA") {{
			size = 7;
			itemCapacity = 1500;
			consumePower(2400 / 60f);
			consumeLiquid(Liquids.cryofluid, 60 / 60f);
			//
			requirements = ItemStack.with(
				STItem.Nanotube, 500,
				STItem.Superconductor, 500,
				STItem.Chromal, 750,
				STItem.Metrystal, 250,
				Items.phaseFabric, 500,
				Items.plastanium, 500
			);
			plans.add(new UnitFactory.UnitPlan(MATRIX_A3, 60 * 120, ItemStack.with(
				STItem.Chromal, 2500,
				STItem.Nanotube, 500,
				STItem.Superconductor, 800,
				STItem.Suspended, 1500,
				STItem.Metrystal, 400,
				STItem.Antimatter, 300
			)));
			plans.add(new UnitFactory.UnitPlan(Destroyer, 60 * 240, ItemStack.with(
				STItem.Chromal, 6000,
				STItem.Nanotube, 2500,
				STItem.Superconductor, 1500,
				STItem.Suspended, 5000,
				STItem.Metrystal, 2500,
				STItem.Antimatter, 1500,
				STItem.LIGHT_ELEMENT, 50,
				STItem.Darkmatter, 50
			)));
			Inject(this, 3);
		}};*/
	}
}