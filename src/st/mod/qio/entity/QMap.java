package st.mod.qio.entity;


import org.json.JSONObject;
import st.id.entity.ID;

import java.util.HashMap;

public class QMap extends ID {
	public float Capacity = 128;
	public void Capacity(float max) {
		this.Capacity = max;
		this.Clamp();
	}
	public void Clamp() {
		for (var i : Map.entrySet()) {
			var value = i.getValue();
			if (value > Capacity) value = Capacity;
			if (value < 0) value = 0f;
			i.setValue(value);
		}
	}
	public HashMap<String, Float> Map = new HashMap<>();
	public float Get(String name) {
		var result = Map.get(name);
		if (result == null) return 0.0f;
		if (result < 0 || result > Capacity) {
			if (result < 0) result = 0.0f;
			if (result > Capacity) result = Capacity;
			Map.put(name, result);
		}
		return result;
	}
	public void Set(String name, float value) {
		Map.put(name, Math.min(Math.max(value, 0), Capacity));
	}
	public void Clear(String name) {
		Map.remove(name);
	}
	public boolean Has(String name) {
		return Get(name) > 0;
	}
	public void Add(String name, float value) {
		Set(name, Get(name) + value);
	}
	public void Remove(String name, float value) {
		Set(name, Get(name) - value);
	}
	public void Clear() {
		Map.clear();
	}
	@Override
	public void Configure(JSONObject value) {
		this.Map.clear();
		for (var i : value.keySet()) {
			Map.put(i, value.getFloat(i));
		}
		super.Configure(value);
	}
	@Override
	public JSONObject Serialize() {
		var result = new JSONObject();
		for (var entry : Map.entrySet()) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
