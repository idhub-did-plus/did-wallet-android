package com.idhub.wallet.utils;

import android.text.TextUtils;

import com.idhub.wallet.App;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.greendao.AssetsDefaultType;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.setting.WalletNodeManager;
import com.idhub.wallet.wallet.assets.AssetsType;
import com.idhub.wallet.wallet.mainfragment.QRCodeType;
import com.idhub.wallet.wallet.transaction.SendActivity;

public class QRcodeAnalysisUtils {
    public static final String CONTRACT_ADDRESS = "contractAddress=";
    public static final String DECIMAL = "decimal=";
    public static final String VALUE = "value=";

    public static void handleQRcode(String code) {
        if (code.startsWith(QRCodeType.ETHERUM_TRANSACTION)) {
            String address = code.substring(QRCodeType.ETHERUM_TRANSACTION.length(), code.indexOf("?"));
            String contractAddress = "";
            if (code.contains(CONTRACT_ADDRESS)) {
                int contractAddressBeginIndex = code.indexOf(CONTRACT_ADDRESS) + CONTRACT_ADDRESS.length();
                contractAddress = code.substring(contractAddressBeginIndex, code.indexOf("&", contractAddressBeginIndex));
            }
            String decimal = "";
            if (code.contains(DECIMAL)) {
                int decimalBeginIndex = code.indexOf(DECIMAL) + DECIMAL.length();
                decimal = code.substring(decimalBeginIndex, code.indexOf("&", decimalBeginIndex));
            }
            String value = "";
            if (code.contains(VALUE)) {
                int valueBeginIndex = code.indexOf(VALUE) + VALUE.length();
                value = code.substring(valueBeginIndex);
            }
            if (!TextUtils.isEmpty(contractAddress)) {
                String node = WalletOtherInfoSharpreference.getInstance().getNode();
                AssetsModel assetsModel = null;
                if (node.equals(WalletNodeManager.MAINNET)) {
                    assetsModel = new AssetsModelDbManager().queryByMainContractAddressKeysync(contractAddress);
                } else if (node.equals(WalletNodeManager.ROPSTEN)) {
                    assetsModel = new AssetsModelDbManager().queryByRopstenContractAddressKeysync(contractAddress);
                }
                if (assetsModel == null) {

                    return;
                }
//                SendActivity.startAction(App.getInstance(), mAssetsModel);
            }

        } else {

        }
    }
}
