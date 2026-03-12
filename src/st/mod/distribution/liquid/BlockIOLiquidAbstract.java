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
		this.liquidCapacity = this.InputRate / 60f;
	}
	@Override
	protected Liquid GetSelectFromName(String name) {
		return Vars.content.liquid(name);
	}
	@Override
	protected Seq<Liquid> GetSelectList() {
		return Vars.content.liquids();
	}
	public abstract float GetCapacity(Building building, Liquid liquid);
	public abstract float GetAmount(Building building, Liquid liquid);
	public abstract void AddAmount(Building building, Liquid liquid, float amount);
	public abstract boolean CanHandleLiquid(Building self, Building source, Liquid liquid);
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
			var offset = Distributor.Next(proximity.size);
			var build = proximity.get(offset);
			if (build == null || !build.acceptLiquid(this, select)) {
				Distributor.Update(weight -> -DistributorBlockIO.SoftNorm(weight) / 24f);
				return;
			}
			//0.5
			//addBufferOutput();
			var weight = Distributor.Update(w -> DistributorBlockIO.SoftNorm(w / 24));
			// 1/2 * 3 = 3/2
			var amount = OutputRate / 45f * weight * proximity.size * timeScale;
			//System.out.println("amount " + amount + " , total " + distributor.weightTotal + " op" + proximity.size);
			OutputToBuild(build, amount);
		}
		public void OutputToBuild(@Nullable Building target) {
			OutputToBuild(target, OutputBuffer);
		}
		public void OutputToBuild(@Nullable Building target, float amount) {
			if (target == null || target.liquids == null) return;
			var maxCanTaken = Math.min(Math.min(amount, target.block.liquidCapacity - target.liquids.get(select)), GetAmount(this, select));
			if (maxCanTaken <= 0) return;
			OutputBuffer = Math.min(OutputBuffer - maxCanTaken, 0);
			AddAmount(this, select, -maxCanTaken);
			target.handleLiquid(this, select, maxCanTaken);
		}
		//input
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			if (!InputAble) return false;
			if (!CanHandleLiquid(this, source, liquid)) return false;
			if (source.team != team || source.liquids == null) return false;
			this.InputBufferIncrease();
			var maxCanTaken = Math.min(Math.min(source.liquids.get(liquid), InputBuffer), GetCapacity(source, liquid) - GetAmount(source, liquid));
			if (InputBuffer > liquidCapacity) InputBuffer = liquidCapacity;
			if (maxCanTaken <= 0) return false;
			InputBuffer -= maxCanTaken;
			source.liquids.remove(liquid, maxCanTaken);
			AddAmount(source, liquid, maxCanTaken);
			return false;
		}
		@Override
		public void handleLiquid(Building source, Liquid liquid, float amount) {
			AddAmount(source, liquid, amount);
			InputBuffer -= amount;
			if (InputBuffer < 0) InputBuffer = 0;
		}
	}
}
