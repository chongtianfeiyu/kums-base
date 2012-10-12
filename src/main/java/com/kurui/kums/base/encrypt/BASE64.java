package com.kurui.kums.base.encrypt;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import org.apache.commons.codec.binary.Base64;

public class BASE64 {

	public BASE64() {
	}

	public static String encrypt(String src) {
		String encodeStr = new String(Base64.encodeBase64(src.getBytes()));
		return encodeStr;
	}

	public static String encrypt(String src, String charSet) {
		String encodeStr;
		try {
			encodeStr = new String(Base64.encodeBase64(src.getBytes(charSet)),
					charSet);
			return encodeStr;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String dencrypt(String decodeStr) {
		String str = "";
		try {
			decodeStr = decodeStr.replaceAll("[ ]", "+");
			byte data[] = Base64.decodeBase64(decodeStr.getBytes());
			str = new String(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String dencrypt(String decodeStr, String charSet) {
		String str = "";
		try {
			decodeStr = decodeStr.replaceAll("[ ]", "+");
			byte data[] = Base64.decodeBase64(decodeStr.getBytes(charSet));
			str = new String(data, charSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static void main(String args[]) {
		testBase64();
	}

	public static void testBase64() {
		BigInteger x = new BigInteger("4B306C05", 16);
		System.out.println(x);
		System.out.println("dsds edsds dsds ".trim());
		try {
			System.out.println((new StringBuilder("------------")).append(
					encrypt("276628@qq.com", "UTF-8")).toString());
			System.out
					.println((new StringBuilder("------------"))
							.append(
									dencrypt(
											"L2FnZW50L2FnZW50LmRvP3RoaXNBY3Rpb249Y2hhbmdlUGFzc3dvcmRRdWVzdGlvbiZtZDU9MjhkODYxM2JjYTg1MmU5NTFhMDczZThmMTc3YzM5MGEmZW1haWw9Mjc2NjI4QHFxLmNvbSZ0eXBlPXBhc3N3b3JkJm5vPTU0NDY3MjgzNDYxMTgmbWRubz0wNWNlMTc4YjNmNzg1Njc4ZmY0NzAyYzY3MmM2YWE2OQ==",
											"UTF-8")).toString());
		} catch (Exception exception) {
		}
	}
}
