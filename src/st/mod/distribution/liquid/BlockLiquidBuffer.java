package st.mod.distribution.liquid;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.util.io.Reads;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.Tile;
import mindustry.world.blocks.liquid.LiquidRouter;
import st.ST;
import st.mod.distribution.STDistribution;

import static mindustry.Vars.player;
import static mindustry.Vars.world;

public class BlockLiquidBuffer extends LiquidRouter {
	//all liquid buffer use global placed store
	public int maxPlace = 1;
	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		return (STDistribution.Build.Count(team) < maxPlace);
	}
	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		super.drawPlace(x, y, rotation, valid);
		if (!canPlaceOn(world.tile(x, y), player.team(), rotation)) {
			if (STDistribution.Build.Count(player.team()) >= maxPlace) {
				drawPlaceText(Core.bundle.get(ST.Bar("max_place") + ": " + maxPlace), x, y, valid);
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
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return liquidCapacity - liquids.get(liquid) > 0.2f;
		}
		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);
			STDistribution.Build.Add(this);
		}
		@Override
		public void placed() {
			super.placed();
			STDistribution.Build.Add(this);
		}
		@Override
		public void onRemoved() {
			super.onRemoved();
			STDistribution.Build.Remove(this);
		}
	}
}
