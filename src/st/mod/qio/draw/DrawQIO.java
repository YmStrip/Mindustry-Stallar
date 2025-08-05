package st.mod.qio.draw;


import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;


import static mindustry.Vars.renderer;

public class DrawQIO {
	public float boostrap = 0;
	public Color color = Color.white;
	public void drawLightning(Building building) {
		Sounds.electricHum.at(building.x, building.y);
		Fx.lightning.at(building.x, building.y, (float) (Math.random() * 360), color);
	}
	//black hole
	public DrawBlackHole drawBlackHole = new DrawBlackHole();
	public float shieldRange = 6 * 8;
	public float shieldRotate = 0;
	public int shieldSides = 3;
	public float shieldHit = 0;
	public void drawShield(Building build) {
		var hit = shieldHit;
		Draw.reset();
		Draw.color(color, Color.white, hit);
		Draw.alpha(boostrap);
		if (renderer.animateShields) {
			Draw.z(Layer.shields + 0.001f * hit);
			Fill.poly(build.x, build.y, shieldSides, shieldRange * boostrap, shieldRotate);
		} else {
			Draw.z(Layer.shields);
			Lines.stroke(1.5f);
			Draw.alpha((0.09f + Mathf.clamp(0.08f * hit)) * boostrap);
			Fill.poly(build.x, build.y, shieldSides, shieldRange * boostrap, shieldRotate);
			Draw.alpha(boostrap);
			Lines.poly(build.x, build.y, shieldSides, shieldRange * boostrap, shieldRotate);
		}

		Draw.reset();
	}
	public void tick(Building build) {
		//
		if (build.efficiency >= 1) boostrap += 1 / 60f;
		else boostrap -= 1 / 60f;
		boostrap = Math.min(Math.max(boostrap, 0f), 1f);
		// summon lightning 1/s
		if (boostrap > 0.5f) {
			var bound = 1 / 60f;
			if (Math.random() <= bound) {
				this.drawLightning(build);
			}
		}
		// rotate shield
		if (boostrap > 0.1) {
			shieldRotate += 1 / 60f * 1.5f * 90f;
			if (shieldRotate >= 360) shieldRotate = 0;
		}
		// summon hits
		if (boostrap > 0.1) {
			if (shieldHit > 0) shieldHit = Math.max(shieldHit - 5 / 60f, 0);
			if (Math.random() <= (double) 1 / 60 * 2f) shieldHit += 45f / 60f;
		}
		if (boostrap > 0.1) {
			drawBlackHole.tick();
		}
	}
	public void draw(Building build) {
		if (boostrap > 0.1) drawShield(build);
		if (boostrap > 0.1) drawBlackHole.draw(build, boostrap);
		build.drawTeamTop();
	}
	public void drawHeat(Building building, TextureRegion heat) {
		Draw.reset();
		Draw.alpha(boostrap);
		Draw.rect(heat, building.x, building.y, building.drawrot());
		Draw.reset();
	}
}
