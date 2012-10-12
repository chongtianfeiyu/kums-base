package com.kurui.kums.base.util;

import java.util.Random;

public class NoUtil {

	public static String transValue(String value, int size) {
		int temp = com.kurui.kums.base.Constant.toInt(value);
		int tempNum = String.valueOf(temp).length();
		value = String.valueOf(temp);
		if (temp == 0)
			value = "1";
		for (int i = 0; i < size - tempNum; i++) {
			value = String.valueOf("0") + value;
		}
		return value;
	}

	private static int getNoOfArray(int[] intNo) {
		for (int i = 0; i < intNo.length; i++) {
			if (intNo[i] != intNo[0] + i)
				return intNo[0] + i;
			else
				return intNo[0] + i + 1;
		}
		return 0;
	}

	private static String getNoOfArray(String[] strNo) {
		try {
			int temp = Integer.parseInt(strNo[0]);
			for (int i = 0; i < strNo.length; i++) {
				if (Integer.parseInt(strNo[i]) != temp + i)
					return Integer.toString(temp + i);
			}
			return Integer.toString(temp + strNo.length);
		} catch (Exception ex) {
			return "00";
		}
	}

	public static String getRandom(int num) {
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < num; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}

		return sRand;
	}

	public static String getCurrentRandomNo() {
		String str = "CR111111";
		str = DateUtil.getDateString("yyyyMMddHHmmss");
//		str += getRandom(5);
		return str;
	}

	public static void main(String arg[]) {
		try {
//			System.out.println(NoUtil.getRandom(10));
			System.out.println(getCurrentRandomNo());

		} catch (Exception ex) {

		}
	}
}