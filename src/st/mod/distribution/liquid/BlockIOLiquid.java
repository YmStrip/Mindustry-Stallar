package st.mod.distribution.liquid;


import mindustry.gen.Building;
import mindustry.type.Liquid;
import st.mod.distribution.STORE_LIQUID_BUFFER;

public class BlockIOLiquid extends BlockIOLiquidAbstract {

	public BlockIOLiquid(String name) {
		super(name);
	}
	@Override
	public float getCapacity(Building building, Liquid liquid) {
		return STORE_LIQUID_BUFFER.BUILD.getCapacity(building, liquid);
	}
	@Override
	public float getAmount(Building building, Liquid liquid) {
		return STORE_LIQUID_BUFFER.BUILD.getAmount(building, liquid);
	}
	@Override
	public void addAmount(Building building, Liquid liquid, float amount) {
		STORE_LIQUID_BUFFER.BUILD.addAmount(building, liquid, amount);
	}
}
