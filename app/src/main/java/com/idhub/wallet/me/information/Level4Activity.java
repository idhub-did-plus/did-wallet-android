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
import com.idhub.wallet.utils.ToastUtils;

public class Level4Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView applyBtn;
    private TextView mIdhubVip;
    private RadioGroup mRadioGroup;
    private String FIRST = "first";
    private String SECOND = "second";
    private String THIRD = "third";
    private String FOURTH = "fourth";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level4);
        initView();
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_qualified_purchaser));
        mIdhubVip = findViewById(R.id.tv_vip);
        applyBtn = findViewById(R.id.tv_apply);
        mRadioGroup = findViewById(R.id.qualified_investor_condition);


        initData();
    }
    private void initData() {
        String state = WalletVipSharedPreferences.getInstance().getQualifiedPurchaserVipState();
        if (VipStateType.NO_APPLY_FOR.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_apply_for));
            applyBtn.setOnClickListener(this);
        } else if (VipStateType.APPLY_FOR_ING.equals(state)) {
            applyBtn.setText(getString(R.string.wallet_apply_for_ing));
            setApplyContent();
            applyBtn.setBackgroundResource(R.drawable.wallet_shape_button_grey);
        } else if (VipStateType.HAVE_APPLY_FOR.equals(state)) {
            setApplyContent();
            applyBtn.setText(getString(R.string.wallet_have_apply_for));
            applyBtn.setBackgroundResource(R.drawable.wallet_shape_button_grey);
        }
    }
    private void setApplyContent(){
        String content = WalletVipSharedPreferences.getInstance().getQualifiedPurchaserVipContent();
        mIdhubVip.setVisibility(View.VISIBLE);
        if (FIRST.equals(content)) {
            mIdhubVip.setText(getString(R.string.wallet_qualified_purchaser_condition_first));
        } else if (SECOND.equals(content)) {
            mIdhubVip.setText(getString(R.string.wallet_qualified_purchaser_condition_second));
        }else if (THIRD.equals(content)){
            mIdhubVip.setText(getString(R.string.wallet_qualified_purchaser_condition_third));
        } else if (FOURTH.equals(content)) {
            mIdhubVip.setText(getString(R.string.wallet_qualified_purchaser_condition_fourth));
        }
        mRadioGroup.setVisibility(View.GONE);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, Level4Activity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_apply:
                //请求，加载进行申请
                int checkedRadioButtonId = mRadioGroup.getCheckedRadioButtonId();
                Log.e("LYW", "onClick: " +checkedRadioButtonId );
                String type = "";
                if (checkedRadioButtonId == R.id.qualified_purchaser_condition_first) {
                    type = FIRST;
                } else if (checkedRadioButtonId == R.id.qualified_purchaser_condition_second) {
                    type = SECOND;
                }else if (checkedRadioButtonId == R.id.qualified_purchaser_condition_third){
                    type = THIRD;
                }else if (checkedRadioButtonId == R.id.qualified_purchaser_condition_fourth){
                    type = FOURTH;
                }else{
                    ToastUtils.showShortToast(getString(R.string.wallet_select_item_apply));
                    return;
                }
                WalletVipSharedPreferences.getInstance().setQualifiedPurchaserVipContent(type);
                WalletVipSharedPreferences.getInstance().setQualifiedPurchaserVipState(VipStateType.APPLY_FOR_ING);
                initData();
                WalletVipStateObservable.getInstance().update();
                break;
        }
    }
}
