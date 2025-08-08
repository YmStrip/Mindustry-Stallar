package st.mod.qio.block;


import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.type.UnitType;
import mindustry.world.Tile;
import mindustry.world.blocks.payloads.Payload;
import st.ST;
import st.mod.distribution.unit.BlockIOUnitAbstract;
import st.mod.qio.STORE_QIO;
import st.mod.qio.draw.DrawQIO;

public class BlockQIOUnitInterface extends BlockIOUnitAbstract {
	public Color color = Color.white;
	public TextureRegion heat;
	@Override
	public void load() {
		super.load();
		heat = Core.atlas.find(name + "-heat");
	}
	public BlockQIOUnitInterface(String name) {
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
	public void addAmount(Building source, UnitType unit, float count) {
		STORE_QIO.NETWORK.UNIT.add(unit.name, count);
	}
	@Override
	public float getAmount(Building source, UnitType unit) {
		return STORE_QIO.NETWORK.UNIT.get(unit.name);
	}
	@Override
	public float getCapacity(Building source, UnitType unit) {
		return STORE_QIO.NETWORK.UNIT.capacity;
	}
	@Override
	public boolean canHandlePayload(Building self, Building source, Payload payload) {
		return !(source instanceof BlockQIOUnitInterfaceBuild);
	}
	public class BlockQIOUnitInterfaceBuild extends BlockIOUnitAbstractBuild {
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
			color = BlockQIOUnitInterface.this.color;
			shieldRange = 18 * 8f;
			drawBlackHole.radius = 1.3f * 8;
			drawBlackHole.colorParticle = BlockQIOUnitInterface.this.color;
		}};
		@Override
		public void updateTile() {
			draw.tick(this);
			super.updateTile();
		}
		@Override
		public void draw() {
			super.draw();
			draw.drawHeat(this, heat);
			draw.draw(this);
		}
	}
}
