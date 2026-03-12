package st.mod.qio.event;

import st.id.entity.IDEvent;
import st.mod.qio.drive.QIODrive;

public class EventQIOUnlockDrive extends IDEvent {
	public QIODrive target;
	public EventQIOUnlockDrive(QIODrive target) {
		super(null);
		this.target = target;
	}
}
