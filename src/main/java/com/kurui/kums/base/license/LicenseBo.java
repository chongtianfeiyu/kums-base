package com.kurui.kums.base.license;

import java.io.Serializable;
import java.sql.Timestamp;

public class LicenseBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String licenseId;
	private String companyNo;// 客户企业ID
	private String companyName;// 企业名称

	private String licenseType;// 授权类型 TRIAL试用 OFFICIAL正式

	private int staffNumber = 0;// 最大员工数

	private String macaddress;// 授权网卡地址
	private Timestamp notafter;// 截止时间

	private Timestamp issued;// 授权时间

	private String description;// 备注说明

	// ================
	private String errorCode;// SUCESS FAILED
	private String errorInfo;// ...
	private String localMACAddress;// 本地MAC

	private String licenseFileName = "";

	public String getLicenseFileName() {
		return licenseFileName;
	}

	public void setLicenseFileName(String licenseFileName) {
		this.licenseFileName = licenseFileName;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getLocalMACAddress() {
		return localMACAddress;
	}

	public void setLocalMACAddress(String localMACAddress) {
		this.localMACAddress = localMACAddress;
	}

	public int getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(int staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

	public Timestamp getIssued() {
		return issued;
	}

	public void setIssued(Timestamp issued) {
		this.issued = issued;
	}

	public Timestamp getNotafter() {
		return notafter;
	}

	public void setNotafter(Timestamp notafter) {
		this.notafter = notafter;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
