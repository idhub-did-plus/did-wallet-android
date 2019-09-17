package com.idhub.wallet.me.information;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletVipSharedPreferences;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletVipStateObservable;
import com.idhub.wallet.me.VipStateType;
import com.idhub.wallet.me.information.view.InformationInputItemView;
import com.idhub.wallet.utils.ToastUtils;

public class Level3Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView applyBtn;
    private RadioGroup mRadioGroup;
    private TextView mIdhubVip;
    private String HIGH_INCOME = "high_income";
    private String HIGH_ASSETS = "high_assets";

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
        titleLayout.setTitle(getString(R.string.wallet_qualified_investor));
        mIdhubVip = findViewById(R.id.tv_vip);
        applyBtn = findViewById(R.id.tv_apply);
        mRadioGroup = findViewById(R.id.qualified_investor_condition);
        initData();
    }

    private void initData() {
        String state = WalletVipSharedPreferences.getInstance().getQualifiedInvestorVipState();
        if (VipStateType.NO_APPLY_FOR.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_apply_for));
            applyBtn.setOnClickListener(this);
        } else if (VipStateType.APPLY_FOR_ING.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_apply_for_ing));
            applyBtn.setBackgroundResource(R.drawable.wallet_shape_button_grey);
            setApplyContent();
        } else if (VipStateType.HAVE_APPLY_FOR.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_have_apply_for));
            applyBtn.setBackgroundResource(R.drawable.wallet_shape_button_grey);
            setApplyContent();
        }
    }

    private void setApplyContent(){
        String content = WalletVipSharedPreferences.getInstance().getQualifiedInvestorVipContent();
        mIdhubVip.setVisibility(View.VISIBLE);
        if (HIGH_INCOME.equals(content)) {
            mIdhubVip.setText(getString(R.string.wallet_qualified_investor_condition_first));
        } else if (HIGH_ASSETS.equals(content)) {
            mIdhubVip.setText(getString(R.string.wallet_qualified_investor_condition_second));
        }
        mRadioGroup.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_apply:
                //请求，加载进行申请
                int checkedRadioButtonId = mRadioGroup.getCheckedRadioButtonId();
                Log.e("LYW", "onClick: " +checkedRadioButtonId );
                String type = "";
                if (checkedRadioButtonId == R.id.qualified_investor_condition_first) {
                    type = HIGH_INCOME;
                } else if (checkedRadioButtonId == R.id.qualified_investor_condition_second) {
                    type = HIGH_ASSETS;
                }else {
                    ToastUtils.showShortToast(getString(R.string.wallet_select_item_apply));
                    return;
                }
                WalletVipSharedPreferences.getInstance().setQualifiedInvestorVipContent(type);
                WalletVipSharedPreferences.getInstance().setQualifiedInvestorVipState(VipStateType.APPLY_FOR_ING);
                initData();
                WalletVipStateObservable.getInstance().update();
                break;
        }
    }
}
