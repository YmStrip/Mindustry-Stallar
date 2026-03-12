package st.id.entity;

import st.id.event.IDEventDestroy;

import java.util.HashMap;

public class IDTrigger extends ID {
	public HashMap<Object, HashMap<Object, IDEventTrigger>> Map = new HashMap<>();
	public IDEventTrigger Trigger(Object key, Object subKey) {
		if (this.Map.containsKey(key)) this.UnTrigger(key, subKey);
		if (!this.Map.containsKey(key)) this.Map.put(key, new HashMap<>());
		var trigger = new IDEventTrigger();
		this.Map.get(key).put(subKey, trigger);
		return trigger;
	}
	public void UnTrigger(Object key) {
		var current = this.Map.get(key);
		this.Map.remove(key);
		if (current != null) for (var i : current.values()) i.emit();
	}
	public void UnTrigger(Object key, Object subKey) {
		var current = this.Map.get(key);
		if (current != null && current.containsKey(subKey)) {
			current.get(subKey).emit();
			current.remove(subKey);
			if (current.isEmpty()) Map.remove(key);
		}
	}
	public void UnTriggerWhenDestroy(ID id) {
		id.Event.OnMounted(IDEventDestroy.class, (event) -> {
			this.UnTrigger(id);
		}, this.Trigger(id, "destroy"));
	}
	public void Clear() {
		for (var i : this.Map.values()) {
			for (var j : i.values()) {
				j.emit();
			}
		}
		this.Map.clear();
	}
	@Override
	public void Destroy() {
		this.Clear();
		super.Destroy();
	}
}
