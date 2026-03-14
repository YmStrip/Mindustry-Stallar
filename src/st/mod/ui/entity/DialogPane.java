package st.mod.ui.entity;

import arc.Core;
import arc.scene.ui.Dialog;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import mindustry.core.GameState;
import mindustry.graphics.Pal;
import st.ST;

import java.util.HashMap;

import static mindustry.Vars.net;
import static mindustry.Vars.state;

/**
 * dialog with top menu
 */
public class DialogPane extends Dialog {
	public HashMap<String, PaneSelect> Pane = new HashMap<>();
	public String Current = "";
	public Table Body;
	public void Pause() {
		if (!state.isPaused() && !net.active()) {
			state.set(GameState.State.paused);
		}
	}
	public void Resume() {
		if (state.isPaused()) state.set(GameState.State.playing);
	}

	public static interface PaneSelect {
		void Call(Table table, Object param);
	}
	public void Pane(String name, PaneSelect call) {
		Pane.put(name, call);
	}
	public void Toggle(String name) {
		Toggle(name, null);
	}
	public void Toggle(String name, Object param) {
		var def = Pane.get(name);
		if (def == null) return;
		if (Body == null) return;
		Body.clear();
		Current = name;
		def.Call(Body, param);
	}
	@Override
	public Dialog show() {
		addCloseButton();
		setFillParent(true);
		title.setAlignment(Align.center);
		closeOnBack();
		titleTable.row();
		titleTable.add(new Image()).growX().height(3f).pad(4f).get().setColor(Pal.accent);
		top();
		var builder = cont.table(table -> {
			table.table(topbar -> {
				for (var i : Pane.keySet()) {
					topbar.button(ST.UI("title_" + i), () -> {
						Toggle(i);
					}).width(Core.bundle.get(ST.UI("title_" + i)).length() * 16);
				}
			}).expandX().fillX().top().left().row();
			table.pane((table1) -> {
				Body = table1;
			}).fillX().growX().fillY().growY().top().left();
		}).fillX().growX().fillY().growY().marginBottom(4).top().left();
		if (Core.app.isMobile()) {
			builder.pad(20);
		} else {
			builder.pad(150);
		}

		return super.show();
	}
	@Override
	public void hide() {
		super.hide();
	}
}
