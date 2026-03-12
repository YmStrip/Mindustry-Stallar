package st.mod.distribution.entity;

import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;


public abstract class BlockIO<T extends UnlockableContent> extends BlockSelect<T> {
	@FunctionalInterface
	public interface getProximityBuilding {
		boolean test(Building building);
	}

	public boolean InputAble = false;
	public boolean OutputAble = false;
	public float InputRate = 4;
	public float InputBufferMax = 4;
	public float OutputRate = 4;
	public float OutputBufferMax = 4;
	public BlockIO(String name) {
		super(name);
		canOverdrive = true;
	}
	public class BlockIOBuild extends BlockSelectBuild {
		//amount can be input in current tick
		public float InputBuffer = 0;
		public float OutputBuffer = 0;
		//lazy scheduler
		public DistributorBlockIO Distributor = new DistributorBlockIO();
		//can only increase once per tick
		public void InputBufferIncrease() {
			InputBuffer = Math.min(InputRate / 45f * timeScale * efficiency + InputBuffer, InputBufferMax * timeScale);
		}
		//can only increase once per tick
		public void OutputBufferIncrease() {
			OutputBuffer = Math.min(OutputRate / 45f * timeScale * efficiency + OutputBuffer, OutputBufferMax * timeScale);
		}
		public void InputBufferReduce() {
			InputBuffer = Math.max(InputBuffer - InputBuffer / 45f, 0);
		}
		public void OutputBufferReduce() {
			OutputBuffer = Math.max(OutputBuffer - OutputRate / 45f, 0);
		}
		public Building GetProximityBuilding(getProximityBuilding test) {
			this.incrementDump(this.proximity.size);
			for (var i = 0; i < proximity.size; ++i) {
				var other = this.proximity.get((i + cdump) % this.proximity.size);
				if (other == null) continue;
				if (other.team != this.team) continue;
				if (test.test(other)) return other;
			}
			return null;
		}
	}
}
