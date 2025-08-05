package st.id;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.Random;

public class UTIL_ID {
	private static long randCurrentTime = 0;
	private static int randCurrent = 0;
	private static final Random RANDOM = new Random();

	public static String nextkey_(
		String key,
		Predicate<String> get,
		Function<Integer, String> iter
	) {
		int count = 0;
		while (get.test(key)) {
			count++;
			key = iter.apply(count);
		}
		return key;
	}

	public static String nextkey(String key, Predicate<String> get) {
		Function<Integer, String> suffix = number -> {
			if (number < 10) return "000" + number;
			if (number < 100) return "00" + number;
			if (number < 1000) return "0" + number;
			return String.valueOf(number);
		};

		String[] parts = key.split("\\.");
		String last = parts[parts.length - 1];
		int end;
		boolean isNumber;
		try {
			end = Integer.parseInt(last);
			isNumber = true;
		} catch (NumberFormatException e) {
			end = 0;
			isNumber = false;
		}

		boolean finalIsNumber = isNumber;
		int finalEnd = end;
		return nextkey_(
			key,
			get,
			count -> {
				if (parts.length < 2) return key + "." + suffix.apply(count);
				if (finalIsNumber) {
					String prefix = String.join(".", java.util.Arrays.copyOf(parts, parts.length - 1));
					return prefix + "." + suffix.apply(finalEnd + count);
				}
				return key + "." + suffix.apply(count);
			}
		);
	}


	public static String rand() {
		return rand(16, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
	}

	public static String rand(int length, String map) {
		long now = System.currentTimeMillis();
		if (randCurrentTime != now) {
			randCurrentTime = now;
			randCurrent = 0;
		} else {
			randCurrent++;
		}
		String date = Long.toString(now, 36);
		String dateCurrent = Integer.toString(randCurrent, 36) + "_";
		int t = length - dateCurrent.length() - date.length();
		if (t < 0) {
			System.out.println("[random id] at least " + (dateCurrent.length() + date.length()) + " char");
			t = 2;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(date).append(dateCurrent);
		for (int i = 0; i < t; i++) {
			int idx = RANDOM.nextInt(map.length());
			sb.append(map.charAt(idx));
		}
		return sb.toString();
	}
}