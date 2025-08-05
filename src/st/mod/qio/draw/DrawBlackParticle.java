package st.mod.qio.draw;


import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;

public class DrawBlackParticle {
	public Vec2 position;
	public Vec2 vector;
	public float mass;
	public DrawBlackParticle(Vec2 position, Vec2 vector, double mass) {
		this.position = position;
		this.vector = vector;
		this.mass = (float) mass;
	}
	public boolean isAbsorbed(DrawBlackHole hole) {
		var len = hole.position.cpy().sub(this.position).len();
		return len < (hole.radius + 0.5f) || len > hole.particleRadius * 2;
	}
	public float transitionDistance(DrawBlackHole hole, Building building) {
		var len = position.cpy().sub(hole.position).len();
		return (float) (
			1 - Math.tanh(len / hole.particleRadius)
		);
	}
	public void tick(DrawBlackHole hole) {
		var dx = hole.position.x - position.x;
		var dy = hole.position.y - position.y;
		var L2 = Math.sqrt(dx * dx + dy * dy);
		var angle = Mathf.atan2(dx, dy);
		var acceleration = (hole.G * hole.mass * mass) / (L2 * L2);
		var ax = Math.cos(angle) * acceleration;
		var ay = Math.sin(angle) * acceleration;
		vector.add((float) ax, (float) ay);
		position.add(this.vector);
	}
	public void draw(DrawBlackHole hole, Building building, float opacity) {
		var transitionDistance = transitionDistance(hole, building);
		var size = (float) (hole.particleSize / Math.max(0.4, transitionDistance));
		var x = building.x + hole.position.x + position.x;
		var y = building.y + hole.position.y + position.y;
		Draw.reset();
		Draw.color(hole.colorParticle);
		Draw.alpha(opacity * transitionDistance);
		Draw.z(Layer.block);
		Draw.scl(size / 8f);
		Fill.square(
			x,
			y,
			size,
			vector.angle()
		);
		Draw.scl();
		Draw.reset();
		Drawf.light(x, y, size * 2, Color.white, opacity * 0.5f);
	}
	@Override
	public String toString() {
		return "particle(x=" + position.x + ", y=" + position.y + ", mass=" + mass + ")";
	}
}