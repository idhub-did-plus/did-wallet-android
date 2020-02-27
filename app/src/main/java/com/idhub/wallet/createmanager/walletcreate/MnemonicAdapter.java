package com.idhub.wallet.createmanager.walletcreate;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.R;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.common.recyclerview.RecyclerViewHolder;

public class MnemonicAdapter extends BaseRecyclerAdapter<String> {

    private Context context;

    public MnemonicAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void bindViewWithHolder(int position, RecyclerViewHolder holder, String item) {
        TextView textView = holder.getTextView(R.id.mnemonic_preview_item);
        ViewCalculateUtil.setViewConstraintLayoutParam(textView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 10, 0, 7, 7);
        ViewCalculateUtil.setViewPadding(textView, 5, 5, 0, 0);
        ViewCalculateUtil.setTextSize(textView, 15);
        textView.setTextColor(context.getResources().getColor(R.color.wallet_text_black));
        textView.setText(item);
    }

    @Override
    public int getItemLayoutId(int layoutId) {
        return R.layout.wallet_mnemonic_preview_item;
    }
}
