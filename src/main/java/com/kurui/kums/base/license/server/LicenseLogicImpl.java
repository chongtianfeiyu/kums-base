package com.kurui.kums.base.license.server;

import java.io.File;
import java.util.Random;
import java.util.prefs.Preferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kurui.kums.base.util.DateUtil;
import com.kurui.kums.base.util.MachineUtil;
import com.kurui.kums.base.util.StringUtil;

import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

public class LicenseLogicImpl  {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());


	public License createLicense(License license) {
		LicenseParam parameter = getNormalLicenseParam();

		LicenseContent content = new LicenseContent();
		content.setConsumerType("User");// 不能改
		content.setConsumerAmount(1);
		content.setSubject(SUBJECT);

		java.util.Calendar cal = java.util.Calendar.getInstance();
		content.setIssued(cal.getTime());// 发布时间

		content.setNotAfter(license.getNotafter());// 截止有效期

		String info = "<root>";
		info += "<licenseId></licenseId>";
		info += "<corporationId>" + license.getCorporationId()
				+ "</corporationId>";
		info += "<corporationName>" + license.getCorporationName()
				+ "</corporationName>";
		info += "<license-type>" + license.getLicenseType() + "</license-type>";

		info += "<macaddress>" + license.getMacaddress() + "</macaddress>";
		info += "<description>" + license.getDescription() + "</description>";
		info += "<staffNumber>" + license.getStaffNumber() + "</staffNumber>";
		info += "</root>";
		content.setInfo(info);

		String licenseFileName = createLicenseKey(parameter, content);// 创建License

		license.setLicenseFileName(licenseFileName);
		
//		licenseDao.update(license);
		return license;
	}

	private static LicenseParam getNormalLicenseParam() {
		LicenseParam parameter = new DefaultLicenseParam(SUBJECT,
				Preferences.userRoot(), new DefaultKeyStoreParam(
						MyLicenseManager.class, // CUSTOMIZE
						KEYSTORE_RESOURCE, SUBJECT, KEYSTORE_STORE_PWD,
						KEYSTORE_KEY_PWD), new DefaultCipherParam(
						CIPHER_KEY_PWD));
		return parameter;
	}

	private static final String SUBJECT = "privatekey"; // CUSTOMIZE
	private static final String KEYSTORE_RESOURCE = "privateKeys.store"; // 私匙库文件名
	private static final String KEYSTORE_STORE_PWD = "privatestore123"; // 私匙库密码
	private static final String KEYSTORE_KEY_PWD = "privatekey123"; // 私匙库主键密码
	private static final String CIPHER_KEY_PWD = "a8a8a8"; // 即将生成的license密码

	private String createLicenseKey(LicenseParam parameter,
			LicenseContent content) {
		String result = "";
		LicenseManager manager = new LicenseManager(parameter);
		try {
			String fileName = "license";
			fileName += DateUtil.getDateString("yyyyMMddHHmmss");
			fileName += new Random().nextInt(10000);
			fileName += ".lic";

			String filePath = getLicenseStorePath() + fileName;
			manager.store(content, new File(filePath));

			result = fileName;
		} catch (Exception exc) {
			System.err.println("Could not save license key");
			exc.printStackTrace();
			result = "FAILED";
		}
		return result;
	}

	public static String getLicenseStorePath() {
		String realPath = "";
		realPath = MyLicenseManager.class.getResource("").getPath();

		System.out.println(realPath);

		if (!StringUtil.isEmpty(realPath)) {
			int rootIndex = realPath.indexOf("jboss-5.1.0.GA");
			if (rootIndex <0) {
				rootIndex = realPath.indexOf("elt/war");
				if (rootIndex>-1) {
					rootIndex+=4;
				}
			}
			if (rootIndex <0) {
				rootIndex = realPath.indexOf("war/war");
//				if (rootIndex>-1) {
//					rootIndex+=4;
//				}
			}
			if (rootIndex <0) {
				rootIndex = realPath.indexOf("elt/core");
				if (rootIndex>-1) {
					rootIndex+=4;
				}
			}

			if (rootIndex < 0) {
				return null;
			} else {
				realPath = realPath.substring(0, rootIndex);
			}

			int firstIndex = realPath.indexOf("/");
			if (firstIndex == 0) {
				if (MachineUtil.getIsWindowsOS()) {
					realPath = realPath.substring(1, realPath.length());
				}				
			}

			realPath=realPath.replace("file:/", "");

			realPath = realPath + "licenseStore" + File.separator;
		}

		System.out.println(realPath);
		return realPath;
	}

	


}
