package st.id.event;

import st.id.entity.ID;
import st.id.entity.IDEvent;

public class IDEventRename extends IDEvent {
	public String OldName;
	public String NewNam;
	public IDEventRename(ID id, String oldName, String newName) {
		super(id);
		this.OldName = oldName;
		this.NewNam = newName;
	}
}