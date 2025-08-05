package st.mod.unit.entity;

import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;

public class SUnitType extends UnitType {
	public SUnitType(String name) {
		super(name);
		this.constructor = UnitEntity::create;
	}
}