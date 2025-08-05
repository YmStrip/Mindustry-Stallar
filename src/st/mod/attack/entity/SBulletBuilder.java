package st.mod.attack.entity;

import arc.graphics.Color;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.type.Item;
import mindustry.type.StatusEffect;


public class SBulletBuilder {
	public boolean root;
	public float fragUse;
	public float intervalUse;
	public float intervalDelta;
	public int count;
	public int spread;
	public float time;
	public float range;
	public float rate;
	public float damage;
	public float width;
	public float ammoMultiplier;
	public Color color;
	public SBullet sBullet;
	public SBulletBuilder frag;
	public SBulletBuilder interval;
	public SBulletBuilder lastRoot;
	public BulletType bullet;
	public StatusEffect status;

	public float rateMultiplier(float speed) {
		return (float) (0.8795160055160522 / Math.pow((double) speed, 0.8464095592498779));
	}

	public SBulletBuilder(SBullet sBulletP) {
		this.root = true;
		this.fragUse = 0.0F;
		this.intervalUse = 0.0F;
		this.intervalDelta = 6.0F;
		this.count = 1;
		this.spread = 1;
		this.time = -1.0F;
		this.range = -1.0F;
		this.rate = -1.0F;
		this.damage = -1.0F;
		this.width = -1.0F;
		this.ammoMultiplier = -1.0F;
		this.sBullet = sBulletP;
	}

	public SBulletBuilder(SBullet sBulletP, float damage) {
		this(sBulletP);
		this.damage = damage;
	}

	public SBulletBuilder(SBullet sBulletP, float damage, Color color) {
		this(sBulletP, damage);
		this.color = color;
	}


	public SBulletBuilder count(int s) {
		if (this.lastRoot != null) {
			this.lastRoot.count = s;
		} else {
			this.count = s;
		}

		return this;
	}

	public SBulletBuilder time(float s) {
		if (this.lastRoot != null) {
			this.lastRoot.time = s;
		} else {
			this.time = s;
		}

		return this;
	}

	public SBulletBuilder fragUse(float s) {
		if (this.lastRoot != null) {
			this.lastRoot.fragUse = s;
		} else {
			this.fragUse = s;
		}

		return this;
	}

	public SBulletBuilder damage(float s) {
		if (this.lastRoot == null) {
			this.damage = s;
		}

		return this;
	}

	public SBulletBuilder range(float s) {
		if (this.lastRoot != null) {
			this.lastRoot.range = s;
		} else {
			this.range = s;
		}

		return this;
	}

	public SBulletBuilder width(float s) {
		if (this.lastRoot != null) {
			this.lastRoot.width = s;
		} else {
			this.width = s;
		}

		return this;
	}

	public SBulletBuilder rate(float s) {
		if (this.lastRoot != null) {
			this.lastRoot.rate = s;
		} else {
			this.rate = s;
		}

		return this;
	}

	public SBulletBuilder ammoMultiplier(float s) {
		if (this.root) {
			this.ammoMultiplier = s;
		}

		return this;
	}

	public SBulletBuilder vectorShock() {
		if (this.lastRoot != null) {
			this.lastRoot.spread = 2;
		} else {
			this.spread = 2;
		}

		return this;
	}

	public SBulletBuilder interval(SBullet sBullet, float use, frag f) {
		if (this.frag != null) {
			this.lastRoot.interval(sBullet, use, f);
		} else {
			this.intervalUse = use;
			this.interval = new SBulletBuilder(sBullet);
			f.frag(this.interval);
		}

		return this;
	}

	public SBulletBuilder interval(SBullet sBullet, Item item) {
		return this.interval(sBullet, 0.5F, (t) -> {
			t.color = item.color;
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Color color) {
		return this.interval(sBullet, 0.5F, (t) -> {
			t.color = color;
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Item item, frag f) {
		return this.interval(sBullet, 0.5F, (t) -> {
			t.color = item.color;
			f.frag(t);
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Color color, frag f) {
		return this.interval(sBullet, 0.5F, (t) -> {
			t.color = color;
			f.frag(t);
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Item item, int count) {
		return this.interval(sBullet, 0.5F, (t) -> {
			t.color = item.color;
			t.count = count;
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Color color, int count) {
		return this.interval(sBullet, 0.5F, (t) -> {
			t.color = color;
			t.count = count;
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Item item, int count, frag f) {
		return this.interval(sBullet, 0.5F, (t) -> {
			t.color = item.color;
			t.count = count;
			f.frag(t);
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Color color, int count, frag f) {
		return this.interval(sBullet, 0.5F, (t) -> {
			t.color = color;
			t.count = count;
			f.frag(t);
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Item item, int count, float use) {
		return this.interval(sBullet, use, (t) -> {
			t.color = item.color;
			t.count = count;
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Color color, int count, float use) {
		return this.interval(sBullet, use, (t) -> {
			t.color = color;
			t.count = count;
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Item item, int count, float use, frag f) {
		return this.interval(sBullet, use, (t) -> {
			t.color = item.color;
			t.count = count;
			f.frag(t);
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Color color, int count, float use, frag f) {
		return this.interval(sBullet, use, (t) -> {
			t.color = color;
			t.count = count;
			f.frag(t);
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Item item, int count, float use, float range) {
		return this.interval(sBullet, use, (t) -> {
			t.color = item.color;
			t.count = count;
			t.range = range;
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Color color, int count, float use, float range) {
		return this.interval(sBullet, use, (t) -> {
			t.color = color;
			t.count = count;
			t.range = range;
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Color color, int count, float use, float range, frag f) {
		return this.interval(sBullet, use, (t) -> {
			t.color = color;
			t.count = count;
			t.range = range;
			f.frag(t);
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Item item, int count, float use, float range, frag f) {
		return this.interval(sBullet, use, (t) -> {
			t.color = item.color;
			t.count = count;
			t.range = range;
			f.frag(t);
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Item item, int count, float use, float range, float width) {
		return this.interval(sBullet, use, (t) -> {
			t.color = item.color;
			t.count = count;
			t.range = range;
			t.width = width;
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Color color, int count, float use, float range, float width) {
		return this.interval(sBullet, use, (t) -> {
			t.color = color;
			t.count = count;
			t.range = range;
			t.width = width;
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Item item, int count, float use, float range, float width, frag f) {
		return this.interval(sBullet, use, (t) -> {
			t.color = item.color;
			t.count = count;
			t.range = range;
			t.width = width;
			f.frag(t);
		});
	}

	public SBulletBuilder interval(SBullet sBullet, Color color, int count, float use, float range, float width, frag f) {
		return this.interval(sBullet, use, (t) -> {
			t.color = color;
			t.count = count;
			t.range = range;
			t.width = width;
			f.frag(t);
		});
	}

	public SBulletBuilder frag(SBullet sBullet, float use, frag f) {
		if (this.frag != null) {
			this.lastRoot.frag(sBullet, use, f);
			this.lastRoot = this.lastRoot.lastRoot;
		} else {
			this.fragUse = use;
			this.frag = new SBulletBuilder(sBullet);
			this.frag.root = false;
			this.frag.color = this.color;
			this.lastRoot = this.frag;
			f.frag(this.frag);
		}

		return this;
	}

	public SBulletBuilder frag(SBullet sBullet, Item item) {
		return this.frag(sBullet, 0.5F, (t) -> {
			t.color = item.color;
		});
	}

	public SBulletBuilder frag(SBullet sBullet, Color color) {
		return this.frag(sBullet, 0.5F, (t) -> {
			t.color = color;
		});
	}

	public SBulletBuilder frag(SBullet sBullet, Item item, int count) {
		return this.frag(sBullet, 0.5F, (t) -> {
			t.color = item.color;
			t.count = count;
		});
	}

	public SBulletBuilder frag(SBullet sBullet, Color color, int count) {
		return this.frag(sBullet, 0.5F, (t) -> {
			t.color = color;
			t.count = count;
		});
	}

	public SBulletBuilder frag(SBullet sBullet, Item item, int count, float use) {
		return this.frag(sBullet, use, (t) -> {
			t.color = item.color;
			t.count = count;
		});
	}

	public SBulletBuilder frag(SBullet sBullet, Color color, int count, float use) {
		return this.frag(sBullet, use, (t) -> {
			t.color = color;
			t.count = count;
		});
	}

	public SBulletBuilder frag(SBullet sBullet, Item item, int count, float use, float range) {
		return this.frag(sBullet, use, (t) -> {
			t.color = item.color;
			t.count = count;
			t.range = range;
		});
	}

	public SBulletBuilder frag(SBullet sBullet, Color color, int count, float use, float range) {
		return this.frag(sBullet, use, (t) -> {
			t.color = color;
			t.count = count;
			t.range = range;
		});
	}

	public SBulletBuilder frag(SBullet sBullet, Item item, int count, float use, float range, float width) {
		return this.frag(sBullet, use, (t) -> {
			t.color = item.color;
			t.count = count;
			t.range = range;
			t.width = width;
		});
	}

	public SBulletBuilder frag(SBullet sBullet, Color color, int count, float use, float range, float width) {
		return this.frag(sBullet, use, (t) -> {
			t.color = color;
			t.count = count;
			t.range = range;
			t.width = width;
		});
	}
	public SBulletBuilder status(StatusEffect status) {
		this.status = status;
		return this;
	}

	public BulletType bullet() {
		this.init(this.damage);
		return this.bullet;
	}

	public BulletType bullet(float damage) {
		this.init(damage);
		return this.bullet;
	}
	@FunctionalInterface
	public interface SelfBullet {
		void call(BulletType bullet);
	}
	public BulletType bullet(SelfBullet callback) {
		this.bullet();
		callback.call(this.bullet);
		return this.bullet;
	}

	public SBulletBuilder self(Self t) {
		t.self(this);
		return this;
	}

	float def(float f, float def1) {
		return f == -1.0F ? def1 : f;
	}

	Color def(Color now, Color def) {
		if (now == null) {
			return def == null ? Color.white : def;
		} else {
			return now;
		}
	}

	public BulletType fragContainer(final SBulletBuilder frag) {
		return new BasicBulletType() {
			{
				this.lifetime = 0.0F;
				this.damage = 0.0F;
				this.speed = 0.0F;
				this.fragBullets = frag.count;
				if (SBulletBuilder.this.spread == 1) {
					this.weaveRandom = true;
				} else {
					this.weaveMag = 1.0F;
					this.weaveScale = 1.0F;
				}

			}
		};
	}

	public void init(float d) {
		final float _lifetime = this.def(this.time, this.sBullet.time);
		final float _width = this.def(this.width, this.sBullet.width) * 8.0F;
		final float _range = this.def(this.range, this.sBullet.range) * 8.0F;
		final float _rate = this.def(this.rate, this.sBullet.rate);
		final Color _color = this.def(this.color, this.sBullet.color);
		float _ammoMultiplier = this.def(this.ammoMultiplier, this.sBullet.ammoMultiplier);
		final float _speed = _range / _lifetime;
		float _baseDamage = this.def(d, this.def(this.damage, this.sBullet.damage));
		float _fragDamage = 1.0F;
		float _intervalDamage = 1.0F;
		float _intervalDelta = 0.0F;
		float _damage = 1.0F;
		float _damageAssign = 1.0F;
		float _rateMultiplier = this.rateMultiplier(_rate);
		if (this.frag != null) {
			_damageAssign += this.fragUse;
		}

		if (this.interval != null) {
			_damageAssign += this.intervalUse;
		}

		if (_damageAssign == 0.0F) {
			_damageAssign = 1.0F;
		}

		if (this.frag != null) {
			_fragDamage = _baseDamage * (this.fragUse / _damageAssign) * _rateMultiplier / (float) this.frag.count;
		}

		if (this.interval != null) {
			_intervalDelta = _lifetime / (float) this.interval.count;
			_intervalDamage = _baseDamage * (this.intervalUse / _damageAssign) * _rateMultiplier / (float) this.interval.count;
		}

		_damage = 1.0F / _damageAssign * _baseDamage * _rateMultiplier;
		_damage *= this.sBullet.damageMultiplier;
		final float final_damage = _damage;
		this.bullet = this.sBullet.buildBullet(new SBulletDamageProv() {
			{
				this.color = _color;
				this.damage = final_damage;
				this.range = _range;
				this.speed = _speed;
				this.rate = _rate;
				this.lifetime = _lifetime;
				this.width = _width;
			}
		});
		if (this.status != null) {
			this.bullet.status = this.status;
		}
		if (this.frag != null) {
			this.bullet.fragBullet = this.fragContainer(this.frag);
			this.bullet.fragBullet.fragBullet = this.frag.bullet(_fragDamage);
			this.bullet.fragBullets = 1;
			this.bullet.fragBullet.init();
		}

		if (this.interval != null) {
			this.bullet.intervalBullet = this.fragContainer(this.interval);
			this.bullet.intervalBullet.fragBullet = this.interval.bullet(_intervalDamage);
			this.bullet.bulletInterval = _intervalDelta;
			this.bullet.intervalBullets = 1;
			this.bullet.intervalBullet.init();
		}

		if (this.root) {
			this.bullet.range = _range;
		}

		this.bullet.reloadMultiplier = _rate;
		if (this.bullet.lifetime == -1.0F) {
			this.bullet.lifetime = _lifetime;
		}

		if (this.bullet.speed == -1.0F) {
			this.bullet.speed = _speed;
		}

		this.bullet.init();
		if (this.root) {
			this.bullet.maxRange = _range;
			this.bullet.ammoMultiplier = _ammoMultiplier;
		}
	}

	@FunctionalInterface
	public interface frag {
		void frag(SBulletBuilder var1);
	}

	@FunctionalInterface
	public interface Self {
		void self(SBulletBuilder var1);
	}

	public class SBulletDamageProv {
		public float speed = 1.0F;
		public float damage = 1.0F;
		public float range = 1.0F;
		public float rate = 1.0F;
		public float lifetime = 1.0F;
		public float width = 1.0F;
		public Color color;

		public SBulletDamageProv() {
		}
	}
}
