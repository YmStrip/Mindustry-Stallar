package st;

import arc.util.*;
import mindustry.mod.*;
import st.mod.ST_TECH;
import st.mod.content.ST_ATTRIBUTE;
import st.mod.content.ST_FLOOR;
import st.mod.content.ST_LIQUID;
import st.mod.distribution.ST_DISTRIBUTION;
import st.mod.functional.FunctionalModule;
import st.mod.functional.ST_FUNCTIONAL;
import st.mod.item.ItemModule;
import st.mod.item.ST_ITEM;
import st.mod.power.PowerModule;
import st.mod.power.ST_POWER;
import st.mod.product.ProductModule;
import st.mod.product.ST_PRODUCT;
import st.mod.distribution.DistributionModule;
import st.mod.qio.QIOModule;
import st.mod.qio.ST_QIO;
import st.mod.resource.ResourceModule;
import st.mod.resource.ST_RESOURCE;
import st.mod.turret.ST_TURRET;
import st.mod.turret.TurretModule;
import st.mod.unit.ST_UNIT;
import st.mod.unit.UnitModule;
import st.mod.wall.ST_WALL;
import st.mod.wall.WallModule;


public class index extends Mod {
	public index() {
		Log.info("START LOAD ST");
	}
	@Override
	public void loadContent() {
		//Log.info("name: "+ Vars.content.transformName("x"));
		//
		ST_TECH.load();
		//
		ST_ITEM.load();
		ST_UNIT.load();
		ST_ATTRIBUTE.load();
		ST_FLOOR.load();
		ST_LIQUID.load();
		ST_PRODUCT.load();
		ST_POWER.load();
		ST_RESOURCE.load();
		ST_TURRET.load();
		ST_FUNCTIONAL.load();
		ST_WALL.load();
		ST_DISTRIBUTION.load();
		ST_QIO.load();
		//
		new ItemModule();
		new PowerModule();
		new FunctionalModule();
		new ProductModule();
		new ResourceModule();
		new TurretModule();
		new UnitModule();
		new WallModule();
		new DistributionModule();
		new QIOModule();
	}
}
