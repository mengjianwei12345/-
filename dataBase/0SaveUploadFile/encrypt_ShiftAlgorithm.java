

public class ShiftAlgorithm {
	private static final int EXP = 5;

	// º”√‹
	public static String encrypt(String str) {
		StringBuffer buf = new StringBuffer();
		char[] cs = str.toCharArray();
		char letter;
		for (int i = 0; i < cs.length; i++) {
			int baseCharNum = (int) cs[i];
			int delta = EXP - (126 - baseCharNum);
			if (delta > 0) {
				letter = (char) ((32 - 1) + delta);
			} else {
				letter = (char) ((int) cs[i] + EXP);
			}
			buf.append(letter);
		}
		return buf.toString();
	}

	// Ω‚√‹
	public static String decrypt(String str) {
		StringBuffer buf = new StringBuffer();
		char[] cs = str.toCharArray();
		char letter;
		for (int i = 0; i < cs.length; i++) {
			int baseCharNum = (int) cs[i];
			int delta = baseCharNum - (32 + EXP);
			if (delta < 0) {
				letter = (char) ((126 + 1) + delta);
			} else {
				letter = (char) ((int) cs[i] - EXP);
			}
			buf.append(letter);
		}
		return buf.toString();
	}
}
