package st.mod.distribution.item;

import mindustry.gen.Building;
import mindustry.type.Item;

public class BlockIOItem extends BlockIOItemAbstract {
	public BlockIOItem(String name) {
		super(name);
	}
	@Override
	protected float getAmount(Building building, Item item) {
		var core = building.core();
		if (core == null) return 0;
		return core.items.get(item);
	}
	@Override
	protected float getCapacity(Building building, Item item) {
		var core = building.core();
		if (core == null) return 0;
		return core.storageCapacity;
	}
	@Override
	protected void addAmount(Building building, Item item) {
		var core = building.core();
		if (core == null) return;
		core.handleItem(building, item);
	}
	@Override
	protected void removeAmount(Building building, Item item) {
		var core = building.core();
		if (core == null) return;
		core.itemTaken(item);
		core.items.remove(item, 1);
	}
}
