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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 * a minimum ID impl - Cannot use constructor
 */
public class ID {
	private static Field getField(Class type, String key) {
		try {
			return type.getField(key);
		} catch (Exception e) {
			return null;
		}
	}
	private static Object getFiledValue(Object object, Field field) {
		try {
			return field.get(object);
		} catch (Exception e) {
			return null;
		}
	}
	private static void setFiledValue(Object object, Field field, Object value) {
		try {
			field.set(object, value);
		} catch (Exception e) {
		}
	}
	private static Method getMethod(Class type, String key) {
		try {
			return type.getDeclaredMethod(key);
		} catch (Exception e) {
			return null;
		}
	}
	public IDEventEmitter event = new IDEventEmitter();
	public void configure(JSONObject value) {
		var type = this.getClass();
		for (var i : value.keySet()) {
			//
			var data = value.get(i);
			var field = getField(type, i);
			if (field == null) continue;
			//[configure]
			var configure = field.getAnnotation(KeyConfigure.class);
			var configureMethod = configure != null ? getMethod(type, configure.methodName()) : null;
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
			var currentValue = getFiledValue(this, field);
			if (currentValue instanceof ID) {
				if (data instanceof JSONObject) {
					((ID) currentValue).configure((JSONObject) data);
				}
				continue;
			}
			if ((data == null) || (data instanceof Number && currentValue instanceof Number) || (data.getClass() == currentValue.getClass())) {
				setFiledValue(this, field, data);
			}
		}
	}
	public JSONObject serialize() {
		var result = new JSONObject();
		var fields = this.getClass().getDeclaredFields();
		for (var field : fields) {
			var value = getFiledValue(this, field);
			var key = field.getName();
			//[serialize]
			var serialize = field.getAnnotation(KeySerialize.class);
			var serializeMethod = serialize != null ? getMethod(field.getType(), serialize.methodName()) : null;
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
				result.put(key, ((ID) value).serialize());
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
	public HashSet<ID> refs = new HashSet<>();
	public HashSet<ID> subs = new HashSet<>();
	//
	public String name = "";
	public ID() {
	}
	//
	public IDGroupAbstract GROUP;
	//
	public Boolean destroyed = false;
	public void destroy() {
		if (destroyed) return;
		if (this.cascadeDestroyFromTo != null) {
			for (var i : this.cascadeDestroyFromTo) i.destroy();
			this.cascadeDestroyFromTo.clear();
		}
		for (var iter = subs.iterator(); iter.hasNext(); ) {
			var sub = iter.next();
			sub.unref(this);
		}
		for (var iter = refs.iterator(); iter.hasNext(); ) {
			var ref = iter.next();
			this.unref(ref);
		}
		this.destroyed = true;
		this.event.emit(new IDEventDestroy(this));
		this.event.clear();

	}
	public void ref(ID id) {
		if (this.refs.contains(id)) return;
		this.refs.add(id);
		this.event.emit(new IDEventRef(this, id));
	}
	public void sub(ID id) {
		id.ref(this);
	}
	public void unref(ID id) {
		if (!this.refs.contains(id)) return;
		this.refs.remove(id);
		this.event.emit(new IDEventUnRef(this, id));
	}
	public void unsub(ID id) {
		id.unref(this);
	}
	public void rename(String name) {
		if (Objects.equals(this.name, name)) return;
		var oldName = this.name;
		this.name = name;
		this.event.emit(new IDEventRename(this, oldName, name));
	}
	//Link
	private HashSet<ID> cascadeDestroyFromTo;
	public ID CascadeDestroyFrom(ID parent) {
		parent.CascadeDestroyTo(this);
		return this;
	}
	public ID CascadeDestroyTo(ID child) {
		if (this.cascadeDestroyFromTo == null) this.cascadeDestroyFromTo = new HashSet<>();
		this.cascadeDestroyFromTo.add(child);
		this.ref(child);
		return this;
	}
	//NO: TICKER AND MANAGER
}