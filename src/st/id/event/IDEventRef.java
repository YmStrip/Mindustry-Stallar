package st.id.event;

import st.id.entity.ID;
import st.id.entity.IDEvent;

public class IDEventRef extends IDEvent {
	public ID Target;
	public IDEventRef(ID id,ID target) {
		super(id);
		this.Target = target;
	}
}