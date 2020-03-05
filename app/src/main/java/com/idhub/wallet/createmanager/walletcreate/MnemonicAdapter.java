package com.idhub.wallet.createmanager.walletcreate;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.wallet.R;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.common.recyclerview.RecyclerViewHolder;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class MnemonicAdapter extends BaseRecyclerAdapter<String> {

    private Context context;

    public MnemonicAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void bindViewWithHolder(int position, RecyclerViewHolder holder, String item) {
        TextView textView = holder.getTextView(R.id.mnemonic_preview_item);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        textView.setPadding(0, AutoSizeUtils.dp2px(context,7), 0, AutoSizeUtils.dp2px(context,7));

        textView.setTextColor(context.getResources().getColor(R.color.wallet_text_black));
        textView.setText(item);
    }

    @Override
    public int getItemLayoutId(int layoutId) {
        return R.layout.wallet_mnemonic_preview_item;
    }
}
