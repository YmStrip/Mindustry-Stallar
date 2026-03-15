package st.mod.util;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.Building;
import mindustry.world.Block;
import org.json.JSONObject;

/**
 * Normalized serialize and deserialize
 */
public class BlockCodec extends Block {
	public BlockCodec(String name) {
		super(name);
		configurable = true;
		config(String.class, (building, string) -> {
			if (building instanceof BlockCodecBuilding b) {
				try {
					var obj = new JSONObject(string);
					b.Configure(obj, true);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
	}
	public class BlockCodecBuilding extends Building {
		public void Configure(JSONObject value, boolean isBluemap) {
		}
		public JSONObject Serialize(boolean isBluemap) {
			return new JSONObject();
		}
		@Override
		public Object config() {
			return Serialize(true).toString();
		}
		@Override
		public void write(Writes write) {
			super.write(write);
			var json = Serialize(false);
			var jsonStr = json.toString();
			write.i(jsonStr.length());
			write.b(jsonStr.getBytes());
		}
		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);
			var str = new String(read.b(read.i()));
			try {
				Configure(new JSONObject(str), false);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
