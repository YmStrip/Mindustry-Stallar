package st.id.entity;

import java.util.ArrayList;

public class IDEventTrigger {
	@FunctionalInterface
	public interface Func {
		public void Callback();
	}
	private ArrayList<Func> Listener = new ArrayList<>();
	public void listen(Func fn) {
		this.Listener.add(fn);
	}
	public void emit() {
		for (Func fn : Listener) {
			fn.Callback();
		}
		this.Listener.clear();
	}
}
