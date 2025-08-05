package st.mod.unit;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.gen.Unit;

public class ST_ABILITY {
	public static class SHIELD_FIELD_EXTRA extends ForceFieldAbility {
		public Color color;
		public float realRad;

		public SHIELD_FIELD_EXTRA(float radius, float regen, float max, float cooldown) {
			super(radius, regen, max, cooldown);
			this.color = Color.orange;
			this.realRad = 0.0F;
		}

		public void draw(Unit unit) {
			this.checkRadius(unit);
			if (unit.shield > 0.0F) {
				if (Vars.renderer.animateShields) {
					Draw.color(this.color, Color.white, Mathf.clamp(this.alpha));
					Draw.z(125.0F + 0.001F * this.alpha);
					Fill.poly(unit.x, unit.y, this.sides, this.realRad, this.rotation + unit.rotation);
				} else {
					Draw.z(125.0F);
					Draw.color(unit.team.color, Color.white, Mathf.clamp(this.alpha));
					Lines.stroke(1.5F);
					Draw.color(this.color, Color.white, Mathf.clamp(this.alpha));
					Draw.alpha(0.09F);
					Fill.poly(unit.x, unit.y, this.sides, this.radius, this.rotation + unit.rotation);
					Draw.alpha(1.0F);
					Lines.poly(unit.x, unit.y, this.sides, this.radius, this.rotation + unit.rotation);
				}
			}

		}

		public void checkRadius(Unit unit) {
			this.realRad = this.radiusScale * this.radius;
		}
	}
}
