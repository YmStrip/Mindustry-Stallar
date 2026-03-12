package st.mod.qio.drive;


import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.type.StatusEffect;
import st.mod.qio.STQIO;
import st.mod.qio.event.EventQIOUnlockDrive;

public class QIODrive extends UnlockableContent {
	public float CapacityLiquid = 0;
	public float CapacityItem = 0;
	public float CapacityUnit = 0;
	public float CapacityPayload = 0;
	public QIODrive(String name) {
		super(name);
	}
	@Override
	public void onUnlock() {
		super.onUnlock();
		STQIO.Event.Emit(new EventQIOUnlockDrive(this));
	}
	@Override
	public ContentType getContentType() {
		return ContentType.status;
	}
}
