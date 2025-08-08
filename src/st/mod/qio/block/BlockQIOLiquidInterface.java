package st.mod.qio.block;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.Tile;
import st.ST;
import st.mod.distribution.STORE_LIQUID_BUFFER;
import st.mod.distribution.liquid.BlockIOLiquidAbstract;
import st.mod.qio.STORE_QIO;
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
	public int maxPlace = 4;
	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		return super.canPlaceOn(tile, team, rotation) && STORE_QIO.BUILD.count(team) < maxPlace;
	}
	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		super.drawPlace(x, y, rotation, valid);
		if (!valid) drawPlaceText(ST.bar("max_place") + ": " + maxPlace, x, y, valid);
	}
	@Override
	public float getCapacity(Building building, Liquid liquid) {
		return STORE_QIO.NETWORK.LIQUID.capacity;
	}
	@Override
	public float getAmount(Building building, Liquid liquid) {
		return STORE_QIO.NETWORK.LIQUID.get(liquid.name);
	}
	@Override
	public void addAmount(Building building, Liquid liquid, float amount) {
		STORE_QIO.NETWORK.LIQUID.add(liquid.name, amount);
	}
	@Override
	public boolean canHandleLiquid(Building self, Building source, Liquid liquid) {
		return !(source instanceof BlockQIOLiquidInterfaceBuild);
	}
	public class BlockQIOLiquidInterfaceBuild extends BlockIOLiquidAbstractBuild {
		@Override
		public void placed() {
			super.placed();
			STORE_QIO.BUILD.add(this);
		}
		@Override
		public void onRemoved() {
			super.onRemoved();
			STORE_QIO.BUILD.remove(this);
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
			var bufferCapacity = STORE_LIQUID_BUFFER.BUILD.getCapacity(this, select);
			var bufferAmount = STORE_LIQUID_BUFFER.BUILD.getAmount(this, select);
			var amount = getAmount(this, select);
			if (amount > 0 && bufferCapacity - bufferAmount > 0) addBufferOutput();
			var maxCanTaken = Math.min(Math.min(bufferOutput, bufferCapacity - bufferAmount), amount);
			if (maxCanTaken > 0) {
				bufferOutput -= maxCanTaken;
				addAmount(this, select, -maxCanTaken);
				STORE_LIQUID_BUFFER.BUILD.addAmount(this, select, maxCanTaken);
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
