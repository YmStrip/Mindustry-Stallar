package st.mod.distribution.item;

import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.type.Item;
import st.mod.distribution.entity.BlockIO;

public abstract class BlockIOItemAbstract extends BlockIO<Item> {
	public BlockIOItemAbstract(String name) {
		super(name);
	}
	@Override
	protected Item GetSelectFromName(String name) {
		return Vars.content.item(name);
	}
	@Override
	protected Seq<Item> GetSelectList() {
		return Vars.content.items();
	}
	protected abstract float GetAmount(Building building, Item item);
	protected abstract float GetCapacity(Building building, Item item);
	protected abstract void AddAmount(Building building, Item item);
	protected abstract void RemoveAmount(Building building, Item item);
	public abstract boolean CanHandleItem(Building self, Building source, Item item);
	public boolean DestroyItem = true;

	public class BlockIOItemAbstractBuild extends BlockIOBuild {
		//output
		@Override
		public void updateTile() {
			super.updateTile();
			if (select == null) return;
			this.outputToBuild(GetProximityBuilding(building -> building.acceptItem(this, select)));
		}
		public void outputToBuild(@Nullable Building target) {
			if (target == null) return;
			OutputBufferIncrease();
			while (OutputBuffer > 1) {
				OutputBuffer--;
				if (target.acceptItem(this, select) && GetAmount(this, select) > 1) {
					RemoveAmount(this, select);
					target.handleItem(target, select);
				} else {
					OutputBuffer /= 2;
					break;
				}
			}
		}
		//input
		@Override
		public boolean acceptItem(Building source, Item item) {
			if (!InputAble) return false;
			if (!CanHandleItem(this, source, item)) return false;
			this.InputBufferIncrease();
			if (InputBuffer < 1 || source.team != team) return false;
			return DestroyItem || GetCapacity(source, item) - GetAmount(source, item) >= 1;
		}
		@Override
		public void handleItem(Building source, Item item) {
			this.InputBufferIncrease();
			if (InputBuffer < 1) return;
			InputBuffer--;
			if (!DestroyItem && GetCapacity(source, item) - GetAmount(source, item) < 1) return;
			AddAmount(source, item);
		}
	}
}
