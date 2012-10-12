package com.kurui.kums.base.encrypt;

public class Encode {
	public static int GB2312 = 0;
	public static int GBK = 1;
	public static int BIG5 = 2;
	public static int UTF8 = 3;
	public static int UNICODE = 4;
	public static int EUC_KR = 5;
	public static int SJIS = 6;
	public static int EUC_JP = 7;
	public static int ASCII = 8;
	public static int UNKNOWN = 9;
	public static int TOTALT = 10;
	public static final int SIMP = 0;
	public static final int TRAD = 1;
	public static String javaname[];
	public static String nicename[];
	public static String htmlname[];

	public Encode() {
		javaname = new String[TOTALT];
		nicename = new String[TOTALT];
		htmlname = new String[TOTALT];
		javaname[GB2312] = "GB2312";
		javaname[GBK] = "GBK";
		javaname[BIG5] = "BIG5";
		javaname[UTF8] = "UTF8";
		javaname[UNICODE] = "Unicode";
		javaname[EUC_KR] = "EUC_KR";
		javaname[SJIS] = "SJIS";
		javaname[EUC_JP] = "EUC_JP";
		javaname[ASCII] = "ASCII";
		javaname[UNKNOWN] = "ISO8859_1";
		htmlname[GB2312] = "GB2312";
		htmlname[GBK] = "GBK";
		htmlname[BIG5] = "BIG5";
		htmlname[UTF8] = "UTF-8";
		htmlname[UNICODE] = "UTF-16";
		htmlname[EUC_KR] = "EUC-KR";
		htmlname[SJIS] = "Shift_JIS";
		htmlname[EUC_JP] = "EUC-JP";
		htmlname[ASCII] = "ASCII";
		htmlname[UNKNOWN] = "ISO8859-1";
		nicename[GB2312] = "GB-2312";
		nicename[GBK] = "GBK";
		nicename[BIG5] = "Big5";
		nicename[UTF8] = "UTF-8";
		nicename[UNICODE] = "Unicode";
		nicename[EUC_KR] = "EUC-KR";
		nicename[SJIS] = "Shift-JIS";
		nicename[EUC_JP] = "EUC-JP";
		nicename[ASCII] = "ASCII";
		nicename[UNKNOWN] = "UNKNOWN";
	}

	public String toEncoding(int type) {
		return (new StringBuilder(String.valueOf(javaname[type]))).append(",")
				.append(nicename[type]).append(",").append(htmlname[type])
				.toString().intern();
	}

}
