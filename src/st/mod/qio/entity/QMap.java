package st.mod.qio.entity;


import org.json.JSONObject;
import st.id.entity.ID;

import java.util.HashMap;

public class QMap extends ID {
	public float capacity = 128;
	public void capacity(float max) {
		this.capacity = max;
		this.clamp();
	}
	public void clamp() {
		for (var i : map.entrySet()) {
			var value = i.getValue();
			if (value > capacity) value = capacity;
			if (value < 0) value = 0f;
			i.setValue(value);
		}
	}
	public HashMap<String, Float> map = new HashMap<>();
	public float get(String name) {
		var result = map.get(name);
		if (result == null) return 0.0f;
		if (result < 0 || result > capacity) {
			if (result < 0) result = 0.0f;
			if (result > capacity) result = capacity;
			map.put(name, result);
		}
		return result;
	}
	public void set(String name, float value) {
		map.put(name, Math.min(Math.max(value, 0), capacity));
	}
	public void clear(String name) {
		map.remove(name);
	}
	public boolean has(String name) {
		return get(name) > 0;
	}
	public void add(String name, float value) {
		set(name, get(name) + value);
	}
	public void remove(String name, float value) {
		set(name, get(name) - value);
	}
	public void clear() {
		map.clear();
	}
	@Override
	public void configure(JSONObject value) {
		this.map.clear();
		for (var i : value.keySet()) {
			map.put(i, value.getFloat(i));
		}
		super.configure(value);
	}
	@Override
	public JSONObject serialize() {
		var result = new JSONObject();
		for (var entry : map.entrySet()) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
