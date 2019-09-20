package com.idhub.magic.common.ustorage.entity;

import java.util.Date;

import com.idhub.magic.common.ustorage.entity.component.Address;
import com.idhub.magic.common.ustorage.entity.component.Name;

public class IdentityInfo {
/*	填入基本身份信息，包括
	姓名，姓/Last Name 名/First Name，必填
	生日，生日/Date of Birth （月）/ （日）/ （年），必填
	国籍，国籍/ Nationality，必填
	居住国，居住国/ Country of Residence，必填
	身份证件号码，身份证件号码/ID Number
	美国可选，若填写需上传身份证件照片（图片或PDF）
	中国必填，需上传身份证件照片（图片或PDF）
	护照号码，护照号码/Passport Number，可选
	若填写需上传护照照片（图片或PDF）*/
	
	Name name;
	Address address;
	Date birthday;
	String country;
	String residenceCountry;
	
	String idcardNumber;
	String idcardImage;
	String passportNumber;
	String passportImage;
	String phoneNumber;
	String gender;

	public Date getBirthday() {
		return birthday;
	}
	public String getCountry() {
		return country;
	}
	
	public String getIdcardImage() {
		return idcardImage;
	}
	public String getIdcardNumber() {
		return idcardNumber;
	}
	
	public String getPassportImage() {
		return passportImage;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public String getResidenceCountry() {
		return residenceCountry;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setIdcardImage(String idcardImage) {
		this.idcardImage = idcardImage;
	}
	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}
	
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public void setPassportImage(String passportImage) {
		this.passportImage = passportImage;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public void setResidenceCountry(String residenceCountry) {
		this.residenceCountry = residenceCountry;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
