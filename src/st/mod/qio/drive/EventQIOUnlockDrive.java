package st.mod.qio.drive;

import st.id.entity.IDEvent;

public class EventQIOUnlockDrive extends IDEvent {
	public QIODrive target;
	public EventQIOUnlockDrive(QIODrive target) {
		super(null);
		this.target = target;
	}
}
