package com.idhub.wallet.wallet.token;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.idhub.base.App;
import com.idhub.config.ConfigPropertiesUtils;
import com.idhub.wallet.R;
import com.idhub.base.greendao.entity.AssetsModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenTypeManager {

    public static Map<String, Integer> assetsMipmap = new HashMap<>();

    static {
        assetsMipmap.put("USDT", R.mipmap.wallet_usdt);
        assetsMipmap.put("IDHUB", R.mipmap.wallet_idhub);
        assetsMipmap.put("WETH", R.mipmap.wallet_weth);
        assetsMipmap.put("DAI", R.mipmap.wallet_dai);
        assetsMipmap.put("PETH", R.mipmap.wallet_peth);
        assetsMipmap.put("MKR", R.mipmap.wallet_mkr);
        assetsMipmap.put("USDC", R.mipmap.wallet_usdc);
        assetsMipmap.put("TUSD", R.mipmap.wallet_tusd);
    }

    public static List<AssetsModel> getAssetsList() {
        List<AssetsModel> assetsModels = new Gson().fromJson(ConfigPropertiesUtils.getAssets(App.getInstance()), new TypeToken<List<AssetsModel>>() {}.getType());
        return assetsModels;
    }
}
