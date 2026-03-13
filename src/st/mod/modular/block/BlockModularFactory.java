package st.mod.modular.block;


import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import arc.struct.EnumSet;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.BuildVisibility;
import st.ST;
import st.mod.modular.STModular;
import st.mod.modular.entity.Recipe;
import st.mod.modular.struct.StructModular;
import st.mod.modular.ui.DialogModular;
import st.mod.multiblock.STMultiBlock;
import st.mod.ui.UtilUI;

import java.util.Arrays;
import java.util.Objects;


public class BlockModularFactory extends BlockModular {
	public static String FilterRecipe = "";
	public TextureRegion IconBase;
	public TextureRegion IconHeat;
	public TextureRegion IconTop;
	//
	public Effect EffectCraft = Fx.none;
	public Effect EffectUpdate = Fx.none;
	//
	public BlockModularFactory(String name) {
		super(name);
		buildVisibility = BuildVisibility.shown;
		hasItems = true;
		hasLiquids = true;
		hasPower = true;
		flags = EnumSet.of(BlockFlag.factory);
		drawArrow = false;
		configurable = true;
		config(String.class, (building, string) -> {
			if (building instanceof BlockModularFactoryBuilding b) {
				b.RecipeName = string;
				b.RecipeUpdate();
			}
		});
	}
	@Override
	public void loadIcon() {
		super.loadIcon();
		IconBase = Core.atlas.find(this.name + "-base");
		IconTop = Core.atlas.find(this.name + "-top");
		IconHeat = Core.atlas.find(this.name + "-heat");
	}
	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		var scl = Draw.scl;
		Draw.scl(size / 5f);
		Draw.rect(uiIcon, x, y, rotation);
		Draw.scl(scl);
	}
	@Override
	public void ViewConfig(Building building, Table table, DialogModular dialog) {
		if (!(building instanceof BlockModularFactoryBuilding b)) return;
		var View = new UtilUI.ModelView<Object>(null) {
			public void Render(Object param) {
				var list = STModular.RecipeByFactoryName.get(building.block.name);
				if (list == null) {
					labelWrap(ST.UI("modular_no_recipe"));
				} else {
					for (var i : list) {
						if (Vars.state.isCampaign() && !i.unlocked()) continue;
						if (!(i.localizedName.toLowerCase().contains(FilterRecipe.toLowerCase()))) continue;
						var container = new Table();
						var card = Recipe.RenderRecipeCard(i);
						var isCurrent = Objects.equals(b.RecipeName, i.name);
						card.add(UtilUI.CreateIconButton(isCurrent ? Icon.cancel.getRegion() : Icon.add.getRegion(), () -> {
							if (!Objects.equals(b.RecipeName, i.name)) {
								b.RecipeUpdate(i.name);
								Update();
							} else {
								b.RecipeUpdate("");
								Update();
							}
						})).size(42);
						container.setBackground(Styles.black);
						container.add(card).left().expandX().fillX();
						if (isCurrent) {
							container.row();
							container.table(pane -> {
								pane.table(a -> {
									Recipe.RenderRecipeTable(i, ST.UI("modular_input"), i.Input, a);
								}).expandX().fillX().top().left();
								pane.table(b -> {
									Recipe.RenderRecipeTable(i, ST.UI("modular_output"), i.Output, b);
								}).expandX().fillX().top().left();
							}).expandX().fillX().row();
						}
						add(container).pad(isCurrent ? 15 : 0).expandX().fillX().row();
					}
				}
			}
		};
		table.table(recipe -> {
			recipe.labelWrap(ST.UI("modular_recipe")).left().row();
			recipe.add(new UtilUI.Input(FilterRecipe) {
				@Override
				public void HandleInput(String text) {
					FilterRecipe = text;
					View.Update();
				}
			}).expandX().fillX().row();
			recipe.add(View).expandX().fillX().row();
		}).fillX().expandX().left().top().row();
	}
	@Override
	public void ViewBar(Building building, Table table, DialogModular dialog) {
		table.add(UtilUI.CreateIconButton(building.block.uiIcon, () -> {
		})).size(42);
		table.labelWrap(building.block.localizedName).expandX().left();
		if (building instanceof BlockModularFactoryBuilding b) {
			if (b.Recipe != null) {
				table.labelWrap(" " + b.Recipe.localizedName).expandX().padRight(5);
				table.add(UtilUI.CreateIconButton(b.Recipe.uiIcon, () -> {
				})).size(42);
			}
		}
	}
	public class BlockModularFactoryBuilding extends BlockModularBuilding {
		public float Boostrap = 0;
		public float BoostrapTime = 90;
		public float Progress = 0;
		protected void ProgressReduce() {
			if (Boostrap > 0) {
				Boostrap = Math.max(0, Boostrap - 1 / BoostrapTime);
			}
		}
		protected void ProgressIncrease() {
			if (Boostrap < 1) {
				Boostrap = Math.min(1, Boostrap + 1 / BoostrapTime);
			}
		}
		public st.mod.modular.entity.Recipe Recipe;
		public String RecipeName = "";
		public void RecipeUpdate(String name) {
			RecipeName = name;
			RecipeUpdate();
		}
		public void RecipeUpdate() {
			var str = STMultiBlock.StructByBuilding.get(this);
			if (str instanceof StructModular st) {
				st.RemoveViewIO(this);
			}
			var old = Recipe;
			this.Recipe = STModular.RecipeByName.get(RecipeName);
			if (Recipe != old) {
				Progress = 0;
			}
			if (str instanceof StructModular st) {
				st.AddViewIO(this);
			}
		}
		@Override
		public void updateTile() {
			super.updateTile();
			if (Recipe == null) {
				ProgressReduce();
				return;
			}
			//check storage
		}
		@Override
		public float getPowerProduction() {
			return super.getPowerProduction();

		}
		@Override
		public void draw() {
			//Running
			var scl = Draw.scl;
			Draw.scl(size / 5f);
			Draw.rect(IconBase, x, y);
			Draw.rect(IconTop, x, y);
			Draw.scl(scl);
		}
		@Override
		public byte version() {
			return 0;
		}
		@Override
		public void write(Writes write) {
			super.write(write);
			write.i(RecipeName.getBytes().length);
			write.b(RecipeName.getBytes());
			write.f(Boostrap);
		}
		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);
			RecipeName = Arrays.toString(read.b(read.i()));
			RecipeUpdate();
			Boostrap = read.f();
		}
		@Override
		public Object config() {
			return this.RecipeName;
		}
		@Override
		public void buildConfiguration(Table table) {
			new DialogModular(this).show().exited(this::deselect);
		}
	}
}
