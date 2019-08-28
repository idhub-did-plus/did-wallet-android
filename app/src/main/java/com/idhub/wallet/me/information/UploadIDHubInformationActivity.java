package com.idhub.wallet.me.information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.idhub.wallet.R;
import com.idhub.wallet.common.country.Country;
import com.idhub.wallet.common.country.CountryPickerCallbacks;
import com.idhub.wallet.common.country.CountryPickerDialog;
import com.idhub.wallet.common.date.DatePicker;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.me.information.view.InformationInputItemView;
import com.idhub.wallet.me.information.view.InformationSelectItemView;

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
    private InformationInputItemView mPhoneNumberView;
    private InformationInputItemView mTaxIdView;
    private InformationInputItemView mSSNView;
    private InformationInputItemView mAddressView;
    private InformationInputItemView mEmailView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_idhub_information);
        initView();
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle("Authentication Information");
        mLastNameView = findViewById(R.id.last_name);
        mLastNameView.setData("姓氏", "请输入姓氏");
        mFirstNameView = findViewById(R.id.first_name);
        mFirstNameView.setData("名", "请输入名字");
        mBirthdayView = findViewById(R.id.birthday);
        mBirthdayView.setData("生日", "点击选择生日");
        mBirthdayView.setOnClickListener(this);
        mNationalityView = findViewById(R.id.nationality);
        mNationalityView.setData("国籍", "点击选择国籍");
        mNationalityView.setOnClickListener(this);
        mCountryOfResidenceView = findViewById(R.id.country_of_residence);
        mCountryOfResidenceView.setData("居住国", "请输入居住国");
        mCountryOfResidenceView.setOnClickListener(this);

        mIdNumberView = findViewById(R.id.id_number);
        mIdNumberView.setData("身份证件号码", "请输入身份证件号码");
        mPassportNumberView = findViewById(R.id.passport_number);
        mPassportNumberView.setData("护照号码", "请输入护照号码");
        mPhoneNumberView = findViewById(R.id.phone_number);
        mPhoneNumberView.setData("手机号", "请输入手机号");
        mTaxIdView = findViewById(R.id.tax_id);
        mTaxIdView.setData("纳税号", "请输入纳税号");
        mSSNView = findViewById(R.id.ssn);
        mSSNView.setData("社保号", "请输入社保号");
        mAddressView = findViewById(R.id.address);
        mAddressView.setData("地址", "请输入地址");
        mEmailView = findViewById(R.id.email);
        mEmailView.setData("邮箱", "请输入邮箱");
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
                selectNationlity(mNationalityView);
                break;
            case R.id.country_of_residence:
                selectNationlity(mCountryOfResidenceView);
                break;
        }
    }

    private void selectNationlity(InformationSelectItemView informationSelectItemView) {
        CountryPickerDialog countryPicker =
                new CountryPickerDialog(UploadIDHubInformationActivity.this, new CountryPickerCallbacks() {
                    @Override
                    public void onCountrySelected(Country country) {
                        informationSelectItemView.setInformation(country.getCountryName(UploadIDHubInformationActivity.this));
                    }
                }, true, 0);
        countryPicker.show();
    }

    private void selectBirthday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        DatePicker mDatePicker = new DatePicker(this, time -> { // 回调接口，获得选中的时间
            mBirthdayView.setInformation(time);
        }, "1900-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        mDatePicker.showSpecificTime(true); // 不显示时和分
        mDatePicker.setIsLoop(false);
        mDatePicker.show(now);
    }
}
