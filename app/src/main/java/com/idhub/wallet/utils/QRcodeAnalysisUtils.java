package com.idhub.wallet.utils;

import android.content.Context;
import android.text.TextUtils;

import com.idhub.wallet.App;
import com.idhub.wallet.R;
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

    public static void handleQRcode(Context context,String code) {
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
                    ToastUtils.showLongToast(context.getString(R.string.wallet_not_found_assets));
                    return;
                }
                assetsModel.value = value;
                assetsModel.toAddress = address;
                SendActivity.startAction(context, assetsModel);
            }else {
                AssetsModel assetsModel = new AssetsModel();
                assetsModel.value = value;
                assetsModel.toAddress = address;
                assetsModel.setType(AssetsDefaultType.ETH_NAME);
                assetsModel.setName(AssetsDefaultType.ETH_NAME);
                assetsModel.setDecimals("18");
                assetsModel.setSymbol(AssetsDefaultType.ETH_NAME);
                SendActivity.startAction(context, assetsModel);
            }
        } else {

        }
    }
}
