package st.mod.functional.entity;

import arc.Core;
import mindustry.game.Team;
import mindustry.type.Category;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;
import st.ST;
import st.mod.functional.STORE_CORE;
import st.mod.util.SBuildMap;



import static mindustry.Vars.*;
import static mindustry.Vars.player;

public class SCore extends CoreBlock {
	public boolean canBreak = false;
	public int maxCore = 1;
	public int maxPlace = 1;


	@Override
	public boolean canBreak(Tile tile) {
		return state.isEditor() || canBreak;
	}
	public SCore(String name) {
		super(name);
		category = Category.effect;
	}
	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		return team.cores().size < maxCore && STORE_CORE.BUILD.count(team) < maxPlace;
	}
	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		if (world.tile(x, y) == null) return;
		if (!canPlaceOn(world.tile(x, y), player.team(), rotation)) {
			if (STORE_CORE.BUILD.count(player.team()) >= maxPlace) {
				drawPlaceText(Core.bundle.get(ST.bar("max_place") + ": " + maxPlace), x, y, valid);
			} else if (player.team().cores().size >= maxCore) {
				drawPlaceText(Core.bundle.get(ST.bar("max_place") + ": " + maxCore), x, y, valid);
			}
			//Recursive o(1) drawPlace -> canPlaceOn -> super.drawPlace -> canPlaceOn
			else super.drawPlace(x, y, rotation, valid);
		}
	}
	public class ScoreBuild extends CoreBuild {
		@Override
		public void placed() {
			super.placed();
			STORE_CORE.BUILD.add(this);
		}
		@Override
		public void onRemoved() {
			super.onRemoved();
			STORE_CORE.BUILD.remove(this);
		}
	}
}
