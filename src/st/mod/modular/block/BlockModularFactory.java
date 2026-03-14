package st.mod.modular.block;


import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.scene.ui.layout.Table;
import arc.struct.EnumSet;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.BlockStatus;
import mindustry.world.meta.BuildVisibility;
import st.ST;
import st.mod.modular.STModular;
import st.mod.modular.entity.Recipe;
import st.mod.modular.struct.StructModular;
import st.mod.modular.ui.DialogModular;
import st.mod.multiblock.STMultiBlock;
import st.mod.ui.UtilUI;


import java.util.HashMap;
import java.util.Objects;


public class BlockModularFactory extends BlockModular {
	public static String FilterRecipe = "";
	//
	public BlockModularFactory(String name) {
		super(name);
		buildVisibility = BuildVisibility.shown;
		hasItems = true;
		hasLiquids = true;
		hasPower = true;
		flags = EnumSet.of(BlockFlag.factory);
		configurable = true;
		itemCapacity = 24;
		liquidCapacity = 120;
		CapacityUnit = 2;
		outputsPower = true;
		config(String.class, (building, string) -> {
			if (building instanceof BlockModularFactoryBuilding b) {
				b.RecipeName = string;
				b.RecipeUpdate();
			}
		});
	}
	@Override
	public void HandleStructAdd(StructModular struct, BlockModularBuilding building) {
		if (building instanceof BlockModularFactoryBuilding b) struct.AddIO(b);
	}
	@Override
	public void HandleStructRemove(StructModular struct, BlockModularBuilding building) {
		if (building instanceof BlockModularFactoryBuilding b) struct.RemoveIO(b);
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
								if (i.InputPower > 0)
									pane.labelWrap(ST.UI("modular_input_power") + ": " + (Math.round(i.InputPower * 6000) / 100) + "/s").row();
								if (i.OutputPower > 0)
									pane.labelWrap(ST.UI("modular_output_power") + ": " + (Math.round(i.OutputPower * 6000) / 100) + "/s").row();
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
		table.labelWrap(" " + building.block.localizedName).expandX().left();
		if (building instanceof BlockModularFactoryBuilding b) {
			if (b.Recipe != null) {
				table.labelWrap(" " + b.Recipe.localizedName).expandX().padRight(5);
				table.add(UtilUI.CreateIconButton(b.Recipe.uiIcon, () -> {
				})).size(42);
			}
		}
	}
	public class BlockModularFactoryBuilding extends BlockModularBuilding {
		public st.mod.modular.entity.Recipe Recipe;
		public String RecipeName = "";
		public float Boostrap = 0;
		public float BoostrapTime = 90;
		public float Progress = 0;
		public boolean Filled = false;
		public boolean Producing = false;
		public boolean RecipeInited = false;
		public HashMap<UnlockableContent, Float> Input = new HashMap<>();
		public HashMap<UnlockableContent, Float> Output = new HashMap<>();
		private byte RecipeUpdateByRead = 3;
		protected void BootstrapReduce() {
			if (Boostrap > 0) {
				Boostrap = Math.max(0, Boostrap - 1 / BoostrapTime);
			}
		}
		protected void BootstrapIncrease() {
			if (Boostrap < 1) {
				Boostrap = Math.min(1, Boostrap + 1 / BoostrapTime);
			}
		}
		protected void ProgressIncrease() {
			if (Recipe == null) return;
			Progress = Math.min(Progress + (1 / Recipe.CraftTime) * edelta(), 1f);
		}
		protected void ProgressReduce() {
			if (Recipe == null) {
				Progress = Math.max(Progress - 1 / 90f, 0);
			} else {
				Progress = Math.max(Progress - 1 / Recipe.CraftTime, 0);
			}
		}
		public void Craft() {
			if (!this.Producing || Progress < 1) return;
			Producing = false;
			Progress = 0;
			RecipeInited = false;
			for (var i : Input.keySet()) {
				if (i instanceof Item item) {
					items.remove(item, Math.round(Input.getOrDefault(i, 0f)));
				}
				if (i instanceof Liquid liquid) {
					liquids.remove(liquid, Math.round(Input.getOrDefault(i, 0f)));
				}
				if (i instanceof UnitType unit) {
					Unit.put(unit, Math.max(0, Unit.getOrDefault(unit, 0f) - Input.get(i)));
				}
			}
			for (var i : Output.keySet()) {
				if (i instanceof Item item) {
					items.add(item, Math.round(Output.get(i)));
				}
				if (i instanceof Liquid liquid) {
					liquids.add(liquid, Output.get(i));
				}
				if (i instanceof UnitType unit) {
					Unit.put(unit, Unit.getOrDefault(unit, 0f) + Output.get(i));
				}
			}
			if (this.wasVisible && Recipe != null) {
				Recipe.EffectCraft.at(this.x, this.y);
			}
			ProducingCheck();
		}
		public void ProducingCheck() {
			Filled = false;
			RecipeInit();
			var result = true;
			for (var i : Input.keySet()) {
				if (i instanceof Item item) {
					if (items.get(item) < Input.get(i)) {
						result = false;
						break;
					}
				}
				if (i instanceof Liquid liquid) {
					if (liquids.get(liquid) < Input.get(i)) {
						result = false;
						break;
					}
				}
				if (i instanceof UnitType unit) {
					if (Unit.get(unit) < Input.get(i)) {
						result = false;
						break;
					}
				}
			}
			for (var i : Output.keySet()) {
				if (i instanceof Item item) {
					if (items.get(item) + Output.get(i) > itemCapacity) {
						result = false;
						Filled = true;
						break;
					}
				}
				if (i instanceof Liquid liquid) {
					if (liquids.get(liquid) + Output.get(i) > liquidCapacity) {
						result = false;
						Filled = true;
						break;
					}
				}
				if (i instanceof UnitType unit) {
					if (Unit.get(unit) + Output.get(i) > CapacityUnit) {
						result = false;
						Filled = true;
						break;
					}
				}
			}
			Producing = result;
		}
		public void RecipeUpdate(String name) {
			RecipeName = name;
			RecipeUpdate();
		}
		public void RecipeUpdate() {
			RecipeInited = false;
			Input.clear();
			Output.clear();
			var str = STMultiBlock.StructByBuilding.get(this);
			if (str instanceof StructModular st) {
				st.RemoveIO(this);
			}
			var old = Recipe;
			this.Recipe = STModular.RecipeByName.get(RecipeName);
			if (Recipe != old) {
				Progress = 0;
			}
			if (str instanceof StructModular st) {
				st.AddIO(this);
			}
			RecipeInit(true);
		}
		public void RecipeInit() {
			RecipeInit(false);
		}
		public void RecipeInit(boolean force) {
			if (RecipeInited && !force || Recipe == null) return;
			RecipeInited = true;
			Producing = false;
			for (var i : Recipe.Input.keySet()) {
				Input.put(i, Recipe.Input.get(i).Value());
			}
			for (var i : Recipe.Output.keySet()) {
				Output.put(i, Recipe.Output.get(i).Value());
			}
			//Move excess
			if (Struct != null) {
				for (var i : Vars.content.items()) {
					var def = items.get(i);
					if (Input.get(i) == null && def > 0) {
						var ls = Struct.AddRecourse(i, def);
						items.set(i, (int) (ls));
					}
				}
				for (var i : Vars.content.liquids()) {
					var def = liquids.get(i);
					if (Input.get(i) == null && def > 0) {
						var ls = Struct.AddRecourse(i, def);
						liquids.set(i, ls);
					}
				}
				for (var i : Unit.keySet()) {
					var def = Unit.get(i);
					if (Input.get(i) == null && def > 0) {
						var ls = Struct.AddRecourse(i, def);
						Unit.put(i, ls);
					}
				}
			}
			ProducingCheck();
		}
		/*@Override
		public void itemTaken(Item item) {
			super.itemTaken(item);
			ProducingCheck();
		}*/
		@Override
		public int removeStack(Item item, int amount) {
			var r = super.removeStack(item, amount);
			if (r > 0) ProducingCheck();
			return r;
		}
		@Override
		public void updateTile() {
			super.updateTile();
			if (RecipeUpdateByRead < 3) {
				RecipeUpdateByRead++;
				if (RecipeUpdateByRead == 2) {
					RecipeUpdate();
				}
			}
			if (Recipe == null) {
				BootstrapReduce();
				return;
			}
			if (Producing) {
				BootstrapIncrease();
				ProgressIncrease();
			} else {
				BootstrapReduce();
				ProgressReduce();
			}
			if (Progress >= 1) Craft();
		}
		@Override
		public boolean acceptItem(Building source, Item item) {
			return this.Input.get(item) != null && this.items.get(item) < itemCapacity;
		}
		@Override
		public void handleItem(Building source, Item item) {
			super.handleItem(source, item);
			ProducingCheck();
		}
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return this.Input.get(liquid) != null && this.liquids.get(liquid) < liquidCapacity;
		}
		@Override
		public void handleLiquid(Building source, Liquid liquid, float amount) {
			super.handleLiquid(source, liquid, amount);
			ProducingCheck();
		}
		@Override
		public boolean acceptPayload(Building source, Payload payload) {
			if (!(payload instanceof UnitType unit)) return false;
			return this.Input.get(unit) != null && this.Unit.getOrDefault(unit, 0f) < CapacityUnit;
		}
		@Override
		public void handlePayload(Building source, Payload payload) {
			if (payload instanceof UnitType unit) {
				Unit.put(unit, Math.min(CapacityUnit, Unit.getOrDefault(unit, 0f) + 1));
				ProducingCheck();
			}
		}
		@Override
		public float getPowerProduction() {
			if (Recipe == null) return 0;
			if (!Producing) return 0;
			return edelta() * Recipe.OutputPower;
		}
		@Override
		public void draw() {
			var size = block.size * Vars.tilesize;
			//Running
			Draw.rect(IconBase, x, y, size, size);
			Draw.rect(IconTop, x, y, size, size);
			var color = Color.white;
			if (Recipe != null && Recipe.IconOutput != null) {
				if (Recipe.IconOutput instanceof Item item) {
					color = item.color;
				} else if (Recipe.IconOutput instanceof Liquid liquid) {
					color = liquid.color;
				}
			}
			DrawHeat(color, Boostrap, 90);
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
			RecipeName = new String(read.b(read.i()));
			Boostrap = read.f();
			RecipeUpdateByRead = 0;
		}
		@Override
		public Object config() {
			return this.RecipeName;
		}
		@Override
		public BlockStatus status() {
			if (efficiency <= 1e-6) return BlockStatus.noInput;
			if (this.Filled) return BlockStatus.noOutput;
			if (this.Producing) return BlockStatus.active;
			return BlockStatus.noInput;
		}
	}
}
