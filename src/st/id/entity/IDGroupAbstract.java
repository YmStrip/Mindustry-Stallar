package st.id.entity;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class IDGroupAbstract<T extends ID> extends ID {
	public ArrayList<T> children = new ArrayList<>();
	public HashSet<T> map = new HashSet<>();
	public IDTrigger TRIGGER = new IDTrigger() {{
		CascadeDestroyFrom(this);
	}};
	public abstract boolean remove(T child);
	public abstract boolean add(T child);
	public void clear() {
		for (var iter = this.children.iterator(); iter.hasNext(); ) {
			var child = iter.next();
			this.remove(child);
		}
	}
	@Override
	public void destroy() {
		this.clear();
		super.destroy();
	}
}