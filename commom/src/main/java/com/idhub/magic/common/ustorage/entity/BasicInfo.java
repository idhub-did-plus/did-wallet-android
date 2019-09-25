package com.idhub.magic.common.ustorage.entity;

public class BasicInfo {
/*	地址，地址/Address，需上传住址证明照片Proof of Address（图片或PDF）
	邮箱，邮箱/eMail
	手机号，手机号/Phone Number
	纳税号，纳税号/TaxID
	社保号，社保号 SSN*/

	String email;
	String taxId;
	String ssn;


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
}
