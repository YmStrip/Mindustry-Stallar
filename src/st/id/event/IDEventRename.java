package st.id.event;

import st.id.entity.ID;
import st.id.entity.IDEvent;

public class IDEventRename extends IDEvent {
	public String oldName;
	public String newName;
	public IDEventRename(ID id, String oldName, String newName) {
		super(id);
		this.oldName = oldName;
		this.newName = newName;
	}
}