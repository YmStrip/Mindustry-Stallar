package st.mod.functional.entity;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mindustry.graphics.Layer;
import mindustry.world.blocks.defense.ForceProjector;

import static mindustry.Vars.renderer;

public class SForceProjector extends ForceProjector {
	public Color backColor;
	
	public SForceProjector(String name) {
		super(name);
	}
	
	public class SForceProjectorBuild extends ForceBuild {
		@Override
		public void drawShield() {
			if (!broken) {
				float radius = realRadius();
				
				if (radius > 0.001f) {
					Draw.color(backColor == null ? team.color : backColor, Color.white, Mathf.clamp(hit));
					
					if (renderer.animateShields) {
						Draw.z(Layer.shields + 0.001f * hit);
						Fill.poly(x, y, sides, radius, shieldRotation);
					} else {
						Draw.z(Layer.shields);
						Lines.stroke(1.5f);
						Draw.alpha(0.09f + Mathf.clamp(0.08f * hit));
						Fill.poly(x, y, sides, radius, shieldRotation);
						Draw.alpha(1f);
						Lines.poly(x, y, sides, radius, shieldRotation);
						Draw.reset();
					}
				}
			}
			Draw.reset();
		}
	}
}
