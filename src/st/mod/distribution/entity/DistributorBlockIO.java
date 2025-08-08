package st.mod.distribution.entity;

import java.util.ArrayList;

public class DistributorBlockIO {
	public static float softNorm(float norm) {
		return (0.5f - Math.abs(0.8f * (norm - 0.5f)));
	}
	//recalibrate cycle
	public float recalibrateCycle = 120;
	public float recalibrateCycleIter = 0;
	//
	public float weightTotal = 0;
	public int offset = 0;
	public ArrayList<Float> weight = new ArrayList<>();
	public int next(int max) {
		if (weight.size() > max) {
			for (var i = weight.size() - 1; i >= max; i--) {
				var value = weight.get(i);
				weightTotal -= value;
				weight.remove(i);
			}
		}
		if (weight.size() < max) {
			for (var i = weight.size(); i < max; i++) {
				weight.add(0f);
			}
		}
		offset = (1 + offset) % max;
		return offset;
	}
	@FunctionalInterface
	public interface updateWeight {
		float update(float weight);
	}
	public float update(float weightDelta) {
		recalibrateCycleIter++;
		if (recalibrateCycleIter >= recalibrateCycle) {
			recalibrateCycleIter = 0;
			weightTotal = 0;
			for (var i : weight) weightTotal += i;
		}
		var valueCurrent = weight.get(offset);
		var valueChanged = Math.min(Math.max(valueCurrent + weightDelta, 0), 1);
		weightTotal += valueChanged - valueCurrent;
		weight.set(offset, valueChanged);
		if (weightTotal == 0) return 0f;
		/*String a = "[";
		for (var i : weight) {
			a += "," + i;
		}
		System.out.println("Total " + weightTotal + " , is " + a + "]");*/
		return valueChanged / weightTotal;
	}
	public float update(updateWeight weight) {
		return update(weight.update(this.weight.get(offset)));
	}
}
