package st.mod.unit.entity;

import mindustry.entities.bullet.BulletType;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.type.Weapon;

public class SWeapon extends Weapon {
	public boolean DisplayDraw = true;
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
	@Override
	public void drawOutline(Unit unit, WeaponMount mount) {
		if (!DisplayDraw) return;
		super.drawOutline(unit, mount);
	}
	@Override
	public void draw(Unit unit, WeaponMount mount) {
		if (!DisplayDraw) return;
		super.draw(unit, mount);
	}
}