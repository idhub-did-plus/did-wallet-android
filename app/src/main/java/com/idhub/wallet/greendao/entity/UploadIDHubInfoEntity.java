package com.idhub.wallet.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UploadIDHubInfoEntity {
    @Id(autoincrement = true)
    private Long id;
    private String firstName = "";
    private String middleName = "";
    private String lastName = "";
    private String gender = "";
    private String birthday ="";
    private String country = "";
    private String residenceCountry ="";
    private String idcardNumber = "";
    private String passportNumber ="";
    private String taxNumber ="";
    private String ssnNumber ="";
    private String street ="";
    private String addressCountry ="";
    private String addressCountryCode ="";
    private String postalCode ="";
    private String neighborhood ="";
    private String city ="";
    private String state ="";


    private String phone ="";
    private String phoneDialingCode ="";
    private String email ="";
    private String residenceCountryIsoCode ="";
    private String countryIsoCode ="";

    private boolean highAssets =false;
    private boolean highIncome =false;


    @Generated(hash = 859305568)
    public UploadIDHubInfoEntity(Long id, String firstName, String middleName,
            String lastName, String gender, String birthday, String country,
            String residenceCountry, String idcardNumber, String passportNumber,
            String taxNumber, String ssnNumber, String street,
            String addressCountry, String addressCountryCode, String postalCode,
            String neighborhood, String city, String state, String phone,
            String phoneDialingCode, String email, String residenceCountryIsoCode,
            String countryIsoCode, boolean highAssets, boolean highIncome) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.country = country;
        this.residenceCountry = residenceCountry;
        this.idcardNumber = idcardNumber;
        this.passportNumber = passportNumber;
        this.taxNumber = taxNumber;
        this.ssnNumber = ssnNumber;
        this.street = street;
        this.addressCountry = addressCountry;
        this.addressCountryCode = addressCountryCode;
        this.postalCode = postalCode;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.phone = phone;
        this.phoneDialingCode = phoneDialingCode;
        this.email = email;
        this.residenceCountryIsoCode = residenceCountryIsoCode;
        this.countryIsoCode = countryIsoCode;
        this.highAssets = highAssets;
        this.highIncome = highIncome;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddressCountryCode() {
        return addressCountryCode;
    }

    public void setAddressCountryCode(String addressCountryCode) {
        this.addressCountryCode = addressCountryCode;
    }
    public boolean getHighAssets() {
        return this.highAssets;
    }
    public void setHighAssets(boolean highAssets) {
        this.highAssets = highAssets;
    }
    public boolean getHighIncome() {
        return this.highIncome;
    }
    public void setHighIncome(boolean highIncome) {
        this.highIncome = highIncome;
    }
}
