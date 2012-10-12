package com.kurui.kums.base.encrypt;

import java.security.MessageDigest;
import java.util.Random;

public class EncryptUtil {
	private static String keyCode = "YNn8K1JaXWIURiHjOtSs5GoZVrBb3MdEkeTgPc6FpLmDl27AhQq94Cf0";

	public static void main(String[] args) {
		System.out.println(Encrypt("123456"));
		System.out.println(Decrypt("T"));
		System.out.println(Decrypt("S# !&'$"));
		
//		System.out.println((new StringBuilder("the sune365=")).append(
//				enPassword("254543")).toString());
//		System.out.println((new StringBuilder("the 1234=")).append(
//				enPassword("12b34")).toString());
//		System.out.println((new StringBuilder("the 1255=")).append(
//				enPassword("111111")).toString());
//		System.out.println((new StringBuilder("the 1255=")).append(
//				ExchangePassword("111111", "MD5")).toString());
	}

	public static String Encrypt(String code) {
		if (code == null)
			return null;
		if (code.trim().equals(""))
			return "";
		Random random = new Random();
		int M;
		for (M = 0; M < 3; M = random.nextInt(keyCode.length()))
			;
		byte codeByte[] = code.getBytes();
		for (int i = 0; i < codeByte.length; i++)
			codeByte[i] = (byte) (codeByte[i] ^ (byte) M);

		String manual = new String(codeByte);
		manual = (new StringBuilder(String.valueOf(keyCode.substring(M, M + 1))))
				.append(manual).toString();
		return manual;
	}

	public static String Decrypt(String code) {
		if (code == null)
			return null;
		if (code.trim().equals(""))
			return "";
		String s = code.substring(0, 1);
		code = code.substring(1);
		int M = keyCode.indexOf(s);
		byte manualByte[] = code.getBytes();
		for (int i = 0; i < manualByte.length; i++)
			manualByte[i] = (byte) (manualByte[i] ^ (byte) M);

		String raw = new String(manualByte);
		return raw;
	}
	
	public static String enPassword(String src) {
		int len = src.length();
		int iTemp = 0;
		int iCode = 0;
		for (int i = 0; i < len; i++) {
			char ch = src.charAt(i);
			iTemp = ch + 12 ^ 0x4d2;
			iTemp = (iTemp << 4) + 12;
			iCode += iTemp;
		}

		return Integer.toString(iCode);
	}

	public static String exchangePassword(String src) {
		return ExchangePassword(src, "MD5");
	}

	public static int getRandom(int min, int max) {
		return min + (int) (Math.random() * (double) (max - min));
	}

	public static String ExchangePassword(String password, String algorithm) {
		byte unencodedPassword[] = password.getBytes();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			System.err.println((new StringBuilder("Exception: ")).append(e).toString());
			return password;
		}
		md.reset();
		md.update(unencodedPassword);
		byte encodedPassword[] = md.digest();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 16)
				buf.append("0");
			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}
}
