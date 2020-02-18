package com.idhub.wallet.assets.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.R;

import java.util.List;

public class STMainView extends ConstraintLayout {

    private View firstStView;
    private View secondStView;
    private View thirdStView;
    private View fourthStView;
    private View fifthStView;
    private View sixthStView;
    private View seventhStView;

    public STMainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        firstStView = findViewById(R.id.first_st);
        secondStView = findViewById(R.id.second_st);
        thirdStView = findViewById(R.id.third_st);
        fourthStView = findViewById(R.id.fourth_st);
        fifthStView = findViewById(R.id.fifth_st);
        sixthStView = findViewById(R.id.sixth_st);
        seventhStView = findViewById(R.id.seventh_st);
    }

    public void setData(List<AssetsModel> assetsModels) {
        int size = assetsModels.size();
        if (size <= 0) {
            return;
        }
        switch (size) {
            case 1:
                firstStView.setVisibility(VISIBLE);
                firstStView.setBackgroundResource(R.drawable.wallet_st_shape_first);
                ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 343, 156, 8, 0, 16, 16);
                secondStView.setVisibility(View.GONE);
                thirdStView.setVisibility(View.GONE);
                fourthStView.setVisibility(View.GONE);
                fifthStView.setVisibility(View.GONE);
                sixthStView.setVisibility(View.GONE);
                seventhStView.setVisibility(View.GONE);
                break;
            case 2:
                firstStView.setVisibility(VISIBLE);
                secondStView.setVisibility(View.VISIBLE);
                firstStView.setBackgroundResource(R.drawable.wallet_st_shape_first_top_radius);
                secondStView.setBackgroundResource(R.drawable.wallet_st_shape_second_bottom_radius);
                ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 343, 87, 8, 0, 16, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(secondStView, 343, 64, 5, 0, 0, 0);
                thirdStView.setVisibility(View.GONE);
                fourthStView.setVisibility(View.GONE);
                fifthStView.setVisibility(View.GONE);
                sixthStView.setVisibility(View.GONE);
                seventhStView.setVisibility(View.GONE);
                break;
            case 3:
                firstStView.setVisibility(VISIBLE);
                secondStView.setVisibility(View.VISIBLE);
                thirdStView.setVisibility(View.VISIBLE);
                ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 140, 87, 8, 0, 16, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(secondStView, 109, 64, 5, 0, 0, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(thirdStView, 107, 64, 0, 0, 5, 0);
                fourthStView.setVisibility(View.GONE);
                fifthStView.setVisibility(View.GONE);
                sixthStView.setVisibility(View.GONE);
                seventhStView.setVisibility(View.GONE);
                break;
            case 4:
                firstStView.setVisibility(VISIBLE);
                secondStView.setVisibility(View.VISIBLE);
                thirdStView.setVisibility(View.VISIBLE);
                fourthStView.setVisibility(View.VISIBLE);
                ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 140, 87, 8, 0, 16, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(secondStView, 109, 64, 5, 0, 0, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(thirdStView, 107, 64, 0, 0, 5, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(fourthStView, 0, 0, 0, 0, 5, 0);
                fifthStView.setVisibility(View.GONE);
                sixthStView.setVisibility(View.GONE);
                seventhStView.setVisibility(View.GONE);
                break;
            case 5:
                firstStView.setVisibility(VISIBLE);
                secondStView.setVisibility(View.VISIBLE);
                thirdStView.setVisibility(View.VISIBLE);
                fourthStView.setVisibility(View.VISIBLE);
                fifthStView.setVisibility(View.VISIBLE);
                ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 140, 87, 8, 0, 16, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(secondStView, 109, 64, 5, 0, 0, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(thirdStView, 107, 64, 0, 0, 5, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(fourthStView, 0, 0, 0, 0, 5, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(fifthStView, 56, 95, 0, 0, 5, 0);
                sixthStView.setVisibility(View.GONE);
                seventhStView.setVisibility(View.GONE);
                break;
            case 6:
                firstStView.setVisibility(VISIBLE);
                secondStView.setVisibility(View.VISIBLE);
                thirdStView.setVisibility(View.VISIBLE);
                fourthStView.setVisibility(View.VISIBLE);
                fifthStView.setVisibility(View.VISIBLE);
                sixthStView.setVisibility(View.VISIBLE);
                ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 140, 87, 8, 0, 16, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(secondStView, 109, 64, 5, 0, 0, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(thirdStView, 107, 64, 0, 0, 5, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(fourthStView, 0, 0, 0, 0, 5, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(fifthStView, 56, 95, 0, 0, 5, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(sixthStView, 56, 95, 0, 0, 5, 0);
                seventhStView.setVisibility(View.GONE);
                break;
            case 7:
            default:
                ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 140, 87, 8, 0, 16, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(secondStView, 109, 64, 5, 0, 0, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(thirdStView, 107, 64, 0, 0, 5, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(fourthStView, 0, 0, 0, 0, 5, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(fifthStView, 56, 95, 0, 0, 5, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(sixthStView, 56, 95, 0, 0, 5, 0);
                ViewCalculateUtil.setViewConstraintLayoutParam(seventhStView, 0, 56, 0, 0, 0, 0);
                break;
        }
    }
}
