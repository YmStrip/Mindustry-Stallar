package st.mod.attack.entity;

import arc.graphics.Color;
import mindustry.entities.bullet.BulletType;
import mindustry.type.Item;


public class SBullet {
	public float damageMultiplier = 1f;
	//shoot rate
	public float rate = 1f;
	//bullet width
	public float width = 0.5f;
	//range
	public float range = 30f;
	//bullet time
	public float time = 30f;
	public Color color = Color.white;
	//dps
	public float damage = 50f;
	public float ammoMultiplier = 2f;
	
	//damage color range speed width
	public SBulletBuilder build(float damage) {
		return new SBulletBuilder(this, damage);
	}
	
	public SBulletBuilder build(float damage, Color color) {
		return new SBulletBuilder(this, damage, color);
	}
	
	public SBulletBuilder build(float damage, Item item) {
		return new SBulletBuilder(this, damage, item.color);
	}
	
	
	public SBulletBuilder build(float damage, Color color, float range, float rate, float width) {
		return new SBulletBuilder(this, damage, color).range(range).rate(rate).width(width);
	}
	
	public SBulletBuilder build(float damage, Item item, float range, float rate, float width) {
		return new SBulletBuilder(this, damage, item.color).range(range).rate(rate).width(width);
	}
	
	public SBulletBuilder build(float damage, Color color, float range, float rate) {
		return new SBulletBuilder(this, damage, color).range(range).rate(rate);
	}
	
	public SBulletBuilder build(float damage, Item item, float range, float rate) {
		return new SBulletBuilder(this, damage, item.color).range(range).rate(rate);
	}
	
	public SBulletBuilder build(float damage, Color color, float range) {
		return new SBulletBuilder(this, damage, color).range(range);
	}
	
	public SBulletBuilder build(float damage, Item item, float range) {
		return new SBulletBuilder(this, damage, item.color).range(range);
	}
	public BulletType buildBullet(SBulletBuilder.SBulletDamageProv prov) {
		return new BulletType();
	}
}
