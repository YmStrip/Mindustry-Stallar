package st.id.event;

import st.id.entity.ID;
import st.id.entity.IDEvent;

public class IDEventRef extends IDEvent {
	public ID target;
	public IDEventRef(ID id,ID target) {
		super(id);
		this.target = target;
	}
}