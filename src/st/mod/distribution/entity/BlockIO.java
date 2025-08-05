package st.mod.distribution.entity;

import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;


public abstract class BlockIO<T extends UnlockableContent> extends BlockSelect<T> {
	@FunctionalInterface
	public interface getProximityBuilding {
		boolean test(Building building);
	}

	public boolean canInput = false;
	public boolean canOutput = false;
	public float speedInput = 4;
	public float speedOutput = 4;
	public BlockIO(String name) {
		super(name);
		canOverdrive = true;
	}
	public class BlockIOBuild extends BlockSelectBuild {
		//amount can be input in current tick
		public float bufferInput = 0;
		public float bufferOutput = 0;
		//store:
		private boolean bufferInputAdded = false;
		private boolean bufferOutputAdded = false;
		//can only increase once per tick
		public void addBufferInput() {
			if (bufferInputAdded) return;
			bufferInput = Math.min(speedInput / 60f * timeScale * efficiency + bufferInput, speedInput);
		}
		//can only increase once per tick
		public void addBufferOutput() {
			if (bufferOutputAdded) return;
			bufferOutput = Math.min(speedOutput / 60f * timeScale * efficiency + bufferOutput, speedOutput);
		}
		public void removeBufferInput() {
			bufferInput = Math.max(bufferInput / 60f - bufferInput, 0);
		}
		public void removeBufferOutput() {
			bufferOutput = Math.max(speedOutput / 60f - bufferOutput, 0);
		}
		@Override
		public void updateTile() {
			super.updateTile();
			this.bufferInputAdded = false;
			this.bufferOutputAdded = false;
		}
		public Building getProximityBuilding(getProximityBuilding test) {
			for (var i = 0; i < proximity.size; ++i) {
				this.incrementDump(this.proximity.size);
				var other = this.proximity.get((i + cdump) % this.proximity.size);
				if (other == null) continue;
				if (other.team != this.team) continue;
				if (test.test(other)) return other;
			}
			return null;
		}
	}
}
