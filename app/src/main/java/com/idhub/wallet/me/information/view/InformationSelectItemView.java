package com.idhub.wallet.me.information.view;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.idhub.wallet.R;

public class InformationSelectItemView extends ConstraintLayout {

    private TextView mInformationEtView;
    private TextView mTitleView;

    public InformationSelectItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mInformationEtView = findViewById(R.id.tv_information);
        mTitleView = findViewById(R.id.tv_title);
    }

    public void setData(String title,String hint){
        mTitleView.setText(title);
        mInformationEtView.setHint(hint);
    }

    public void setInformation(String information){
        mInformationEtView.setText(information);
    }

    public String getInformation(){
        return mInformationEtView.getText().toString();
    }
}
