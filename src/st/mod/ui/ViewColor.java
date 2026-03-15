package st.mod.ui;

import arc.graphics.Color;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Button;
import arc.scene.ui.layout.Table;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.dialogs.ColorPicker;


public class ViewColor extends Table {
	final TextureRegionDrawable Temp = (TextureRegionDrawable) Tex.whiteui;
	public Color Model;
	public Table Background;
	public Button Button;
	public ViewColor(Color model) {
		Model = new Color(model);
		Update();
		Background = new Table();
		Button = UtilUI.CreateIconButton(Icon.edit.getRegion(), () -> {
			new ColorPicker().show(Model, color -> {
				Model = new Color(color);
				UpdateStyle();
				Update();
			});
		});
		add(Button).size(42).left();
		add(Background).expandX().fillX().width(150).height(42).left();
		UpdateStyle();
	}
	protected void UpdateStyle() {
		Background.setBackground(Temp.tint(Model.r, Model.g, Model.b, Model.a));
	}
	public void Update() {

	}
}
