package st.mod.qio.block;

import arc.scene.ui.layout.Table;
import mindustry.gen.Building;
import mindustry.world.Block;
import st.mod.qio.ui.DialogQIO;

public class BlockQIOPane extends Block {
	public BlockQIOPane(String name) {
		super(name);
		configurable = true;
		update = true;
	}
	public class BlockQIOPaneBuild extends Building {
		@Override
		public void buildConfiguration(Table table) {
			new DialogQIO().show().exited(this::deselect);
		}
	}
}
