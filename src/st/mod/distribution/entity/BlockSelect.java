package st.mod.distribution.entity;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.blocks.ItemSelection;


public abstract class BlockSelect<T extends UnlockableContent> extends Block {
	{
		solid = true;
		underBullets = true;
		update = true;
		canOverdrive = false;
		saveConfig = false;
		noUpdateDisabled = false;
		config(String.class, (building, name) -> {
			((BlockSelectBuild) building).select = GetSelectFromName(name);
		});
	}

	public TextureRegion MainRegion;
	public TextureRegion TopRegion;
	public boolean CanSelect = false;
	public void load() {
		super.load();
		this.MainRegion = Core.atlas.find(this.name);
		this.TopRegion = Core.atlas.find(this.name + "-top");
	}
	@Override
	public void init() {
		super.init();
		if (CanSelect) {
			configurable = true;
		}
	}
	protected abstract T GetSelectFromName(String name);
	protected abstract Seq<T> GetSelectList();
	public BlockSelect(String name) {
		super(name);
	}
	public class BlockSelectBuild extends Building {
		public T select;
		@Override
		public void draw() {
			Draw.rect(MainRegion, this.x, this.y);
			if (TopRegion.found()) {
				if (this.select != null) {
					var color = select instanceof Item ? ((Item) select).color :
						select instanceof Liquid ? ((Liquid) select).color
							: Color.white;
					Draw.color((new Color(color)).a(0.9F));
				} else {
					Draw.color(Color.white);
				}
				Draw.rect(TopRegion, this.x, this.y);
			}
		}
		@Override
		public void buildConfiguration(Table table) {
			ItemSelection.buildTable(table, GetSelectList(), () -> this.select, this::configure);
		}
		@Override
		public boolean onConfigureBuildTapped(Building other) {
			if (!CanSelect) return false;
			if (this == other) {
				this.deselect();
				this.configure((Object) null);
				return false;
			} else {
				return true;
			}
		}
		@Override
		public void configure(Object value) {
			super.configure(value);
			try {
				select = (T) value;
			} catch (Exception e) {
			}
		}
		@Override
		public Object config() {
			return this.select == null ? "null" : this.select.name;
		}
		@Override
		public byte version() {
			return 1;
		}
		@Override
		public void write(Writes write) {
			super.write(write);
			var name = (select == null ? "null" : select.name);
			//System.out.println("write: " + name.length() + " " + (select == null ? "null" : select.name));
			write.str(name);
		}
		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);
			try {
				var name = read.str();
				//System.out.println("read name: " + name);
				select = GetSelectFromName(name);
			} catch (Exception ignored) {
				//System.out.println("15");
				System.out.println(ignored);
			}
		}
		/**
		 * read some shits
		 *
		 * @param read read
		 */
		@Override
		public void read(Reads read) {
			super.read(read);
		}
	}
}
