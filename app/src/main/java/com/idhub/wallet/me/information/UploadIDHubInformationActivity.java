package com.idhub.wallet.me.information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.idhub.magic.center.ustorage.entity.BasicInfo;
import com.idhub.magic.center.ustorage.entity.FinancialProfile;
import com.idhub.magic.center.ustorage.entity.IdentityArchive;
import com.idhub.magic.center.ustorage.entity.IdentityInfo;
import com.idhub.wallet.R;
import com.idhub.wallet.common.country.Country;
import com.idhub.wallet.common.country.CountryPickerCallbacks;
import com.idhub.wallet.common.country.CountryPickerDialog;
import com.idhub.wallet.common.date.DatePicker;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.greendao.UploadIDHubInfoDbManager;
import com.idhub.wallet.greendao.entity.UploadIDHubInfoEntity;
import com.idhub.wallet.me.information.view.InformationInputItemView;
import com.idhub.wallet.me.information.view.InformationSelectItemView;
import com.idhub.wallet.utils.DateUtils;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UploadIDHubInformationActivity extends AppCompatActivity implements View.OnClickListener {

    private InformationInputItemView mLastNameView;
    private InformationInputItemView mFirstNameView;
    private InformationSelectItemView mNationalityView;
    private InformationSelectItemView mCountryOfResidenceView;
    private InformationSelectItemView mBirthdayView;
    private InformationInputItemView mIdNumberView;
    private InformationInputItemView mPassportNumberView;
    private EditText mPhoneNumberView;
    private InformationInputItemView mTaxIdView;
    private InformationInputItemView mSSNView;
    private InformationInputItemView mAddressView;
    private InformationInputItemView mEmailView;
    private UploadIDHubInfoEntity mIdHubInfoEntity;
    private UploadIDHubInfoDbManager mUploadIDHubInfoDbManager;
    private String mPhoneDialingCode;
    private String mResidenceCountryIsoCode;
    private String mCountryIsoCode;
    private TextView mPhoneDialingCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_idhub_information);
        initView();
        initData();
    }

    private void initData() {
        mUploadIDHubInfoDbManager = new UploadIDHubInfoDbManager();
        mUploadIDHubInfoDbManager.queryById(1, new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                mIdHubInfoEntity = (UploadIDHubInfoEntity) operation.getResult();
                if (mIdHubInfoEntity != null) {
                    mFirstNameView.setValue(mIdHubInfoEntity.getFirstName());
                    mLastNameView.setValue(mIdHubInfoEntity.getLastName());
                    mBirthdayView.setInformation(mIdHubInfoEntity.getBirthday());
                    mNationalityView.setInformation(mIdHubInfoEntity.getCountry());
                    mCountryOfResidenceView.setInformation(mIdHubInfoEntity.getResidenceCountry());
                    mIdNumberView.setValue(mIdHubInfoEntity.getIdcardNumber());
                    mPassportNumberView.setValue(mIdHubInfoEntity.getPassportNumber());
                    mPhoneNumberView.setText(mIdHubInfoEntity.getPhone());
                    mAddressView.setValue(mIdHubInfoEntity.getAddress());
                    mTaxIdView.setValue(mIdHubInfoEntity.getTaxNumber());
                    mSSNView.setValue(mIdHubInfoEntity.getSsnNumber());
                    mEmailView.setValue(mIdHubInfoEntity.getEmail());
                    mPhoneDialingCodeView.setText(mIdHubInfoEntity.getPhoneDialingCode());
                }
            }
        });
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_id_hub_information_title));
        mLastNameView = findViewById(R.id.last_name);
        mLastNameView.setData(getString(R.string.wallet_first_name), getString(R.string.wallet_please_input) + getString(R.string.wallet_first_name));
        mFirstNameView = findViewById(R.id.first_name);
        mFirstNameView.setData(getString(R.string.wallet_last_name), getString(R.string.wallet_please_input) + getString(R.string.wallet_last_name));
        mBirthdayView = findViewById(R.id.birthday);
        mBirthdayView.setData(getString(R.string.wallet_birthday), getString(R.string.wallet_please_input) + getString(R.string.wallet_birthday));
        mBirthdayView.setOnClickListener(this);
        mNationalityView = findViewById(R.id.nationality);
        mNationalityView.setData(getString(R.string.wallet_country), getString(R.string.wallet_please_input) + getString(R.string.wallet_country));
        mNationalityView.setOnClickListener(this);
        mCountryOfResidenceView = findViewById(R.id.country_of_residence);
        mCountryOfResidenceView.setData(getString(R.string.wallet_residence_country), getString(R.string.wallet_please_input) + getString(R.string.wallet_residence_country));
        mCountryOfResidenceView.setOnClickListener(this);

        mIdNumberView = findViewById(R.id.id_number);
        mIdNumberView.setData(getString(R.string.wallet_id_number), getString(R.string.wallet_please_input) + getString(R.string.wallet_id_number));
        mPassportNumberView = findViewById(R.id.passport_number);
        mPassportNumberView.setData(getString(R.string.wallet_passport_number), getString(R.string.wallet_please_input) + getString(R.string.wallet_passport_number));
        mPhoneNumberView = findViewById(R.id.et_phone_number);
        mPhoneDialingCodeView = findViewById(R.id.tv_phone_dialing_code);
        mPhoneDialingCodeView.setOnClickListener(this);
        TextView phoneNumberView = findViewById(R.id.tv_phone_number);
        phoneNumberView.setText(getString(R.string.wallet_phone_number));
        mPhoneNumberView.setHint(getString(R.string.wallet_please_input) + getString(R.string.wallet_phone_number));

        mTaxIdView = findViewById(R.id.tax_id);
        mTaxIdView.setData(getString(R.string.wallet_tax_number), getString(R.string.wallet_please_input) + getString(R.string.wallet_tax_number));
        mSSNView = findViewById(R.id.ssn);
        mSSNView.setData(getString(R.string.wallet_ssn_number), getString(R.string.wallet_please_input) + getString(R.string.wallet_ssn_number));
        mAddressView = findViewById(R.id.address_proof);
        mAddressView.setData(getString(R.string.wallet_address), getString(R.string.wallet_please_input) + getString(R.string.wallet_address));
        mEmailView = findViewById(R.id.email);
        mEmailView.setData(getString(R.string.wallet_email), getString(R.string.wallet_please_input) + getString(R.string.wallet_email));
        findViewById(R.id.tv_upgrade).setOnClickListener(this);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, UploadIDHubInformationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.birthday:
                selectBirthday();
                break;
            case R.id.nationality:
                selectNationlity(mNationalityView, "country");
                break;
            case R.id.country_of_residence:
                selectNationlity(mCountryOfResidenceView, "residenceCountry");
                break;
            case R.id.tv_upgrade:
                submit();
                break;
            case R.id.tv_phone_dialing_code:
                selectDialingCode();
                break;
        }
    }

    private void submit() {
        String lastNameViewInputData = mLastNameView.getInputData();
        String firstNameViewInputData = mFirstNameView.getInputData();
        String birthdayViewInformation = mBirthdayView.getInformation();
        String nationalityViewInformation = mNationalityView.getInformation();
        String countryOfResidenceViewInformation = mCountryOfResidenceView.getInformation();
        String idNumberViewInputData = mIdNumberView.getInputData();
        String passportNumberViewInputData = mPassportNumberView.getInputData();
        String phoneNumberViewInputData = mPhoneNumberView.getText().toString();
        String dialingCode = mPhoneDialingCode;
        String taxIdViewInputData = mTaxIdView.getInputData();
        String ssnViewInputData = mSSNView.getInputData();
        String addressViewInputData = mAddressView.getInputData();
        String emailViewInputData = mEmailView.getInputData();
        if (mIdHubInfoEntity == null) {
            UploadIDHubInfoEntity uploadIDHubInfoEntity = new UploadIDHubInfoEntity();
            uploadIDHubInfoEntity.setFirstName(firstNameViewInputData);
            uploadIDHubInfoEntity.setLastName(lastNameViewInputData);
            uploadIDHubInfoEntity.setBirthday(birthdayViewInformation);
            uploadIDHubInfoEntity.setCountry(nationalityViewInformation);
            uploadIDHubInfoEntity.setResidenceCountry(countryOfResidenceViewInformation);
            uploadIDHubInfoEntity.setIdcardNumber(idNumberViewInputData);
            uploadIDHubInfoEntity.setPassportNumber(passportNumberViewInputData);
            uploadIDHubInfoEntity.setPhone(phoneNumberViewInputData);
            uploadIDHubInfoEntity.setPhoneDialingCode(dialingCode);
            uploadIDHubInfoEntity.setTaxNumber(taxIdViewInputData);
            uploadIDHubInfoEntity.setSsnNumber(ssnViewInputData);
            uploadIDHubInfoEntity.setAddress(addressViewInputData);
            uploadIDHubInfoEntity.setEmail(emailViewInputData);
            uploadIDHubInfoEntity.setResidenceCountryIsoCode(mResidenceCountryIsoCode);
            uploadIDHubInfoEntity.setCountryIsoCode(mCountryIsoCode);
            mUploadIDHubInfoDbManager.insertData(uploadIDHubInfoEntity, null);
        } else {
            mIdHubInfoEntity.setFirstName(firstNameViewInputData);
            mIdHubInfoEntity.setLastName(lastNameViewInputData);
            mIdHubInfoEntity.setBirthday(birthdayViewInformation);
            mIdHubInfoEntity.setCountry(nationalityViewInformation);
            mIdHubInfoEntity.setResidenceCountry(countryOfResidenceViewInformation);
            mIdHubInfoEntity.setIdcardNumber(idNumberViewInputData);
            mIdHubInfoEntity.setPassportNumber(passportNumberViewInputData);
            mIdHubInfoEntity.setPhone(phoneNumberViewInputData);
            mIdHubInfoEntity.setPhoneDialingCode(dialingCode);
            mIdHubInfoEntity.setTaxNumber(taxIdViewInputData);
            mIdHubInfoEntity.setSsnNumber(ssnViewInputData);
            mIdHubInfoEntity.setAddress(addressViewInputData);
            mIdHubInfoEntity.setEmail(emailViewInputData);
            mIdHubInfoEntity.setResidenceCountryIsoCode(mResidenceCountryIsoCode);
            mIdHubInfoEntity.setCountryIsoCode(mCountryIsoCode);
            mUploadIDHubInfoDbManager.update(mIdHubInfoEntity);
        }
        IdentityArchive identityArchive = new IdentityArchive();
        BasicInfo basicInfo = new BasicInfo();
        identityArchive.setBasicInfo(basicInfo);
        IdentityInfo identityInfo = new IdentityInfo();
//        identityInfo.setBirthday();
        identityArchive.setIdentityInfo(identityInfo);
        FinancialProfile financialProfile = new FinancialProfile();

        identityArchive.setFinancialProfile(financialProfile);
//        ApiFactory.getArchiveStorage().storeArchive()


    }

    private void selectNationlity(InformationSelectItemView informationSelectItemView, String source) {
        CountryPickerDialog countryPicker =
                new CountryPickerDialog(UploadIDHubInformationActivity.this, new CountryPickerCallbacks() {
                    @Override
                    public void onCountrySelected(Country country) {
                        Log.e("LYW", "onCountrySelected: " + country.getDialingCode() + " " + country.getIsoCode());
                        if ("residenceCountry".equals(source)) {
                            if (TextUtils.isEmpty(mPhoneDialingCode)) {
                                mPhoneDialingCode = country.getDialingCode();
                                mPhoneDialingCodeView.setText("+" + mPhoneDialingCode);
                            }
                            mResidenceCountryIsoCode = country.getIsoCode();
                        } else {
                            mCountryIsoCode = country.getIsoCode();
                        }
                        informationSelectItemView.setInformation(country.getCountryName(UploadIDHubInformationActivity.this));
                    }
                }, false, 0);
        countryPicker.show();
    }

    private void selectDialingCode() {
        CountryPickerDialog countryPicker =
                new CountryPickerDialog(UploadIDHubInformationActivity.this, new CountryPickerCallbacks() {
                    @Override
                    public void onCountrySelected(Country country) {
                        mPhoneDialingCode = country.getDialingCode();
                        mPhoneDialingCodeView.setText("+" + mPhoneDialingCode);
                    }
                }, true, 0);
        countryPicker.show();
    }

    private void selectBirthday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        DatePicker mDatePicker = new DatePicker(this, time -> { // 回调接口，获得选中的时间
            mBirthdayView.setInformation(DateUtils.dateFormatYMD(time));
        }, "1900-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        mDatePicker.showSpecificTime(false); // 显示时和分
        mDatePicker.setIsLoop(false);
        mDatePicker.show(now);
    }
}
