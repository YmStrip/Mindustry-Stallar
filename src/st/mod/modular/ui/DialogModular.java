package st.mod.modular.ui;


import arc.scene.ui.Dialog;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import st.ST;
import st.mod.modular.block.BlockModular;
import st.mod.modular.struct.StructModular;
import st.mod.multiblock.STMultiBlock;
import st.mod.ui.UtilUI;
import st.mod.ui.entity.DialogPane;


public class DialogModular extends DialogPane {
	public BlockModular.BlockModularBuilding Building;
	public DialogModular(BlockModular.BlockModularBuilding building) {
		Building = building;
		Pane("modular_current", (table, param) -> {
			var target = (param instanceof mindustry.gen.Building tar) ? tar : building;
			if (target.block instanceof BlockModular b) {
				b.ViewConfig(target, table, this);
			} else {
				table.labelWrap(ST.UI("modular_not_found"));
			}
		});
		Pane("modular_network", (table, param) -> {
			var struct = STMultiBlock.StructByBuilding.get(building);
			if (!(struct instanceof StructModular st)) {
				table.labelWrap(ST.UI("modular_not_found"));
			} else {
				if (st.Overload) {
					table.labelWrap(ST.UI("modular_overload")).row();
				}
				if (st.OverloadController) {
					table.labelWrap(ST.UI("modular_overload_controller"));
					table.labelWrap("[red](" + st.Controller.size() + "/" + st.CapacityController + ")[white]").left().row();
				}
				table.labelWrap(ST.UI("modular_capacity_building") + ": " + (st.Building.size()) + "/" + (st.CapacityBuilding) + (st.OverloadCapacity ? ST.UI("modular_overload_capacity") : "")).left().row();
				//
				table.labelWrap(ST.UI("modular_building_list")).left().row();
				for (var i : st.Building) {
					if (i.block instanceof BlockModular p) {
						table.table(table1 -> {
							table1.setBackground(Styles.black);
							p.ViewBar(i, table1, this);
							table1.button(Icon.settings, () -> {
								Toggle("modular_current", i);
							}).size(42);
						}).expandX().fillX().left().row();
					} else {
						var row = UtilUI.CreateIconRow(i.block.uiIcon);
						row.labelWrap(i.block.localizedName).expandX().fillX();
						table.add(row).expandX().fillX().left().row();
					}
				}
			}
		});
	}
	@Override
	public Dialog show() {
		var t = super.show();
		Toggle("modular_current");
		return t;
	}
}
