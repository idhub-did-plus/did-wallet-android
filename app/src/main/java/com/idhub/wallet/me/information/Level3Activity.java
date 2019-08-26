package com.idhub.wallet.me.information;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.idhub.wallet.R;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.me.information.view.InformationInputItemView;

public class Level3Activity extends AppCompatActivity {

    private InformationInputItemView mEmailConfirmView;
    private InformationInputItemView mSMSCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3);
        initView();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, Level3Activity.class);
        context.startActivity(intent);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle("Authentication Information");
        mSMSCodeView = findViewById(R.id.sms_code);
        mSMSCodeView.setData("短信验证码","请输入短信验证码");
        mEmailConfirmView = findViewById(R.id.email_confirmation);
        mEmailConfirmView.setData("邮箱验证码","请输入邮箱验证码");

    }
}
