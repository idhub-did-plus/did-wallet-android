package com.idhub.wallet.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UploadIDHubInfoEntity {
    @Id(autoincrement = true)
    private Long id;
    private String firstName = "";
    private String lastName = "";
    private String birthday ="";
    private String country = "";
    private String residenceCountry ="";
    private String idcardNumber = "";
    private String passportNumber ="";
    private String taxNumber ="";
    private String ssnNumber ="";
    private String address ="";
    private String phone ="";
    private String phoneDialingCode ="";
    private String email ="";
    private String residenceCountryIsoCode ="";
    private String countryIsoCode ="";
    @Generated(hash = 367612103)
    public UploadIDHubInfoEntity(Long id, String firstName, String lastName,
            String birthday, String country, String residenceCountry,
            String idcardNumber, String passportNumber, String taxNumber,
            String ssnNumber, String address, String phone, String phoneDialingCode,
            String email, String residenceCountryIsoCode, String countryIsoCode) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.country = country;
        this.residenceCountry = residenceCountry;
        this.idcardNumber = idcardNumber;
        this.passportNumber = passportNumber;
        this.taxNumber = taxNumber;
        this.ssnNumber = ssnNumber;
        this.address = address;
        this.phone = phone;
        this.phoneDialingCode = phoneDialingCode;
        this.email = email;
        this.residenceCountryIsoCode = residenceCountryIsoCode;
        this.countryIsoCode = countryIsoCode;
    }
    @Generated(hash = 1575712821)
    public UploadIDHubInfoEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getResidenceCountry() {
        return this.residenceCountry;
    }
    public void setResidenceCountry(String residenceCountry) {
        this.residenceCountry = residenceCountry;
    }
    public String getIdcardNumber() {
        return this.idcardNumber;
    }
    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber;
    }
    public String getPassportNumber() {
        return this.passportNumber;
    }
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
    public String getTaxNumber() {
        return this.taxNumber;
    }
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }
    public String getSsnNumber() {
        return this.ssnNumber;
    }
    public void setSsnNumber(String ssnNumber) {
        this.ssnNumber = ssnNumber;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneDialingCode() {
        return this.phoneDialingCode;
    }
    public void setPhoneDialingCode(String phoneDialingCode) {
        this.phoneDialingCode = phoneDialingCode;
    }
    public String getResidenceCountryIsoCode() {
        return this.residenceCountryIsoCode;
    }
    public void setResidenceCountryIsoCode(String residenceCountryIsoCode) {
        this.residenceCountryIsoCode = residenceCountryIsoCode;
    }
    public String getCountryIsoCode() {
        return this.countryIsoCode;
    }
    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

}
