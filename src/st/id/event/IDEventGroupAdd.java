package st.id.event;

import st.id.entity.ID;
import st.id.entity.IDEvent;

public class IDEventGroupAdd extends IDEvent {
	public ID target;
	public IDEventGroupAdd(ID id, ID target) {
		super(id);
		this.target = target;
	}
}