package com.idhub.wallet.me.information;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletVipSharedPreferences;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletVipStateObservable;
import com.idhub.wallet.me.VipStateType;
import com.idhub.wallet.me.information.view.InformationInputItemView;
import com.idhub.wallet.utils.ToastUtils;

public class Level2Activity extends AppCompatActivity implements View.OnClickListener {


    private TextView applyBtn;

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

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_idhub_super_vip));
        applyBtn = findViewById(R.id.tv_apply);
        initData();
    }
    private void initData() {
        String state = WalletVipSharedPreferences.getInstance().getIdhubSuperVipState();
        if (VipStateType.NO_APPLY_FOR.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_apply_for));
            applyBtn.setOnClickListener(this);
        } else if (VipStateType.APPLY_FOR_ING.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_apply_for_ing));
            applyBtn.setBackgroundResource(R.drawable.wallet_shape_button_grey);
        } else if (VipStateType.HAVE_APPLY_FOR.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_have_apply_for));
            applyBtn.setBackgroundResource(R.drawable.wallet_shape_button_grey);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_apply:
                //请求，加载进行申请
                WalletVipSharedPreferences instance = WalletVipSharedPreferences.getInstance();
                String idhubVipState = instance.getIdhubVipState();
                if (!VipStateType.HAVE_APPLY_FOR.equals(idhubVipState)) {
                    ToastUtils.showShortToast(getString(R.string.wallet_have_apply_for_idhub_vip));
                    return;
                }
                instance.setIdhubSuperVipState(VipStateType.APPLY_FOR_ING);
                initData();
                WalletVipStateObservable.getInstance().update();
                break;
        }
    }
}
