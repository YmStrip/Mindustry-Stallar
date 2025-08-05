package st.mod.qio;


import st.id.entity.IDEventEmitter;
import st.mod.distribution.entity.BlockIO;
import st.mod.qio.entity.QNetwork;
import st.mod.util.SBuildMap;

public class STORE_QIO {
	public static SBuildMap<BlockIO.BlockIOBuild> BUILD = new SBuildMap<>();
	public static QNetwork NETWORK = new QNetwork();
	public static IDEventEmitter EVENT = new IDEventEmitter();
}
