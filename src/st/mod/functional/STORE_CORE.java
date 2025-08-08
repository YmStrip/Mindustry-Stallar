package st.mod.functional;

import mindustry.gen.Building;
import st.mod.functional.entity.SCore;
import st.mod.util.SBuildMap;

public class STORE_CORE {
	public static SBuildMap<SCore.ScoreBuild> BUILD = new SBuildMap<>() {
		@Override
		public boolean testBuild(Building build) {
			return build instanceof SCore.ScoreBuild;
		}
	};
}
