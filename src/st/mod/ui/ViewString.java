package st.mod.ui;

import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.TextField;
import arc.scene.ui.layout.Table;
import mindustry.gen.Icon;
import mindustry.ui.Styles;

public class ViewString extends Table {
	public String Model = "";
	public void HandleInput(String text) {
	}
	public TextField El;
	public ViewString(String def, TextureRegion icon) {
		Model = def;
		El = new TextField();
		El.setText(def);
		setBackground(Styles.black);
		add(UtilUI.CreateIconButton(icon, () -> {
		})).size(42);
		El.addCaptureListener((event) -> {
			HandleInput(Model = El.getText());
			return true;
		});
		add(El).expandX().fillX();
		add(UtilUI.CreateIconButton(Icon.cancel.getRegion(), () -> {
			El.setText("");
			HandleInput(Model = El.getText());
		})).size(42);
	}
	public ViewString(String def) {
		this(def, Icon.pencil.getRegion());
	}
	public ViewString() {
		this("");
	}
}
