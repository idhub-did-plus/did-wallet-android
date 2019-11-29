package com.idhub.wallet.utils;

public class QRcodeAnalysisUtils {
    public static final String CONTRACT_ADDRESS = "contractAddress=";
    public static final String DECIMAL = "decimal=";
    public static final String VALUE = "value=";

//    public static void handleQRcode(Context context,String code) {
//        if (code.startsWith(QRCodeType.ETHERUM_TRANSACTION)) {
//            String address = code.substring(QRCodeType.ETHERUM_TRANSACTION.length(), code.indexOf("?"));
//            String contractAddress = "";
//            if (code.contains(CONTRACT_ADDRESS)) {
//                int contractAddressBeginIndex = code.indexOf(CONTRACT_ADDRESS) + CONTRACT_ADDRESS.length();
//                contractAddress = code.substring(contractAddressBeginIndex, code.indexOf("&", contractAddressBeginIndex));
//            }
//            String decimal = "";
//            if (code.contains(DECIMAL)) {
//                int decimalBeginIndex = code.indexOf(DECIMAL) + DECIMAL.length();
//                decimal = code.substring(decimalBeginIndex, code.indexOf("&", decimalBeginIndex));
//            }
//            String value = "";
//            if (code.contains(VALUE)) {
//                int valueBeginIndex = code.indexOf(VALUE) + VALUE.length();
//                value = code.substring(valueBeginIndex);
//            }
//
//            if (!TextUtils.isEmpty(contractAddress)) {
//                String node = WalletNoteSharedPreferences.getInstance().getNode();
//                AssetsModel assetsModel = null;
//                if (node.equals(WalletNodeManager.MAINNET)) {
//                    assetsModel = new AssetsModelDbManager().queryByMainContractAddressKeysync(contractAddress);
//                } else {
//                    assetsModel = new AssetsModelDbManager().queryByRopstenContractAddressKeysync(contractAddress);
//                }
//                if (assetsModel == null) {
//                    ToastUtils.showLongToast(context.getString(R.string.wallet_not_found_assets));
//                    return;
//                }
//                assetsModel.value = value;
//                assetsModel.toAddress = address;
//                SendActivity.startAction(context, assetsModel);
//            }else {
//                AssetsModel assetsModel = new AssetsModel();
//                assetsModel.value = value;
//                assetsModel.toAddress = address;
//                assetsModel.setType(TransactionTokenType.ETH_NAME);
//                assetsModel.setName(TransactionTokenType.ETH_NAME);
//                assetsModel.setDecimals("18");
//                assetsModel.setSymbol(TransactionTokenType.ETH_NAME);
//                SendActivity.startAction(context, assetsModel);
//            }
//        } else {
//
//        }
//    }
}
