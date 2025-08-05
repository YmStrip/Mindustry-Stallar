package st.id.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class IDEventEmitter {
	public HashMap<Class<IDEvent>, ArrayList<IDEventListener<IDEvent>>> on = new HashMap<>();
	public HashMap<Class<IDEvent>, ArrayList<IDEventListener<IDEvent>>> once = new HashMap<>();
	protected void onHook(Class<IDEvent> name, IDEventListener<IDEvent> listener) {
	}
	protected void offHook(Class<IDEvent> name, IDEventListener<IDEvent> listener) {
	}
	public <T extends IDEvent> void on(Class<T> event, IDEventListener<T> listener) {
		if (on.get(event) == null) {
			on.put((Class<IDEvent>) event, new ArrayList<>());
		}
		var array = on.get(event);
		if (!array.contains(listener)) array.add((IDEventListener<IDEvent>) listener);
		this.onHook((Class<IDEvent>) event, (IDEventListener<IDEvent>) listener);
	}
	public <T extends IDEvent> void onMounted(Class<T> event, IDEventListener<T> listener, IDEventTrigger trigger) {
		this.on(event, listener);
		trigger.listen(() -> {
			this.off(event, listener);
		});
	}
	public <T extends IDEvent> void once(Class<T> event, IDEventListener<T> listener) {
		if (once.get(event) == null) {
			once.put((Class<IDEvent>) event, new ArrayList<>());
		}
		var array = once.get(event);
		if (!array.contains(listener)) array.add((IDEventListener<IDEvent>) listener);
		this.onHook((Class<IDEvent>) event, (IDEventListener<IDEvent>) listener);
	}
	public <T extends IDEvent> void onceMounted(Class<T> event, IDEventListener<T> listener, IDEventTrigger trigger) {
		this.once(event, listener);
		trigger.listen(() -> {
			this.off(event, listener);
		});
	}
	public <T extends IDEvent> void off(Class<T> event, IDEventListener<T> listener) {
		var once = this.once.get(event);
		var on = this.on.get(event);
		if (on != null) {
			if (on.contains(listener)) {
				on.remove(listener);
				offHook((Class<IDEvent>) event, (IDEventListener<IDEvent>) listener);
			}
			if (on.size() == 0) {
				this.on.remove(event);
			}
		}
		if (once != null) {
			if (once.contains(listener)) {
				once.remove(listener);
				offHook((Class<IDEvent>) event, (IDEventListener<IDEvent>) listener);
			}
			if (once.size() == 0) {
				this.once.remove(event);
			}
		}
	}
	public <T extends IDEvent> void off(Class<T> event) {
		var once = this.once.get(event);
		var on = this.on.get(event);
		if (on != null) {
			for (IDEventListener<IDEvent> listener : on) {
				offHook((Class<IDEvent>) event, listener);
			}
			on.clear();
			this.on.remove(event);
		}
		if (once != null) {
			for (IDEventListener<IDEvent> listener : on) {
				offHook((Class<IDEvent>) event, listener);
			}
			once.clear();
			this.once.remove(event);
		}
	}
	public <T extends IDEvent> void clear() {
		for (var iterator = this.on.entrySet().iterator(); iterator.hasNext(); ) {
			var entry = iterator.next();
			var key = entry.getKey();
			this.off(key);
		}
		for (var iterator = this.once.entrySet().iterator(); iterator.hasNext(); ) {
			var entry = iterator.next();
			var key = entry.getKey();
			this.off(key);
		}
	}
	public <T extends IDEvent> void emit(T event) {
		var name = (Class<IDEvent>) event.getClass();
		var once = this.once.getOrDefault(name, null);
		var on = this.on.getOrDefault(name, null);
		if (once != null) {
			for (var i : once) {
				try {
					i.fn(event);
				} catch (Exception e) {
					System.out.println("[EventEmitter] Error: " + e.getMessage());
				}
			}
			for (var i : once) {
				this.offHook(name, i);
			}
			once.clear();
		}
		if (on != null) {
			for (var i : on) {
				try {
					i.fn(event);
				} catch (Exception e) {
					System.out.println("[EventEmitter] Error: " + e.getMessage());
				}
			}
		}
	}
	@FunctionalInterface
	public static interface IDEventListener<T extends IDEvent> {
		public void fn(T event);
	}
}