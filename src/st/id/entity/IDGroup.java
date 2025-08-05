package st.id.entity;


import org.json.JSONObject;
import st.id.GLOBAL;
import st.id.UTIL_ID;
import st.id.event.IDEventDestroy;
import st.id.event.IDEventGroupAdd;
import st.id.event.IDEventGroupRemove;
import st.id.event.IDEventRename;


import java.util.ArrayList;
import java.util.HashMap;


public class IDGroup<T extends ID> extends IDGroupAbstract<T> {
	public ArrayList<T> children = new ArrayList<>();
	public HashMap<String, T> data = new HashMap<>();
	//
	private void renameItem(T value, String oldName, String newName) {
		var exist = this.data.get(oldName) == value;
		var nextName = UTIL_ID.nextkey(newName, key -> this.data.containsKey(key));
		if (exist) this.data.remove(oldName);
		value.rename(nextName);
		if (exist) this.data.put(nextName, value);
	}
	public boolean add(T value) {
		if (this.map.contains(value)) return false;
		if (value == this) return false;
		if (value.GROUP instanceof IDGroup<?>) ((IDGroup<T>) value.GROUP).remove(value);
		this.renameItem(value, value.name, value.name);
		this.map.add(value);
		this.data.put(value.name, value);
		this.children.add(value);
		value.GROUP = this;
		value.event.onMounted(IDEventDestroy.class, event -> this.remove(value), TRIGGER.trigger(value, "destroy"));
		value.event.onMounted(IDEventRename.class, event -> this.renameItem(value, event.oldName, event.newName), TRIGGER.trigger(value, "rename"));
		this.event.emit(new IDEventGroupAdd(this, value));
		return true;
		/*
		if (this.map.has(value.identity)) return false
		if (value.identity == this.identity) return false
		//check: exist in else group and remove
		if ((value.GROUP) instanceof IDGroup) value.GROUP.remove(value, false)
		//check: name is repeat
		this.renameItem(value, value.name, value.name)
		//set
		this.map.set(value.identity, value)
		this.data[value.name] = value
		this.children.push(value)
		value.Key('GROUP', this)
		value.Project(this.PROJECT)
		this.ref(value)
		//bind: dynamic replace event
		value.event.onMounted('destroy', () => this.remove(value), this.TRIGGER.trigger(value, 'destroy'))
		value.event.onMounted('destroy:history', () => this.removeHistoric(value), this.TRIGGER.trigger(value, 'destroy:history'))
		value.event.onMounted('rename', (oldname, newname) => this.renameItem(value, oldname, newname), this.TRIGGER.trigger(value, 'rename'))
		this.FOR.add(value)
		//
		this.event.emitSync('add', value)
		this.change(IDGroup.FLAG_CHILDREN)
		return true*/
	}
	public boolean remove(T value) {
		if (!this.map.contains(value)) return false;
		this.map.remove(value);
		var index = this.children.indexOf(value);
		if (index != -1) this.children.remove(index);
		if (value.GROUP == this) value.GROUP = null;
		this.data.remove(value.name);
		this.TRIGGER.unTrigger(value);
		this.event.emit(new IDEventGroupRemove(this, value));
		value.destroy();
		return true;
		/*
		if (!this.map.has(value.identity)) return false
		//delete
		this.map.delete(value.identity)
		this.statics.delete(value.identity)
		delete this.data[value.name]
		const index = this.indexOf(value)
		if (index != -1) this.children.splice(index, 1)
		value.KeyRemove('group')
		this.unref(value)
		//unbind event
		this.TRIGGER.untrigger(value)
		this.FOR.remove(value)
		//
		this.event.emitSync('remove', value)
		this.change(IDGroup.FLAG_CHILDREN)
		//
		if (del) value.destroy()
		return true
		*/
	}
	//{class:any,data:any}
	@Override
	public void configure(JSONObject value) {
		super.configure(value);
		var data = value.get("children");
		if (data instanceof ArrayList arr) {
			for (var item : arr) {
				if (item instanceof HashMap<?,?> set) {
					var className = set.get("class");
					var dataName = set.get("data");
					if (className == null || dataName == null) continue;
					var classType = GLOBAL.CLASS.get(className);
					if (classType==null) continue;
				}
			}
		}
	}
}