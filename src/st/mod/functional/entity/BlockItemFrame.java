package st.mod.functional.entity;

import arc.graphics.g2d.Draw;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import st.mod.distribution.entity.BlockSelect;

public class BlockItemFrame extends BlockSelect {
	public BlockItemFrame(String name) {
		super(name);
	}
	@Override
	protected UnlockableContent GetSelectFromName(String name) {
		return Vars.content.item(name);
	}
	@Override
	protected Seq GetSelectList() {
		return Vars.content.items();
	}
	public class BlockItemFrameBuilding extends BlockSelectBuild {
		@Override
		public void draw() {
			super.draw();
			if (this.select != null && this.select.uiIcon != null) {
				var scl = Draw.scl;
				var k = Math.min(this.select.uiIcon.width, this.select.uiIcon.height) / (size * 32);
				if (k != 1) {
					Draw.scl(1f / k);
					Draw.rect(this.select.uiIcon, x, y);
					Draw.scl(scl);
				} else {
					Draw.rect(this.select.uiIcon, x, y);
				}
			}
		}
	}
}
