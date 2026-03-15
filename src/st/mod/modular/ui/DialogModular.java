package st.mod.modular.ui;


import arc.scene.ui.Dialog;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import st.ST;
import st.mod.modular.block.BlockModular;
import st.mod.modular.struct.StructModular;
import st.mod.multiblock.STMultiBlock;
import st.mod.ui.UtilUI;
import st.mod.ui.DialogPane;


public class DialogModular extends DialogPane {
	public BlockModular.BlockModularBuilding Building;
	public DialogModular(BlockModular.BlockModularBuilding building) {
		Building = building;
		Pane("modular_current", (table, param) -> {
			var target = (param instanceof BlockModular.BlockModularBuilding tar) ? tar : building;
			if (target.block instanceof BlockModular b) {
				b.RenderConfig(target, table, this);
				b.RenderStyle(target, table, this);
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
				//buffer
				table.labelWrap(() -> {
					return ST.UI("modular_buffer_state_item") + ": " + DisplayBuffer(st.BufferItemInput, st.BufferItemOutput, st.CapacityBufferItem, st.CapacityTransItem);
				}).left().row();
				table.labelWrap(() -> {
					return ST.UI("modular_buffer_state_liquid") + ": " + DisplayBuffer(st.BufferLiquidInput, st.BufferLiquidOutput, st.CapacityBufferLiquid, st.CapacityTransLiquid);
				}).left().row();
				table.labelWrap(() -> {
					return ST.UI("modular_buffer_state_unit") + ": " + DisplayBuffer(st.BufferUnitInput, st.BufferUnitOutput, st.CapacityBufferUnit, st.CapacityTransUnit);
				}).left().row();
				//buildings
				table.labelWrap(ST.UI("modular_building_list")).left().row();
				for (var i : st.Building) {
					if (i.block instanceof BlockModular p) {
						table.table(table1 -> {
							table1.setBackground(Styles.black);
							if (i instanceof BlockModular.BlockModularBuilding b) {
								p.RenderBar(b, table1, this);
							}
							table1.button(Icon.settings, () -> {
								Toggle("modular_current", i);
							}).size(42).right();
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
	public String DisplayNumber(float v) {
		return (Math.round(v * 1000) / 1000) + "";
	}
	public String DisplayBuffer(float input, float output, float max, float speed) {
		/**
		 * eg. 80% (350|400|3000) +0.5/s
		 */
		var p = Math.max(input / max, output / max);
		var color = p < 0.2 ? "[red]" : p < 0.6 ? "[orange]" : "[green]";
		return color + DisplayNumber(p * 100) + "% [gray](" + DisplayNumber(input) + "|" + DisplayNumber(output) + "|" + (DisplayNumber(max)) + ") +" + DisplayNumber(speed) + "/s)[white]";
	}
	@Override
	public Dialog show() {
		var t = super.show();
		Toggle("modular_current");
		return t;
	}
}
