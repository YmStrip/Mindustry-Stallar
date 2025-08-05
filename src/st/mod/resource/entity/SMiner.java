package st.mod.resource.entity;

import arc.Core;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.blocks.production.Separator;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Stat;


import java.util.HashMap;

public class SMiner extends Separator {
	public float boostScale = 1f;
	public float maxBoost = 1f;
	public float baseEfficiency = 0;
	public float minEfficiency = 0;
	public float displayEfficiencyScale = 1f;
	public boolean displayEfficiency = true;
	public boolean scaleLiquidConsumption = false;
	public Attribute attribute = Attribute.heat;
	/**
	 * if true, directly output the item to core(no side effect,not product)
	 */
	public boolean offloadToCore = false;
	/**
	 * if true , allow all attributes
	 */
	public boolean attributeAll = false;
	public SMiner(String name) {
		super(name);
	}
	public HashMap<ItemStack, Float> weight = new HashMap<>();
	public void updateWeight() {
		weight.clear();
		var total = 0;
		for (var i : this.results) total += i.amount;
		for (var i : this.results) weight.put(i, (float) (i.amount / (total == 0 ? 1 : total)));
	}
	@Override
	public void init() {
		super.init();
		updateWeight();
	}
	@Override
	public void setBars() {
		super.setBars();

		if (!displayEfficiency) return;
		if (!attributeAll) {
			addBar("efficiency", (SExcavatorBuild entity) -> new Bar(() -> Core.bundle.format("bar.efficiency", (int) (entity.efficiencyMultiplier() * 100 * displayEfficiencyScale)), () -> Pal.lightOrange, entity::efficiencyMultiplier));
		}
	}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		super.drawPlace(x, y, rotation, valid);

		if (!displayEfficiency) return;

		drawPlaceText(Core.bundle.format("bar.efficiency", (int) ((baseEfficiency + Math.min(maxBoost, boostScale * sumAttribute(attribute, x, y))) * 100f)), x, y, valid);
	}
	@Override
	public float sumAttribute(Attribute attr, int x, int y) {
		if (attributeAll) return 1;
		return super.sumAttribute(attr, x, y);
	}
	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		if (attributeAll) return super.canPlaceOn(tile, team, rotation);
		return baseEfficiency + tile.getLinkedTilesAs(this, tempTiles).sumf(other -> other.floor().attributes.get(attribute)) > minEfficiency;
	}

	@Override
	public void setStats() {
		super.setStats();

		stats.add(baseEfficiency <= 0.0001f ? Stat.tiles : Stat.affinities, attribute, floating, boostScale * size * size, !displayEfficiency);
	}

	public class SExcavatorBuild extends SeparatorBuild {
		public float attrsum = 0f;
		@Override
		public void offload(Item item) {
			if (offloadToCore) {
				var core = team.core();
				if (core == null) return;
				core.items.add(item, 1);
			} else {
				super.offload(item);
			}
		}
		@Override
		public float getProgressIncrease(float base) {
			return super.getProgressIncrease(base) * efficiencyMultiplier();
		}

		public float efficiencyMultiplier() {
			return baseEfficiency + Math.min(maxBoost, boostScale * (attributeAll ? 1 : attrsum)) + attribute.env();
		}

		@Override
		public float efficiencyScale() {
			return scaleLiquidConsumption ? efficiencyMultiplier() : super.efficiencyScale();
		}

		@Override
		public void pickedUp() {
			attrsum = 0f;
			warmup = 0f;
		}

		@Override
		public void onProximityUpdate() {
			super.onProximityUpdate();
			attrsum = sumAttribute(attribute, tile.x, tile.y);
		}
	}
}
