package com.kurui.kums.base.license.server;

import java.io.File;
import java.util.Random;
import java.util.prefs.Preferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kurui.kums.base.file.FileUtil;
import com.kurui.kums.base.license.LicenseBo;
import com.kurui.kums.base.license.server.example.MyLicenseManager;
import com.kurui.kums.base.util.DateUtil;

import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

public class LicenseServerLogic  {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		LicenseBo license=new LicenseBo();
		license=createLicense(license);
		
	}

	public static LicenseBo createLicense(LicenseBo license) {
		LicenseContent content = new LicenseContent();
		content.setConsumerType("User");// 不能改
		content.setConsumerAmount(1);
		content.setSubject(SUBJECT);

		java.util.Calendar cal = java.util.Calendar.getInstance();
		content.setIssued(cal.getTime());// 发布时间

		content.setNotAfter(license.getNotafter());// 截止有效期

		String info = "<root>";
		info += "<licenseId></licenseId>";
		info += "<corporationId>" + license.getCompanyNo()
				+ "</corporationId>";
		info += "<corporationName>" + license.getCompanyName()
				+ "</corporationName>";
		info += "<license-type>" + license.getLicenseType() + "</license-type>";

		info += "<macaddress>" + license.getMacaddress() + "</macaddress>";
		info += "<description>" + license.getDescription() + "</description>";
		info += "<staffNumber>" + license.getStaffNumber() + "</staffNumber>";
		info += "</root>";
		content.setInfo(info);

		LicenseParam parameter = getNormalLicenseParam();
		String licenseFileName = createLicenseKey(parameter, content);// 创建License

		license.setLicenseFileName(licenseFileName);
		
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

	private static String createLicenseKey(LicenseParam parameter,
			LicenseContent content) {
		String result = "";
		LicenseManager manager = new LicenseManager(parameter);
		try {
			String fileName = "license";
			fileName += DateUtil.getDateString("yyyyMMddHHmmss");
			fileName += new Random().nextInt(10000);
			fileName += ".lic";

			String filePath = KeyStoreUtil.getCertPath()+File.separator+"license";
			FileUtil.createFolder(filePath);
			filePath+=File.separator+ fileName;
			manager.store(content, new File(filePath));

			result = fileName;
		} catch (Exception exc) {
			System.err.println("Could not save license key");
			exc.printStackTrace();
			result = "FAILED";
		}
		return result;
	}



	


}
