package st.mod.modular.entity;


public abstract class RecipeValue {
	private static String FormatProductPerMin(float value, float craftTime) {
		return " [gray](" + ((Math.round(3600 / craftTime * 100 * value) / 100) + "/min") + ")[white]";
	}
	public abstract String Description(float craftTime);
	public abstract float Value();
	public static class RecipeValueStatic extends RecipeValue {
		public float Value = 0;
		public RecipeValueStatic(float value) {
			Value = value;
		}
		@Override
		public String Description(float craftTime) {
			return Value + RecipeValue.FormatProductPerMin(Value, craftTime);
		}
		@Override
		public float Value() {
			return Value;
		}
	}

	public static class RecipeValueRandom extends RecipeValue {
		public float Min = 0;
		public float Max = 0;
		public float Chance = 1;
		/**
		 * if chance check fail , return default
		 */
		public float Default = 0;
		@Override
		public String Description(float craftTime) {
			var str = new StringBuilder();
			if (this.Min == this.Max) {
				str.append(this.Max).append(RecipeValue.FormatProductPerMin(Max, craftTime));
			} else {
				str.append(Min).append(RecipeValue.FormatProductPerMin(Min, craftTime));
				str.append("~");
				str.append(Max).append(RecipeValue.FormatProductPerMin(Max, craftTime));
			}
			if (Chance != 1) {
				str.append(" ").append(Math.round(Chance * 1000) / 10).append("%");
			}
			return str.toString();
		}
		@Override
		public float Value() {
			if (Chance == 1) {
				if (Min == Max) return Min;
				return (float) (Math.random() * (Max - Min) + Min);
			}
			if (Math.random() <= Chance) {
				if (Min == Max) return Min;
				return (float) (Math.random() * (Max - Min) + Min);
			}
			return Default;
		}
	}
}
