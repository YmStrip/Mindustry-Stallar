package st;

import arc.util.*;
import mindustry.mod.*;
import st.mod.STTech;
import st.mod.content.STAttribute;
import st.mod.content.STFloor;
import st.mod.content.STLiquid;
import st.mod.distribution.STDistribution;
import st.mod.item.STItem;
import st.mod.modular.struct.StructModular;
import st.mod.multiblock.STMultiBlock;
import st.mod.power.STPower;
import st.mod.qio.STQIO;
import st.mod.modular.STModular;
import st.mod.unit.STUnit;
import st.mod.wall.StWall;


public class index extends Mod {
	public index() {
		Log.info("START LOAD ST");
	}
	@Override
	public void loadContent() {
		//Log.info("name: "+ Vars.content.transformName("x"));
		//
		STTech.Init();
		//
		STItem.Init();
		STModular.Init();
		STUnit.Init();
		STAttribute.Init();
		STFloor.Init();
		STLiquid.Init();
		STPower.Init();
		STMultiBlock.Init();
		StructModular.Init();

		//ST_RESOURCE.load();
		//ST_FUNCTIONAL.load();
		StWall.Init();
		STDistribution.Init();
		STQIO.Init();
	}
}
