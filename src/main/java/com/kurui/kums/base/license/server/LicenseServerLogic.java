package com.kurui.kums.base.license.server;

import java.io.File;
import java.util.Date;
import java.util.Random;
import java.util.prefs.Preferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kurui.kums.base.file.FileUtil;
import com.kurui.kums.base.license.LicenseBo;
import com.kurui.kums.base.license.server.example.MyLicenseManager;
import com.kurui.kums.base.util.DateUtil;
import com.sun.jmx.snmp.Timestamp;

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
		license.setCompanyNo("C223333");
		license.setCompanyName("通用汽车");
		license.setLicenseType("OFFICIAL");
		license.setStaffNumber(10);
		license.setMacaddress("00-50-56-C0-00-08");
		license.setNotafter(DateUtil.getTimestamp("2012-12-1", "yyyy-MM-dd"));
		license.setDescription("hello world");
//		private String companyNo;// 客户企业ID
//		private String companyName;// 企业名称
//		private String licenseType;// 授权类型 TRIAL试用 OFFICIAL正式
//		private int staffNumber = 0;// 最大员工数
//		private String macaddress;// 授权网卡地址
//		private Date notafter;// 截止时间
//		private Date issued;// 授权时间
//		private String description;// 备注说明
		
		license=createLicense(license);
		
	}

	public static LicenseBo createLicense(LicenseBo license) {
		LicenseContent content = new LicenseContent();
		content.setConsumerType("User");// 不能改
		content.setConsumerAmount(1);
		content.setSubject(SUBJECT);

		java.util.Calendar cal = java.util.Calendar.getInstance();
		content.setIssued(cal.getTime());// 发布时间

		String notafterDateStr=DateUtil.getDateString(license.getNotafter(),"yyyy-MM-dd HH:mm:ss");
		Date notafterDate=DateUtil.getDate(notafterDateStr,"yyyy-MM-dd HH:mm:ss");
		content.setNotAfter(notafterDate);// 截止有效期

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
