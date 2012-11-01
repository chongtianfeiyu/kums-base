package com.kurui.kums.base.license.client;

import java.sql.Timestamp;
import java.util.prefs.Preferences;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kurui.kums.base.file.XmlUtil_dom4j;
import com.kurui.kums.base.license.LicenseBo;
import com.kurui.kums.base.license.client.example.LicenseClientUtil;
import com.kurui.kums.base.util.DateUtil;
import com.kurui.kums.base.util.MachineUtil;
import com.kurui.kums.base.util.StringUtil;

import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;

public class LicenseClientLogic  {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		queryLicenseContent();
	}
	/**
	 * 获取客户端授权证书信息
	 */

	public static LicenseBo queryLicenseContent() {
		LicenseBo licenseBo=new  LicenseBo();
		LicenseContent content = null;
		try {
			String licensePath = LicenseClientUtil.getCertPath();

			final String PRIVATEKEY_SUBJECT = "privatekey"; //
			final String PUBSTORE_SUBJECT = "publiccert"; //

			// /F:/project/elt/core/target/classes/com/chinarewards/elt/service/license/
			final String KEYSTORE_RESOURCE = "publicCerts"; //

			// F:/project/elt/core/target/classes/
			// final String KEYSTORE_RESOURCE = "/publicCerts.store"; //

			//
			final String KEYSTORE_STORE_PWD = "publicstore123"; // CUSTOMIZE
			final String CIPHER_KEY_PWD = "a8a8a8"; //

			LicenseManager manager = new LicenseManager(
					new DefaultLicenseParam(PRIVATEKEY_SUBJECT,
							Preferences
									.userNodeForPackage(LicenseClientUtil.class),
							new DefaultKeyStoreParam(
									LicenseClientUtil.class, // CUSTOMIZE
									KEYSTORE_RESOURCE, PUBSTORE_SUBJECT,
									KEYSTORE_STORE_PWD, null),// 这里一定要是null
							new DefaultCipherParam(CIPHER_KEY_PWD)));

			manager.install(new java.io.File(licensePath + "license.lic"));
//			manager.install(new java.io.File(licensePath + "license201203301001193147.lic"));
			
			content = manager.verify();

			String subject = content.getSubject();
			System.out.println("subject========" + subject);
			System.out.println(content.getInfo() + "--"
					+ content.getConsumerType() + "--" + content.getIssued()
					+ "--" + content.getNotAfter()+"=="+content.getIssuer().getName());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			content = null;
			
//			String msg=e.getCause().getMessage();
//			if("License Certificate has expired!".equals(msg)){
//				licenseBo=new LicenseBo();
//				licenseBo.setErrorCode("FAILED");
//				licenseBo.setErrorInfo("授权过期");
//				return licenseBo;
//			}
			
		}

		licenseBo=adapter(content);
		
		return licenseBo;
	}
	
	public static LicenseBo adapter(LicenseContent content){
		LicenseBo licenseBo=new LicenseBo();
		
		if (content!=null) {			
			licenseBo.setIssued(new Timestamp(content.getIssued().getTime()));
			licenseBo.setNotafter(new Timestamp(content.getNotAfter().getTime()));
			
			
			String contentInfo=content.getInfo();
			if (!StringUtil.isEmpty(contentInfo)) {
				Document doc=XmlUtil_dom4j.readResult(new StringBuffer(contentInfo));
				if(doc!=null){
					String licenseId=XmlUtil_dom4j.getTextByNode(doc,"//root/licenseId");
					String corporationId=XmlUtil_dom4j.getTextByNode(doc,"//root/corporationId");
					String corporationName=XmlUtil_dom4j.getTextByNode(doc,"//root/corporationName");		
					String licenseType=XmlUtil_dom4j.getTextByNode(doc,"//root/license-type");
					String macaddress=XmlUtil_dom4j.getTextByNode(doc,"//root/macaddress");
					String description=XmlUtil_dom4j.getTextByNode(doc,"//root/description");
					String staffNumber=XmlUtil_dom4j.getTextByNode(doc,"//root/staffNumber");							
					
					licenseBo.setLicenseId(licenseId);
					licenseBo.setCompanyNo(corporationId);
					licenseBo.setCompanyName(corporationName);
					licenseBo.setLicenseType(licenseType);
					licenseBo.setMacaddress(macaddress);
					licenseBo.setDescription(description);
					if (!StringUtil.isEmpty(staffNumber)) {
						int staffNumberValue=Integer.valueOf(staffNumber);
						licenseBo.setStaffNumber(staffNumberValue);
					}
					
				}
			}
		}
		
	   String localMACAddress=	MachineUtil.getMACAddress();
	   licenseBo.setLocalMACAddress(localMACAddress);
	   
//	   if (!StringUtil.isEmptyString(licenseBo.getMacaddress())) {
//		
//		   if (localMACAddress.equals(licenseBo.getMacaddress())==false) {
//			
//		}
//	   }
	   
		licenseBo.setErrorCode("SUCESS");
		return licenseBo;
	}


}
