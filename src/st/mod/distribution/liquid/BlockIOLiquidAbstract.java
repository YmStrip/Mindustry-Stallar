package st.mod.distribution.liquid;

import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import st.mod.distribution.entity.BlockIO;
import st.mod.distribution.entity.DistributorBlockIO;


public abstract class BlockIOLiquidAbstract extends BlockIO<Liquid> {
	//change liquidCapacity to fit IO-SPEED
	@Override
	public void init() {
		super.init();
		this.liquidCapacity = this.speedInput / 60f;
	}
	@Override
	protected Liquid getSelectFromName(String name) {
		return Vars.content.liquid(name);
	}
	@Override
	protected Seq<Liquid> getSelectList() {
		return Vars.content.liquids();
	}
	public abstract float getCapacity(Building building, Liquid liquid);
	public abstract float getAmount(Building building, Liquid liquid);
	public abstract void addAmount(Building building, Liquid liquid, float amount);
	public abstract boolean canHandleLiquid(Building self, Building source, Liquid liquid);
	public BlockIOLiquidAbstract(String name) {
		super(name);
		underBullets = false;
		hasLiquids = true;
	}
	public class BlockIOLiquidAbstractBuild extends BlockIOBuild {
		//output
		@Override
		public void updateTile() {
			super.updateTile();
			if (select == null || proximity.size == 0) return;
			var offset = distributor.next(proximity.size);
			var build = proximity.get(offset);
			if (build == null || !build.acceptLiquid(this, select)) {
				distributor.update(weight -> -DistributorBlockIO.softNorm(weight) / 24f);
				return;
			}
			//0.5
			//addBufferOutput();
			var weight = distributor.update(w -> DistributorBlockIO.softNorm(w / 24));
			// 1/2 * 3 = 3/2
			var amount = speedOutput / 45f * weight * proximity.size * timeScale;
			//System.out.println("amount " + amount + " , total " + distributor.weightTotal + " op" + proximity.size);
			outputToBuild(build, amount);
		}
		public void outputToBuild(@Nullable Building target) {
			outputToBuild(target, bufferOutput);
		}
		public void outputToBuild(@Nullable Building target, float amount) {
			if (target == null || target.liquids == null) return;
			var maxCanTaken = Math.min(Math.min(amount, target.block.liquidCapacity - target.liquids.get(select)), getAmount(this, select));
			if (maxCanTaken <= 0) return;
			bufferOutput = Math.min(bufferOutput - maxCanTaken, 0);
			addAmount(this, select, -maxCanTaken);
			target.handleLiquid(this, select, maxCanTaken);
		}
		//input
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			if (!canInput) return false;
			if (!canHandleLiquid(this, source, liquid)) return false;
			if (source.team != team || source.liquids == null) return false;
			this.addBufferInput();
			var maxCanTaken = Math.min(Math.min(source.liquids.get(liquid), bufferInput), getCapacity(source, liquid) - getAmount(source, liquid));
			if (bufferInput > liquidCapacity) bufferInput = liquidCapacity;
			if (maxCanTaken <= 0) return false;
			bufferInput -= maxCanTaken;
			source.liquids.remove(liquid, maxCanTaken);
			addAmount(source, liquid, maxCanTaken);
			return false;
		}
		@Override
		public void handleLiquid(Building source, Liquid liquid, float amount) {
			addAmount(source, liquid, amount);
			bufferInput -= amount;
			if (bufferInput < 0) bufferInput = 0;
		}
	}
}
