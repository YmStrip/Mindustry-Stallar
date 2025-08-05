package st.mod.qio.block;


import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Tile;
import st.ST;
import st.mod.distribution.item.BlockIOItemAbstract;
import st.mod.qio.STORE_QIO;
import st.mod.qio.draw.DrawQIO;


public class BlockQIOItemInterface extends BlockIOItemAbstract {
	public Color color = Color.white;
	public TextureRegion heat;
	@Override
	public void load() {
		super.load();
		heat = Core.atlas.find(name + "-heat");
	}
	public BlockQIOItemInterface(String name) {
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
	protected float getAmount(Building building, Item item) {
		return STORE_QIO.NETWORK.ITEM.get(item.name);
	}
	@Override
	protected float getCapacity(Building building, Item item) {
		return STORE_QIO.NETWORK.ITEM.capacity;
	}
	@Override
	protected void addAmount(Building building, Item item) {
		STORE_QIO.NETWORK.ITEM.add(item.name, 1);
	}
	@Override
	protected void removeAmount(Building building, Item item) {
		STORE_QIO.NETWORK.ITEM.remove(item.name, 1);
	}
	public class BlockQIOItemInterfaceBuild extends BlockIOItemAbstractBuild {
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
			color = BlockQIOItemInterface.this.color;
			drawBlackHole.colorParticle = BlockQIOItemInterface.this.color;
		}};
		@Override
		public void updateTile() {
			draw.tick(this);
			if (select == null) return;
			this.outputToBuild(team.core());
		}
		@Override
		public void draw() {
			super.draw();
			draw.drawHeat(this, heat);
			draw.draw(this);
		}
	}
}
