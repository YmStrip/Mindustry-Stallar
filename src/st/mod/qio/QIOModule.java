package st.mod.qio;

import arc.Core;
import arc.Events;
import arc.util.Log;
import mindustry.game.EventType;
import org.json.JSONObject;
import st.ST;
import st.mod.ST_TECH;
import st.mod.qio.drive.EventQIOUnlockDrive;


public class QIOModule {
	public void write() {
		Core.settings.put(ST.name("qio"), STORE_QIO.NETWORK.serialize().toString());
	}
	public void read() {
		try {
			var source = Core.settings.getString(ST.name("qio"));
			if (!(source == null || source.isEmpty())) {
				var object = new JSONObject(source);
				STORE_QIO.NETWORK.configure(object);
			}
		} catch (Exception e) {
			Log.info("[QIO] Read Data Failed");
			Log.err(e);
		}
	}
	/**
	 * update all capacity
	 */
	public void updateCapacity() {
		var liquid = 1024f;
		var item = 256f;
		var unit = 8f;
		var payload = 8f;
		if (ST_QIO.QIO_DRIVE.unlocked()) {
			liquid += ST_QIO.QIO_DRIVE.liquidCapacity;
			item += ST_QIO.QIO_DRIVE.itemCapacity;
			unit += ST_QIO.QIO_DRIVE.unitCapacity;
			payload += ST_QIO.QIO_DRIVE.payloadCapacity;
		}
		if (ST_QIO.QIO_DRIVE_EXTRA.unlocked()) {
			liquid += ST_QIO.QIO_DRIVE_EXTRA.liquidCapacity;
			item += ST_QIO.QIO_DRIVE_EXTRA.itemCapacity;
			unit += ST_QIO.QIO_DRIVE_EXTRA.unitCapacity;
			payload += ST_QIO.QIO_DRIVE_EXTRA.payloadCapacity;
		}
		STORE_QIO.NETWORK.ITEM.capacity(item);
		STORE_QIO.NETWORK.LIQUID.capacity(liquid);
		STORE_QIO.NETWORK.UNIT.capacity(unit);
		STORE_QIO.NETWORK.PAYLOAD.capacity(payload);
	}

	public QIOModule() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			read();
			ST_TECH
				.createTechNodeRoot(ST_QIO.QIO_ITEM_INTERFACE)
				.add(ST_QIO.QIO_DRIVE, t -> t
					.add(ST_QIO.QIO_DRIVE_EXTRA)
				)
				.add(ST_QIO.QIO_PANE)
				.children(ST_QIO.QIO_LIQUID_INTERFACE)
				.children(ST_QIO.QIO_UNIT_INTERFACE)
			;
		});
		STORE_QIO.EVENT.on(EventQIOUnlockDrive.class, e -> {
			updateCapacity();
		});
		Events.on(EventType.ModContentLoadEvent.class, e -> {
			updateCapacity();
		});
		Events.on(EventType.SaveWriteEvent.class, e -> {
			write();
		});
	}
}
