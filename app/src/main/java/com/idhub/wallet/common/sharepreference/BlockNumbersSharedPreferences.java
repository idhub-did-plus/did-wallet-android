package com.idhub.wallet.common.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.idhub.base.App;

public class BlockNumbersSharedPreferences {
    private static BlockNumbersSharedPreferences mBlockNumbersSharedPreferences;
    private static SharedPreferences sharedPreferences;
    private String FILE_NAME = "wallet_block_number";
    private final String WALLET_BLOCK_NUMBERS = "wallet_block_numbers";

    private BlockNumbersSharedPreferences() {
        sharedPreferences = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized BlockNumbersSharedPreferences getInstance() {
        if (mBlockNumbersSharedPreferences == null) {
            mBlockNumbersSharedPreferences = new BlockNumbersSharedPreferences();
        }
        return mBlockNumbersSharedPreferences;
    }

    public long getBlockNumber() {
        return sharedPreferences.getLong(WALLET_BLOCK_NUMBERS, 0);
    }

    public void setBlockNumber(long blockNumber) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(WALLET_BLOCK_NUMBERS, blockNumber).apply();
    }
}
