package com.idhub.base.node;

import android.content.Context;
import android.content.SharedPreferences;

import com.idhub.base.App;

public class WalletNoteSharedPreferences {
    private static WalletNoteSharedPreferences sWalletNoteSharedPreferences;
    private static SharedPreferences sharedPreferences;
    private String FILE_NAME = "wallet_other_info";

    private String WALLET_NODE = "wallet_node";

    private WalletNoteSharedPreferences() {
        sharedPreferences = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized WalletNoteSharedPreferences getInstance() {
        if (sWalletNoteSharedPreferences == null) {
            sWalletNoteSharedPreferences = new WalletNoteSharedPreferences();
        }
        return sWalletNoteSharedPreferences;
    }


    public String getNode() {
        return sharedPreferences.getString(WALLET_NODE, WalletNodeManager.nodes.get(1));
    }

    public boolean setNode(String node) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
      return   edit.putString(WALLET_NODE, node).commit();
    }

}
