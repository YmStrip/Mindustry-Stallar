package st.mod.qio;


import mindustry.gen.Building;
import st.id.entity.IDEventEmitter;
import st.mod.distribution.entity.BlockIO;
import st.mod.qio.block.BlockQIOItemInterface;
import st.mod.qio.block.BlockQIOLiquidInterface;
import st.mod.qio.block.BlockQIOUnitInterface;
import st.mod.qio.entity.QNetwork;
import st.mod.util.SBuildMap;

public class STORE_QIO {
	public static SBuildMap<BlockIO.BlockIOBuild> BUILD = new SBuildMap<>() {
		@Override
		public boolean testBuild(Building build) {
			return (
				build instanceof BlockQIOItemInterface.BlockQIOItemInterfaceBuild ||
					build instanceof BlockQIOLiquidInterface.BlockQIOLiquidInterfaceBuild ||
					build instanceof BlockQIOUnitInterface.BlockQIOUnitInterfaceBuild
			);
		}
	};
	public static QNetwork NETWORK = new QNetwork();
	public static IDEventEmitter EVENT = new IDEventEmitter();
}
