package st.mod.modular.entity;


import arc.scene.ui.layout.Table;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.ui.Styles;
import st.ST;
import st.mod.ui.UtilUI;


import java.util.HashMap;
import java.util.HashSet;


public class Recipe extends UnlockableContent {
	//modifier
	public static Table RenderRecipeCard(Recipe t) {
		var table = new Table();
		table.setBackground(Styles.black);
		table.add(UtilUI.CreateIconButton(t.uiIcon, () -> {
		})).size(42).left();
		table.labelWrap(t.localizedName).expandX().fillX().padLeft(5).padRight(5);
		return table;
	}
	public static Table RenderRecipeTable(Recipe t, String name, HashMap<UnlockableContent, RecipeValue> value, Table table) {
		if (name != null) table.labelWrap(name).left().row();
		for (var i : value.keySet()) {
			var row = UtilUI.CreateIconRow(i);
			row.labelWrap(" " + value.get(i).Description(t.CraftTime));
			table.add(row).left().fillX().growX().marginBottom(2).minWidth(150).row();
		}
		return table;
	}
	public float CraftTime = 30.0F;
	public int Level = 1;
	public UnlockableContent IconOutput;
	public Recipe(String name) {
		super(name);
	}
	@Override
	public ContentType getContentType() {
		return ContentType.status;
	}
	/**
	 * Factory name that are allow to use
	 */
	public HashSet<String> Factory = new HashSet<>();
	public HashMap<UnlockableContent, RecipeValue> Input = new HashMap<>();
	public HashMap<UnlockableContent, RecipeValue> Output = new HashMap<>();
	public void InputPower(float value) {
		InputPower = value;
	}
	public void OutputPower(float value) {
		OutputPower = value;
	}
	public void IconOutput(UnlockableContent icon) {
		this.IconOutput = icon;
	}
	public void Input(UnlockableContent type, float value) {
		this.Input.put(type, new RecipeValue.RecipeValueStatic(value));
	}
	public void Output(UnlockableContent type, float value) {
		this.Output.put(type, new RecipeValue.RecipeValueStatic(value));
		this.IconOutput(type);
	}
	public float InputPower = 0;
	public float OutputPower = 0;
}
