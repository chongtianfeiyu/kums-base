package com.kurui.kums.base.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.kurui.kums.base.Constant;

/**
 * 字符串处理工具类
 * 
 * @author yanrui
 */
public class StringUtil {

	public static void main(String[] args) {

//		String rights = "sb01-sb11,sf03";
//		rights = getRightStrByGroup(rights);

//		System.out.println(rights);
		
		String str="type11,22,33";
//		str=null;
		System.out.println(move(str,"type"));
	}
	
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str) || "null".equals(str);
	}

	public static String[] getRightArrayByGroup(String rights) {
		rights = getRightStrByGroup(rights);
		return getSplitString(rights, ",");
	}

	public static String getRightStrByGroup(String rights) {
		String result = "";
		// "sb01-sb11,sf03"
		String[] rightsArray = getSplitString(rights, ",");
		for (int i = 0; i < rightsArray.length; i++) {
			String temp = rightsArray[i];
			// sb01-sb11
			// System.out.println(temp);

			int spiltIndex = temp.indexOf("-");
			// System.out.println("spiltIndex:" + spiltIndex);
			if (spiltIndex > 0) {
				String base = temp.substring(0, 2);
				// System.out.println("base:" + base);
				String beginNoStr = temp.substring(3, spiltIndex);
				// System.out.println("beginNoStr:" + beginNoStr);
				String endNoStr = temp.substring(spiltIndex + 1 + 2, temp
						.length());
				// System.out.println("endNoStr:" + endNoStr);

				int[] tempNo = StringUtil.getUnitNoByBeginString(beginNoStr,
						endNoStr, 1);

				for (int j = 0; j < tempNo.length; j++) {
					String right = "";
					if (tempNo[j] < 10) {
						right = base + "0" + tempNo[j];
					} else {
						right = base + tempNo[j];
					}
					result += right + ",";
				}
			} else {
				result += temp + ",";
			}
		}
		if (result.indexOf(",", result.length() - 1) > 1) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String removeChiness(String content) {
		String text = "";
		content = Constant.toString(content);
		text = content.replaceAll("[^u4e00-u9fa5]*", "");
		return text;
	}

	/**
	 * 连接字符串
	 * 
	 * @param String
	 *            fullString 原始字符串
	 * @param String
	 *            cellString 需要拼接的字符串
	 * @param Sting
	 *            appString 连接字符
	 * 
	 */
	public static String appendString(String fullString, String cellStrng,
			String appString) {
		if ("".equals(fullString)) {
			fullString = cellStrng + appString;
		} else {
			fullString = fullString + cellStrng + appString;
		}
		// System.out.println("fullString=" + fullString);
		return fullString;
	}

	/**
	 * 将字符串按分隔符转成字符数组
	 * 
	 * @支持的分隔符: , / @ #
	 * @param String
	 *            strSrc
	 * @param String
	 *            splitStr
	 * @return String[]
	 */
	public static String[] getSplitString(String strSrc, String splitStr) {
		String splitString[] = strSrc.split(splitStr);
		return splitString;
	}

	public static String getBetweenString(String content, String beginStr,
			String endStr) {
		String text = "";
		if (Constant.toString(content) != "") {
			int beginIndex = content.indexOf(beginStr);
			int endIndex = content.indexOf(endStr);
			text = content.substring(beginIndex + beginStr.length(), endIndex);
		}
		return text;
	}

	public static String removeAppointStr(String oldStr, String pointStr) {
		if (oldStr != null && pointStr != null) {
			oldStr = oldStr.replaceAll(pointStr, "");
		}
		return oldStr;
	}

	public static String move(String str, char c, int count) {
		int m = 0;
		int mycount = 0;
		int tempLength = str.split(",").length;
		count %= tempLength;
		for (int i = 0; i < count; i++) {
			m = str.indexOf(c);
			if (m >= 0) {
				str = str.substring(m + 1);
				mycount++;
			}
			if (mycount > count)
				return str;
		}

		return str;
	}
	
	public static String move(String str, String removeStr) {
		if(str!=null){
			String[] strArray=str.split(",");
			int strArrayLength = strArray.length;
			String[] result=new String[strArrayLength];
			int j=0;
			for (int i = 0; i < strArrayLength; i++) {
				String tempStr=strArray[i];
				int m = tempStr.indexOf(removeStr);
				if (m >= 0) {
				//		
				}else{
					result[j] = tempStr;
				}
				j++;
			}
			
			str=getStringByArray(result, ",");
			
			return str;
		}else{
			return "";
		}		
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isLetter(String str) {
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean containsExistString(String orderNo,
			String stringStore, String splitStr) {
		boolean flag = false;
		String[] stringArray = getSplitString(stringStore, splitStr);

		if (stringArray != null) {
			for (int i = 0; i < stringArray.length; i++) {
				String temStr = stringArray[i];
				if (orderNo.equals(temStr)) {
					return true;
				}
			}
		}

		return flag;
	}

	public static boolean containsExistString(String orderNo, String stringStore) {
		if (orderNo == null) {
			return false;
		}
		if (stringStore == null) {
			return false;
		}

		int flag = stringStore.indexOf(orderNo);

		if (flag >= 0) { // 大于0 则表示存在 为-1 则表示不存在
			return true;
		} else {
			return false;
		}
	}

	public static String getStringByArray(String[] array, String splitStr) {
		StringBuffer content = new StringBuffer();
		if (array != null) {
			for (int j = 0; j < array.length; j++) {
				String tempStr=array[j];
				if(tempStr!=null){
					content.append(tempStr);
					if (j < array.length - 1) {
						content.append(splitStr);
					}
				}
				
			}
		}
		return content.toString();
	}

	public static String getStringByArray(long[] array, String splitStr) {
		StringBuffer content = new StringBuffer();
		if (array != null) {
			for (int j = 0; j < array.length; j++) {
				content.append(array[j]);
				if (j < array.length - 1) {
					content.append(splitStr);
				}
			}
		}
		return content.toString();
	}

	public static String getStringByArray(int[] array, String splitStr) {
		StringBuffer content = new StringBuffer();
		if (array != null) {
			for (int j = 0; j < array.length; j++) {
				content.append(array[j]);
				if (j < array.length - 1) {
					content.append(splitStr);
				}
			}
		}
		return content.toString();
	}

	
	public static String transStr(int[] str) {
		if (str.length == 0)
			return "";
		String temp = "(";
		for (int i = 0; i < str.length; i++) {
			if (str.length - 1 == i)
				temp = temp + str[i];
			else
				temp = temp + str[i] + ",";
		}
		return temp + ")";
	}
	
	/**
	 * 读取文本文件的内容
	 * 
	 * @param:String url
	 * @return String
	 */
	public static String readStrFromTxt(String url) {
		String str = "";
		try {
			char data[] = new char[6000];
			FileReader reader = new FileReader(url);

			int num = reader.read(data);
			System.out.println("num is--" + num);

			str = new String(data, 0, num);

			System.out.println("读取的字符是----");
			System.out.println(str);
		} catch (Exception e) {
			System.out.println("读取文件失败，原因是----------");
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 将字符串按分隔符转成Vector @ 支持 |
	 * @param String
	 *            strSrc
	 * @param String
	 *            strKen
	 * @return Vector<String>
	 */
	public static Vector<String> getVectorString(String strSrc, String strKen) {
		StringTokenizer toKen = new StringTokenizer(strSrc, strKen);
		Vector<String> vec = new Vector<String>();
		int i = 0;
		while (toKen.hasMoreElements()) {
			String value = (String) toKen.nextElement();
			if (value.equals(""))
				value = "&nbsp;";
			vec.add(i++, value);
		}
		for (int j = 0; j < vec.size(); j++) {
			System.out.println(j + "---" + vec.get(j));
		}
		return vec;
	}

	/**
	 * 将字符串按分隔符放进Set
	 * 
	 * @param String
	 *            strSrc
	 * @param String
	 *            strKen
	 * @return Vector<String>
	 */
	public static Set<String> getSetString(String strSrc, String strKen) {
		StringTokenizer toKen = new StringTokenizer(strSrc, strKen);
		Set<String> set = new HashSet<String>();
		int i = 0;
		while (toKen.hasMoreElements()) {
			String value = (String) toKen.nextElement();
			if (value.equals(""))
				value = "&nbsp;";
			set.add(value);
		}
		// for (Iterator iterator = set.iterator(); iterator.hasNext();) {
		// String name = (String) iterator.next();
		// System.out.println(name);
		// }
		return set;
	}

	/**
	 * 删除字符数组中指定的元素
	 * 
	 * @param String[]
	 *            array
	 * @param String
	 *            para 需要删除的元素
	 */
	public static String[] delArrayCellByStr(String[] array, String para) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && array[i].equals(para)) {
				array[i] = "";
			}
		}
		return array;
	}

	/**
	 * @param int
	 *            type:1,sampleTxt字符串 2,sampleTxt路径
	 * @return BufferedReader
	 */
	public static BufferedReader getBufferReaderByInput(int type,
			String sampleTxt) {
		BufferedReader br = null;

		try {
			if (type == 1) {
				ByteArrayInputStream byteIn = new ByteArrayInputStream(
						sampleTxt.getBytes());
				br = new BufferedReader(new InputStreamReader(byteIn));
			} else if (type == 2) {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(sampleTxt)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return br;
	}

	/**
	 * 去除多余空格
	 */
	public static String[] removeSpilthSpace(String[] infoArray, int newArrayLen) {
		if (newArrayLen == 0) {
			return new String[0];
		}
		String[] newInfoArray = new String[newArrayLen];
		int j = 0;
		for (int i = 0; i < infoArray.length; i++) {
			String info = infoArray[i];
			if (info != null && info.length() > 0) {
				newInfoArray[j] = info;
				j++;
			}
		}
		return newInfoArray;
	}

	public static int getIntByString(String str) {
		int a = 0;

		try {
			a = Integer.parseInt(str.trim());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return a;
	}

	public static int[] getUnitNoByBeginString(String beginNoStr,
			String endNoStr, int increment) {
		int beginNo = StringUtil.getIntByString(beginNoStr);
		int endNo = StringUtil.getIntByString(endNoStr);

		int[] a = getUnitNoByBegin(beginNo, endNo, increment);

		return a;
	}

	public static int[] getUnitNoByBegin(int beginNo, int endNo, int increment) {
		int[] a = new int[1];

		int count = (endNo - beginNo) + 1;

		if (count > 0) {
			a = new int[count];

			for (int i = 0; i < count; i++) {
				a[i] = beginNo;
				beginNo = beginNo + increment;
			}

		} else {
			a[0] = beginNo;
		}
		return a;
	}

	public static String replace(String str, String substr, String restr) {
		String tmp[] = split(str, substr);
		String returnstr = null;
		if (tmp.length != 0) {
			returnstr = tmp[0];
			for (int i = 0; i < tmp.length - 1; i++)
				returnstr = (new StringBuilder(String
						.valueOf(dealNull(returnstr)))).append(restr).append(
						tmp[i + 1]).toString();

		}
		return dealNull(returnstr);
	}

	public static String rTrim(String str) {
		String rstr = str;
		if (str == null) {
			rstr = "";
		} else {
			if (str.length() == 0)
				rstr = "";
			if (str.length() > 0) {
				for (int i = str.length(); i > 0; i--) {
					if (!str.substring(i - 1, i).equals(" "))
						break;
					rstr = rstr.substring(0, i - 1);
				}

			}
		}
		return rstr;
	}

	public static String[] split(String source, String div) {
		int arynum = 0;
		int intIdx = 0;
		int intIdex = 0;
		int div_length = div.length();
		if (source.compareTo("") != 0) {
			if (source.indexOf(div) != -1) {
				intIdx = source.indexOf(div);
				int intCount = 1;
				do {
					if (source.indexOf(div, intIdx + div_length) != -1) {
						intIdx = source.indexOf(div, intIdx + div_length);
						arynum = intCount;
					} else {
						arynum += 2;
						break;
					}
					intCount++;
				} while (true);
			} else {
				arynum = 1;
			}
		} else {
			arynum = 0;
		}
		intIdx = 0;
		intIdex = 0;
		String returnStr[] = new String[arynum];
		if (source.compareTo("") != 0) {
			if (source.indexOf(div) != -1) {
				intIdx = source.indexOf(div);
				returnStr[0] = source.substring(0, intIdx);
				int intCount = 1;
				do {
					if (source.indexOf(div, intIdx + div_length) != -1) {
						intIdex = source.indexOf(div, intIdx + div_length);
						returnStr[intCount] = source.substring(intIdx
								+ div_length, intIdex);
						intIdx = source.indexOf(div, intIdx + div_length);
					} else {
						returnStr[intCount] = source.substring(intIdx
								+ div_length, source.length());
						break;
					}
					intCount++;
				} while (true);
			} else {
				returnStr[0] = source.substring(0, source.length());
				return returnStr;
			}
		} else {
			return returnStr;
		}
		return returnStr;
	}

	private static void writeLog(String param) {
		try {
			System.out.println((new StringBuilder()).append(
					new Timestamp(System.currentTimeMillis())).append("  ")
					.append("[StringUtil]").append(" ").append(param)
					.toString());
		} catch (Exception exception) {
		}
	}

	public static boolean ifHasSpace(String str) {
		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) == ' ')
				return true;

		return false;
	}

	public static String transform(String content) {
		String contentnew = content;
		String enter = "<br/>";
		contentnew = contentnew.replaceAll("&", "&amp;");
		contentnew = contentnew.replaceAll("<", "&lt;");
		contentnew = contentnew.replaceAll(" ", "&nbsp;");
		contentnew = contentnew.replaceAll(">", "&gt;");
		Pattern p = Pattern.compile("[\r\n\t]");
		Matcher m = p.matcher(contentnew);
		contentnew = m.replaceAll(enter);
		return contentnew;
	}

	public static String charsetConvert(String str) {
		String strConverted;
		String strCharset;
		String strStatusMsg;
		strConverted = "";
		strCharset = "";
		strStatusMsg = "";
		try {
			Locale locale = Locale.getDefault();
			ResourceBundle myResources = ResourceBundle.getBundle(
					"iwatchconfig", locale);
			strCharset = myResources.getString("charset");
			if (strCharset != null)
				strCharset = strCharset.trim();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		byte bs[];
		try {
			bs = str.getBytes(strCharset);
			strConverted = new String(bs);
			return strConverted;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		System.out.println(strStatusMsg);
		return "";
	}

	public static String convertEncoding(String param, String srcEncoder,
			String desEncoder) {
		String strRet = "";
		strRet = dealNull(param);
		try {
			strRet = new String(strRet.getBytes(srcEncoder), desEncoder);
		} catch (Exception e) {
			System.out.println((new StringBuilder("Exception While Convert:"))
					.append(e.toString()).toString());
		}
		return strRet;
	}

	public static String FormatStr(String str, int len, String c) {
		int clen = str.length();
		for (int i = 0; i < len - clen; i++)
			str = (new StringBuilder(String.valueOf(str))).append(c).toString();

		return str;
	}

	public static String[] getMaxLength(String str[][]) {
		int len = str[0].length;
		int k = 0;
		for (int i = 0; i < str.length; i++)
			if (str[i].length > len) {
				len = str[i].length;
				k = i;
			}

		return str[k];
	}

	public static String doubleChar(char c, int n) {
		String temp = "";
		for (int i = 0; i < n; i++)
			temp = (new StringBuilder(String.valueOf(temp))).append(c)
					.toString();

		return temp;
	}

	public static int dealEmpty(String s) {
		s = dealNull(s);
		if (s.equals(""))
			return 0;
		else
			return Integer.parseInt(s);
	}

	public static Object dealNull(Object obj) {
		Object returnstr = null;
		if (obj == null)
			returnstr = "";
		else
			returnstr = obj;
		return returnstr;
	}

	public static String dealNull(String str) {
		String returnstr = null;
		if (str == null)
			returnstr = "";
		else
			returnstr = str;
		return returnstr;
	}

	public static boolean isInclude(String code1, String code2) {
		String code[] = code2.split("[,/\\\\]");
		String num[] = new String[2];
		int min = -1;
		int max = -1;
		for (int i = 0; i < code.length; i++)
			if (code[i].indexOf("-") > 0) {
				num = code[i].split("-");
				try {
					min = Integer.parseInt(num[0]);
					max = Integer.parseInt(num[1]);
				} catch (Exception ex) {
					min = -1;
					max = -1;
				}
				for (int j = min; j <= max; j++)
					if (code1.equals((new StringBuilder(String.valueOf(j)))
							.toString()))
						return true;

			} else if (code1.equals(code[i]))
				return true;

		return false;
	}

	public static boolean isIncludeLineNo(String code1, String code2) {
		String code[] = code2.split("[,/\\\\]");
		for (int i = 0; i < code.length; i++)
			if (code1.equals(code[i]))
				return true;

		return false;
	}

	public static String replaceString(String sourceStr, String oldStr,
			String newStr) {
		return sourceStr;
	}

	public static String parseSingleQuoteInSQLString(String sqlstr) {
		if (sqlstr == null)
			return "";
		if (sqlstr.trim().equals(""))
			return "";
		String str = sqlstr.trim();
		int startpos = 0;
		for (int pos = 0; (pos = str.indexOf("'", startpos)) != -1;) {
			str = (new StringBuilder(String.valueOf(str.substring(0, pos))))
					.append("'").append(str.substring(pos)).toString();
			startpos = pos + 2;
		}

		return str;
	}

	public static String parsevirgule(String sqlstr) {
		if (sqlstr == null)
			return "";
		if (sqlstr.trim().equals(""))
			return "";
		String str = sqlstr.trim();
		int startpos = 0;
		for (int pos = 0; (pos = str.indexOf("\\", startpos)) != -1;) {
			str = (new StringBuilder(String.valueOf(str.substring(0, pos))))
					.append("*").append(str.substring(pos + 1)).toString();
			startpos = pos + 1;
		}

		return str;
	}

	// RoleListForm.setSelectedRightItems(String[] selectedRightItems)调用
	public static String convert(String array[]) {
		String str = "";
		for (int i = 0; i < array.length; i++)
			if (i == array.length - 1)
				str = (new StringBuilder(String.valueOf(str))).append(array[i])
						.toString();
			else
				str = (new StringBuilder(String.valueOf(str))).append(array[i])
						.append(",").toString();

		return str;
	}

	public static String convert(int array[]) {
		String str = "";
		for (int i = 0; i < array.length; i++)
			if (i == array.length - 1)
				str = (new StringBuilder(String.valueOf(str))).append(array[i])
						.toString();
			else
				str = (new StringBuilder(String.valueOf(str))).append(array[i])
						.append(",").toString();

		return str;
	}

	public static String trim(String str) {
		Pattern p = Pattern.compile("[|#,;/]");
		if (str.length() >= 1) {
			Matcher m = p.matcher(str.substring(0, 1));
			if (m.matches())
				str = str.substring(1);
			int length = str.length();
			m = p.matcher(str.substring(length - 1));
			if (m.matches())
				str = str.substring(0, length - 1);
		}
		return str;
	}

	public static String[] getLowestCommonMultiple(String arrData[]) {
		if (arrData.length == 0)
			return new String[0];
		String temp = "";
		String tempArray[] = new String[0];
		for (int i = 0; i < arrData.length; i++) {
			tempArray = arrData[i].split("[/,\\\\]");
			for (int j = 0; j < tempArray.length; j++)
				if (!tempArray[j].trim().equals("")
						&& !isInclude(tempArray[j].trim(), temp))
					temp = (new StringBuilder(String.valueOf(temp)))
							.append(",").append(tempArray[j].trim()).toString();

		}

		System.out.print(trim(temp));
		return trim(temp).split("[,]");
	}

	// com.kurui.kums.StringTool 未破解
	public static String mk_date(String c_date, int c_mode) {
		String wk_buf = "";
		// wk_buf = " ";
		// if (c_date.indexOf("1900") >= 0)
		// return "";
		// if (c_date.length() < 10)
		// return "";
		// c_mode;
		// JVM INSTR tableswitch 1 6: default 257
		// // 1 68
		// // 2 79
		// // 3 99
		// // 4 153
		// // 5 207
		// // 6 229;
		// goto _L1 _L2 _L3 _L4 _L5 _L6 _L7
		// _L1:
		// break; /* Loop/switch isn't completed */
		// _L2:
		// wk_buf = c_date.substring(0, 10);
		// break; /* Loop/switch isn't completed */
		// _L3:
		// wk_buf = c_date.substring(0, 16);
		// wk_buf = wk_buf.replaceAll("/", "-");
		// break; /* Loop/switch isn't completed */
		// _L4:
		// wk_buf = (new StringBuilder(String.valueOf(c_date.substring(5,
		// 7)))).append("-").append(c_date.substring(8,
		// 10)).append("-").append(c_date.substring(0, 4)).toString();
		// break; /* Loop/switch isn't completed */
		// _L5:
		// wk_buf = (new StringBuilder(String.valueOf(c_date.substring(5,
		// 7)))).append("/").append(c_date.substring(8,
		// 10)).append("/").append(c_date.substring(0, 4)).toString();
		// break; /* Loop/switch isn't completed */
		// _L6:
		// if (c_date.length() < 19)
		// return c_date;
		// wk_buf = c_date.substring(0, 19);
		// break; /* Loop/switch isn't completed */
		// _L7:
		// if (c_date.length() < 19)
		// return c_date;
		// try
		// {
		// wk_buf = c_date.substring(0, 19);
		// wk_buf = replace(wk_buf, "-", "/");
		// }
		// catch (Exception exception) { }
		return wk_buf;
	}

}