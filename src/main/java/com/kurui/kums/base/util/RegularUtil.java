package com.kurui.kums.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用正则
 * 
 */

public class RegularUtil {
	/**
	 * 检查是否手机格式
	 * 
	 * @param mobel
	 * @return
	 */
	public static boolean isMobel(String mobel) {
		String mobelFormate = "(13||15||18)[0-9]{9}";
		Pattern pattern = Pattern.compile(mobelFormate);
		Matcher matcher = pattern.matcher(mobel);
		return matcher.matches();
	}
	
	public boolean isMatch(String str, String r) {
		Pattern p = Pattern.compile(r);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isNumber(String str) {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isMoney(String str) {
		Pattern p = Pattern.compile("[-]?[0-9]+([\\.]{1}[0-9]+)?");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isMTel(String str) {
		Pattern p = Pattern.compile("1[0-9]{2}(\\d){8}");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isMobiles(String str) {
		Pattern p = Pattern.compile("(1[0-9]{2}(\\d){8})(,1[0-9]{2}(\\d){8})*");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isIP(String str)
	{
//		String temp[];
//		int xip;
//		temp = str.split("\\.");
//		xip = 0;
//		if (temp.length != 4)
//			return false;
//		int i = 0;
//		  goto _L1 _L3:
//		xip = Integer.parseInt(temp[i]);
//		if (xip >= 255)
//			return false;
//		i++;
//_L1:
//		if (i < 4) goto _L3; else goto _L2
//_L2:
//		return true;
//		Exception ex;
//		ex;
		return false;
	}

	public static boolean is5Date(String fvcode) {
		if (fvcode.length() != 5) {
			return false;
		} else {
			String p = "[0-3][0-9](JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)";
			Pattern pp = Pattern.compile(p);
			Matcher m = pp.matcher(fvcode);
			return m.matches();
		}
	}

	public static boolean is7Date(String fvcode) {
		if (fvcode.length() == 5 || fvcode.length() == 7) {
			String p = "[0-3][0-9](JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(08|09|10|11)";
			Pattern pp = Pattern.compile(p);
			Matcher m = pp.matcher(fvcode);
			return m.matches();
		} else {
			return false;
		}
	}

	public static boolean is8Date(String fvcode) {
		if (fvcode.length() == 2) {
			String p = "^\\d{2}";
			Pattern pp = Pattern.compile(p);
			Matcher m = pp.matcher(fvcode);
			return m.matches();
		} else {
			return false;
		}
	}

	public static boolean is10Date(String date) {
		String p = "20[0-9]{2}[\\-][0-9]{2}[\\-][0-9]{2}";
		Pattern pp = Pattern.compile(p);
		Matcher m = pp.matcher(date);
		return m.matches();
	}

	public static boolean is4Time(String fourTime) {
		if (fourTime.length() != 4) {
			return false;
		} else {
			String p = "[0-2][0-9][0-5][0-9]";
			Pattern pp = Pattern.compile(p);
			Matcher m = pp.matcher(fourTime);
			return m.matches();
		}
	}

	public static boolean is3Week(String fvcode) {
		if (fvcode.length() != 3) {
			return false;
		} else {
			String p = "(MON|TUE|WED|THU|FRI|SAT|SUN)";
			Pattern pp = Pattern.compile(p);
			Matcher m = pp.matcher(fvcode);
			return m.matches();
		}
	}

	public static boolean isEMail(String email) {
		Pattern p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean is4Digit(String str) {
		Pattern p = Pattern.compile("[0-2]{1}[0-9]{1}[0-6]{1}[0-9]{1}");
		Matcher m = p.matcher(String.valueOf(str));
		return m.matches();
	}

	public static boolean is2LetterDigit(String str) {
		Pattern p = Pattern.compile("[A-Z]{2}[0-9]{4}");
		Matcher m = p.matcher(String.valueOf(str));
		return m.matches();
	}

	public static boolean isOnlyDigit(String str) {
		Pattern p = Pattern.compile("[0-9]{1}\\.");
		Matcher m = p.matcher(String.valueOf(str));
		return m.matches();
	}

	public static boolean isWeekMonth(String fvcode) {
		String p = "(MO|TU|WE|TH|FR|SA|SU)[0-9]{2}(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)";
		Pattern pp = Pattern.compile(p);
		Matcher m = pp.matcher(String.valueOf(fvcode));
		return m.matches();
	}

	public static boolean isDigitStr(String str) {
		Pattern p = Pattern.compile("[0-9]{1,2}\\.[A-Z]{3}[0-9]{3}");
		Matcher m = p.matcher(String.valueOf(str));
		return m.matches();
	}

	public static boolean isOneDigit(String str) {
		Pattern p = Pattern.compile("[0-9]{1,2}\\.{1}.*");
		Matcher m = p.matcher(String.valueOf(str));
		return m.matches();
	}

	public static boolean isTwoDigit(String str) {
		Pattern p = Pattern.compile("[0-9]{1,2}\\.");
		Matcher m = p.matcher(String.valueOf(str));
		return m.matches();
	}

	public static boolean isGBKChar(String info) {
		Pattern s = Pattern.compile("[0-9]{1,2}\\.[һ-�]+");
		Matcher m = s.matcher(info);
		return m.matches();
	}

	public static void main(String args[]) {
		System.out.println(isMoney(null));
	}
}
