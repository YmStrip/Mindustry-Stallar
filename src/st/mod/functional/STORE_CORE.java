package st.mod.functional;

import mindustry.gen.Building;
import st.mod.functional.entity.SCore;
import st.mod.util.CountBuild;

public class STORE_CORE {
	public static CountBuild<SCore.ScoreBuild> BUILD = new CountBuild<>() {
		@Override
		public boolean Filter(Building build) {
			return build instanceof SCore.ScoreBuild;
		}
	};
}
