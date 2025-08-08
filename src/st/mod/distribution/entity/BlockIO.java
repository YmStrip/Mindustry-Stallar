package st.mod.distribution.entity;

import arc.struct.Seq;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;

import java.util.ArrayList;


public abstract class BlockIO<T extends UnlockableContent> extends BlockSelect<T> {
	@FunctionalInterface
	public interface getProximityBuilding {
		boolean test(Building building);
	}

	public boolean canInput = false;
	public boolean canOutput = false;
	public float speedInput = 4;
	public float bufferInputMax = 4;
	public float speedOutput = 4;
	public float bufferOutputMax = 4;
	public BlockIO(String name) {
		super(name);
		canOverdrive = true;
	}
	public class BlockIOBuild extends BlockSelectBuild {
		//amount can be input in current tick
		public float bufferInput = 0;
		public float bufferOutput = 0;
		//lazy scheduler
		public DistributorBlockIO distributor = new DistributorBlockIO();
		//can only increase once per tick
		public void addBufferInput() {
			bufferInput = Math.min(speedInput / 45f * timeScale * efficiency + bufferInput, bufferInputMax * timeScale);
		}
		//can only increase once per tick
		public void addBufferOutput() {
			bufferOutput = Math.min(speedOutput / 45f * timeScale * efficiency + bufferOutput, bufferOutputMax * timeScale);
		}
		public void removeBufferInput() {
			bufferInput = Math.max(bufferInput - bufferInput / 45f, 0);
		}
		public void removeBufferOutput() {
			bufferOutput = Math.max(bufferOutput - speedOutput / 45f, 0);
		}
		public Building getProximityBuilding(getProximityBuilding test) {
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
