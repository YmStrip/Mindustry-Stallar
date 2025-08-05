package st.mod.distribution.liquid;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import mindustry.game.Team;
import mindustry.world.Tile;
import mindustry.world.blocks.liquid.LiquidRouter;
import st.ST;
import st.mod.distribution.STORE_LIQUID_BUFFER;

import static mindustry.Vars.player;
import static mindustry.Vars.world;

public class BlockLiquidBuffer extends LiquidRouter {
	//all liquid buffer use global placed store
	public int maxPlace = 1;
	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		return (STORE_LIQUID_BUFFER.BUILD.count(team) < maxPlace);
	}
	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		super.drawPlace(x, y, rotation, valid);
		if (!canPlaceOn(world.tile(x, y), player.team(), rotation)) {
			if (STORE_LIQUID_BUFFER.BUILD.count(player.team()) >= maxPlace) {
				drawPlaceText(Core.bundle.get(ST.bar("max_place") + ": " + maxPlace), x, y, valid);
			}
		}
	}
	@Override
	public TextureRegion[] icons() {
		return new TextureRegion[]{region};
	}
	public BlockLiquidBuffer(String name) {
		super(name);
		outputsLiquid = true;
		update = true;
		solid = true;
		hasLiquids = true;
	}
	public class BlockBufferLiquidBuild extends LiquidRouterBuild {
		@Override
		public void placed() {
			super.placed();
			STORE_LIQUID_BUFFER.BUILD.add(this);
		}
		@Override
		public void onRemoved() {
			super.onRemoved();
			STORE_LIQUID_BUFFER.BUILD.remove(this);
		}
	}
}
