package st.mod.distribution.unit;

import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.payloads.UnitPayload;
import st.mod.distribution.entity.BlockIO;

import static mindustry.Vars.state;


public abstract class BlockIOUnitAbstract extends BlockIO<UnitType> {
	public BlockIOUnitAbstract(String name) {
		super(name);
		acceptsPayload = true;
	}
	@Override
	protected UnitType getSelectFromName(String name) {
		return Vars.content.unit(name);
	}
	@Override
	protected Seq<UnitType> getSelectList() {
		return Vars.content.units();
	}
	@Override
	public void setBars() {
		super.setBars();
		addBar("progress", (BlockIOUnitAbstractBuild e) -> new Bar("bar.progress", Pal.ammo, e::fraction));
	}
	public abstract void addAmount(Building source, UnitType unit, float count);
	public abstract float getAmount(Building source, UnitType unit);
	public abstract float getCapacity(Building source, UnitType unit);
	public class BlockIOUnitAbstractBuild extends BlockIOBuild {
		public float progress = 0;
		public float fraction() {
			return select == null ? 0 : (progress);
		}
		public void removeProgress() {
			this.progress += speedOutput / 60;
		}
		public void addProgress() {
			this.progress += speedOutput / 60;
		}
		//input unit
		@Override
		public boolean acceptPayload(Building source, Payload payload) {
			//must be unitType
			if (!(payload instanceof UnitPayload unitPayload)) return false;
			if (unitPayload.unit == null) return false;
			//cannot input damaged
			if (unitPayload.unit.health < unitPayload.unit.health * 0.8) return false;
			if (getCapacity(this, unitPayload.unit.type) - getAmount(this, unitPayload.unit.type) < 1) {
				removeBufferInput();
				return false;
			}
			if (efficiency < 1) {
				removeBufferInput();
				return false;
			}
			addBufferInput();
			return !(bufferInput < 1);
		}
		@Override
		public void handlePayload(Building source, Payload payload) {
			if (!(payload instanceof UnitPayload unitPayload)) return;
			if (unitPayload.unit == null) return;
			this.bufferInput = Math.max(0, this.bufferInput - 1);
			addAmount(this, unitPayload.unit.type, 1);
		}
		//reset progress when change select
		@Override
		public void configure(Object value) {
			super.configure(value);
			if (value instanceof UnitType unitType) {
				if (select != unitType) {
					this.bufferOutput = 0;
				}
			}
		}
		public Vec2 getProximityPosition(float a, float b) {
			var theta = Math.random() * Math.PI * 2;
			var pow = 30;
			var r = 1 / Math.pow(
				Math.pow(Math.abs(Math.cos(theta) / a * 2), pow) +
					Math.pow(Math.abs(Math.sin(theta) / b * 2), pow),
				1f / pow
			);
			var x = Math.cos(theta) * r;
			var y = Math.sin(theta) * r;
			var vec = new Vec2();
			vec.x = (float) x;
			vec.y = (float) y;
			return vec;
		}
		//output unit
		@Override
		public void updateTile() {
			if (this.efficiency < 1 || select == null || state.rules.isBanned(select) || getAmount(this, select) < 1) {
				removeProgress();
				return;
			}
			var data = team.data();
			var units = data.getUnits(select);
			if (units != null && data.unitCap - units.size < 1) {
				removeProgress();
				System.out.println("remove");
			}
			addProgress();
			if (progress >= 1) {
				addAmount(this, select, -1);
				//if not a flying unit, it maybe booms directly
				var position = getProximityPosition(size + 1, size + 1)
					.scl(8)
					.add(x, y);
				progress = Math.max(0, progress - 1);
				select.spawn(team, position.x, position.y);
				/*unit.x = x + size / 2f;
				unit.y = y + size / 2f;
				//
				//no client dumping
				if (Vars.net.client()) return;
				//prevents stacking
				unit.vel.add(Mathf.range(0.5f), Mathf.range(0.5f));
				unit.add();
				unit.unloaded();
				Events.fire(new EventType.UnitUnloadEvent(unit));
				Units.notifyUnitSpawn(unit);*/
			}
		}
		@Override
		public void write(Writes write) {
			super.write(write);
			write.f(progress);
		}
		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);
			progress = read.f();
		}
	}
}
