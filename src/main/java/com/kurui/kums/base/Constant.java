package com.kurui.kums.base;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Constant {
	private static String localHost = "";
	public static String SERVLET_PATH = "";
	public static String ROOT_CONTEXT_PATH = "";
	public static String SERVLET_MAIN_PATH = "";
	public static String SERVLET_XML_PATH = "";
	public static String SERVLET_CHART_PATH = "";

	public static String WEB_INFO_PATH = "";
	public static String CONNECTION_STRING_NAME = "";
	public static String PROJECT_UPLOAD_PATH = "";
	public static String PROJECT_LOG_PATH = "";

	public static ArrayList<String> url = null;

	public void setUrl(ArrayList<String> url) {
		Constant.url = url;
	}

	public static int getRandomNum() {
		double temp = Math.random();
		return (int) (temp * 100D);
	}

	public static String getLocalHost() {
		return localHost;
	}

	public void setLocalHost(String localHost) {
		Constant.localHost = localHost;
	}

	public static void main(String[] args) {
		// String str = "ca n 00000";
		// str = clearSpace(str);
		// System.out.println(toUpperCase(str, new Long(3)));

		// System.out.println(toBigDecimalByPercent("-3.3%"));
		// System.out.println(toPercentByBigDecimal(BigDecimal.valueOf(0.4433)));
		// System.out.println(toPercentByBigDecimal(BigDecimal.valueOf(0.0433)));
		// System.out.println(toPercentByBigDecimal(BigDecimal.valueOf(1.00)));
		// System.out.println(toPercentByBigDecimal(BigDecimal.valueOf(0.5)));
		// System.out.println(MessageStore.getContent("NOORDER"));
	}

	public static String clearSpace(String paramString) {
		if (paramString != null) {
			paramString = paramString.replaceAll("\\s*", "");
		}
		return paramString;
	}

	public static String toUpperCase(String paramString, Long length) {
		String localString = "";
		try {
			if (paramString != null) {
				localString = clearSpace(paramString);
				localString = localString.trim().toUpperCase();

				if (localString.length() > length) {
					localString = localString.substring(0, length.intValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localString;
	}

	public static String toUpperCase(String paramString) {
		String localString = "";
		try {
			if (paramString != null) {
				localString = paramString.trim().toUpperCase();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localString;
	}

	public static String toLowerCase(String paramString) {
		String localString = "";
		try {
			if (paramString != null) {
				localString = paramString.trim().toLowerCase();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localString;
	}

	public static int toInt(String paramString) {
		int i = 0;
		try {
			i = Integer.parseInt(paramString);
		} catch (Exception e) {
			e.printStackTrace();
			i = 0;
		}
		return i;
	}

	public static Integer toInteger(String paramString) {
		Integer localInteger;
		try {
			localInteger = new Integer(paramString);
		} catch (Exception e) {
			e.printStackTrace();
			localInteger = new Integer(0);
		}
		return localInteger;
	}

	public static Long toLong(String paramString) {
		Long localLong;
		try {
			localLong = new Long(paramString);
		} catch (Exception e) {
			e.printStackTrace();
			localLong = new Long(0L);
		}
		return localLong;
	}

	public static Long toLong(Long paramString) {
		Long localLong;
		try {
			if (paramString != null) {
				localLong = new Long(paramString);
			} else {
				localLong = new Long(0L);
			}
		} catch (Exception e) {
			e.printStackTrace();
			localLong = new Long(0L);
		}
		return localLong;
	}

	public static float toFloat(String paramString) {
		float f = 0.0F;
		try {
			f = Float.parseFloat(paramString);
		} catch (Exception e) {
			e.printStackTrace();
			f = 0.0F;
		}
		return f;
	}

	public static BigDecimal toBigDecimal(BigDecimal paramString) {
		BigDecimal localBigDecimal;
		;
		try {
			if (paramString != null) {
				localBigDecimal = paramString;
			} else {
				localBigDecimal = BigDecimal.ZERO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			localBigDecimal = BigDecimal.ZERO;
		}
		return localBigDecimal;
	}

	public static BigDecimal toBigDecimal(String paramString) {
		BigDecimal localBigDecimal;
		try {
			if (paramString != null) {
				paramString = paramString.trim();
				localBigDecimal = new BigDecimal(paramString);
			} else {
				localBigDecimal = BigDecimal.ZERO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			localBigDecimal = BigDecimal.ZERO;
		}
		return localBigDecimal;
	}

	public static String toDouble(String mult1, int fraction, boolean separator) {
		String returnResult = "00.00";
		if (mult1 == null || mult1.trim().equals("")
				|| Double.parseDouble(mult1) == 0.0D)
			return "";
		try {
			Double multDouble1 = new Double(mult1);
			double multValue = multDouble1.doubleValue();
			DecimalFormat df = new DecimalFormat("#,###.00");
			df.setDecimalSeparatorAlwaysShown(separator);
			df.setMaximumFractionDigits(fraction);
			returnResult = df.format(multValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnResult;
	}

	public static String toDouble(String mult1, int fraction, int intDot,
			boolean separator) {
		String returnResult = "00.00";
		if (mult1 == null || mult1.trim().equals("")
				|| Double.parseDouble(mult1) == 0.0D)
			return "";
		try {
			Double multDouble1 = new Double(mult1);
			double multValue = multDouble1.doubleValue();
			String strDot = "#,###.";
			if (intDot == 0)
				strDot = "#,###";
			for (int i = 0; i < intDot; i++)
				strDot = (new StringBuilder(String.valueOf(strDot)))
						.append("0").toString();

			DecimalFormat df = new DecimalFormat(strDot);
			df.setDecimalSeparatorAlwaysShown(separator);
			df.setMaximumFractionDigits(fraction);
			returnResult = df.format(multValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnResult;
	}

	public static BigDecimal toBigDecimalByPercent(String percentStr) {
		BigDecimal result = BigDecimal.ZERO;
		if (percentStr != null) {
			percentStr = percentStr.replace("%", "");
		}
		result = toBigDecimal(percentStr);
		result = result.divide(BigDecimal.valueOf(100)).stripTrailingZeros();
		return result;
	}

	public static String toPercentByBigDecimal(BigDecimal percentBigDec) {
		String result = "";

		percentBigDec = toBigDecimal(percentBigDec);
		if (percentBigDec.compareTo(BigDecimal.ONE) == 0
				|| percentBigDec.compareTo(BigDecimal.ONE) > 0) {
			percentBigDec = percentBigDec.multiply(BigDecimal.valueOf(100));
		} else {
			percentBigDec = percentBigDec.multiply(BigDecimal.valueOf(100));
			// .stripTrailingZeros()
			// System.out.println(percentBigDec);
		}

		result = percentBigDec + "%";

		return result;
	}

	public static String toString(String paramString) {
		if (paramString == null) {
			return "";
		} else {
			return paramString.trim();
		}
	}

	public boolean stringToBoolean(String strBoolean) {
		if (strBoolean.equals("1") || strBoolean.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	public String booleanToString(boolean blnInt) {
		if (blnInt) {
			return "1";
		} else {
			return "0";
		}
	}
}