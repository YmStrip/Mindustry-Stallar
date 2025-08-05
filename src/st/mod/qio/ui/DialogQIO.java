package st.mod.qio.ui;

import arc.Core;
import arc.math.Mathf;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.*;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import mindustry.Vars;
import mindustry.core.GameState;
import mindustry.core.UI;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;
import mindustry.world.blocks.ItemSelection;
import st.ST;
import st.mod.qio.STORE_QIO;
import st.mod.qio.entity.QMap;

import static mindustry.Vars.*;

public class DialogQIO extends Dialog {
	public DialogQIO() {
		super(ST.ui("qio"));
	}
	private Table createItem(UnlockableContent item, float count, float max) {
		var table = new Table();
		table.align(Align.left);
		table.setBackground(Styles.black);
		/*var icon = new ImageButton();
		icon.setStyle(Styles.squarei);
		icon.setSize(42,42);
		icon.getStyle().imageUp= new TextureRegionDrawable(item.uiIcon);
		table.add(icon).l;*/
		table.button(button -> {
			button.image(new TextureRegionDrawable(item.uiIcon));
		}, Styles.squarei, () -> {
		}).size(42).left();
		table.labelWrap(UI.formatAmount((long) count) + " / " + UI.formatAmount((long) max)).fillX().growX().padLeft(15).align(Align.left);
		table.setWidth(getMaxWidth() / 2.5f);
		return table;
	}
	@FunctionalInterface
	private interface getContent {
		UnlockableContent content(String name);
	}
	private void buildMapView(Table parent, String name, QMap map, float max, getContent get) {
		{
			var itemList = new Table();
			var split = 0;
			var k = Core.app.isMobile() ? 2 : 3;
			itemList.add(new TextField(name) {{
				disabled = true;
			}}).left().fillX().growX().row();
			var container = new Table();
			itemList.add(container).growX().growY().fillX().fillY();
			for (var i : map.map.entrySet()) {
				var item = get.content(i.getKey());
				if (item == null) continue;
				var builder = container.add(createItem(item, i.getValue().intValue(), max)).left().fillX().growX().marginBottom(2);
				if (Core.app.isMobile()) builder.row();
				else if (++split % k == 0) {
					builder.marginLeft(2).row();
				}
			}
			parent.add(itemList).fillX().growX().marginLeft(24).marginRight(24).left();
			parent.row();
		}
	}
	@Override
	public Dialog show() {
		addCloseButton();
		setFillParent(true);
		title.setAlignment(Align.center);
		titleTable.row();
		titleTable.add(new Image()).growX().height(3f).pad(4f).get().setColor(Pal.accent);
		top();
		var builder = cont.pane(table -> {
			table.setWidth(getMaxWidth() * 0.8f);
			buildMapView(table, ST.ui("qio_item"), STORE_QIO.NETWORK.ITEM, STORE_QIO.NETWORK.ITEM.capacity, name -> Vars.content.item(name));
			buildMapView(table, ST.ui("qio_liquid"), STORE_QIO.NETWORK.LIQUID, STORE_QIO.NETWORK.LIQUID.capacity, name -> Vars.content.liquid(name));
			buildMapView(table, ST.ui("qio_unit"), STORE_QIO.NETWORK.UNIT, STORE_QIO.NETWORK.UNIT.capacity, name -> Vars.content.unit(name));
		}).fillX().growX().fillY().growY().marginBottom(4).top();
		if (Core.app.isMobile()) {
			builder.pad(20);
		} else {
			builder.pad(150);
		}
		closeOnBack();
		cont.row().button("close", () -> {
			hide();
			remove();
		}).width(100);
		if (!state.isPaused() && !net.active()) {
			state.set(GameState.State.paused);
		}
		return super.show();
	}
	@Override
	public void hide() {
		super.hide();
		if (state.isPaused()) state.set(GameState.State.playing);
	}
}
