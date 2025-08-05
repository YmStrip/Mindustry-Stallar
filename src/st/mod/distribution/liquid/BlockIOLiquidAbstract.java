package st.mod.distribution.liquid;

import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import st.mod.distribution.entity.BlockIO;


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
			if (select == null) return;
			outputToBuild(getProximityBuilding(building -> building.acceptLiquid(this, select)));
		}

		public void outputToBuild(@Nullable Building target) {
			if (target == null || target.liquids == null) return;
			addBufferOutput();
			var maxCanTaken = Math.min(Math.min(bufferOutput, target.block.liquidCapacity - target.liquids.get(select)), getAmount(this, select));
			//if (bufferOutput > liquidCapacity) bufferOutput = liquidCapacity;
			if (maxCanTaken <= 0) return;
			bufferOutput -= maxCanTaken;
			addAmount(this, select, -maxCanTaken);
			target.handleLiquid(this, select, maxCanTaken);
		}
		//input
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
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
