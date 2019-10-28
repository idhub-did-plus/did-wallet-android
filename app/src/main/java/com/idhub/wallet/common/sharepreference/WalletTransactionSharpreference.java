package com.idhub.wallet.common.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.idhub.base.App;

public class WalletTransactionSharpreference {
    private static WalletTransactionSharpreference sWalletOtherSharpreference;
    private static SharedPreferences sharedPreferences;
    private String FILE_NAME = "wallet_transaction";
    private String WALLET_GAS_PRICE = "wallet_gas_price";
    private String WALLET_GAS_ETH_LIMIT = "wallet_gas_eth_limit";
    private String WALLET_GAS_ERC20_LIMIT = "wallet_gas_erc20_limit";
    private String WALLET_GAS_ST_LIMIT = "wallet_gas_st_limit";

    private WalletTransactionSharpreference() {
        sharedPreferences = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized WalletTransactionSharpreference getInstance() {
        if (sWalletOtherSharpreference == null) {
            sWalletOtherSharpreference = new WalletTransactionSharpreference();
        }
        return sWalletOtherSharpreference;
    }

    public String getGasPrice() {
        return sharedPreferences.getString(WALLET_GAS_PRICE, "1000000000");
    }

    public void setGasPrice(String gasPrice) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_GAS_PRICE, gasPrice).apply();
    }

    public String getEthGasLimit() {
        return sharedPreferences.getString(WALLET_GAS_ETH_LIMIT, "25200");
    }

    public void setEthGasLimit(String gasLimit) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_GAS_ETH_LIMIT, gasLimit).apply();
    }

    public String getERC20GasLimit() {
        return sharedPreferences.getString(WALLET_GAS_ERC20_LIMIT, "72000");
    }

    public void setERC20GasLimit(String gasLimit) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_GAS_ERC20_LIMIT, gasLimit).apply();
    }

    public String getSTGasLimit() {
        return sharedPreferences.getString(WALLET_GAS_ST_LIMIT, "720000");
    }

    public void setSTGasLimit(String gasLimit) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_GAS_ST_LIMIT, gasLimit).apply();
    }

}
