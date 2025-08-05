package st.id.entity;

import st.id.event.IDEventDestroy;

import java.util.HashMap;

public class IDTrigger extends ID {
	public HashMap<Object, HashMap<Object, IDEventTrigger>> map = new HashMap<>();
	public IDEventTrigger trigger(Object key, Object subKey) {
		if (this.map.containsKey(key)) this.unTrigger(key, subKey);
		if (!this.map.containsKey(key)) this.map.put(key, new HashMap<>());
		var trigger = new IDEventTrigger();
		this.map.get(key).put(subKey, trigger);
		return trigger;
	}
	public void unTrigger(Object key) {
		var current = this.map.get(key);
		this.map.remove(key);
		if (current != null) for (var i : current.values()) i.emit();
	}
	public void unTrigger(Object key, Object subKey) {
		var current = this.map.get(key);
		if (current != null && current.containsKey(subKey)) {
			current.get(subKey).emit();
			current.remove(subKey);
			if (current.isEmpty()) map.remove(key);
		}
	}
	public void unTriggerWhenDestroy(ID id) {
		id.event.onMounted(IDEventDestroy.class, (event) -> {
			this.unTrigger(id);
		}, this.trigger(id, "destroy"));
	}
	public void clear() {
		for (var i : this.map.values()) {
			for (var j : i.values()) {
				j.emit();
			}
		}
		this.map.clear();
	}
	@Override
	public void destroy() {
		this.clear();
		super.destroy();
	}
}
