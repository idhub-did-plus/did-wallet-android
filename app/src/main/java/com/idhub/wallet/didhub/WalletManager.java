package com.idhub.wallet.didhub;

import android.os.Environment;
import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.model.Messages;
import com.idhub.wallet.didhub.model.TokenException;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.utils.LogUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Hashtable;

public class WalletManager {
    private static Hashtable<String, DidHubKeyStore> keystoreMap = new Hashtable<>();

    private static final String keystoreDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static DidHubKeyStore mCurrentDidHubKeyStore;

    public static void createWallet(DidHubKeyStore keystore) {
        File file = generateWalletFile(keystore.getId());
        writeToFile(keystore, file);
        keystoreMap.put(keystore.getId(), keystore);
    }

    public static File generateWalletFile(String walletID) {
        return new File(getDefaultKeyDirectory(), walletID + ".json");
    }

    public static File getDefaultKeyDirectory() {
        File directory = new File(new File(keystoreDir), "wallets");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    private static void writeToFile(WalletKeystore keyStore, File destination) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.writeValue(destination, keyStore);
        } catch (IOException ex) {
            throw new TokenException(Messages.WALLET_STORE_FAIL, ex);
        }
    }

    public static void scanWallets() {
        File directory = getDefaultKeyDirectory();

        keystoreMap.clear();
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                try {
                    DidHubKeyStore keystore = null;
                    CharSource charSource = Files.asCharSource(file, Charset.forName("UTF-8"));
                    String jsonContent = charSource.read();
                    JSONObject jsonObject = new JSONObject(jsonContent);
                    int version = jsonObject.getInt("version");
                    if (version == 3) {
                        if (jsonContent.contains("encMnemonic")) {
                            keystore = unmarshalKeystore(jsonContent, DidHubKeyStore.class);
                        }
                    }

                    if (keystore != null) {
                        keystoreMap.put(keystore.getId(), keystore);
                    }
                } catch (Exception ex) {
                    LogUtils.e("didhub", "Can't loaded " + file.getName() + " file", ex);
                }
            }
        }
    }

    public static int getWalletNum() {
        return keystoreMap.size();
    }

    public static Hashtable<String, DidHubKeyStore> getWalletKeystores() {
        return keystoreMap;
    }

    private static <T extends WalletKeystore> T unmarshalKeystore(String keystoreContent, Class<T> clazz) {
        T importedKeystore;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
            importedKeystore = mapper.readValue(keystoreContent, clazz);
        } catch (IOException ex) {
            throw new TokenException(Messages.WALLET_INVALID_KEYSTORE, ex);
        }
        return importedKeystore;
    }


//    public static Wallet importWalletFromKeystore(Metadata metadata, String keystoreContent, String password, boolean overwrite) {
//        WalletKeystore importedKeystore = validateKeystore(keystoreContent, password);
//
//        if (metadata.getSource() == null)
//            metadata.setSource(Metadata.FROM_KEYSTORE);
//
//        String privateKey = NumericUtil.bytesToHex(importedKeystore.decryptCiphertext(password));
//        try {
//            new PrivateKeyValidator(privateKey).validate();
//        } catch (TokenException ex) {
//            if (Messages.PRIVATE_KEY_INVALID.equals(ex.getMessage())) {
//                throw new TokenException(Messages.KEYSTORE_CONTAINS_INVALID_PRIVATE_KEY);
//            } else {
//                throw ex;
//            }
//        }
//        return importWalletFromPrivateKey(metadata, privateKey, password, overwrite);
//    }
//
//    public static WalletInfo importWalletFromPrivateKey(Metadata metadata, String prvKeyHex, String password, boolean overwrite) {
//        IMTKeystore keystore = V3Keystore.create(metadata, password, prvKeyHex);
//        WalletInfo walletinfo = flushWallet(keystore, overwrite);
//        Identity.getCurrentIdentity().addWallet(wallet);
//        return walletinfo;
//    }

    public static WalletInfo flushWallet(DidHubKeyStore keystore, boolean overwrite) {
        DidHubKeyStore existsKeystore = findKeystoreByAddress(keystore.getAddress());
        if (existsKeystore != null) {
            if (!overwrite) {
                throw new TokenException(Messages.WALLET_EXISTS);
            } else {
                keystore.setId(existsKeystore.getId());
            }
        }

        File file = generateWalletFile(keystore.getId());
        writeToFile(keystore, file);
        keystoreMap.put(keystore.getId(), keystore);
        return new WalletInfo(keystore);
    }

    private static DidHubKeyStore findKeystoreByAddress(String address) {
        if (Strings.isNullOrEmpty(address)) return null;

        for (DidHubKeyStore keystore : keystoreMap.values()) {

            if (Strings.isNullOrEmpty(keystore.getAddress())) {
                continue;
            }

            if (keystore.getAddress().equals(address)) {
                return keystore;
            }
        }
        return null;
    }

    public static String getAddress() {
        return NumericUtil.prependHexPrefix(getCurrentKeyStore().getAddress());
    }

    public static DidHubKeyStore getCurrentKeyStore() {
        String selectedId = WalletOtherInfoSharpreference.getInstance().getSelectedId();
        mCurrentDidHubKeyStore = keystoreMap.get(selectedId);
        if (mCurrentDidHubKeyStore == null && keystoreMap.size() > 0) {
            String key = keystoreMap.keySet().iterator().next();
            mCurrentDidHubKeyStore = keystoreMap.get(key);
            WalletOtherInfoSharpreference.getInstance().setSelectedId(mCurrentDidHubKeyStore.getId());
        }
        return mCurrentDidHubKeyStore;
    }

    public static DidHubKeyStore getKeyStore(String id) {
        DidHubKeyStore didHubKeyStore = keystoreMap.get(id);
        return didHubKeyStore;
    }

    public static String getDefaultAddress() {
        String address = "";
        if (keystoreMap.size() > 0) {
            for (DidHubKeyStore keystore : keystoreMap.values()) {
                if (keystore.getWallet().isDefaultAddress()) {
                    address = NumericUtil.prependHexPrefix(keystore.getAddress());
                }
            }
        }
        return address;
    }

    public static boolean delete(DidHubKeyStore keyStore, String password) {
        boolean b = keyStore.verifyPassword(password) && WalletManager.generateWalletFile(keyStore.getId()).delete();
        if (b)
            keystoreMap.remove(keyStore.getId());
        return b;
    }
}
