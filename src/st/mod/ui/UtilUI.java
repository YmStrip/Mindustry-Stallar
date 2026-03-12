package st.mod.ui;


import arc.graphics.g2d.TextureRegion;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Button;
import arc.scene.ui.TextField;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Icon;
import mindustry.ui.Styles;


public class UtilUI {
	public static class Input extends Table {
		public void HandleInput(String text) {
		}
		public TextField El;
		public Input(String def, TextureRegion icon) {
			El = new TextField();
			El.setText(def);
			setBackground(Styles.black);
			add(CreateIconButton(icon, () -> {
			})).size(42);
			El.addCaptureListener((event) -> {
				HandleInput(El.getText());
				return true;
			});
			add(El).expandX().fillX();
			add(CreateIconButton(Icon.cancel.getRegion(), () -> {
				El.setText("");
				HandleInput(El.getText());
			})).size(42);
		}
		public Input(String def) {
			this(def, Icon.pencil.getRegion());
		}
		public Input() {
			this("");
		}
	}

	/**
	 * no support signal because not life cycle maintained
	 * @param <T>
	 */
	public static abstract class ModelView<T> extends Table {
		public T Param;
		public ModelView(T param) {
			Update(param);
		}
		public void Update() {
			clearChildren();
			Render(Param);
		}
		public void Update(T param) {
			Param = param;
			Update();
		}
		protected abstract void Render(T param);
	}
	public static Table CreateIconRow(UnlockableContent item) {
		return CreateIconRow(item.uiIcon);
	}
	public static Button CreateIconButton(TextureRegion item, Runnable listener) {
		var button = new Button();
		button.clearChildren();
		button.clicked(listener);
		button.image(new TextureRegionDrawable(item));
		button.setSize(42);
		button.setStyle(Styles.squarei);
		return button;
	}
	public static Table CreateIconRow(TextureRegion item) {
		var table = new Table();
		table.align(Align.left);
		table.setBackground(Styles.black);
		/*var icon = new ImageButton();
		icon.setStyle(Styles.squarei);
		icon.setSize(42,42);
		icon.getStyle().imageUp= new TextureRegionDrawable(item.uiIcon);
		table.add(icon).l;*/
		table.button(button -> {
			button.image(new TextureRegionDrawable(item));
		}, Styles.squarei, () -> {
		}).size(42).left();
		return table;
	}
}
