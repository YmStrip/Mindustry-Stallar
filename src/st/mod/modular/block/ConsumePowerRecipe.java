package st.mod.modular.block;

import mindustry.gen.Building;
import mindustry.world.consumers.ConsumePower;

public class ConsumePowerRecipe extends ConsumePower {
	public ConsumePowerRecipe(float usage, float capacity, boolean buffered) {
		super(usage, capacity, buffered);
	}
	public ConsumePowerRecipe(float usage) {
		this(usage, usage * 10, false);
	}
	@Override
	public float requestedPower(Building entity) {
		if (entity instanceof BlockModularFactory.BlockModularFactoryBuilding b) {
			if (b.Recipe != null && b.Producing) return b.Recipe.InputPower;
			if (!b.Producing) return 0f;
		}
		return super.requestedPower(entity);
	}
}
