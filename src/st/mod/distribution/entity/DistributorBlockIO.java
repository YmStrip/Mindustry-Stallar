package st.mod.distribution.entity;

import java.util.ArrayList;

public class DistributorBlockIO {
	public static float SoftNorm(float norm) {
		return (0.5f - Math.abs(0.8f * (norm - 0.5f)));
	}
	//recalibrate cycle
	public float RecalibrateCycle = 120;
	public float RecalibrateCycleIter = 0;
	//
	public float WeightTotal = 0;
	public int Offset = 0;
	public ArrayList<Float> Weight = new ArrayList<>();
	public int Next(int max) {
		if (Weight.size() > max) {
			for (var i = Weight.size() - 1; i >= max; i--) {
				var value = Weight.get(i);
				WeightTotal -= value;
				Weight.remove(i);
			}
		}
		if (Weight.size() < max) {
			for (var i = Weight.size(); i < max; i++) {
				Weight.add(0f);
			}
		}
		Offset = (1 + Offset) % max;
		return Offset;
	}
	@FunctionalInterface
	public interface UpdateWeight {
		float Update(float weight);
	}
	public float Update(float weightDelta) {
		RecalibrateCycleIter++;
		if (RecalibrateCycleIter >= RecalibrateCycle) {
			RecalibrateCycleIter = 0;
			WeightTotal = 0;
			for (var i : Weight) WeightTotal += i;
		}
		var valueCurrent = Weight.get(Offset);
		var valueChanged = Math.min(Math.max(valueCurrent + weightDelta, 0), 1);
		WeightTotal += valueChanged - valueCurrent;
		Weight.set(Offset, valueChanged);
		if (WeightTotal == 0) return 0f;
		/*String a = "[";
		for (var i : weight) {
			a += "," + i;
		}
		System.out.println("Total " + weightTotal + " , is " + a + "]");*/
		return valueChanged / WeightTotal;
	}
	public float Update(UpdateWeight weight) {
		return Update(weight.Update(this.Weight.get(Offset)));
	}
}
