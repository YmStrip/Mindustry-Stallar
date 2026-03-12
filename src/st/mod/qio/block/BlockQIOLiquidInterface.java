package st.mod.qio.block;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.Tile;
import st.ST;
import st.mod.distribution.STDistribution;
import st.mod.distribution.liquid.BlockIOLiquidAbstract;
import st.mod.qio.STQIO;
import st.mod.qio.draw.DrawQIO;


public class BlockQIOLiquidInterface extends BlockIOLiquidAbstract {
	public Color color = Color.white;
	public TextureRegion heat;
	@Override
	public void load() {
		super.load();
		heat = Core.atlas.find(name + "-heat");
	}
	public BlockQIOLiquidInterface(String name) {
		super(name);
	}
	public int PlaceMax = 4;
	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		return super.canPlaceOn(tile, team, rotation) && STQIO.Build.Count(team) < PlaceMax;
	}
	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		super.drawPlace(x, y, rotation, valid);
		if (!valid) drawPlaceText(ST.Bar("max_place") + ": " + PlaceMax, x, y, valid);
	}
	@Override
	public float GetCapacity(Building building, Liquid liquid) {
		return STQIO.Network.Liquid.Capacity;
	}
	@Override
	public float GetAmount(Building building, Liquid liquid) {
		return STQIO.Network.Liquid.Get(liquid.name);
	}
	@Override
	public void AddAmount(Building building, Liquid liquid, float amount) {
		STQIO.Network.Liquid.Add(liquid.name, amount);
	}
	@Override
	public boolean CanHandleLiquid(Building self, Building source, Liquid liquid) {
		return !(source instanceof BlockQIOLiquidInterfaceBuild);
	}
	public class BlockQIOLiquidInterfaceBuild extends BlockIOLiquidAbstractBuild {
		@Override
		public void placed() {
			super.placed();
			STQIO.Build.Add(this);
		}
		@Override
		public void onRemoved() {
			super.onRemoved();
			STQIO.Build.Remove(this);
		}
		public DrawQIO draw = new DrawQIO() {{
			color = BlockQIOLiquidInterface.this.color;
			drawBlackHole.colorParticle = BlockQIOLiquidInterface.this.color;
		}};
		//output to liquid buffer
		@Override
		public void updateTile() {
			draw.tick(this);
			if (select == null) return;
			var bufferCapacity = STDistribution.Build.GetCapacity(this, select);
			var bufferAmount = STDistribution.Build.GetAmount(this, select);
			var amount = GetAmount(this, select);
			if (amount > 0 && bufferCapacity - bufferAmount > 0) OutputBufferIncrease();
			var maxCanTaken = Math.min(Math.min(OutputBuffer, bufferCapacity - bufferAmount), amount);
			if (maxCanTaken > 0) {
				OutputBuffer -= maxCanTaken;
				AddAmount(this, select, -maxCanTaken);
				STDistribution.Build.AddAmount(this, select, maxCanTaken);
			} else {
				super.updateTile();
			}
		}
		@Override
		public void draw() {
			super.draw();
			draw.drawHeat(this, heat);
			draw.draw(this);
		}
	}
}
