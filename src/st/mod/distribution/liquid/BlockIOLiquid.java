package st.mod.distribution.liquid;


import mindustry.gen.Building;
import mindustry.type.Liquid;
import st.mod.distribution.STDistribution;


public class BlockIOLiquid extends BlockIOLiquidAbstract {

	public BlockIOLiquid(String name) {
		super(name);
	}
	@Override
	public float GetCapacity(Building building, Liquid liquid) {
		return STDistribution.Build.GetCapacity(building, liquid);
	}
	@Override
	public float GetAmount(Building building, Liquid liquid) {
		return STDistribution.Build.GetAmount(building, liquid);
	}
	@Override
	public void AddAmount(Building building, Liquid liquid, float amount) {
		STDistribution.Build.AddAmount(building, liquid, amount);
	}
	@Override
	public boolean CanHandleLiquid(Building self, Building source, Liquid liquid) {
		return !(source instanceof BlockIOLiquidAbstractBuild);
	}
}
