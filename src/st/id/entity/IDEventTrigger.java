package st.id.entity;

import java.util.ArrayList;

public class IDEventTrigger {
	@FunctionalInterface
	public interface fn {
		public void fn();
	}
	private ArrayList<fn> listener = new ArrayList<>();
	public void listen(fn fn) {
		this.listener.add(fn);
	}
	public void emit() {
		for (fn fn : listener) {
			fn.fn();
		}
		this.listener.clear();
	}
}
