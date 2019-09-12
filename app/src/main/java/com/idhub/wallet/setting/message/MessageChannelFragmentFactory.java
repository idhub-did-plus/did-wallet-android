package com.idhub.wallet.setting.message;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.idhub.wallet.R;
import com.idhub.wallet.setting.message.idhub.IdHubMessageFragment;
import com.idhub.wallet.setting.message.moretransaction.fragment.ERC20TransactionFragment;
import com.idhub.wallet.setting.message.moretransaction.fragment.EthTransactionFragment;
import com.idhub.wallet.setting.message.moretransaction.fragment.STTransactionFragment;
import com.idhub.wallet.setting.message.transaction.TransactionMessageFragment;

public class MessageChannelFragmentFactory {
    private static String TAG = MessageChannelFragmentFactory.class.getName();

    public static Fragment createChannelFragment(Context context, String title) {
        if (context.getResources().getString(R.string.wallet_message_transaction).equals(title)) {
            return new TransactionMessageFragment();
        } else if (context.getResources().getString(R.string.wallet_message_idhub).equals(title)) {
            return new IdHubMessageFragment();
        } else if (context.getString(R.string.wallet_ETH).equals(title)) {
            return new EthTransactionFragment();
        } else if (context.getString(R.string.wallet_ERC20).equals(title)) {
            return new ERC20TransactionFragment();
        } else if (context.getString(R.string.wallet_ST).equals(title)) {
            return new STTransactionFragment();
        } else {
            return new TransactionMessageFragment();
        }
    }

}
