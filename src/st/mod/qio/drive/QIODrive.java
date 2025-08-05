package st.mod.qio.drive;


import mindustry.type.StatusEffect;
import st.mod.qio.STORE_QIO;

public class QIODrive extends StatusEffect {
	public float liquidCapacity = 0;
	public float itemCapacity = 0;
	public float unitCapacity = 0;
	public float payloadCapacity = 0;
	public QIODrive(String name) {
		super(name);
	}
	@Override
	public void onUnlock() {
		super.onUnlock();
		STORE_QIO.EVENT.emit(new EventQIOUnlockDrive(this));
	}
}
