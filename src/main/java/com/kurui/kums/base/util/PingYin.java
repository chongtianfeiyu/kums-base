package com.kurui.kums.base.util;

public class PingYin {

	public static final int secPosValueList[] = { 1601, 1637, 1833, 2078, 2274,
			2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,
			4027, 4086, 4390, 4558, 4684, 4925, 5249 };
	static final char firstLetter[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x',
			'y', 'z' };

	public PingYin() {
	}

	public static String getFirstLetter(String oriStr) {
		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		try {
			for (int i = 0; i < str.length(); i++) {
				char ch = str.charAt(i);
				char temp[] = { ch };
				byte uniCode[] = (new String(temp)).getBytes();
				if (uniCode[0] < 128 && uniCode[0] > 0)
					buffer.append(temp);
				else
					buffer.append(convert(uniCode));
			}

		} catch (Exception exception) {
		}
		return buffer.toString().toUpperCase();
	}

	public static String getFirstLetter(String oriStr, String charset) {
		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		try {
			for (int i = 0; i < str.length(); i++) {
				char ch = str.charAt(i);
				char temp[] = { ch };
				byte uniCode[] = (new String(temp)).getBytes(charset);
				if (uniCode[0] < 128 && uniCode[0] > 0)
					buffer.append(temp);
				else
					buffer.append(convert(uniCode));
			}

		} catch (Exception exception) {
		}
		return buffer.toString().toUpperCase();
	}

	public static char convert(byte bytes[]) {
		char result = '-';
		int secPosValue = 0;
		for (int i = 0; i < bytes.length; i++)
			bytes[i] -= 160;

		secPosValue = bytes[0] * 100 + bytes[1];
		for (int i = 0; i < 23; i++) {
			if (secPosValue > 5249) {
				result = 'z';
				break;
			}
			if (secPosValue < secPosValueList[i]
					|| secPosValue >= secPosValueList[i + 1])
				continue;
			result = firstLetter[i];
			break;
		}

		return result;
	}

}
