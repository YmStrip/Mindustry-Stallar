package st.mod.qio.draw;


import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.geom.Vec2;
import mindustry.gen.Building;
import mindustry.graphics.Layer;


import java.util.ArrayList;

public class DrawBlackHole {
	public Vec2 position = new Vec2();
	public float mass = 500;
	public float radius = 0.3f * 8;
	public int particleMax = 30;
	public float particleRadius = 10 * 8;
	public float particleRayDistance = 2f;
	public float spinStrength = 0.1f;
	public float particleSize = 1f;
	public float G = 0.1f;
	public float particleBaseSpeed = 0.01f;
	public float particleBaseSpeedRandom = 0.05f;
	public Color colorBackHole = Color.black;
	public Color colorParticle = Color.white;
	public float rotateOutside = 0f;
	public float rotateInner = 0f;
	public ArrayList<DrawBlackParticle> particles = new ArrayList<>();

	public void spawnParticle() {
		//
		var directionAngle = Math.random() * Math.PI * 2;
		var dx = Math.cos(directionAngle);
		var dy = Math.sin(directionAngle);
		//
		var nx = -dy;
		var ny = dx;
		//[param]
		var perpendicularOffset = particleRayDistance;
		var alongRayOffset = particleRadius + Math.random() * 70;
		//
		var x = position.x + dx * alongRayOffset + nx * perpendicularOffset;
		var y = position.y + dy * alongRayOffset + ny * perpendicularOffset;
		//
		var baseSpeed = particleBaseSpeed + Math.random() * particleBaseSpeedRandom;
		var spinStrength = this.spinStrength;
		//
		var vx = -dx * baseSpeed - dy * spinStrength;
		var vy = -dy * baseSpeed + dx * spinStrength;
		var mass = Math.random() * 2 + 1;
		particles.add(new DrawBlackParticle(new Vec2((float) x, (float) y), new Vec2((float) vx, (float) vy), mass));
	}
	public void tick() {
		for (var i = particles.size() - 1; i >= 0; i--) {
			var particle = particles.get(i);
			particle.tick(this);
			if (particle.isAbsorbed(this)) {
				particles.remove(particle);
			}
		}
		while (particles.size() < particleMax) {
			spawnParticle();
		}
		rotateOutside -= (float) 1 / 60 * 90f;
		rotateInner += (float) 2 / 60 * 90f;
		if (rotateInner > 360) rotateInner = 0;
		if (rotateOutside > 360) rotateOutside = 0;
		//debug();
	}
	public void draw(Building building, float opacity) {
		Draw.reset();
		Draw.z(Layer.block);
		Draw.color(colorBackHole);
		Draw.alpha(opacity);
		Fill.light(
			building.x,
			building.y,
			6,
			radius * 2.5f,
			rotateOutside,
			Color.white.cpy().a(opacity),
			colorParticle.cpy().a(opacity * 0.5f)
		);
		Draw.reset();
		Draw.z(Layer.block);
		Draw.color(colorBackHole);
		Draw.alpha(opacity);
		Fill.light(
			building.x,
			building.y,
			5,
			radius * 2,
			rotateInner,
			Color.white,
			Color.white.cpy().a(opacity * 0.5f)
		);
		Fill.light(
			building.x,
			building.y,
			24,
			radius,
			Color.black,
			Color.white.cpy()
		);
		Fill.circle(building.x, building.y, radius/2);
		Draw.reset();
		for (var i : particles) i.draw(this, building, opacity);
	}
}