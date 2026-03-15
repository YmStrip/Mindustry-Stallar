package st.mod.ui;


import arc.graphics.g2d.TextureRegion;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Button;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import mindustry.ctype.UnlockableContent;
import mindustry.ui.Styles;


public class UtilUI {

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
