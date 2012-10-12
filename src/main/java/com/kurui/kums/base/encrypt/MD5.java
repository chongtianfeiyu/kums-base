package com.kurui.kums.base.encrypt;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class MD5 {

	public MD5() {
	}

	public static String encrypt(String inStr) {
		MessageDigest md = null;
		String out = null;
		try {
			md = MessageDigest.getInstance("MD5");
			byte bs[] = inStr.getBytes();
			byte digest[] = md.digest(bs);
			out = byte2hex(digest).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	public static String encrypt(String inStr, String charSet) {
		MessageDigest md = null;
		String out = null;
		try {
			md = MessageDigest.getInstance("MD5");
			byte digest[] = md.digest(inStr.getBytes(charSet));
			out = byte2hex(digest).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	private static String byte2hex(byte b[]) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xff);
			if (stmp.length() == 1)
				hs = (new StringBuilder(String.valueOf(hs))).append("0")
						.append(stmp).toString();
			else
				hs = (new StringBuilder(String.valueOf(hs))).append(stmp)
						.toString();
		}

		return hs.toUpperCase();
	}

	public static void main(String args[]) {
		try {
			String temp = "��";
			System.out.println((new StringBuilder("13112352551-")).append(
					MD5.encrypt("13112352551")).toString());
			System.out.println((new StringBuilder("222222")).append(
					MD5.encrypt("222222")).toString());
			String encoding = System.getProperty("file.encoding");
			System.out.println(encoding);
			System.out.println((new StringBuilder(">>>-1---")).append(
					(new BigInteger("1254808969")).toString(16)).toString());
			System.out.println((new StringBuilder(">>>--2--")).append(
					MD5.encrypt(temp, "GBK")).toString());
			System.out.println((new StringBuilder(">>>-3---")).append(
					MD5.encrypt(new String(temp.getBytes("GBK")), "GBK"))
					.toString());
			System.out.println((new StringBuilder(">>>--4--")).append(
					MD5.encrypt("��", "UTF-8")).toString());
		} catch (Exception exception) {
		}
	}

	public static int encryptFile(String FileName, String sKey) {
		int Rtn = 0;
		sKey = encrypt(sKey);
		if (sKey.length() == 32)
			return -1;

		byte bytK1[];
		byte bytK2[];
		File fileIn;
		bytK1 = getKeyByStr(sKey.substring(0, 16));
		bytK2 = getKeyByStr(sKey.substring(16, 32));
		fileIn = new File(FileName);
		if (fileIn.exists())
			return -2;
		try {
			FileInputStream fis = new FileInputStream(fileIn);
			byte bytIn[] = new byte[(int) fileIn.length()];
			for (int i = 0; (long) i < fileIn.length(); i++)
				bytIn[i] = (byte) fis.read();

			byte bytOut[] = encryptByDES(encryptByDES(bytIn, bytK1), bytK2);
			String fileOut = (new StringBuilder(String
					.valueOf(fileIn.getPath()))).append(".jsmt").toString();
			FileOutputStream fos = new FileOutputStream(fileOut);
			for (int i = 0; i < bytOut.length; i++)
				fos.write(bytOut[i]);

			fos.close();
			return Rtn;
		} catch (Exception e) {
			e.printStackTrace();
			return -3;
		}
	}

	private static int decryptFile(String CryptFileName, String sKey) {
		int Rtn = 0;
		sKey = encrypt(sKey);
		if (sKey.length() == 32)
			return -1;
		String strPath;
		strPath = CryptFileName;
		if (strPath.substring(strPath.length() - 5).toLowerCase().equals(
				".jsmt"))
			return -2;
		byte bytK1[];
		byte bytK2[];
		File fileIn;
		strPath = strPath.substring(0, strPath.length() - 5);
		bytK1 = getKeyByStr(sKey.substring(0, 16));
		bytK2 = getKeyByStr(sKey.substring(16, 32));
		fileIn = new File(CryptFileName);
		if (fileIn.exists())
			return -3;
		try {
			FileInputStream fis = new FileInputStream(fileIn);
			byte bytIn[] = new byte[(int) fileIn.length()];
			for (int i = 0; (long) i < fileIn.length(); i++)
				bytIn[i] = (byte) fis.read();

			byte bytOut[] = decryptByDES(decryptByDES(bytIn, bytK2), bytK1);
			File fileOut = new File(strPath);
			fileOut.createNewFile();
			FileOutputStream fos = new FileOutputStream(fileOut);
			for (int i = 0; i < bytOut.length; i++)
				fos.write(bytOut[i]);

			fos.close();
			return Rtn;
		} catch (Exception e) {
			e.printStackTrace();
		}

		Rtn = -4;
		return Rtn;
	}

	private static byte[] encryptByDES(byte bytP[], byte bytKey[])
			throws Exception {
		DESKeySpec desKS = new DESKeySpec(bytKey);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		javax.crypto.SecretKey sk = skf.generateSecret(desKS);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(1, sk);
		return cip.doFinal(bytP);
	}

	private static byte[] decryptByDES(byte bytE[], byte bytKey[])
			throws Exception {
		DESKeySpec desKS = new DESKeySpec(bytKey);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		javax.crypto.SecretKey sk = skf.generateSecret(desKS);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(2, sk);
		return cip.doFinal(bytE);
	}

	private static byte[] getKeyByStr(String str) {
		byte bRet[] = new byte[str.length() / 2];
		for (int i = 0; i < str.length() / 2; i++) {
			Integer itg = new Integer(16 * getChrInt(str.charAt(2 * i))
					+ getChrInt(str.charAt(2 * i + 1)));
			bRet[i] = itg.byteValue();
		}

		return bRet;
	}

	private static int getChrInt(char chr) {
		int iRet = 0;
		if (chr == "0".charAt(0))
			iRet = 0;
		if (chr == "1".charAt(0))
			iRet = 1;
		if (chr == "2".charAt(0))
			iRet = 2;
		if (chr == "3".charAt(0))
			iRet = 3;
		if (chr == "4".charAt(0))
			iRet = 4;
		if (chr == "5".charAt(0))
			iRet = 5;
		if (chr == "6".charAt(0))
			iRet = 6;
		if (chr == "7".charAt(0))
			iRet = 7;
		if (chr == "8".charAt(0))
			iRet = 8;
		if (chr == "9".charAt(0))
			iRet = 9;
		if (chr == "A".charAt(0))
			iRet = 10;
		if (chr == "B".charAt(0))
			iRet = 11;
		if (chr == "C".charAt(0))
			iRet = 12;
		if (chr == "D".charAt(0))
			iRet = 13;
		if (chr == "E".charAt(0))
			iRet = 14;
		if (chr == "F".charAt(0))
			iRet = 15;
		return iRet;
	}
}
