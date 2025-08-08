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
	protected Item getSelectFromName(String name) {
		return Vars.content.item(name);
	}
	@Override
	protected Seq<Item> getSelectList() {
		return Vars.content.items();
	}
	protected abstract float getAmount(Building building, Item item);
	protected abstract float getCapacity(Building building, Item item);
	protected abstract void addAmount(Building building, Item item);
	protected abstract void removeAmount(Building building, Item item);
	public abstract boolean canHandleItem(Building self, Building source, Item item);
	public boolean destroyItem = true;

	public class BlockIOItemAbstractBuild extends BlockIOBuild {
		//output
		@Override
		public void updateTile() {
			super.updateTile();
			if (select == null) return;
			this.outputToBuild(getProximityBuilding(building -> building.acceptItem(this, select)));
		}
		public void outputToBuild(@Nullable Building target) {
			if (target == null) return;
			addBufferOutput();
			while (bufferOutput > 1) {
				bufferOutput--;
				if (target.acceptItem(this, select) && getAmount(this, select) > 1) {
					removeAmount(this, select);
					target.handleItem(target, select);
				} else {
					bufferOutput /= 2;
					break;
				}
			}
		}
		//input
		@Override
		public boolean acceptItem(Building source, Item item) {
			if (!canInput) return false;
			if (!canHandleItem(this, source, item)) return false;
			this.addBufferInput();
			if (bufferInput < 1 || source.team != team) return false;
			return destroyItem || getCapacity(source, item) - getAmount(source, item) >= 1;
		}
		@Override
		public void handleItem(Building source, Item item) {
			this.addBufferInput();
			if (bufferInput < 1) return;
			bufferInput--;
			if (!destroyItem && getCapacity(source, item) - getAmount(source, item) < 1) return;
			addAmount(source, item);
		}
	}
}
