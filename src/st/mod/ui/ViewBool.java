package st.mod.ui;


import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Button;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import mindustry.gen.Icon;
import mindustry.gen.Tex;


public class ViewBool extends Table {
	final TextureRegionDrawable Temp = (TextureRegionDrawable) Tex.whiteui;
	public boolean Model;
	public Button Button;
	public Image Checked;
	public Image Check;
	public String Label;
	public ViewBool(boolean model, String label) {
		Label = label;
		Model = model;
		Check = new Image(Icon.eyeOff);
		Checked = new Image(Icon.eye);
		Button = UtilUI.CreateIconButton(Icon.edit.getRegion(), () -> {
			Model = !Model;
			UpdateStyle();
			Update();
		});
		add(Button).size(42).expandX().fillX().left();
		labelWrap(()->" " + Label).expandX().left();
		UpdateStyle();
	}
	protected void UpdateStyle() {
		Button.clearChildren();
		Button.add(Model ? Checked : Check).size(42);
	}
	public void Update() {

	}
}
