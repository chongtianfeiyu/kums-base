package com.kurui.kums.base.util;

public class ArrayUtil {

	public ArrayUtil() {
	}

	public static boolean isInclude(String array1[], String array2[]) {
		boolean f = false;
		for (int i = 0; i < array2.length; i++) {
			f = false;
			for (int j = 0; j < array1.length; j++)
				if (array2[i].equals(array1[j]))
					f = true;

			if (!f)
				return false;
		}

		return true;
	}

	public static boolean isInclude(String str, String array[]) {
		for (int j = 0; j < array.length; j++)
			if (array[j].equals(str))
				return true;

		return false;
	}

	public static String[] getDiffArray(String str[], String subStr[]) {
		String temp[];
		temp = new String[0];
		String ss = "";
		boolean b = true;
		int k = 0;
		temp = new String[str.length - subStr.length];
		for (int i = 0; i < str.length; i++) {
			b = true;
			for (int j = 0; j < subStr.length; j++)
				if (str[i].equals(subStr[j]))
					b = false;

			if (b)
				temp[k++] = str[i];
		}

		return temp;
	}

	public static String getStrOfArray(String array[]) {
		if (array.length == 0)
			return "";
		String str = "('";
		for (int i = 0; i < array.length; i++)
			if (i == array.length - 1)
				str = (new StringBuilder(String.valueOf(str))).append(array[i])
						.append("')").toString();
			else
				str = (new StringBuilder(String.valueOf(str))).append(array[i])
						.append("','").toString();

		return str;
	}

	public static String getSqlStrOfArray(String array[]) {
		if (array.length == 0)
			return "";
		String str = "('";
		for (int i = 0; i < array.length; i++)
			if (i == array.length - 1)
				str = (new StringBuilder(String.valueOf(str))).append(array[i])
						.append("')").toString();
			else
				str = (new StringBuilder(String.valueOf(str))).append(array[i])
						.append("','").toString();

		return str;
	}

	public static String getSqlStrOfArray(int array[]) {
		if (array.length == 0)
			return "";
		String str = "(";
		for (int i = 0; i < array.length; i++)
			if (i == array.length - 1)
				str = (new StringBuilder(String.valueOf(str))).append(array[i])
						.append(")").toString();
			else
				str = (new StringBuilder(String.valueOf(str))).append(array[i])
						.append(",").toString();

		return str;
	}

	public static int[] trimZeroInOfEnd(int array[]) {
		int temp[] = array;
		if (array.length == 0)
			return array;
		while (array[array.length - 1] == 0) {
			temp = new int[array.length - 1];
			for (int i = 0; i < array.length - 1; i++)
				temp[i] = array[i];

			array = temp;
			if (array.length == 1 && array[0] == 0)
				return new int[0];
			if (array.length == 0)
				return new int[0];
		}
		return temp;
	}

	public static int[] removeAt(int array[], int index) {
		if (array.length == 0 || index < 0 || index >= array.length)
			return array;
		int temp[] = new int[array.length - 1];
		int count = 0;
		for (int i = 0; i < array.length; i++) {
			if (i == index)
				;
			temp[count++] = array[i];
		}

		return temp;
	}
	

	public static void main(String arg[]) {
		String s1[] = { "1", "2", "3", "gffgfgfg" };
		String s2[] = { "1", "2", "3" };
		String temp[] = getDiffArray(s1, s2);
		for (int i = 0; i < temp.length; i++)
			System.out.println((new StringBuilder("ss=")).append(temp[i])
					.toString());

	}
}
