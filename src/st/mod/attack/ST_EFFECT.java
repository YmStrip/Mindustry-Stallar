package st.mod.attack;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.entities.Effect;
import mindustry.entities.effect.WaveEffect;
import mindustry.graphics.Drawf;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;


public class ST_EFFECT {

	public static Color WHITE = Color.rgb(200, 210, 219);
	public static Color YELLOW = Color.rgb(255, 255, 0);
	public static Color YELLOW_DARK = Color.rgb(209, 192, 0);

	public static Color CYAN = Color.rgb(0, 255, 255);
	public static Color CYAN_DARK = Color.rgb(0, 219, 219);
	public static Color CYAN_LIGHT = Color.rgb(161, 219, 219);
	public static Color GREEN = Color.rgb(39, 255, 136);
	public static Color GREEN_DARK = Color.rgb(31, 201, 107);
	public static Color BLUE = Color.rgb(10, 229, 255);
	public static Color BLUE_DARK = Color.rgb(8, 173, 193);
	public static Color RED = Color.rgb(255, 8, 8);
	public static Color RED_DARK = Color.rgb(209, 4, 7);
	public static Color PURPLE = Color.rgb(253, 7, 250);
	public static Color PURPLE_DARK = Color.rgb(184, 5, 182);

	public static Effect EFFECT_LIGHT(Color light) {
		return new Effect(10f, 500f, e -> {
			if (!(e.data instanceof Seq)) return;
			Seq<Vec2> lines = e.data();
			stroke(3f * e.fout());
			color(light, Color.white, e.fin());
			for (int i = 0; i < lines.size - 1; i++) {
				Vec2 cur = lines.get(i);
				Vec2 next = lines.get(i + 1);
				Lines.line(cur.x, cur.y, next.x, next.y, false);
			}
			for (Vec2 p : lines) {
				Fill.circle(p.x, p.y, Lines.getStroke() / 2f);
			}
		});
	}
	public static Effect EFFECT_RAIL_THIN(Color light) {
		return new Effect(16f, e -> {
			color(Color.white);
			stroke(4);
			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, 4f * e.fout(), 24f, e.rotation + 90 + 90f * i);
			}
			Drawf.light(e.x, e.y, 60f * e.fout(), light, 0.5f);
		});
	}

	public static Effect EFFECT_RAIL(Color light, Color dark) {
		return EFFECT_RAIL(light, dark, 15);
	}

	public static Effect EFFECT_RAIL(Color light, Color dark, float width) {
		return new Effect(30, e -> {
			for (int i = 0; i < 2; i++) {
				color(i == 0 ? WHITE : light);
				float m = i == 0 ? 1f : 0.5f;

				float rot = e.rotation + 180f;
				float w = width * e.fout() * m;
				Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, width)) * m, rot);
				Drawf.tri(e.x, e.y, w, width * 0.666f * m, rot + 180f);
			}

			Drawf.light(e.x, e.y, 60f, dark, 0.6f * e.fout());
		});
	}

	public static Effect EFFECT_SHOOT(Color light, Color dark, float width) {
		return new Effect(24f, e -> {
			e.scaled(10f, b -> {
				color(light, dark, b.fin());
				stroke(b.fout() * 3f + 0.2f);
				Lines.circle(b.x, b.y, b.fin() * width * 2);
			});

			color(light);

			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, 13f * e.fout(), width * 3, e.rotation + 90f * i);
				Drawf.tri(e.x, e.y, width / 2 * e.fout(), width * 2, e.rotation + 20f * i);
			}

			Drawf.light(e.x, e.y, 180f, dark, 0.9f * e.fout());
		});
	}
	public static Effect EFFECT_SHOOT(Color light, Color dark) {
		return EFFECT_SHOOT(light, dark, 23);
	}
	public static Effect EFFECT_ATTACK_SMOKE(Color light, Color dark) {
		return new Effect(70, e -> {
			randLenVectors(e.id, e.fin(), 30, 30f, (x, y, fin, fout) -> {
				color(light);
				alpha((0.5f - Math.abs(fin - 0.5f)) * 2f);
				Fill.circle(e.x + x, e.y + y, 0.5f + fout * 4f);
			});
		});
	}
	public static Effect EFFECT_ATTACK(Color light, Color dark) {
		return EFFECT_ATTACK(light, dark, 23);
	}
	public static Effect EFFECT_ATTACK(Color light, Color dark, float width) {
		return new Effect(20f, 200f, e -> {
			color(dark);
			for (int i = 0; i < 2; i++) {
				color(i == 0 ? light : WHITE);

				float m = i == 0 ? 1f : 0.5f;

				for (int j = 0; j < 5; j++) {
					float rot = e.rotation + Mathf.randomSeedRange(e.id + j, width * 2);
					float w = width * e.fout() * m;
					Drawf.tri(e.x, e.y, w, (width * 3 + Mathf.randomSeedRange(e.id + j, width * 2)) * m, rot);
					Drawf.tri(e.x, e.y, w, width * 2 * m, rot + 180f);
				}
			}

			e.scaled(10f, c -> {
				color(light);
				stroke(c.fout() * 2f + 0.2f);
				Lines.circle(e.x, e.y, c.fin() * 30f);
			});

			e.scaled(12f, c -> {
				color(dark);
				randLenVectors(e.id, 25, 5f + e.fin() * 80f, e.rotation, 60f, (x, y) -> {
					Fill.square(e.x + x, e.y + y, c.fout() * 3f, 45f);
				});
			});
		});
	}

	public static Effect EFFECT_DISAPPEAR(Color light, Color dark) {
		return new Effect(45f, 100f, e -> {
			color(dark);
			stroke(e.fout() * 4f);
			Lines.circle(e.x, e.y, 4f + e.finpow() * 20f);

			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, 6f, 80f * e.fout(), i * 90 + 45);
			}

			color();
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, 3f, 30f * e.fout(), i * 90 + 45);
			}

			Drawf.light(e.x, e.y, 150f, dark, 0.9f * e.fout());
		});
	}

	public static Effect EFFECT_EXPLODE(Color color, float range) {
		return new WaveEffect() {{
			lightOpacity = 0.5f;
			lifetime = 30;
			sizeFrom = 2f;
			sizeTo = range;
			strokeFrom = 2f;
			strokeTo = range / 10;
			colorFrom = Color.white;
			colorTo = color;
		}};
	}

	public static Effect RAIL_BLUE = EFFECT_RAIL(CYAN, CYAN_DARK);
	public static Effect SHOOT_BLUE = EFFECT_SHOOT(CYAN, CYAN_DARK);
	public static Effect ATTACK_BLUE = EFFECT_ATTACK(CYAN, CYAN_DARK);
	public static Effect DISAPPEAR_BLUE = EFFECT_DISAPPEAR(CYAN, CYAN_DARK);
}
