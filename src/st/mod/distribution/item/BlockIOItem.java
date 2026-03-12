package st.mod.distribution.item;

import mindustry.gen.Building;
import mindustry.type.Item;

public class BlockIOItem extends BlockIOItemAbstract {
	public BlockIOItem(String name) {
		super(name);
	}
	@Override
	protected float GetAmount(Building building, Item item) {
		var core = building.core();
		if (core == null) return 0;
		return core.items.get(item);
	}
	@Override
	protected float GetCapacity(Building building, Item item) {
		var core = building.core();
		if (core == null) return 0;
		return core.storageCapacity;
	}
	@Override
	protected void AddAmount(Building building, Item item) {
		var core = building.core();
		if (core == null) return;
		core.handleItem(building, item);
	}
	@Override
	protected void RemoveAmount(Building building, Item item) {
		var core = building.core();
		if (core == null) return;
		core.itemTaken(item);
		core.items.remove(item, 1);
	}
	@Override
	public boolean CanHandleItem(Building self, Building source, Item item) {
		return !(source instanceof BlockIOItemAbstractBuild);
	}
}
