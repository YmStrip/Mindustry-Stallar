package st.mod.modular.block;


import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.type.UnitType;
import mindustry.world.blocks.payloads.Payload;
import st.mod.modular.struct.StructModular;

public class BlockModularStorage extends BlockModular {
	public BlockModularStorage(String name) {
		super(name);
	}
	@Override
	public void HandleStructAdd(StructModular struct, BlockModularBuilding building) {
		if (!(building instanceof BlockModularStorageBuild b)) return;
		struct.AddStorage(b);
	}
	@Override
	public void HandleStructRemove(StructModular struct, BlockModularBuilding building) {
		if (!(building instanceof BlockModularStorageBuild b)) return;
		struct.RemoveStorage(b);
	}
	public class BlockModularStorageBuild extends BlockModular.BlockModularBuilding {
		@Override
		public boolean acceptItem(Building source, Item item) {
			return this.items.get(item) < itemCapacity;
		}
		@Override
		public void handleItem(Building source, Item item) {
			this.AddRecourse(item, 1);
		}
		@Override
		public void itemTaken(Item item) {
			var old = items.get(item);
			items.remove(item, 1);
			var dt = items.get(item) - old;
			if (Struct != null && dt == 1) {
				Struct.StorageCount.put(item, Math.max(0f, Struct.StorageCount.getOrDefault(item, 0f) - dt));
			}
		}
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return this.liquids.get(liquid) < itemCapacity;
		}
		@Override
		public void handleLiquid(Building source, Liquid liquid, float amount) {
			this.AddRecourse(liquid, 1);
		}
		@Override
		public boolean acceptPayload(Building source, Payload payload) {
			return (payload instanceof UnitType u) && this.Unit.getOrDefault(u, 0f) < CapacityUnit;
		}
		@Override
		public void handlePayload(Building source, Payload payload) {
			if (!(payload instanceof UnitType unit)) return;
			this.AddRecourse(unit, 1);
		}
		public boolean HasRecourse(UnlockableContent type, float count) {
			if (Struct == null) return false;
			if (type instanceof Item item) return items.get(item) >= count;
			if (type instanceof Liquid liquid) return liquids.get(liquid) >= count;
			if (type instanceof UnitType unit) return Unit.get(unit) >= count;
			return false;
		}
		/**
		 * Add Recourse
		 *
		 * @param type  type
		 * @param count count
		 * @return excess
		 */
		public float AddRecourse(UnlockableContent type, float count) {
			if (Struct == null) return count;
			if (count < 0) return 0f;
			if (type instanceof Item item) {
				var old = items.get(item);
				items.add(item, (int) count);
				var dt = items.get(item) - old;
				Struct.StorageCount.put(type, Struct.StorageCount.getOrDefault(type, 0f) + dt);
				return count - dt;
			}
			if (type instanceof Liquid liquid) {
				var old = liquids.get(liquid);
				liquids.add(liquid, count);
				var dt = liquids.get(liquid) - old;
				Struct.StorageCount.put(type, Struct.StorageCount.getOrDefault(type, 0f) + dt);
				return count - dt;
			}
			if (type instanceof UnitType unit) {
				var old = Unit.getOrDefault(unit, 0f);
				var cur = Math.min(CapacityUnit, old + count);
				var dt = cur - old;
				Unit.put(unit, cur);
				Struct.StorageCount.put(type, Struct.StorageCount.getOrDefault(type, 0f) + dt);
				return count - dt;
			}
			return count;
		}
		/**
		 * Remove Recourse
		 *
		 * @param type  type
		 * @param count count
		 * @return taken
		 */
		public float RemoveRecourse(UnlockableContent type, float count) {
			if (Struct == null) return 0;
			if (count < 0) return 0f;
			if (type instanceof Item item) {
				var old = items.get(item);
				var taken = Math.min(old, count);
				var cur = (int) (old - taken);
				var dt = old - cur;
				items.set(item, cur);
				Struct.StorageCount.put(type, Struct.StorageCount.getOrDefault(type, 0f) - dt);
				return dt;
			}
			if (type instanceof Liquid liquid) {
				var old = liquids.get(liquid);
				var taken = Math.min(old, count);
				liquids.set(liquid, old - taken);
				Struct.StorageCount.put(type, Struct.StorageCount.getOrDefault(type, 0f) - taken);
				return taken;
			}
			if (type instanceof UnitType unit) {
				var old = Unit.getOrDefault(unit, 0f);
				var taken = Math.min(old, count);
				Unit.put(unit, old - taken);
				Struct.StorageCount.put(type, Struct.StorageCount.getOrDefault(type, 0f) - taken);
				return taken;
			}
			return 0;
		}
	}
}
