package st.mod.qio.block;


import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.Button;
import arc.scene.ui.Slider;
import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.type.Item;
import mindustry.ui.Styles;
import mindustry.world.Tile;
import st.ST;
import st.mod.distribution.item.BlockIOItemAbstract;
import st.mod.qio.STQIO;
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
	public int PalceMax = 4;
	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		return super.canPlaceOn(tile, team, rotation) && STQIO.Build.Count(team) < PalceMax;
	}
	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		super.drawPlace(x, y, rotation, valid);
		if (!valid) drawPlaceText(ST.Bar("max_place") + ": " + PalceMax, x, y, valid);
	}
	@Override
	protected float GetAmount(Building building, Item item) {
		return STQIO.Network.Item.Get(item.name);
	}
	@Override
	protected float GetCapacity(Building building, Item item) {
		return STQIO.Network.Item.Capacity;
	}
	@Override
	protected void AddAmount(Building building, Item item) {
		STQIO.Network.Item.Add(item.name, 1);
	}
	@Override
	protected void RemoveAmount(Building building, Item item) {
		STQIO.Network.Item.Remove(item.name, 1);
	}
	@Override
	public boolean CanHandleItem(Building self, Building source, Item item) {
		return !(source instanceof BlockQIOItemInterfaceBuild);
	}
	public class BlockQIOItemInterfaceBuild extends BlockIOItemAbstractBuild {
		public float speed = 0;
		public boolean output = true;
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
			color = BlockQIOItemInterface.this.color;
			drawBlackHole.colorParticle = BlockQIOItemInterface.this.color;
		}};
		@Override
		public void updateTile() {
			draw.tick(this);
			if (select == null) return;
			if (output) {
				this.outputToBuild(team.core());
			} else {
				//get item from core
				var core = team.core();
				if (core == null) return;
				var amount = core.items.get(select);
				var remainCapacity = GetCapacity(this, select) - GetAmount(this, select);
				if (amount < 1 || remainCapacity < 1) return;
				InputBufferIncrease();
				if (InputBuffer > 1) {
					var canTake = (int) Math.min(Math.min(Math.floor(InputBuffer), amount), remainCapacity);
					InputBuffer -= canTake;
					core.items.remove(select, canTake);
					STQIO.Network.Item.Add(select.name, canTake);
				}
			}
		}
		@Override
		public void InputBufferIncrease() {
			InputBuffer = Math.min(speed / 45f * timeScale * efficiency + InputBuffer, InputRate * timeScale);
		}
		@Override
		public void OutputBufferIncrease() {
			OutputBuffer = Math.min(speed / 45f * timeScale * efficiency + OutputBuffer, OutputRate * timeScale);
		}
		@Override
		public void draw() {
			super.draw();
			draw.drawHeat(this, heat);
			draw.draw(this);
		}
		@Override
		public void buildConfiguration(Table table) {
			var container = new Table() {{
				var topBar = new Table() {{
					setBackground(Styles.black5);
					labelWrap(() -> "speed: " + (Math.round(speed * 1000) / 1000) + "/s").left().fillX().padLeft(10).growX().height(40);
					var ioMode = new Button(Icon.up) {{
						clicked(() -> {
							output = !output;
							change();
						});
						changed(() -> setStyle(new ButtonStyle(output ? Icon.down : Icon.up, null, null)));
					}};
					ioMode.change();
					this.add(ioMode).left().size(40);
				}};
				var sub = new Table() {{
					var left = new Table();
					BlockQIOItemInterfaceBuild.super.buildConfiguration(left);
					var right = new Table() {{
						setBackground(Styles.black5);
						var slider = new Slider(0, OutputRate, OutputRate / 120, true) {{
							setValue(speed);
							this.moved(v -> speed = v);
							getKnobDrawable();
						}};
						this.add(slider).fillY().growY().top().left().width(36);
					}};
					this.add(left).fillX().growX().fillY().growY().left().top();
					this.add(right).fillY().growY().top().left();
				}};
				this.add(topBar).fillX().growX().left().top().row();
				this.add(sub).fillX().growX().fillY().growY().left().top();
			}};
			table.add(container).fillX().fillY().growX().growY().left().top().row();
		}
		@Override
		public byte version() {
			return 2;
		}
		@Override
		public void write(Writes write) {
			//1
			/**
			 * super.write(write);
			 */
			super.write(write);
			//config speed
			write.f(speed);
			//is output
			write.bool(output);
		}
		@Override
		public void read(Reads read, byte revision) {
			if (revision == 1) {
				super.read(read, revision);
				return;
			}
			if (revision == 2) {
				super.read(read, revision);
				speed = Math.min(Math.max(read.f(), 0), OutputRate);
				output = read.bool();
			}
		}
	}
}
