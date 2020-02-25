package com.idhub.wallet.assets.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.idhub.base.ui.UIUtils;

public class StaggeredDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Context context;
    private int interval;

    public StaggeredDividerItemDecoration(Context context, int interval) {
        this.context = context;
        this.interval = interval;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
//        int interval = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
//                this.interval, context.getResources().getDisplayMetrics());
        int interval = UIUtils.getInstance().getHeight(this.interval);
//        // 中间间隔
//        if (position % 2 == 0) {
//            outRect.left = 0;
//        } else {
        // item为奇数位，设置其左间隔为5dp
        outRect.left = interval / 2;
        outRect.right = interval / 2;
//        }
        // 下方间隔
        outRect.bottom = interval;
    }
}
