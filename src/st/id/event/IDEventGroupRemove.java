package st.id.event;

import st.id.entity.ID;
import st.id.entity.IDEvent;

public class IDEventGroupRemove extends IDEvent {
	public ID target;
	public IDEventGroupRemove(ID id, ID target) {
		super(id);
		this.target = target;
	}
}