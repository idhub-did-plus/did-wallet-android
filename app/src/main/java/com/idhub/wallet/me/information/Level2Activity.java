package com.idhub.wallet.me.information;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.idhub.wallet.R;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.me.information.view.InformationInputItemView;

public class Level2Activity extends AppCompatActivity{

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
        setContentView(R.layout.activity_level2);
        initView();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, Level2Activity.class);
        context.startActivity(intent);
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle("Authentication Information");
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

}
