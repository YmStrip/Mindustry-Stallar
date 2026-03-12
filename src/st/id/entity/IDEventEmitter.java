package st.id.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class IDEventEmitter {
	public HashMap<Class<IDEvent>, ArrayList<IDEventListener<IDEvent>>> on = new HashMap<>();
	public HashMap<Class<IDEvent>, ArrayList<IDEventListener<IDEvent>>> once = new HashMap<>();
	protected void OnHook(Class<IDEvent> name, IDEventListener<IDEvent> listener) {
	}
	protected void OffHook(Class<IDEvent> name, IDEventListener<IDEvent> listener) {
	}
	public <T extends IDEvent> void On(Class<T> event, IDEventListener<T> listener) {
		if (on.get(event) == null) {
			on.put((Class<IDEvent>) event, new ArrayList<>());
		}
		var array = on.get(event);
		if (!array.contains(listener)) array.add((IDEventListener<IDEvent>) listener);
		this.OnHook((Class<IDEvent>) event, (IDEventListener<IDEvent>) listener);
	}
	public <T extends IDEvent> void OnMounted(Class<T> event, IDEventListener<T> listener, IDEventTrigger trigger) {
		this.On(event, listener);
		trigger.listen(() -> {
			this.Off(event, listener);
		});
	}
	public <T extends IDEvent> void Once(Class<T> event, IDEventListener<T> listener) {
		if (once.get(event) == null) {
			once.put((Class<IDEvent>) event, new ArrayList<>());
		}
		var array = once.get(event);
		if (!array.contains(listener)) array.add((IDEventListener<IDEvent>) listener);
		this.OnHook((Class<IDEvent>) event, (IDEventListener<IDEvent>) listener);
	}
	public <T extends IDEvent> void OnceMounted(Class<T> event, IDEventListener<T> listener, IDEventTrigger trigger) {
		this.Once(event, listener);
		trigger.listen(() -> {
			this.Off(event, listener);
		});
	}
	public <T extends IDEvent> void Off(Class<T> event, IDEventListener<T> listener) {
		var once = this.once.get(event);
		var on = this.on.get(event);
		if (on != null) {
			if (on.contains(listener)) {
				on.remove(listener);
				OffHook((Class<IDEvent>) event, (IDEventListener<IDEvent>) listener);
			}
			if (on.size() == 0) {
				this.on.remove(event);
			}
		}
		if (once != null) {
			if (once.contains(listener)) {
				once.remove(listener);
				OffHook((Class<IDEvent>) event, (IDEventListener<IDEvent>) listener);
			}
			if (once.size() == 0) {
				this.once.remove(event);
			}
		}
	}
	public <T extends IDEvent> void Off(Class<T> event) {
		var once = this.once.get(event);
		var on = this.on.get(event);
		if (on != null) {
			for (IDEventListener<IDEvent> listener : on) {
				OffHook((Class<IDEvent>) event, listener);
			}
			on.clear();
			this.on.remove(event);
		}
		if (once != null) {
			for (IDEventListener<IDEvent> listener : on) {
				OffHook((Class<IDEvent>) event, listener);
			}
			once.clear();
			this.once.remove(event);
		}
	}
	public <T extends IDEvent> void Clear() {
		for (var iterator = this.on.entrySet().iterator(); iterator.hasNext(); ) {
			var entry = iterator.next();
			var key = entry.getKey();
			this.Off(key);
		}
		for (var iterator = this.once.entrySet().iterator(); iterator.hasNext(); ) {
			var entry = iterator.next();
			var key = entry.getKey();
			this.Off(key);
		}
	}
	public <T extends IDEvent> void Emit(T event) {
		var name = (Class<IDEvent>) event.getClass();
		var once = this.once.getOrDefault(name, null);
		var on = this.on.getOrDefault(name, null);
		if (once != null) {
			for (var i : once) {
				try {
					i.Callback(event);
				} catch (Exception e) {
					System.out.println("[EventEmitter] Error: " + e.getMessage());
				}
			}
			for (var i : once) {
				this.OffHook(name, i);
			}
			once.clear();
		}
		if (on != null) {
			for (var i : on) {
				try {
					i.Callback(event);
				} catch (Exception e) {
					System.out.println("[EventEmitter] Error: " + e.getMessage());
				}
			}
		}
	}
	@FunctionalInterface
	public static interface IDEventListener<T extends IDEvent> {
		public void Callback(T event);
	}
}