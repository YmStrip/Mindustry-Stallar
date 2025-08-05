package st.mod.util;

import arc.Core;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Stat;

public class STransBlock extends Block {
	public boolean displayEfficiency = true;
	public float time = 60;
	public Attribute attr;
	public float attrMultiplier = 1;
	public float baseEfficiency = 1;

	@Override
	public void setBars() {
		super.setBars();
		
		if (!displayEfficiency) return;
		
		addBar("efficiency", (STransBuild entity) ->
			new Bar(
				() -> Core.bundle.format("bar.efficiency", (int) (entity.efficiency() * 100)),
				() -> Pal.lightOrange,
				entity::efficiency));
	}

	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		if (attr == null) return true;
		return baseEfficiency + tile.getLinkedTilesAs(this, tempTiles).sumf(other -> other.floor().attributes.get(attr)) > 0;
	}
	
	@Override
	public void setStats() {
		super.setStats();
		stats.add(baseEfficiency <= 0.0001f ? Stat.tiles : Stat.affinities, attr, floating, size * size, !displayEfficiency);
	}
	
	public STransBlock(String name) {
		super(name);
		hasLiquids = true;
	}
	
	public class STransBuild extends Building {
		public float efficiency() {
			return 0;
		}
		
		public void trans() {
		
		}
		
		@Override
		public void updateTile() {
			trans();
		}
	}
}
