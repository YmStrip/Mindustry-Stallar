package st.id.entity;

import org.json.JSONObject;
import st.id.event.IDEventDestroy;
import st.id.anno.KeyConfigure;
import st.id.anno.KeyInclude;
import st.id.anno.KeySerialize;
import st.id.event.IDEventRef;
import st.id.event.IDEventRename;
import st.id.event.IDEventUnRef;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;

/**
 * a minimum ID impl - Cannot use constructor
 */
public class ID {
	private static Field GetField(Class type, String key) {
		try {
			return type.getField(key);
		} catch (Exception e) {
			return null;
		}
	}
	private static Object GetFiledValue(Object object, Field field) {
		try {
			return field.get(object);
		} catch (Exception e) {
			return null;
		}
	}
	private static void SetFiledValue(Object object, Field field, Object value) {
		try {
			field.set(object, value);
		} catch (Exception e) {
		}
	}
	private static Method GetMethod(Class type, String key) {
		try {
			return type.getDeclaredMethod(key);
		} catch (Exception e) {
			return null;
		}
	}
	public IDEventEmitter Event = new IDEventEmitter();
	public void Configure(JSONObject value) {
		var type = this.getClass();
		for (var i : value.keySet()) {
			//
			var data = value.get(i);
			var field = GetField(type, i);
			if (field == null) continue;
			//[configure]
			var configure = field.getAnnotation(KeyConfigure.class);
			var configureMethod = configure != null ? GetMethod(type, configure.methodName()) : null;
			if (configureMethod != null) {
				try {
					configureMethod.invoke(this, data);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				continue;
			}
			//[auto]
			if (field.getAnnotation(KeyInclude.class) == null) continue;
			var currentValue = GetFiledValue(this, field);
			if (currentValue instanceof ID) {
				if (data instanceof JSONObject) {
					((ID) currentValue).Configure((JSONObject) data);
				}
				continue;
			}
			if ((data == null) || (data instanceof Number && currentValue instanceof Number) || (data.getClass() == currentValue.getClass())) {
				SetFiledValue(this, field, data);
			}
		}
	}
	public JSONObject Serialize() {
		var result = new JSONObject();
		var fields = this.getClass().getDeclaredFields();
		for (var field : fields) {
			var value = GetFiledValue(this, field);
			var key = field.getName();
			//[serialize]
			var serialize = field.getAnnotation(KeySerialize.class);
			var serializeMethod = serialize != null ? GetMethod(field.getType(), serialize.methodName()) : null;
			if (serializeMethod != null) {
				try {
					result.put(key, serializeMethod.invoke(this, value));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				continue;
			}
			//[auto]
			if (field.getAnnotation(KeyInclude.class) == null) continue;
			if (value instanceof ID) {
				result.put(key, ((ID) value).Serialize());
				continue;
			}
			if (value instanceof Number) {
				result.put(key, ((Number) value).doubleValue());
			}
			if (value instanceof String || value instanceof Boolean) {
				result.put(key, value);
			}
		}
		return result;
	}
	public HashSet<ID> Refs = new HashSet<>();
	public HashSet<ID> Subs = new HashSet<>();
	//
	public String Name = "";
	public ID() {
	}
	//
	public Boolean Destroyed = false;
	public void Destroy() {
		if (Destroyed) return;
		if (this.CascadeDestroyFromTo != null) {
			for (var i : this.CascadeDestroyFromTo) i.Destroy();
			this.CascadeDestroyFromTo.clear();
			this.CascadeDestroyFromTo = null;
		}
		for (var iter = Subs.iterator(); iter.hasNext(); ) {
			var sub = iter.next();
			sub.Unref(this);
		}
		for (var iter = Refs.iterator(); iter.hasNext(); ) {
			var ref = iter.next();
			this.Unref(ref);
		}
		this.Destroyed = true;
		this.Event.Emit(new IDEventDestroy(this));
		this.Event.Clear();

	}
	public void Ref(ID id) {
		if (this.Refs.contains(id)) return;
		this.Refs.add(id);
		this.Event.Emit(new IDEventRef(this, id));
	}
	public void Sub(ID id) {
		id.Ref(this);
	}
	public void Unref(ID id) {
		if (!this.Refs.contains(id)) return;
		this.Refs.remove(id);
		this.Event.Emit(new IDEventUnRef(this, id));
	}
	public void Unsub(ID id) {
		id.Unref(this);
	}
	public void Rename(String name) {
		if (Objects.equals(this.Name, name)) return;
		var oldName = this.Name;
		this.Name = name;
		this.Event.Emit(new IDEventRename(this, oldName, name));
	}
	//Link
	private HashSet<ID> CascadeDestroyFromTo;
	public ID CascadeDestroyFrom(ID parent) {
		parent.CascadeDestroyTo(this);
		return this;
	}
	public ID CascadeDestroyTo(ID child) {
		if (this.CascadeDestroyFromTo == null) this.CascadeDestroyFromTo = new HashSet<>();
		this.CascadeDestroyFromTo.add(child);
		this.Ref(child);
		return this;
	}
	//NO: TICKER AND MANAGER
}