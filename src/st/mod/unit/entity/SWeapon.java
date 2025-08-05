package st.mod.unit.entity;

import mindustry.entities.bullet.BulletType;
import mindustry.type.Weapon;

public class SWeapon extends Weapon {
	public SWeapon(String name) {
		super(name);
	}
	{
		reload = 60;
	}

	public void bullet(BulletType bullet) {
		this.reload = 60 / bullet.reloadMultiplier;
		this.bullet = bullet;
	}
}