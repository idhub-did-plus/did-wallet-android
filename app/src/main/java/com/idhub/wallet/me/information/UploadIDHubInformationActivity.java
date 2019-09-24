package com.idhub.wallet.me.information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.idhub.magic.common.parameter.MagicResponse;
import com.idhub.magic.common.ustorage.entity.BasicInfo;
import com.idhub.magic.common.ustorage.entity.IdentityArchive;
import com.idhub.magic.common.ustorage.entity.IdentityInfo;
import com.idhub.magic.common.ustorage.entity.component.Address;
import com.idhub.magic.common.ustorage.entity.component.AddressElement;
import com.idhub.magic.common.ustorage.entity.component.Name;
import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.country.Country;
import com.idhub.wallet.common.country.CountryPickerCallbacks;
import com.idhub.wallet.common.country.CountryPickerDialog;
import com.idhub.wallet.common.date.DatePicker;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.greendao.UploadIDHubInfoDbManager;
import com.idhub.wallet.greendao.entity.UploadIDHubInfoEntity;
import com.idhub.wallet.me.information.view.InformationInputItemView;
import com.idhub.wallet.me.information.view.InformationSelectItemView;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.DateUtils;
import com.idhub.wallet.utils.ToastUtils;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.reactivex.observers.DisposableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wallet.idhub.com.clientlib.ApiFactory;

public class UploadIDHubInformationActivity extends AppCompatActivity implements View.OnClickListener {

    private InformationInputItemView mLastNameView;
    private InformationSelectItemView mGenderView;
    private InformationInputItemView mFirstNameView;
    private InformationSelectItemView mNationalityView;
    private InformationSelectItemView mCountryOfResidenceView;
    private InformationSelectItemView mBirthdayView;
    private InformationInputItemView mIdNumberView;
    private InformationInputItemView mPassportNumberView;
    private EditText mPhoneNumberView;
    private InformationInputItemView mTaxIdView;
    private InformationInputItemView mSSNView;
    private InformationInputItemView mEmailView;
    private UploadIDHubInfoEntity mIdHubInfoEntity;
    private UploadIDHubInfoDbManager mUploadIDHubInfoDbManager;
    private String mPhoneDialingCode;
    private String mResidenceCountryIsoCode;
    private String mCountryIsoCode;
    private TextView mPhoneDialingCodeView;
    private InformationInputItemView mMiddleNameView;
    private InformationInputItemView mStreetView;
    private InformationSelectItemView mAddressCountryView;
    private InformationInputItemView mCityView;
    private InformationInputItemView mPostalCodeView;
    private InformationInputItemView mStateView;
    private InformationInputItemView mNeighborhoodView;
    private InformationInputItemView mAddressDetailView;
    private boolean mIsLocalzh;
    private String mAddressCountryCode;
    private LoadingAndErrorView mLoadingAndErrorView;
    private WalletKeystore mDefaultKeystore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_idhub_information);
        mDefaultKeystore = WalletManager.getDefaultKeystore();
        if (mDefaultKeystore == null) {
            mDefaultKeystore = WalletManager.getCurrentKeyStore();
            if (mDefaultKeystore == null) {
                finish();
                return;
            }
        }
        initView();
        initData();

    }

    private void initData() {
        String locale = Locale.getDefault().toString();
        mIsLocalzh = locale.contains("zh");
        if (mIsLocalzh) {
            mMiddleNameView.setVisibility(View.GONE);
            mStreetView.setVisibility(View.GONE);
            mAddressDetailView.setVisibility(View.VISIBLE);
        } else {
            mMiddleNameView.setVisibility(View.VISIBLE);
            mStreetView.setVisibility(View.VISIBLE);
            mAddressDetailView.setVisibility(View.GONE);
        }

        mUploadIDHubInfoDbManager = new UploadIDHubInfoDbManager();
        mUploadIDHubInfoDbManager.queryById(1, operation -> {
            mIdHubInfoEntity = (UploadIDHubInfoEntity) operation.getResult();
            if (mIdHubInfoEntity != null) {
                mFirstNameView.setValue(mIdHubInfoEntity.getFirstName());
                mLastNameView.setValue(mIdHubInfoEntity.getLastName());
                mGenderView.setInformation(mIdHubInfoEntity.getGender());
                mBirthdayView.setInformation(mIdHubInfoEntity.getBirthday());
                mNationalityView.setInformation(mIdHubInfoEntity.getCountry());
                mCountryOfResidenceView.setInformation(mIdHubInfoEntity.getResidenceCountry());
                mIdNumberView.setValue(mIdHubInfoEntity.getIdcardNumber());
                mPassportNumberView.setValue(mIdHubInfoEntity.getPassportNumber());
                mPhoneNumberView.setText(mIdHubInfoEntity.getPhone());
                if (mIsLocalzh) {
                    mAddressDetailView.setValue(mIdHubInfoEntity.getStreet());
                } else {
                    mStreetView.setValue(mIdHubInfoEntity.getStreet());
                    mMiddleNameView.setValue(mIdHubInfoEntity.getMiddleName());
                }
                mAddressCountryView.setInformation(mIdHubInfoEntity.getAddressCountry());
                mPostalCodeView.setValue(mIdHubInfoEntity.getPostalCode());
                mCityView.setValue(mIdHubInfoEntity.getCity());
                mStateView.setValue(mIdHubInfoEntity.getState());
                mNeighborhoodView.setValue(mIdHubInfoEntity.getNeighborhood());

                mTaxIdView.setValue(mIdHubInfoEntity.getTaxNumber());
                mSSNView.setValue(mIdHubInfoEntity.getSsnNumber());
                mEmailView.setValue(mIdHubInfoEntity.getEmail());
                mPhoneDialingCodeView.setText("+"+mIdHubInfoEntity.getPhoneDialingCode());
                mCountryIsoCode = mIdHubInfoEntity.getCountryIsoCode();
                mResidenceCountryIsoCode = mIdHubInfoEntity.getResidenceCountryIsoCode();
                mAddressCountryCode = mIdHubInfoEntity.getAddressCountryCode();
            }
        });
    }


    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_id_hub_information_title));
        mFirstNameView = findViewById(R.id.first_name);
        mFirstNameView.setData(getString(R.string.wallet_first_name), getString(R.string.wallet_please_input) + getString(R.string.wallet_first_name));
        mMiddleNameView = findViewById(R.id.middle_name);
        mMiddleNameView.setData(getString(R.string.wallet_middle_name), getString(R.string.wallet_please_input) + getString(R.string.wallet_middle_name));
        mLastNameView = findViewById(R.id.last_name);
        mLastNameView.setData(getString(R.string.wallet_last_name), getString(R.string.wallet_please_input) + getString(R.string.wallet_last_name));
        mGenderView = findViewById(R.id.gender);
        mGenderView.setData(getString(R.string.wallet_gender), getString(R.string.wallet_please_select) + getString(R.string.wallet_gender));
        mGenderView.setOnClickListener(this);
        mBirthdayView = findViewById(R.id.birthday);
        mBirthdayView.setData(getString(R.string.wallet_birthday), getString(R.string.wallet_please_select) + getString(R.string.wallet_birthday));
        mBirthdayView.setOnClickListener(this);
        mNationalityView = findViewById(R.id.nationality);
        mNationalityView.setData(getString(R.string.wallet_country), getString(R.string.wallet_please_select) + getString(R.string.wallet_country));
        mNationalityView.setOnClickListener(this);
        mCountryOfResidenceView = findViewById(R.id.country_of_residence);
        mCountryOfResidenceView.setData(getString(R.string.wallet_residence_country), getString(R.string.wallet_please_select) + getString(R.string.wallet_residence_country));
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

        mStreetView = findViewById(R.id.street);
        mStreetView.setData(getString(R.string.wallet_street), getString(R.string.wallet_please_input) + getString(R.string.wallet_street));
        mAddressCountryView = findViewById(R.id.address_country);
        mAddressCountryView.setOnClickListener(this);
        mAddressCountryView.setData(getString(R.string.wallet_address_country), getString(R.string.wallet_address_country_hint));
        mPostalCodeView = findViewById(R.id.postal_code);
        mPostalCodeView.setData(getString(R.string.wallet_postal_code), getString(R.string.wallet_please_input) + getString(R.string.wallet_postal_code));
        mCityView = findViewById(R.id.city);
        mCityView.setData(getString(R.string.wallet_city), getString(R.string.wallet_please_input) + getString(R.string.wallet_city));
        mStateView = findViewById(R.id.state);
        mStateView.setData(getString(R.string.wallet_state), getString(R.string.wallet_please_input) + getString(R.string.wallet_state));
        mNeighborhoodView = findViewById(R.id.neighborhood);
        mNeighborhoodView.setData(getString(R.string.wallet_neighborhood), getString(R.string.wallet_please_input) + getString(R.string.wallet_neighborhood));
        mAddressDetailView = findViewById(R.id.address_detail);
        mAddressDetailView.setData(getString(R.string.wallet_address_detail), getString(R.string.wallet_address_detail_hint));


        mEmailView = findViewById(R.id.email);
        mEmailView.setData(getString(R.string.wallet_email), getString(R.string.wallet_please_input) + getString(R.string.wallet_email));
        findViewById(R.id.tv_upgrade).setOnClickListener(this);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);
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
                inputPasswordDialogShow();
                break;
            case R.id.tv_phone_dialing_code:
                selectDialingCode();
                break;
            case R.id.address_country:
                selectNationlity(mAddressCountryView, "addressCountry");
                break;
            case R.id.gender:
                selectGender();
                break;
        }
    }

    private void selectGender() {
        GenderSelectDialogFragment fragment = GenderSelectDialogFragment.getInstance();
        fragment.show(getSupportFragmentManager(), "gender_select_dialog_fragment");
        fragment.setGenderSelectDialogFragmentListener(new GenderSelectDialogFragment.GenderSelectDialogFragmentListener() {
            @Override
            public void genderSelect(String gender) {
                mGenderView.setInformation(gender);
            }
        });
    }

    private void inputPasswordDialogShow() {
            InputDialogFragment instance = InputDialogFragment.getInstance("idhub_information", getString(R.string.wallet_default_address_password), InputType.TYPE_CLASS_TEXT);
            instance.show(getSupportFragmentManager(), "input_dialog_fragment");
            instance.setInputDialogFragmentListener((data, source) -> {
                WalletInfo walletInfo = new WalletInfo(mDefaultKeystore);
                walletInfo.verifyPassword(data, new DisposableObserver<Boolean>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        mLoadingAndErrorView.onLoading();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            submit(walletInfo.exportPrivateKey(data));
                        } else {
                            ToastUtils.showShortToast(getString(R.string.wallet_input_password_false));
                            mLoadingAndErrorView.onGone();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShortToast(getString(R.string.wallet_input_password_false));
                        mLoadingAndErrorView.onGone();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

            });
    }

    private void submit(String privateKey) {
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
        String middleName = mMiddleNameView.getInputData();
        String gender = mGenderView.getInformation();
        String street = mStreetView.getInputData();
        String addressContry = mAddressCountryView.getInformation();
        String postalCode = mPostalCodeView.getInputData();
        String city = mCityView.getInputData();
        String state = mStateView.getInputData();
        String neighborhood = mNeighborhoodView.getInputData();
        String addressDetail = mAddressDetailView.getInputData();
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
            if (mIsLocalzh) {
                uploadIDHubInfoEntity.setStreet(addressDetail);
            } else {
                uploadIDHubInfoEntity.setStreet(street);
            }
            uploadIDHubInfoEntity.setMiddleName(middleName);
            uploadIDHubInfoEntity.setGender(gender);
            uploadIDHubInfoEntity.setAddressCountry(addressContry);
            uploadIDHubInfoEntity.setAddressCountryCode(mAddressCountryCode);
            uploadIDHubInfoEntity.setPostalCode(postalCode);
            uploadIDHubInfoEntity.setCity(city);
            uploadIDHubInfoEntity.setState(state);
            uploadIDHubInfoEntity.setNeighborhood(neighborhood);
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
            if (mIsLocalzh) {
                mIdHubInfoEntity.setStreet(addressDetail);
            } else {
                mIdHubInfoEntity.setStreet(street);
            }
            mIdHubInfoEntity.setMiddleName(middleName);
            mIdHubInfoEntity.setGender(gender);
            mIdHubInfoEntity.setAddressCountry(addressContry);
            mIdHubInfoEntity.setAddressCountryCode(mAddressCountryCode);
            mIdHubInfoEntity.setPostalCode(postalCode);
            mIdHubInfoEntity.setCity(city);
            mIdHubInfoEntity.setState(state);
            mIdHubInfoEntity.setNeighborhood(neighborhood);
            mIdHubInfoEntity.setEmail(emailViewInputData);
            mIdHubInfoEntity.setResidenceCountryIsoCode(mResidenceCountryIsoCode);
            mIdHubInfoEntity.setCountryIsoCode(mCountryIsoCode);
            mUploadIDHubInfoDbManager.update(mIdHubInfoEntity);
        }
        IdentityArchive identityArchive = new IdentityArchive();
        BasicInfo basicInfo = new BasicInfo();
        basicInfo.setTaxId(taxIdViewInputData);
        basicInfo.setEmail(emailViewInputData);
        basicInfo.setSsn(ssnViewInputData);

        IdentityInfo identityInfo = new IdentityInfo();
        Name name = new Name();
        name.setFirstName(firstNameViewInputData);
        name.setMiddleName(middleName);
        name.setLastName(lastNameViewInputData);
        Address address = new Address();
        address.setPostalCode(postalCode);
        ArrayList<AddressElement> elements = new ArrayList<>();
        if (mIsLocalzh) {
            AddressElement addressCountryElement = new AddressElement();
            addressCountryElement.name = "国家";
            addressCountryElement.value = mAddressCountryCode;
            elements.add(addressCountryElement);

            AddressElement addressCityElement = new AddressElement();
            addressCityElement.name = "省份";
            addressCityElement.value = city;
            elements.add(addressCityElement);

            AddressElement addressStateElement = new AddressElement();
            addressStateElement.name = "城市";
            addressStateElement.value = state;
            elements.add(addressStateElement);

            AddressElement addressNeighborhoodElement = new AddressElement();
            addressNeighborhoodElement.name = "区县";
            addressNeighborhoodElement.value = neighborhood;
            elements.add(addressNeighborhoodElement);

            AddressElement addressDetailElement = new AddressElement();
            addressDetailElement.name = "详细地址";
            addressDetailElement.value = addressDetail;
            elements.add(addressDetailElement);

        } else {
            AddressElement addressDetailElement = new AddressElement();
            addressDetailElement.name = "street";
            addressDetailElement.value = street;
            elements.add(addressDetailElement);

            AddressElement addressCountryElement = new AddressElement();
            addressCountryElement.name = "country";
            addressCountryElement.value = mAddressCountryCode;
            elements.add(addressCountryElement);

            AddressElement addressCityElement = new AddressElement();
            addressCityElement.name = "city";
            addressCityElement.value = city;
            elements.add(addressCityElement);

            AddressElement addressStateElement = new AddressElement();
            addressStateElement.name = "state";
            addressStateElement.value = state;
            elements.add(addressStateElement);

            AddressElement addressNeighborhoodElement = new AddressElement();
            addressNeighborhoodElement.name = "neighborhood";
            addressNeighborhoodElement.value = neighborhood;
            elements.add(addressNeighborhoodElement);
        }
        address.setAddressSequence(elements);
        identityInfo.setName(name);
        identityInfo.setAddress(address);
        identityInfo.setGender(gender);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = sdf.parse(birthdayViewInformation);
            identityInfo.setBirthday(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        identityInfo.setCountry(mCountryIsoCode);
        identityInfo.setResidenceCountry(mResidenceCountryIsoCode);
        identityInfo.setIdcardNumber(idNumberViewInputData);
        identityInfo.setPassportNumber(passportNumberViewInputData);
        identityInfo.setPhoneNumber(phoneNumberViewInputData);

        identityArchive.setIdentityInfo(identityInfo);
        identityArchive.setBasicInfo(basicInfo);
        IDHubCredentialProvider.setDefaultCredentials(privateKey);
        Call<MagicResponse> call = ApiFactory.getArchiveStorage().storeArchive(identityArchive, WalletManager.getDefaultAddress());
        call.enqueue(new Callback<MagicResponse>() {
            @Override
            public void onResponse(Call<MagicResponse> call, Response<MagicResponse> response) {
                mLoadingAndErrorView.onGone();
                MagicResponse body = response.body();
                if (body != null && body.isSuccess()) {
                    ToastUtils.showShortToast(getString(R.string.wallet_upload_success));
                    MainActivity.startAction(UploadIDHubInformationActivity.this, "upload_information");
                    finish();
                } else {
                    ToastUtils.showShortToast(getString(R.string.wallet_upload_fail));
                }
            }

            @Override
            public void onFailure(Call<MagicResponse> call, Throwable t) {
                mLoadingAndErrorView.onGone();
                ToastUtils.showShortToast(getString(R.string.wallet_upload_fail));
            }
        });

    }

    private void selectNationlity(InformationSelectItemView informationSelectItemView, String source) {
        CountryPickerDialog countryPicker =
                new CountryPickerDialog(UploadIDHubInformationActivity.this, new CountryPickerCallbacks() {
                    @Override
                    public void onCountrySelected(Country country) {
                        if ("residenceCountry".equals(source)) {
                            if (TextUtils.isEmpty(mPhoneDialingCode)) {
                                mPhoneDialingCode = country.getDialingCode();
                                mPhoneDialingCodeView.setText("+" + mPhoneDialingCode);
                            }
                            mResidenceCountryIsoCode = country.getIsoCode();
                            String information = mAddressCountryView.getInformation();
                            if (TextUtils.isEmpty(information)) {
                                mAddressCountryView.setInformation(country.getCountryName(UploadIDHubInformationActivity.this));
                                mAddressCountryCode = country.getIsoCode();
                            }
                        } else if ("country".equals(source)) {
                            mCountryIsoCode = country.getIsoCode();
                        } else if ("addressCountry".equals(source)) {
                            mAddressCountryCode = country.getIsoCode();
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
