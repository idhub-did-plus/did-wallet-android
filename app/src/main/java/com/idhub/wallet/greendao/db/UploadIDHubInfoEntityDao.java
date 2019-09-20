package com.idhub.wallet.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.idhub.wallet.greendao.entity.UploadIDHubInfoEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "UPLOAD_IDHUB_INFO_ENTITY".
*/
public class UploadIDHubInfoEntityDao extends AbstractDao<UploadIDHubInfoEntity, Long> {

    public static final String TABLENAME = "UPLOAD_IDHUB_INFO_ENTITY";

    /**
     * Properties of entity UploadIDHubInfoEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property FirstName = new Property(1, String.class, "firstName", false, "FIRST_NAME");
        public final static Property MiddleName = new Property(2, String.class, "middleName", false, "MIDDLE_NAME");
        public final static Property LastName = new Property(3, String.class, "lastName", false, "LAST_NAME");
        public final static Property Gender = new Property(4, String.class, "gender", false, "GENDER");
        public final static Property Birthday = new Property(5, String.class, "birthday", false, "BIRTHDAY");
        public final static Property Country = new Property(6, String.class, "country", false, "COUNTRY");
        public final static Property ResidenceCountry = new Property(7, String.class, "residenceCountry", false, "RESIDENCE_COUNTRY");
        public final static Property IdcardNumber = new Property(8, String.class, "idcardNumber", false, "IDCARD_NUMBER");
        public final static Property PassportNumber = new Property(9, String.class, "passportNumber", false, "PASSPORT_NUMBER");
        public final static Property TaxNumber = new Property(10, String.class, "taxNumber", false, "TAX_NUMBER");
        public final static Property SsnNumber = new Property(11, String.class, "ssnNumber", false, "SSN_NUMBER");
        public final static Property Street = new Property(12, String.class, "street", false, "STREET");
        public final static Property AddressCountry = new Property(13, String.class, "addressCountry", false, "ADDRESS_COUNTRY");
        public final static Property AddressCountryCode = new Property(14, String.class, "addressCountryCode", false, "ADDRESS_COUNTRY_CODE");
        public final static Property PostalCode = new Property(15, String.class, "postalCode", false, "POSTAL_CODE");
        public final static Property Neighborhood = new Property(16, String.class, "neighborhood", false, "NEIGHBORHOOD");
        public final static Property City = new Property(17, String.class, "city", false, "CITY");
        public final static Property State = new Property(18, String.class, "state", false, "STATE");
        public final static Property Phone = new Property(19, String.class, "phone", false, "PHONE");
        public final static Property PhoneDialingCode = new Property(20, String.class, "phoneDialingCode", false, "PHONE_DIALING_CODE");
        public final static Property Email = new Property(21, String.class, "email", false, "EMAIL");
        public final static Property ResidenceCountryIsoCode = new Property(22, String.class, "residenceCountryIsoCode", false, "RESIDENCE_COUNTRY_ISO_CODE");
        public final static Property CountryIsoCode = new Property(23, String.class, "countryIsoCode", false, "COUNTRY_ISO_CODE");
    }


    public UploadIDHubInfoEntityDao(DaoConfig config) {
        super(config);
    }
    
    public UploadIDHubInfoEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"UPLOAD_IDHUB_INFO_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"FIRST_NAME\" TEXT," + // 1: firstName
                "\"MIDDLE_NAME\" TEXT," + // 2: middleName
                "\"LAST_NAME\" TEXT," + // 3: lastName
                "\"GENDER\" TEXT," + // 4: gender
                "\"BIRTHDAY\" TEXT," + // 5: birthday
                "\"COUNTRY\" TEXT," + // 6: country
                "\"RESIDENCE_COUNTRY\" TEXT," + // 7: residenceCountry
                "\"IDCARD_NUMBER\" TEXT," + // 8: idcardNumber
                "\"PASSPORT_NUMBER\" TEXT," + // 9: passportNumber
                "\"TAX_NUMBER\" TEXT," + // 10: taxNumber
                "\"SSN_NUMBER\" TEXT," + // 11: ssnNumber
                "\"STREET\" TEXT," + // 12: street
                "\"ADDRESS_COUNTRY\" TEXT," + // 13: addressCountry
                "\"ADDRESS_COUNTRY_CODE\" TEXT," + // 14: addressCountryCode
                "\"POSTAL_CODE\" TEXT," + // 15: postalCode
                "\"NEIGHBORHOOD\" TEXT," + // 16: neighborhood
                "\"CITY\" TEXT," + // 17: city
                "\"STATE\" TEXT," + // 18: state
                "\"PHONE\" TEXT," + // 19: phone
                "\"PHONE_DIALING_CODE\" TEXT," + // 20: phoneDialingCode
                "\"EMAIL\" TEXT," + // 21: email
                "\"RESIDENCE_COUNTRY_ISO_CODE\" TEXT," + // 22: residenceCountryIsoCode
                "\"COUNTRY_ISO_CODE\" TEXT);"); // 23: countryIsoCode
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"UPLOAD_IDHUB_INFO_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UploadIDHubInfoEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String firstName = entity.getFirstName();
        if (firstName != null) {
            stmt.bindString(2, firstName);
        }
 
        String middleName = entity.getMiddleName();
        if (middleName != null) {
            stmt.bindString(3, middleName);
        }
 
        String lastName = entity.getLastName();
        if (lastName != null) {
            stmt.bindString(4, lastName);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(5, gender);
        }
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(6, birthday);
        }
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(7, country);
        }
 
        String residenceCountry = entity.getResidenceCountry();
        if (residenceCountry != null) {
            stmt.bindString(8, residenceCountry);
        }
 
        String idcardNumber = entity.getIdcardNumber();
        if (idcardNumber != null) {
            stmt.bindString(9, idcardNumber);
        }
 
        String passportNumber = entity.getPassportNumber();
        if (passportNumber != null) {
            stmt.bindString(10, passportNumber);
        }
 
        String taxNumber = entity.getTaxNumber();
        if (taxNumber != null) {
            stmt.bindString(11, taxNumber);
        }
 
        String ssnNumber = entity.getSsnNumber();
        if (ssnNumber != null) {
            stmt.bindString(12, ssnNumber);
        }
 
        String street = entity.getStreet();
        if (street != null) {
            stmt.bindString(13, street);
        }
 
        String addressCountry = entity.getAddressCountry();
        if (addressCountry != null) {
            stmt.bindString(14, addressCountry);
        }
 
        String addressCountryCode = entity.getAddressCountryCode();
        if (addressCountryCode != null) {
            stmt.bindString(15, addressCountryCode);
        }
 
        String postalCode = entity.getPostalCode();
        if (postalCode != null) {
            stmt.bindString(16, postalCode);
        }
 
        String neighborhood = entity.getNeighborhood();
        if (neighborhood != null) {
            stmt.bindString(17, neighborhood);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(18, city);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(19, state);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(20, phone);
        }
 
        String phoneDialingCode = entity.getPhoneDialingCode();
        if (phoneDialingCode != null) {
            stmt.bindString(21, phoneDialingCode);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(22, email);
        }
 
        String residenceCountryIsoCode = entity.getResidenceCountryIsoCode();
        if (residenceCountryIsoCode != null) {
            stmt.bindString(23, residenceCountryIsoCode);
        }
 
        String countryIsoCode = entity.getCountryIsoCode();
        if (countryIsoCode != null) {
            stmt.bindString(24, countryIsoCode);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UploadIDHubInfoEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String firstName = entity.getFirstName();
        if (firstName != null) {
            stmt.bindString(2, firstName);
        }
 
        String middleName = entity.getMiddleName();
        if (middleName != null) {
            stmt.bindString(3, middleName);
        }
 
        String lastName = entity.getLastName();
        if (lastName != null) {
            stmt.bindString(4, lastName);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(5, gender);
        }
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(6, birthday);
        }
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(7, country);
        }
 
        String residenceCountry = entity.getResidenceCountry();
        if (residenceCountry != null) {
            stmt.bindString(8, residenceCountry);
        }
 
        String idcardNumber = entity.getIdcardNumber();
        if (idcardNumber != null) {
            stmt.bindString(9, idcardNumber);
        }
 
        String passportNumber = entity.getPassportNumber();
        if (passportNumber != null) {
            stmt.bindString(10, passportNumber);
        }
 
        String taxNumber = entity.getTaxNumber();
        if (taxNumber != null) {
            stmt.bindString(11, taxNumber);
        }
 
        String ssnNumber = entity.getSsnNumber();
        if (ssnNumber != null) {
            stmt.bindString(12, ssnNumber);
        }
 
        String street = entity.getStreet();
        if (street != null) {
            stmt.bindString(13, street);
        }
 
        String addressCountry = entity.getAddressCountry();
        if (addressCountry != null) {
            stmt.bindString(14, addressCountry);
        }
 
        String addressCountryCode = entity.getAddressCountryCode();
        if (addressCountryCode != null) {
            stmt.bindString(15, addressCountryCode);
        }
 
        String postalCode = entity.getPostalCode();
        if (postalCode != null) {
            stmt.bindString(16, postalCode);
        }
 
        String neighborhood = entity.getNeighborhood();
        if (neighborhood != null) {
            stmt.bindString(17, neighborhood);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(18, city);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(19, state);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(20, phone);
        }
 
        String phoneDialingCode = entity.getPhoneDialingCode();
        if (phoneDialingCode != null) {
            stmt.bindString(21, phoneDialingCode);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(22, email);
        }
 
        String residenceCountryIsoCode = entity.getResidenceCountryIsoCode();
        if (residenceCountryIsoCode != null) {
            stmt.bindString(23, residenceCountryIsoCode);
        }
 
        String countryIsoCode = entity.getCountryIsoCode();
        if (countryIsoCode != null) {
            stmt.bindString(24, countryIsoCode);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UploadIDHubInfoEntity readEntity(Cursor cursor, int offset) {
        UploadIDHubInfoEntity entity = new UploadIDHubInfoEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // firstName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // middleName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // lastName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // gender
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // birthday
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // country
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // residenceCountry
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // idcardNumber
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // passportNumber
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // taxNumber
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // ssnNumber
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // street
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // addressCountry
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // addressCountryCode
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // postalCode
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // neighborhood
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // city
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // state
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // phone
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // phoneDialingCode
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // email
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // residenceCountryIsoCode
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23) // countryIsoCode
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UploadIDHubInfoEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFirstName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMiddleName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLastName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGender(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setBirthday(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCountry(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setResidenceCountry(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIdcardNumber(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setPassportNumber(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setTaxNumber(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setSsnNumber(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setStreet(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setAddressCountry(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setAddressCountryCode(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setPostalCode(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setNeighborhood(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setCity(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setState(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setPhone(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setPhoneDialingCode(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setEmail(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setResidenceCountryIsoCode(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setCountryIsoCode(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UploadIDHubInfoEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UploadIDHubInfoEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UploadIDHubInfoEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
