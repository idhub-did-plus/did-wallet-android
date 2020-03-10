package com.idhub.wallet.auth;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.idhub.base.greendao.entity.UploadIDHubInfoEntity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.country.Country;
import com.idhub.wallet.common.country.CountryPickerCallbacks;
import com.idhub.wallet.common.country.CountryPickerDialog;
import com.idhub.wallet.common.date.DatePicker;
import com.idhub.wallet.me.information.UploadIDHubInformationActivity;
import com.idhub.wallet.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BaseInformationLayout extends LinearLayout implements View.OnClickListener {

    private InformationItemLayout firstNameView;
    private InformationItemLayout middleNameView;
    private InformationItemLayout lastNameView;
    private InformationItemLayout birthdayView;
    private InformationItemLayout emailView;
    private InformationItemLayout nationalityView;
    private InformationItemLayout countryView;
    private EditText phoneView;
    private TextView phoneCodeView;
    private boolean mIsLocalzh;
    private RadioButton maleBtn;
    private RadioButton femaleBtn;
    private RadioGroup genderGroup;
    private View saveBtnView;
    private UploadIDHubInfoEntity uploadInfo;
    private String nationalityCode;
    private String residenceCountryIsoCode;
    private SaveClickListener saveClickListener;

    public BaseInformationLayout(Context context) {
        super(context);
        init();
    }

    public BaseInformationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseInformationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        Context context = getContext();
        inflate(context, R.layout.wallet_base_information_layout, this);
        firstNameView = findViewById(R.id.first_name);
        firstNameView.setContent(context.getString(R.string.wallet_first_name), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_first_name));
        middleNameView = findViewById(R.id.middle_name);
        middleNameView.setContent(context.getString(R.string.wallet_middle_name), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_middle_name));
        lastNameView = findViewById(R.id.last_name);
        lastNameView.setContent(context.getString(R.string.wallet_last_name), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_last_name));
        birthdayView = findViewById(R.id.birthday);
        birthdayView.setContent(context.getString(R.string.wallet_birthday), context.getString(R.string.wallet_please_select) + context.getString(R.string.wallet_birthday));
        birthdayView.setEditable(false);
        birthdayView.setOnClickListener(this);

        emailView = findViewById(R.id.email);
        emailView.setContent(context.getString(R.string.wallet_email), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_email));
        nationalityView = findViewById(R.id.nationality);
        nationalityView.setContent(context.getString(R.string.wallet_country), context.getString(R.string.wallet_please_select) + " " + context.getString(R.string.wallet_country));
        nationalityView.setEditable(false);
        nationalityView.setOnClickListener(this);

        countryView = findViewById(R.id.country_of_residence);
        countryView.setContent(context.getString(R.string.wallet_residence_country), context.getString(R.string.wallet_please_select) + " " + context.getString(R.string.wallet_residence_country));
        countryView.setEditable(false);
        countryView.setOnClickListener(this);
        genderGroup = findViewById(R.id.gender_group);
        maleBtn = findViewById(R.id.male_btn);
        femaleBtn = findViewById(R.id.female_btn);

        phoneView = findViewById(R.id.phone);
        phoneCodeView = findViewById(R.id.tv_phone_dialing_code);
        String locale = Locale.getDefault().toString();
        mIsLocalzh = locale.contains("zh");
        if (mIsLocalzh) {
            middleNameView.setVisibility(View.GONE);
        } else {
            middleNameView.setVisibility(View.VISIBLE);
        }
        saveBtnView = findViewById(R.id.base_info_save);
        saveBtnView.setOnClickListener(this);
    }


    public void setUploadInfo(UploadIDHubInfoEntity uploadInfo) {
        this.uploadInfo = uploadInfo;
        firstNameView.setValue(uploadInfo.getFirstName());
        lastNameView.setValue(uploadInfo.getLastName());
        String gender = uploadInfo.getGender();
        if (getContext().getString(R.string.wallet_gender_boy).equals(gender)) {
            maleBtn.setChecked(true);
        } else {
            femaleBtn.setChecked(true);
        }
        birthdayView.setValue(uploadInfo.getBirthday());

        emailView.setValue(uploadInfo.getEmail());
        //国家的区号和国家的名字分开存储，上传使用code
        nationalityView.setValue(uploadInfo.getCountry());
        nationalityCode = uploadInfo.getCountryIsoCode();
        //国家的区号和国家的名字分开存储，上传使用code
        countryView.setValue(uploadInfo.getResidenceCountry());
        residenceCountryIsoCode = uploadInfo.getResidenceCountryIsoCode();

        phoneView.setText(uploadInfo.getPhone());
        //手机区号
        phoneCodeView.setText(uploadInfo.getPhoneDialingCode());
        phoneCodeView.setOnClickListener(this);
        if (!mIsLocalzh) {
            middleNameView.setValue(uploadInfo.getMiddleName());
        }

    }


    public void setSaveClickListener(SaveClickListener saveClickListener) {
        this.saveClickListener = saveClickListener;
    }

    @Override
    public void onClick(View v) {
        if (v == saveBtnView) {
            String firstName = firstNameView.getValue();
            String middleName = middleNameView.getValue();
            String lastName = lastNameView.getValue();
            String birthday = birthdayView.getValue();
            String email = emailView.getValue();

            String nationality = nationalityView.getValue();
            String country = countryView.getValue();
            int checkedRadioButtonId = genderGroup.getCheckedRadioButtonId();
            String gender = "";
            switch (checkedRadioButtonId) {
                case R.id.male_btn:
                    gender = maleBtn.getText().toString();
                    break;
                case R.id.female_btn:
                    gender = femaleBtn.getText().toString();
                    break;
            }


            String phone = phoneView.getText().toString();
            String phoneCode = phoneCodeView.getText().toString();
            if (uploadInfo == null) {
                uploadInfo = new UploadIDHubInfoEntity();
            }
            uploadInfo.setFirstName(firstName);
            uploadInfo.setMiddleName(middleName);
            uploadInfo.setLastName(lastName);
            uploadInfo.setBirthday(birthday);
            uploadInfo.setEmail(email);
            //本地保存国家区号和名字
            uploadInfo.setCountry(nationality);
            uploadInfo.setCountryIsoCode(nationalityCode);
            //本地保存国家区号和名字
            uploadInfo.setResidenceCountry(country);
            uploadInfo.setResidenceCountryIsoCode(residenceCountryIsoCode);

            uploadInfo.setPhone(phone);
            uploadInfo.setGender(gender);
            uploadInfo.setPhoneDialingCode(phoneCode);
            if (saveClickListener != null) {
                saveClickListener.click(uploadInfo);
            }
        } else if (v == birthdayView) {
            //生日
            selectBirthday();
        } else if (v == nationalityView) {
            selectCountry(nationalityView);
        } else if (v == countryView) {
            selectCountry(countryView);
        } else if (v == phoneCodeView) {
            selectDialingCode();
        }
    }


    private void selectBirthday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        DatePicker mDatePicker = new DatePicker(getContext(), time -> { // 回调接口，获得选中的时间
            birthdayView.setValue(DateUtils.dateFormatYMD(time));
        }, "1900-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        mDatePicker.showSpecificTime(false); // 显示时和分
        mDatePicker.setIsLoop(false);
        mDatePicker.show(now);
    }


    private void selectDialingCode() {
        CountryPickerDialog countryPicker =
                new CountryPickerDialog(getContext(), new CountryPickerCallbacks() {
                    @Override
                    public void onCountrySelected(Country country) {
                        String phoneDialingCode = country.getDialingCode();
                        phoneCodeView.setText(phoneDialingCode);
                    }
                }, true, 0);
        countryPicker.show();
    }

    private void selectCountry(InformationItemLayout informationItemLayout) {
        CountryPickerDialog countryPicker =
                new CountryPickerDialog(getContext(), new CountryPickerCallbacks() {
                    @Override
                    public void onCountrySelected(Country country) {
                        if (informationItemLayout == nationalityView) {
                            //判断手机区号为空时，默认设置成选择国家的区号
                            if (TextUtils.isEmpty(phoneCodeView.getText().toString())) {
                                phoneCodeView.setText(country.getDialingCode());
                            }
                            nationalityCode = country.getIsoCode();

                        } else if (informationItemLayout == countryView) {
                            residenceCountryIsoCode = country.getIsoCode();
                        }
                        informationItemLayout.setValue(country.getCountryName(getContext()));
                    }
                }, false, 0);
        countryPicker.show();
    }
}
