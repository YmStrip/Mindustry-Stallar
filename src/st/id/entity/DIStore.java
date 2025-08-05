package st.id.entity;

public class DIStore {
	public Boolean extended = false;
	public boolean extend(DIStore parent) {
		if (extended) return false;
		extended = true;
		return true;
	}
	public void instance(Object instance) {
	}
	public void classes(Class<Object> type) {
	}
}
