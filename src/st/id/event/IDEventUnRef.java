package st.id.event;

import st.id.entity.ID;
import st.id.entity.IDEvent;

public class IDEventUnRef extends IDEvent {
	public ID Target;
	public IDEventUnRef(ID id, ID target) {
		super(id);
		this.Target = target;
	}
}