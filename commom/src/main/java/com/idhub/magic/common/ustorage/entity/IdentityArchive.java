package com.idhub.magic.common.ustorage.entity;

import java.util.Map;

public class IdentityArchive {

	IdentityInfo identityInfo;
	BasicInfo basicInfo;
	FinancialProfile financialProfile;
	Map<String,Object> extension;
	public BasicInfo getBasicInfo() {
		return basicInfo;
	}
	public FinancialProfile getFinancialProfile() {
		return financialProfile;
	}
	public IdentityInfo getIdentityInfo() {
		return identityInfo;
	}
	public void setBasicInfo(BasicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}
	public void setFinancialProfile(FinancialProfile financialProfile) {
		this.financialProfile = financialProfile;
	}
	public void setIdentityInfo(IdentityInfo identityInfo) {
		this.identityInfo = identityInfo;
	}
}
