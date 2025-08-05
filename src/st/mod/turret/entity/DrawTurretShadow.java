package st.mod.turret.entity;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.draw.DrawTurret;

public class DrawTurretShadow extends DrawTurret {
	public TextureRegion shadow;
	@Override
	public void drawHeat(Turret block, Turret.TurretBuild build) {
		super.drawHeat(block, build);
		return;
		/*
		if (shadow.found() && block instanceof ItemTurret) {
			for (var i : build.ammo) {
				var entry = (ItemTurret.ItemEntry) i;
				if (entry.amount > 0) {
					Drawf.additive(shadow, entry.item.color, build.x + build.recoilOffset.x, build.y + build.recoilOffset.y, build.drawrot(), this.shadowLayer);
					break;
				}
			}
		}
		 */
	}
	@Override
	public void load(Block block) {
		super.load(block);
		shadow = Core.atlas.find(block.name + "-shadow");
	}
}