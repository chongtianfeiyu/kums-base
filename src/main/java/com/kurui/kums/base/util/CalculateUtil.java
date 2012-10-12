package com.kurui.kums.base.util;

import java.text.DecimalFormat;

public class CalculateUtil {
	public static String multiplyDouble2(String mult1, String mult2,
			int fraction, boolean format) {
		String returnResult = "00.00";
		String strPatent = "#,###.00";
		if (mult1.compareTo("") == 0 || mult2.compareTo("") == 0)
			return "00.00";
		if (!format)
			strPatent = "####.00";
		try {
			Double multDouble1 = new Double(mult1);
			Double multDouble2 = new Double(mult2);
			double multValue = multDouble1.doubleValue()
					* multDouble2.doubleValue();
			DecimalFormat df = new DecimalFormat(strPatent);
			df.setDecimalSeparatorAlwaysShown(true);
			df.setMaximumFractionDigits(fraction);
			returnResult = df.format(multValue);
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}
		return returnResult;
	}
}
