package com.idhub.wallet.wallet.assets;

import com.idhub.wallet.R;
import com.idhub.wallet.greendao.AssetsDefaultType;
import com.idhub.base.node.AssetsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetsType {

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

    public static List<AssetsModel> getAssetsList(){
        ArrayList<AssetsModel> assetsModels = new ArrayList<>();
        AssetsModel assetsModelUSDT = new AssetsModel();
        assetsModelUSDT.setType(AssetsDefaultType.ERC20);
        assetsModelUSDT.setName("Tether USD");
        assetsModelUSDT.setDecimals("6");
        assetsModelUSDT.setSymbol("USDT");
        assetsModelUSDT.setMainContractAddress("0xdac17f958d2ee523a2206206994597c13d831ec7");
        assetsModels.add(assetsModelUSDT);

        AssetsModel assetsModelIDHUB = new AssetsModel();
        assetsModelIDHUB.setType(AssetsDefaultType.ERC20);
        assetsModelIDHUB.setName("Identity Hub Token");
        assetsModelIDHUB.setSymbol("IDHUB");
        assetsModelIDHUB.setDecimals("18");
        assetsModelIDHUB.setMainContractAddress("0x29f20242051accda50d52a7e272a5f23237e4696");
        assetsModelIDHUB.setRopstenContractAddress("0x4ede434043c47e9774cd7d28a040c28dd757ddfa");
        assetsModels.add(assetsModelIDHUB);

        AssetsModel assetsModelWETH = new AssetsModel();
        assetsModelWETH.setType(AssetsDefaultType.ERC20);
        assetsModelWETH.setName("Wrapped Ether");
        assetsModelWETH.setDecimals("18");
        assetsModelWETH.setSymbol("WETH");
        assetsModelWETH.setMainContractAddress("0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2");
        assetsModels.add(assetsModelWETH);

        AssetsModel assetsModelDAI = new AssetsModel();
        assetsModelDAI.setType(AssetsDefaultType.ERC20);
        assetsModelDAI.setName("Dai Stablecoin v1.0");
        assetsModelDAI.setSymbol("DAI");
        assetsModelDAI.setDecimals("18");
        assetsModelDAI.setMainContractAddress("0x89d24a6b4ccb1b6faa2625fe562bdd9a23260359");
        assetsModels.add(assetsModelDAI);

        AssetsModel assetsModelPETH = new AssetsModel();
        assetsModelPETH.setType(AssetsDefaultType.ERC20);
        assetsModelPETH.setName("Pooled Ether");
        assetsModelPETH.setDecimals("18");
        assetsModelPETH.setSymbol("PETH");
        assetsModelPETH.setMainContractAddress("0xf53ad2c6851052a81b42133467480961b2321c09");
        assetsModels.add(assetsModelPETH);

        AssetsModel assetsModelMaker = new AssetsModel();
        assetsModelMaker.setType(AssetsDefaultType.ERC20);
        assetsModelMaker.setName("Maker");
        assetsModelMaker.setSymbol("MKR");
        assetsModelMaker.setDecimals("18");
        assetsModelMaker.setMainContractAddress("0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2");
        assetsModels.add(assetsModelMaker);

        AssetsModel assetsModelUSDC = new AssetsModel();
        assetsModelUSDC.setType(AssetsDefaultType.ERC20);
        assetsModelUSDC.setName("USD Coin");
        assetsModelUSDC.setDecimals("6");
        assetsModelUSDC.setSymbol("USDC");
        assetsModelUSDC.setMainContractAddress("0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48");
        assetsModels.add(assetsModelUSDC);

        AssetsModel assetsModelTUSD = new AssetsModel();
        assetsModelTUSD.setType(AssetsDefaultType.ERC20);
        assetsModelTUSD.setName("TrueUSD");
        assetsModelTUSD.setSymbol("TUSD");
        assetsModelTUSD.setDecimals("18");
        assetsModelTUSD.setMainContractAddress("0x0000000000085d4780B73119b644AE5ecd22b376");
        assetsModels.add(assetsModelTUSD);

        return assetsModels;
    }
}
