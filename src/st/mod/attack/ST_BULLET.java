package st.mod.attack;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.ShapePart;
import mindustry.gen.Sounds;
import st.ST;
import st.mod.attack.entity.SBullet;
import st.mod.attack.entity.SBulletBuilder;


public class ST_BULLET {
	public static SBullet LIGHT = new SBullet() {
		{
			time = 15;
			rate = 1;
			color = Color.yellow;
			damage = 500;
			range = 50;
			width = 1.5f;
		}

		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new LaserBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;

				absorbable = true;
				shootEffect = ST_EFFECT.EFFECT_SHOOT(Color.white, Color.rgb((int) (prov.color.r * 0.8), (int) (prov.color.g * 0.8), (int) (prov.color.b * 0.8)));
				smokeEffect = Fx.smokeCloud;
				fragRandomSpread = 1f;
				collides = true;
				pierce = false;
				hitShake = prov.width / 2;
				width = prov.width;
				length = prov.width / 4;
				colors = new Color[]{Color.white, prov.color};
				damage = prov.damage;
			}};
		}
	};
	public static SBullet BULLET_LASER = new SBullet() {
		{
			time = 60;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new LaserBulletType() {{
				lifetime = -1;
				speed = 0;
				range = -1;
				absorbable = true;
				length = prov.range;
				width = prov.width;
				damage = prov.damage;
				colors = new Color[]{prov.color, Color.white};
			}};
		}
	};
	public static SBullet BULLET_ELEMENT = new SBullet() {
		{
			width = 0.8f;
			time = 15;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new FlakBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				collidesAir = true;
				collidesGround = true;
				damage = prov.damage;
				sprite = "missile-large";
				width = prov.width;
				height = prov.width * 1.5f;
				hitSize = prov.width / 2;
				shootEffect = Fx.shootSmokeSquareBig;
				smokeEffect = Fx.shootSmokeDisperse;
				ammoMultiplier = 1;
				hitColor = backColor = trailColor = lightningColor = prov.color;
				frontColor = Color.white;
				hitEffect = despawnEffect = Fx.hitBulletColor;
				buildingDamageMultiplier = 0.3f;

				trailWidth = prov.width / 3;
				trailLength = (int) (prov.width * 1.5f);
				trailEffect = Fx.colorSpark;
				trailInterval = 3f;

				homingPower = 0.17f;
				homingDelay = 19f;
				homingRange = 160f;
				flakInterval = 20f;
				despawnShake = 3f;
			}};
		}
	};
	public static SBullet BULLET_ELEMENT_IMPACT = new SBullet() {
		{
			width = 0.5f;
			range = 4f;
			time = 22;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new LaserBulletType() {{
				lifetime = -1;
				speed = 0;
				damage = prov.damage * 0.2f;
				length = prov.range;
				colors = new Color[]{
					prov.color,
					Color.white
				};
				buildingDamageMultiplier = 0.25f;
				width = prov.width;
				hitEffect = Fx.hitLancer;
				//sideAngle = 175f;
				//sideWidth = 1f;
				//sideLength = rangeP / 4;
				drawSize = prov.range * 3;
				pierceCap = 2;

				lightning = 3;
				lightningCone = 5f;
				lightningLength = (int) (prov.range * 0.5f);
				lightningColor = prov.color;
				lightningDamage = prov.damage * 0.8f / 3;
			}};
		}
	};
	public static SBullet BULLET_CONTINUE_FLAME = new SBullet() {
		{
			width = 2f;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new ContinuousFlameBulletType() {{
				lifetime = -1;
				speed = 0;
				range = -1;
				damage = prov.damage / 60 * 1.5f;
				length = prov.range;
				knockback = prov.width / 16f;
				pierceCap = 1;
				buildingDamageMultiplier = 0.35f;

				colors = new Color[]{
					new Color(prov.color).a(0.55f),
					new Color(prov.color).mul(0.9f).a(0.7f),
					new Color(prov.color).mul(0.8f).a(0.8f),
					new Color(prov.color).mul(0.7f),
					Color.white};
			}};
		}
	};
	public static SBullet BULLET_CONTINUE_LASER = new SBullet() {
		{
			width = 2f;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new ContinuousLaserBulletType() {{
				lifetime = -1;
				speed = 0;
				range = -1;
				absorbable = true;
				damage = prov.damage / 60 * 1.5f;
				length = prov.range;
				hitEffect = Fx.hitMeltdown;
				hitColor = prov.color;
				status = StatusEffects.melting;
				drawSize = prov.range * 2;
				trailRotation = true;
				incendChance = 0f;
				incendSpread = 0f;
				incendAmount = 1;
				colors = new Color[]{prov.color, Color.white};
			}};
		}
	};
	public static SBullet BULLET_RAIL_GUN = new SBullet() {
		{
			width = 2f;
			time = 30;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new PointBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				var light = prov.color;
				var dark = new Color(prov.color).mul(0.8f).a(0.8f);
				shootEffect = ST_EFFECT.EFFECT_SHOOT(light, dark, prov.width);
				hitEffect = ST_EFFECT.EFFECT_ATTACK(light, dark, prov.width);
				smokeEffect = ST_EFFECT.EFFECT_ATTACK_SMOKE(light, dark);
				trailEffect = ST_EFFECT.EFFECT_RAIL(light, dark, prov.width);
				despawnEffect = ST_EFFECT.EFFECT_DISAPPEAR(light, dark);
				damage = prov.damage;
				maxRange = prov.range;
				buildingDamageMultiplier = -0.5f;
				hitShake = 6f;
				pierce = false;
				collidesTiles = true;
				collides = true;
				absorbable = true;
				trailWidth = prov.width;
			}};
		}
	};
	public static SBullet BULLET_CANNON = new SBullet() {
		{
			width = 0.8f;
			time = 60;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new ArtilleryBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				absorbable = true;
				sprite = "spear";
				frontColor = prov.color;
				damage = prov.damage / 4 * 3;
				knockback = (float) (Math.log(prov.damage) / Math.log(1500));
				width = height = prov.width;
				collidesTiles = true;
				splashDamageRadius = (float) (Math.log(prov.damage) / Math.log(1.3));
				splashDamage = prov.damage / 4;


				trailLength = (int) (prov.width * 4);
				trailWidth = prov.width;
				trailSinScl = 2.5f;
				trailSinMag = 0.5f;
				trailColor = prov.color;
				trailEffect = Fx.none;
				despawnShake = 7f;
				smokeEffect = Fx.shootSmokeTitan;
				trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
				shrinkX = 0.2f;
				shrinkY = 0.1f;
				buildingDamageMultiplier = 0.3f;
			}};
		}
	};
	public static SBullet BULLET_MISSILE = new SBullet() {
		{
			width = 1.25f;
			time = 45;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new BasicBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				shootEffect = Fx.shootBig;
				smokeEffect = Fx.shootSmokeMissile;
				ammoMultiplier = 1f;
				absorbable = true;
				sprite = "scathe-missile";
				backColor = prov.color;
				damage = prov.damage / 4 * 3;
				knockback = (float) (Math.log(prov.damage) / Math.log(1500));
				width = height = prov.width;
				collidesTiles = true;
				splashDamageRadius = (float) (Math.log(prov.damage) / Math.log(1.3));
				splashDamage = prov.damage / 4;
				homingPower = 0.08f;
				homingRange = prov.range * 0.9f;

				trailWidth = prov.width / 3;
				trailLength = 12;
				trailEffect = Fx.colorSpark;
				trailInterval = 3f;
				trailColor = prov.color;
			}};
		}
	};
	public static SBullet BULLET_MACHINE_GUN_HAIL = new SBullet() {
		{
			time = 16;
			width = 0.5f;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new PointBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				absorbable = true;
				shootEffect = smokeEffect = Fx.thoriumShoot;
				trailEffect = ST_EFFECT.EFFECT_RAIL_THIN(prov.color);
				trailWidth = prov.width;
				damage = prov.damage;
			}};
		}
	};
	public static SBullet BULLET_MACHINE_GUN = new SBullet() {
		{
			width = 0.8f;
			time = 15;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new BasicBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
				smokeEffect = Fx.shootBigSmoke;
				hitSize = prov.width;
				width = prov.width;
				height = prov.width * 1.5f;
				damage = prov.damage;
				frontColor = prov.color;

				trailWidth = prov.width / 3;
				trailLength = 12;
				trailEffect = Fx.colorSpark;
				trailInterval = 3f;
				trailColor = new Color(prov.color).mul(0.95f).a(0.95f);

				despawnShake = 3f;
			}};
		}
	};
	public static SBullet BULLET_WAVE = new SBullet() {
		{
			damageMultiplier = 1.1f;
			time = 15;
			width = 1.5f;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new BasicBulletType() {
				{
					lifetime = -1;
					speed = -1;
					range = -1;
					width = prov.width;
					sprite = "swave";
					backColor = Color.white;
					frontColor = Color.white;
					collidesTiles = true;
					reflectable = false;
					hittable = false;
					absorbable = true;
					drawSize = 0;
					collidesTeam = false;
					hitSize = 36;
					pierce = true;
					pierceCap = 5;
					pierceBuilding = true;
					knockback = 0;
					ammoMultiplier = 4;
					damage = prov.damage;
					shootEffect = new WaveEffect() {{
						lifetime = 35;
						sizeFrom = 0;
						sizeTo = prov.range / 8;
						strokeFrom = 4;
						strokeTo = 0;
						colorFrom = Color.white;
						colorTo = prov.color;
					}};
					incendChance = 0;
				}
			};
		}
	};
	public static SBullet BULLET_SPREAD = new SBullet() {
		{
			time = 30;
			width = 1.25f;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new BasicBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				damage = prov.damage;
				knockback = prov.width / 20;
				width = prov.width * 1;
				hitSize = prov.width * 1;
				height = prov.width * 1.15f;
				shootEffect = Fx.shootBigColor;
				smokeEffect = Fx.shootSmokeSquareSparse;
				hitColor = backColor = trailColor = Color.white;
				trailWidth = prov.width;
				trailLength = 3;
				frontColor = prov.color;
				hitEffect = despawnEffect = Fx.hitSquaresColor;
				buildingDamageMultiplier = 0.35f;
			}};
		}
	};
	public static SBullet BULLET_TRIANGLE = new SBullet() {
		{
			this.width = 1.0F;
			this.time = 30.0F;
			this.range = 60.0F;
		}

		public BulletType buildBullet(final SBulletBuilder.SBulletDamageProv prov) {
			return new BasicBulletType() {
				{
					this.sprite = ST.atlasName("TRIANGLE");
					this.lifetime = -1.0F;
					this.speed = -1.0F;
					this.range = -1.0F;
					this.width = prov.width;
					this.height = prov.width;
					this.frontColor = prov.color;
					damage = prov.damage;
					this.shrinkX = 0.1F;
					this.shrinkY = 0.1F;
					this.mixColorFrom = prov.color;
					this.mixColorTo = Color.white;
					this.homingPower = 0.04F;
					this.homingRange = prov.width * 30.0F;
					this.trailEffect = new ParticleEffect() {
						{
							this.region = ST.atlasName("SQUARE");
							this.colorFrom = prov.color;
							this.colorTo = Color.white;
							this.particles = 1;
							this.spin = 60.0F;
							this.sizeFrom = prov.width / 3.0F;
							this.sizeTo = 0.0F;
						}
					};
					this.trailWidth = prov.width / 3.0F;
					this.trailColor = prov.color;
					this.trailInterval = 1.0F;
					this.trailChance = 0.01F;
					this.trailLength = (int) prov.width;
					this.despawnEffect = new ParticleEffect() {
						{
							this.colorFrom = prov.color;
							this.colorTo = Color.white;
							this.particles = 1;
							this.region = ST.atlasName("SQUARE");
							this.spin = 60.0F;
							this.sizeFrom = prov.width / 4.0F;
							this.sizeTo = 0.0F;
						}
					};
				}
			};
		}
	};
	public static SBullet BULLET_BLACK_HOLE = new SBullet() {
		{
			this.time = 120.0F;
			this.range = 80.0F;
			this.width = 3.0F;
		}

		public BulletType buildBullet(final SBulletBuilder.SBulletDamageProv prov) {
			return new BasicBulletType() {
				{
					this.lifetime = -1.0F;
					this.speed = -1.0F;
					this.range = -1.0F;
					this.sprite = ST.atlasName("ORB");
					this.width = prov.width;
					this.height = prov.width;
					this.frontColor = prov.color;
					this.backColor = prov.color;
					this.shrinkX = 0.1F;
					this.shrinkY = 0.1F;
					this.parts.add(new ShapePart() {
						{
							this.color = Color.black;
							this.circle = true;
							this.hollow = false;
							this.radius = prov.width / 5.0F;
							this.radiusTo = prov.width / 5.0F;
							this.layer = 120.0F;
						}
					});
					this.trailWidth = this.width / 3.0F;
					this.trailInterval = 2.0F;
					this.trailLength = (int) prov.width;
					this.trailColor = prov.color;
					this.collides = true;
					this.absorbable = false;
					this.drag = 0.001F;
					this.load();
				}
			};
		}
	};
	public static SBullet SHELL_LIGHT = new SBullet() {
		{
			this.damageMultiplier = 1.25F;
			this.range = 8.0F;
			this.time = 30.0F;
			this.width = 1.5F;
		}

		public BulletType buildBullet(final SBulletBuilder.SBulletDamageProv prov) {
			return new LaserBulletType() {
				{
					this.absorbable = true;
					this.pierce = true;
					this.fragOnHit = false;
					this.lightningColor = prov.color;
					this.lightning = 2;
					this.lightningLength = (int) (Math.log((double) prov.damage) / Math.log(1.2999999523162842));
					this.lightningDamage = prov.damage * 0.5F / 2.0F;
					this.width = prov.width;
					this.length = prov.range;
					this.damage = prov.damage * 0.5F;
					this.lifetime = 30.0F;
					this.speed = 0.0F;
					this.colors = new Color[]{Color.white, prov.color};
				}
			};
		}
	};
	public static SBullet SHELL_LIGHTNING = new SBullet() {
		{
			damageMultiplier = 1.25f;
			range = 8;
			time = 30;
			width = 1.5f;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new LaserBulletType() {{
				absorbable = true;
				pierce = true;
				fragOnHit = false;
				lightningColor = prov.color;
				lightning = 2;
				lightningLength = (int) (Math.log(prov.damage) / Math.log(1.3f));
				lightningDamage = prov.damage * 0.5f / 2;
				width = prov.width;
				length = prov.range;
				damage = prov.damage * 0.5f;
				lifetime = 30f;
				speed = 0f;
				colors = new Color[]{Color.white, prov.color};
			}};
		}
	};
	public static SBullet SHELL_STAR = new SBullet() {
		{
			damageMultiplier = 1.3f;
			time = 30;
			range = 10;
			width = 2.5f;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new LaserBulletType() {{
				lifetime = -1;
				speed = -1;
				damage = prov.damage;
				pierce = true;
				fragOnHit = false;
				length = prov.width;
				colors = new Color[]{prov.color, Color.white};
			}};
		}
	};
	public static SBullet SHELL_IMPACT = new SBullet() {
		{
			width = 1.5f;
			range = 8;
			time = 20;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new LaserBulletType() {{
				pierce = true;
				fragOnHit = false;
				length = prov.range;
				width = prov.width;
				damage = prov.damage;
				speed = 2f;
				lifetime = 15f;
				colors = new Color[]{prov.color, Color.white};
			}};
		}
	};
	public static SBullet SHELL_EXPLODE = new SBullet() {
		{
			range = 30;
			width = 2f;
			time = 0;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new BombBulletType() {{
				lifetime = 0f;
				damage = prov.damage * 0.2f;
				despawnEffect = Fx.flakExplosion;
				splashDamageRadius = prov.range;
				splashDamage = prov.damage * 0.8f;
				//hitSound = Sounds.titanExplosion;
				hitEffect = new MultiEffect(Fx.titanExplosion, Fx.smokeCloud);
			}};
		}
	};
	public static SBullet SHELL_COLLISION = new SBullet() {
		{
			range = 6;
			width = 1;
			time = 0;
		}

		@Override
		public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
			return new BulletType() {{
				lifetime = 0;
				range = 0;
				speed = 0f;
				damage = prov.damage;
				splashDamageRadius = prov.rate;
				splashDamage = prov.damage;
				collidesTiles = true;
				collides = true;
				hitSound = Sounds.glow;
				instantDisappear = true;
				hittable = false;
				collidesAir = true;
				shootEffect = Fx.none;
				//特效撞击
				//killShooter = true;
			}};
		}
	};
}
