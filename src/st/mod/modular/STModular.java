package st.mod.modular;

import arc.Events;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.game.EventType;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import st.id.entity.IDEventEmitter;
import st.mod.STTech;
import st.mod.UtilTooltip;
import st.mod.modular.block.*;
import st.mod.modular.entity.*;
import st.mod.util.STooltipBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class STModular {
	//
	public static BlockModularFactory Constructor;
	public static BlockModularFactory Refinery;
	public static BlockModularFactory Assembly;
	public static BlockModularFactory Turbine;
	public static BlockModularFactory Nuclear;
	public static BlockModularFactory Collider;
	//
	public static BlockModularController Controller;
	public static BlockModularRouter Router;
	public static BlockModularStorage Storage;
	//
	public static HashMap<String, Recipe> RecipeByName = new HashMap<>();
	public static HashMap<String, HashSet<Recipe>> RecipeByFactoryName = new HashMap<>();
	public static Recipe RecipeSilicon;
	public static Recipe RecipeSiliconCentrifugal;
	public static Recipe RecipeGraphite;
	//
	public static Recipe RecipeTurbineCoal;
	//
	public static IDEventEmitter Event = new IDEventEmitter();
	public static void Init() {
		_initContent();
		_initEvent();
	}
	private static void _initContent() {
		//
		Controller = new BlockModularController("Controller") {{
			size = 3;
			CapacityBuilding = 8;
			requirements(Category.effect, ItemStack.with(
				Items.silicon, 75,
				Items.metaglass, 25,
				Items.graphite, 25,
				Items.copper, 100,
				Items.lead, 50
			));
			consumePower(8f / 60);
			Inject(this, 1);
		}};
		Router = new BlockModularRouter("Router") {{
			size = 2;
			CapacityTransItem = 12;
			CapacityTransLiquid = 90;
			CapacityTransUnit = 3 / 60f;
			CapacityBufferItem = 120;
			CapacityBufferLiquid = 900;
			CapacityBufferUnit = 1;
			requirements(Category.effect, ItemStack.with(
				Items.silicon, 25,
				Items.copper, 25,
				Items.metaglass, 25,
				Items.lead, 25
			));
			consumePower(12f / 60);
			Inject(this, 1);
		}};
		Storage = new BlockModularStorage("Storage") {{
			size = 3;
			itemCapacity = 120;
			liquidCapacity = 900;
			CapacityUnit = 4f;
			requirements(Category.effect, ItemStack.with(
				Items.copper, 120,
				Items.lead, 80,
				Items.metaglass, 50,
				Items.silicon, 25
			));
			consumePower(8f / 60);
			Inject(this, 1);
		}};
		//
		Constructor = new BlockModularFactory("Constructor") {{
			size = 2;
			requirements(Category.crafting, ItemStack.with(
				Items.silicon, 25,
				Items.graphite, 25,
				Items.copper, 25,
				Items.lead, 25
			));
			itemCapacity = 10;
			liquidCapacity = 24;
			consume(new ConsumePowerRecipe(60f / 60));
			Inject(this, 1);
		}};
		Refinery = new BlockModularFactory("Refinery") {{
			size = 3;
			itemCapacity = 15;
			liquidCapacity = 30;
			requirements(Category.crafting, ItemStack.with(
				Items.plastanium, 50,
				Items.silicon, 100,
				Items.graphite, 25,
				Items.copper, 25,
				Items.lead, 25
			));
			consume(new ConsumePowerRecipe(180f / 60));
			Inject(this, 1);
		}};
		Turbine = new BlockModularFactory("Turbine") {{
			size = 3;
			itemCapacity = 24;
			liquidCapacity = 60;
			requirements(Category.power, ItemStack.with(
				Items.plastanium, 50,
				Items.silicon, 100,
				Items.graphite, 25,
				Items.copper, 25,
				Items.lead, 25
			));
			Inject(this, 1);
			consume(new ConsumePowerRecipe(0f / 60));
		}};
		Assembly = new BlockModularFactory("Assembly") {{
			size = 4;
			itemCapacity = 15;
			liquidCapacity = 30;
			requirements(Category.crafting, ItemStack.with(
				Items.plastanium, 50,
				Items.silicon, 50,
				Items.copper, 80,
				Items.lead, 25,
				Items.titanium, 50
			));
			consume(new ConsumePowerRecipe(120f / 60));
			Inject(this, 2);
		}};
		Nuclear = new BlockModularFactory("Nuclear") {{
			size = 4;
			itemCapacity = 12;
			liquidCapacity = 60;
			requirements(Category.power, ItemStack.with(
				Items.phaseFabric, 75,
				Items.silicon, 75,
				Items.metaglass, 50,
				Items.graphite, 125,
				Items.titanium, 100,
				Items.copper, 80,
				Items.lead, 150
			));
			consume(new ConsumePowerRecipe(120f / 60));
			Inject(this, 2);
		}};
		Collider = new BlockModularFactory("Collider") {{
			size = 5;
			itemCapacity = 24;
			liquidCapacity = 24;
			requirements(Category.production, ItemStack.with(
				Items.surgeAlloy, 500,
				Items.phaseFabric, 250,
				Items.silicon, 500,
				Items.metaglass, 250,
				Items.graphite, 300,
				Items.titanium, 250,
				Items.copper, 500,
				Items.lead, 500
			));
			consume(new ConsumePowerRecipe(1024f / 60));
			Inject(this, 3);
		}};
		//
		RecipeGraphite = new Recipe("RecipeGraphite") {{
			CraftTime = 70.0F;
			InputPower(10f / 60f);
			Input(Items.coal, 2);
			Output(Items.graphite, 1);
			Inject(this, Constructor, 1);
		}};
		RecipeSilicon = new Recipe("RecipeSilicon") {{
			CraftTime = 30.0F;
			InputPower(30f / 60f);
			Input(Items.coal, 1);
			Input(Items.sand, 1);
			Output(Items.silicon, 1);
			Inject(this, Constructor, 1);
		}};
		RecipeSiliconCentrifugal = new Recipe("RecipeSiliconCentrifugal") {{
			CraftTime = 45.0F;
			InputPower(60f / 60f);
			Input(Items.coal, 2);
			Input(Items.sand, 2);
			Output(Items.silicon, 1);
			Output(Items.graphite, 1);
			Inject(this, Refinery, 1);
		}};
		//
		RecipeTurbineCoal = new Recipe("RecipeTurbineCoal") {{
			CraftTime = 90.0F;
			OutputPower(400f / 60f);
			Input(Items.coal, 1);
			Input(Liquids.water, 7);
			Inject(this, Turbine, 1);
		}};
		//
	}
	private static void _initEvent() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			STTech.createTechNodeRoot(Controller)
				.Add(Router);
			STTech.createTechNodeRoot(RecipeGraphite)
				.Require(ItemStack.with(Items.graphite, 25))
				.Add(RecipeSilicon, t -> t
					.Require(ItemStack.with(Items.silicon, 25))
					.Add(RecipeSiliconCentrifugal, t1 -> t1
						.Require(ItemStack.with(Items.silicon, 250, Items.plastanium, 250, Items.metaglass, 150))
					)
				)
			;
		});
	}
	public static STooltipBuilder Inject(BlockModularController t, int level) {
		var builder = UtilTooltip.Tooltip(t.stats).TechLevel(level);
		builder.Add("capacity_building", t.CapacityBuilding);
		return builder;
	}
	public static STooltipBuilder Inject(BlockModularStorage t, int level) {
		var builder = UtilTooltip.Tooltip(t.stats).TechLevel(level);
		builder.Add("modular_capacity_unit", t.CapacityUnit);
		return builder;
	}
	public static STooltipBuilder Inject(BlockModularRouter t, int level) {
		var builder = UtilTooltip.Tooltip(t.stats).TechLevel(level);
		builder.Add("capacity_trans_item", t.CapacityTransItem);
		builder.Add("capacity_trans_liquid", t.CapacityTransLiquid);
		builder.Add("capacity_trans_unit", t.CapacityTransUnit);
		builder.Add("capacity_buffer_item", t.CapacityBufferItem);
		builder.Add("capacity_buffer_liquid", t.CapacityBufferLiquid);
		builder.Add("capacity_buffer_unit", t.CapacityBufferUnit);
		return builder;
	}
	public static STooltipBuilder Inject(BlockModularFactory t) {
		return Inject(t, 1);
	}
	public static STooltipBuilder Inject(BlockModularFactory t, int level) {
		var builder = UtilTooltip.Tooltip(t.stats).TechLevel(level);
		return builder;
	}
	public static STooltipBuilder Inject(Recipe t, BlockModularFactory factory) {
		return Inject(t, factory, 1);
	}
	public static STooltipBuilder Inject(Recipe t, BlockModularFactory factory, int level) {
		t.Level = level;
		t.Factory.add(factory.name);
		var builder = UtilTooltip.Tooltip(t.stats).TechLevel(level);
		InjectRecipe(t);
		return builder;
	}
	public static STooltipBuilder InjectRecipe(Recipe t) {
		RecipeByName.put(t.name, t);
		var st = UtilTooltip.Tooltip(t.stats);
		st.Add("craft_time", (Math.round(100 * t.CraftTime / 60f) / 100) + "s");
		if (t.InputPower > 0) st.Add("input_power", ((t.InputPower * 60f)));
		if (t.OutputPower > 0) st.Add("output_power", ((t.OutputPower * 60f)));
		var recipeFactory = new ArrayList<String>();
		for (var i : t.Factory) {
			var block = Vars.content.block(i);
			if (block != null) {
				recipeFactory.add(block.localizedName);
			} else {
				recipeFactory.add(i);
			}
		}
		st.Add("recipe_factory", String.join(",", recipeFactory));
		//
		st.Add("input", (table) -> {
			table.add().row();
			Recipe.RenderRecipeTable(t, null, t.Input, table);
		});
		st.Add("output", (table) -> {
			table.add().row();
			Recipe.RenderRecipeTable(t, null, t.Output, table);
		});
		//
		for (var i : t.Factory) {
			RecipeByFactoryName.putIfAbsent(i, new HashSet<>());
			var def = RecipeByFactoryName.get(i);
			def.add(t);
		}
		//
		return st;
	}
}
