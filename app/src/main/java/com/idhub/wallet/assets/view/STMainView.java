package com.idhub.wallet.assets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.base.greendao.entity.AssetsModel;
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
    private TextView firstNameView;
    private TextView firstValueView;
    private View firstIconView;
    private TextView secondNameView;
    private TextView secondValueView;
    private TextView thirdValueView;
    private TextView thirdNameView;
    private TextView fourthNameView;
    private TextView fourthValueView;
    private View fifthIconView;
    private TextView fifthValueView;
    private TextView fifthNameView;
    private TextView sixthNameView;
    private TextView sixthValueView;
    private TextView seventhNameView;
    private TextView seventhValueView;
    private View seventhIconView;
    private View fourthIconView;

    public STMainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        firstStView = findViewById(R.id.first_st);
        firstNameView = findViewById(R.id.first_name);
        firstValueView = findViewById(R.id.first_value);
        firstIconView = findViewById(R.id.first_icon);
//        ViewCalculateUtil.setTextSize(firstNameView,16);
//        ViewCalculateUtil.setTextSize(firstValueView,14);
        secondStView = findViewById(R.id.second_st);
        secondNameView = findViewById(R.id.second_name);
        secondValueView = findViewById(R.id.second_value);
//        ViewCalculateUtil.setTextSize(secondNameView,16);
//        ViewCalculateUtil.setTextSize(secondValueView,14);
        thirdStView = findViewById(R.id.third_st);
        thirdNameView = findViewById(R.id.third_name);
        thirdValueView = findViewById(R.id.third_value);
//        ViewCalculateUtil.setTextSize(thirdNameView,16);
//        ViewCalculateUtil.setTextSize(thirdValueView,14);
        fourthStView = findViewById(R.id.fourth_st);
        fourthNameView = findViewById(R.id.fourth_name);
        fourthValueView = findViewById(R.id.fourth_value);
        fourthIconView = findViewById(R.id.fourth_icon);
//        ViewCalculateUtil.setTextSize(fourthNameView,16);
//        ViewCalculateUtil.setTextSize(fourthValueView,14);
        fifthStView = findViewById(R.id.fifth_st);
        fifthNameView = findViewById(R.id.fifth_name);
        fifthValueView = findViewById(R.id.fifth_value);
        fifthIconView = findViewById(R.id.fifth_icon);
//        ViewCalculateUtil.setTextSize(fifthNameView,12);
//        ViewCalculateUtil.setTextSize(fifthValueView,12);
        sixthStView = findViewById(R.id.sixth_st);
        sixthNameView = findViewById(R.id.sixth_name);
        sixthValueView = findViewById(R.id.sixth_value);
//        ViewCalculateUtil.setTextSize(sixthNameView,12);
//        ViewCalculateUtil.setTextSize(sixthValueView,12);

        seventhStView = findViewById(R.id.seventh_st);
        seventhNameView = findViewById(R.id.seventh_name);
        seventhValueView = findViewById(R.id.seventh_value);
        seventhIconView = findViewById(R.id.seventh_icon);
//        ViewCalculateUtil.setTextSize(sixthNameView,14);
//        ViewCalculateUtil.setTextSize(sixthValueView,12);


    }

    public void setData(List<AssetsModel> assetsModels) {
        int size = assetsModels.size();
        if (size <= 0) {
            return;
        }
        switch (size) {
            case 1:
                AssetsModel assetsModel = assetsModels.get(0);
                firstNameView.setText(assetsModel.getSymbol());
                firstStView.setVisibility(VISIBLE);
                firstStView.setBackgroundResource(R.drawable.wallet_st_shape_first);
//                ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 343, 87, 8, 0, 16, 16);
                secondStView.setVisibility(View.GONE);
                thirdStView.setVisibility(View.GONE);
                fourthStView.setVisibility(View.GONE);
                fifthStView.setVisibility(View.GONE);
                sixthStView.setVisibility(View.GONE);
                seventhStView.setVisibility(View.GONE);
                break;
            case 2:
                firstStView.setVisibility(VISIBLE);
                secondStView.setVisibility(View.GONE);
                thirdStView.setVisibility(View.GONE);
                fourthStView.setVisibility(View.VISIBLE);
                fifthStView.setVisibility(View.GONE);
                sixthStView.setVisibility(View.GONE);
                seventhStView.setVisibility(View.GONE);
                fourthIconView.setVisibility(VISIBLE);
                firstStView.setBackgroundResource(R.drawable.wallet_st_shape_first_left_radius);
                fourthStView.setBackgroundResource(R.drawable.wallet_st_shape_seventh_right_radius);
//                ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 169, 87, 8, 0, 16, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(fourthStView, 169, 87, 0, 0, 5, 16);
                firstNameView.setText(assetsModels.get(0).getSymbol());
                fourthNameView.setText(assetsModels.get(1).getSymbol());
                break;
            case 3:
                firstStView.setVisibility(VISIBLE);
                secondStView.setVisibility(View.VISIBLE);
                thirdStView.setVisibility(View.GONE);
                fourthStView.setVisibility(View.VISIBLE);
                fifthStView.setVisibility(View.GONE);
                sixthStView.setVisibility(View.GONE);
                seventhStView.setVisibility(View.GONE);
                fourthIconView.setVisibility(VISIBLE);
                firstStView.setBackgroundResource(R.drawable.wallet_st_shape_first_top_left_radius);
                fourthStView.setBackgroundResource(R.drawable.wallet_st_shape_seventh_top_right_radius);
                secondStView.setBackgroundResource(R.drawable.wallet_st_shape_second_bottom_radius);
//                ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 150, 87, 8, 0, 16, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(secondStView, LayoutParams.MATCH_PARENT, 64, 5, 0, 16, 16);
//                ViewCalculateUtil.setViewConstraintLayoutParam(fourthStView, 188, 87, 0, 0, 5, 16);
                firstNameView.setText(assetsModels.get(0).getSymbol());
                fourthNameView.setText(assetsModels.get(1).getSymbol());
                secondNameView.setText(assetsModels.get(2).getSymbol());
                break;
            case 4:
                firstStView.setVisibility(VISIBLE);
                secondStView.setVisibility(View.VISIBLE);
                thirdStView.setVisibility(View.VISIBLE);
                fourthStView.setVisibility(View.VISIBLE);
                fifthStView.setVisibility(View.GONE);
                sixthStView.setVisibility(View.GONE);
                seventhStView.setVisibility(View.GONE);
                fourthIconView.setVisibility(VISIBLE);
                firstStView.setBackgroundResource(R.drawable.wallet_st_shape_first_top_left_radius);
                fourthStView.setBackgroundResource(R.drawable.wallet_st_shape_seventh_top_right_radius);
                secondStView.setBackgroundResource(R.drawable.wallet_st_shape_second_bottom_left_radius);
                thirdStView.setBackgroundResource(R.drawable.wallet_st_shape_third_bottom_right_radius);
//                ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 150, 87, 8, 0, 16, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(secondStView, 184, 64, 5, 0, 0, 16);
//                ViewCalculateUtil.setViewConstraintLayoutParam(thirdStView, 154, 64, 0, 0, 5, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(fourthStView, 188, 87, 0, 0, 5, 16);
                firstNameView.setText(assetsModels.get(0).getSymbol());
                fourthNameView.setText(assetsModels.get(1).getSymbol());
                secondNameView.setText(assetsModels.get(2).getSymbol());
                thirdNameView.setText(assetsModels.get(3).getSymbol());
                break;
            case 5  :
                firstStView.setVisibility(VISIBLE);
                secondStView.setVisibility(View.VISIBLE);
                thirdStView.setVisibility(View.VISIBLE);
                fourthStView.setVisibility(View.VISIBLE);
                fifthStView.setVisibility(View.GONE);
                seventhStView.setBackgroundResource(R.drawable.wallet_st_shape_seventh_right_radius);
//                ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 140, 87, 8, 0, 16, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(secondStView, 112, 64, 5, 0, 0, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(thirdStView, 123, 64, 0, 0, 5, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(fourthStView, 95, 87, 0, 0, 5, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(seventhStView, 0, 156, 0, 0, 5, 16);
//                ViewCalculateUtil.setViewConstraintLayoutrightToRightParam(seventhValueView, -1);
//                ViewCalculateUtil.setViewConstraintLayoutLeftToLeftParam(seventhValueView, R.id.seventh_name);
//                ViewCalculateUtil.setViewConstraintLayoutParam(seventhValueView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 54, 0, 0, 0);

                sixthStView.setVisibility(View.GONE);
                seventhStView.setVisibility(View.VISIBLE);
                firstNameView.setText(assetsModels.get(0).getSymbol());
                fourthNameView.setText(assetsModels.get(1).getSymbol());
                secondNameView.setText(assetsModels.get(2).getSymbol());
                thirdNameView.setText(assetsModels.get(3).getSymbol());
                seventhNameView.setText(assetsModels.get(4).getSymbol());
                break;
            case 6:
                firstStView.setVisibility(VISIBLE);
                secondStView.setVisibility(View.VISIBLE);
                thirdStView.setVisibility(View.VISIBLE);
                fourthStView.setVisibility(View.VISIBLE);
                fifthStView.setVisibility(View.VISIBLE);
                sixthStView.setVisibility(View.VISIBLE);
                sixthStView.setBackgroundResource(R.drawable.wallet_st_shape_sixth_right_radius);
//                ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 151, 87, 8, 0, 16, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(secondStView, 109, 64, 5, 0, 0, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(thirdStView, 107, 64, 0, 0, 5, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(fourthStView, 126, 87, 0, 0, 5, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(fifthStView, 56, 64, 0, 0, 5, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(sixthStView, 56, 156, 0, 0, 5, 0);
                seventhStView.setVisibility(View.GONE);
                firstNameView.setText(assetsModels.get(0).getSymbol());
                fourthNameView.setText(assetsModels.get(1).getSymbol());
                secondNameView.setText(assetsModels.get(2).getSymbol());
                thirdNameView.setText(assetsModels.get(3).getSymbol());
                fifthNameView.setText(assetsModels.get(4).getSymbol());
                sixthNameView.setText(assetsModels.get(5).getSymbol());
                break;
            case 7:
            default:
                firstStView.setVisibility(VISIBLE);
                secondStView.setVisibility(View.VISIBLE);
                thirdStView.setVisibility(View.VISIBLE);
                fourthStView.setVisibility(View.VISIBLE);
                fifthStView.setVisibility(View.VISIBLE);
                sixthStView.setVisibility(View.VISIBLE);
                seventhStView.setVisibility(View.VISIBLE);
//                ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 140, 87, 8, 0, 16, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(secondStView, 109, 64, 5, 0, 0, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(thirdStView, 107, 64, 0, 0, 5, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(fourthStView, 76, 87, 0, 0, 5, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(fifthStView, 56, 95, 0, 0, 5, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(sixthStView, 56, 95, 0, 0, 5, 0);
//                ViewCalculateUtil.setViewConstraintLayoutParam(seventhStView, 0, 56, 0, 0, 5, 16);
                firstNameView.setText(assetsModels.get(0).getSymbol());
                fourthNameView.setText(assetsModels.get(1).getSymbol());
                secondNameView.setText(assetsModels.get(2).getSymbol());
                thirdNameView.setText(assetsModels.get(3).getSymbol());
                fifthNameView.setText(assetsModels.get(4).getSymbol());
                sixthNameView.setText(assetsModels.get(5).getSymbol());
                seventhNameView.setText(assetsModels.get(6).getSymbol());
                break;
        }
    }
}
