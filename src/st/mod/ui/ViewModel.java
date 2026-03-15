package st.mod.ui;

import arc.scene.ui.layout.Table;

/**
 * no support signal because not life cycle maintained
 *
 * @param <T>
 */
public abstract class ViewModel<T> extends Table {
	public T Param;
	public ViewModel(T param) {
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
