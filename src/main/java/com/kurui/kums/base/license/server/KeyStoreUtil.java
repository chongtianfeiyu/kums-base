package com.kurui.kums.base.license.server;

import java.io.File;
import java.util.Random;

import com.kurui.kums.base.file.FileUtil;
import com.kurui.kums.base.util.CmdUtil;
import com.kurui.kums.base.util.DateUtil;
import com.kurui.kums.base.util.StringUtil;

/**
 * @author yanrui
 * @version 1.0
 */
public class KeyStoreUtil {
	//
	// public static String PRIVATE_PATH = "D:\\cert";
	// public static String PUBLIC_PATH = "D:\\cert";

	public static String PRIVATE_STORE_PASS = "	privatestore123";
	public static String PUBLIC_STORE_PASS = "publicstore123";
	public static String PRIVATE_KEY_PASS = "privatekey123";
	public static String D_NAME = "CN=china-rewards CA, OU=CA Center, O=china-rewards corporation, L=SH, ST=GD, C=CN ";

	public static String PRIVATE_KEY_STORE_PATH = "";
	public static String PUBLIC_CERT_STORE_PATH = "";
	public static String PUBLIC_KEY_STORE_PATH = "";

	public static void main(String[] args) {
		getCertPath();

		 createPrivateKeyStore();
		 exportPublicKeyCert();
		 createPublicKeyStore();

		System.out.println("========1=======:" + PRIVATE_KEY_STORE_PATH);
		System.out.println("========2=======:" + PUBLIC_CERT_STORE_PATH);
		System.out.println("========3=======:" + PUBLIC_KEY_STORE_PATH);

		// System.exit(0);
	}

	/**
	 * 创建私钥库
	 * */
	private static String createPrivateKeyStore() {
		// keytool -genkey -alias privatekey -keystore privateKeys.store

		String fileName = DateUtil.getDateString("yyyyMMddHHmmss");
		fileName += new Random().nextInt(1000);
		fileName += ".store";

		String filePath = getCertPath() + File.separator + "privateKeys";
		FileUtil.createFolder(filePath);
				filePath+= File.separator + fileName;

		String cmd = " keytool -genkey -alias privatekey -keystore " + filePath;
		cmd += " -storepass " + PRIVATE_STORE_PASS;
		cmd += " -keypass " + PRIVATE_KEY_PASS;
		cmd += " -dname \"" + D_NAME + "\"";

		// System.out.println(cmd);
		String result = CmdUtil.exec(cmd);
		System.out.println("private key store result:" + result);

		PRIVATE_KEY_STORE_PATH = filePath;
		return fileName;
	}

	/**
	 * 导出私匙库内的公匙
	 * */
	private static String exportPublicKeyCert() {
		// keytool -export -alias privatekey -file certfile.cer -keystore
		// privateKeys.store

		String fileName = DateUtil.getDateString("yyyyMMddHHmmss");
		fileName += new Random().nextInt(1000);
		fileName += ".cer";

		String filePath = getCertPath()  +File.separator+"publicCerts";
		FileUtil.createFolder(filePath);
		filePath+= File.separator + fileName;

		String cmd = " keytool -export -alias privatekey -file " + filePath
				+ " -keystore " + PRIVATE_KEY_STORE_PATH;
		cmd += " -storepass  " + PRIVATE_STORE_PASS;
		String result = CmdUtil.exec(cmd);
		System.out.println("EXPORT PUBLIC CERT RESULT:" + result);

		PUBLIC_CERT_STORE_PATH = filePath;
		return fileName;
	}

	/**
	 * 生成公钥库
	 * */
	private static String createPublicKeyStore() {
		// keytool -import -alias publiccert -file certfile.cer -keystore
		// publicCerts.store

		String fileName = DateUtil.getDateString("yyyyMMddHHmmss");
		fileName += new Random().nextInt(1000);
		fileName += ".store";

		String filePath = getCertPath() +File.separator+"publicCerts";
		filePath+= File.separator + fileName;

		String cmd = " cmd   /c echo Y | keytool -import -alias publiccert -file "
				+ PUBLIC_CERT_STORE_PATH + " -keystore " + filePath;
		cmd += " -storepass  " + PUBLIC_STORE_PASS;

		String result = CmdUtil.exec(cmd);

		System.out.println(result);

		PUBLIC_KEY_STORE_PATH = filePath;

		System.out.println("====================end---------------");

		return fileName;
	}

	public static String getCertPath() {
		String realPath = "";
		realPath = KeyStoreUtil.class.getResource("").getPath();

		System.out.println(realPath);

		if (!StringUtil.isEmpty(realPath)) {
			String flagstr = "WEB-INF";
			int rootIndex = realPath.indexOf(flagstr);
			
			if (rootIndex < 0) {
				flagstr = "kums-base";
				rootIndex = realPath.indexOf(flagstr);
			}
			
			if (rootIndex < 0) {
				return null;
			} else {
				realPath = realPath.substring(0, rootIndex + flagstr.length());
			}

			int firstIndex = realPath.indexOf("/");
			if (firstIndex == 0) {
				realPath = realPath.substring(1, realPath.length());
			}
			
			realPath=realPath.replaceFirst("file:/","");
			
			realPath = realPath + File.separator + "cert" + File.separator;
		}

		FileUtil.createFolder(realPath);

		System.out.println(realPath);
		return realPath;
	}

}
