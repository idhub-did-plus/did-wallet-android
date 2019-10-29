package com.idhub.wallet.didhub;

import android.os.Environment;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.idhub.base.App;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.didhub.address.EthereumAddressCreator;
import com.idhub.wallet.didhub.keystore.DidHubKeyStore;
import com.idhub.wallet.didhub.keystore.DidHubMnemonicKeyStore;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.model.Messages;
import com.idhub.wallet.didhub.model.TokenException;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.BIP44Util;
import com.idhub.wallet.didhub.util.MnemonicUtil;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.didhub.util.PrivateKeyValidator;
import com.idhub.wallet.utils.LogUtils;

import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.Nullable;

public class WalletManager {
    private static Hashtable<String, WalletKeystore> keystoreMap = new Hashtable<>();

    private static final File keystoreDir = Environment.getExternalStorageDirectory();
//    private static final File keystoreDir = App.getInstance().getFilesDir();
    private static WalletKeystore mCurrentDidHubKeyStore;

    public static void createWallet(WalletKeystore keystore) {
        File file = generateWalletFile(keystore.getId());
        writeToFile(keystore, file);
        keystoreMap.put(keystore.getId(), keystore);
    }

    public static File generateWalletFile(String walletID) {
        return new File(getDefaultKeyDirectory(), walletID + ".json");
    }

    public static File getDefaultKeyDirectory() {
        File directory = new File(keystoreDir, "wallets");
//        File directory = new File(new File(keystoreDir), "id");
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
                    WalletKeystore keystore = null;
                    CharSource charSource = Files.asCharSource(file, Charset.forName("UTF-8"));
                    String jsonContent = charSource.read();
                    JSONObject jsonObject = new JSONObject(jsonContent);
                    int version = jsonObject.getInt("version");
                    if (version == 3) {
                        if (jsonContent.contains("encMnemonic")) {
                            keystore = unmarshalKeystore(jsonContent, DidHubMnemonicKeyStore.class);
                        } else {
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

    public static Hashtable<String, WalletKeystore> getWalletKeystores() {
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


    public static WalletInfo importWalletFromKeystore(Wallet wallet, String keystoreContent, String password, boolean overwrite) {
        WalletKeystore importedKeystore = validateKeystore(keystoreContent, password);

        String privateKey = NumericUtil.bytesToHex(importedKeystore.decryptCiphertext(password));
        try {
            new PrivateKeyValidator(privateKey).validate();
        } catch (TokenException ex) {
            if (Messages.PRIVATE_KEY_INVALID.equals(ex.getMessage())) {
                throw new TokenException(Messages.KEYSTORE_CONTAINS_INVALID_PRIVATE_KEY);
            } else {
                throw ex;
            }
        }
        return importWalletFromPrivateKey(wallet, privateKey, password, overwrite);
    }

    private static DidHubKeyStore validateKeystore(String keystoreContent, String password) {
        DidHubKeyStore importedKeystore = unmarshalKeystore(keystoreContent, DidHubKeyStore.class);
        if (Strings.isNullOrEmpty(importedKeystore.getAddress()) || importedKeystore.getCrypto() == null) {
            throw new TokenException(Messages.WALLET_INVALID_KEYSTORE);
        }

        importedKeystore.getCrypto().validate();

        if (!importedKeystore.verifyPassword(password))
            throw new TokenException(Messages.MAC_UNMATCH);

        byte[] prvKey = importedKeystore.decryptCiphertext(password);
        String address = new EthereumAddressCreator().fromPrivateKey(prvKey);
        if (Strings.isNullOrEmpty(address) || !address.equalsIgnoreCase(importedKeystore.getAddress())) {
            throw new TokenException(Messages.PRIVATE_KEY_ADDRESS_NOT_MATCH);
        }
        return importedKeystore;
    }

    public static WalletInfo importWalletFromPrivateKey(Wallet metadata, String prvKeyHex, String password, boolean overwrite) {
        DidHubKeyStore keystore = DidHubKeyStore.create(metadata, password, prvKeyHex);
        return flushWallet(keystore, overwrite);
    }

    public static WalletInfo flushWallet(WalletKeystore keystore, boolean overwrite) {
        WalletKeystore existsKeystore = findKeystoreByAddress(keystore.getAddress());
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

    public static WalletInfo importWalletFromMnemonic(Wallet wallet, String mnemonic, String path, String password, boolean overwrite) {
        List<String> mnemonicCodes = Arrays.asList(mnemonic.split(" "));
        MnemonicUtil.validateMnemonics(mnemonicCodes);
        DidHubMnemonicKeyStore didHubMnemonicKeyStore = new DidHubMnemonicKeyStore(wallet, mnemonicCodes, password, path, "");
        return flushWallet(didHubMnemonicKeyStore, overwrite);
    }


    public static String findWalletAddressByPrivateKey(String privateKey) {
        new PrivateKeyValidator(privateKey).validate();
        String address = new EthereumAddressCreator().fromPrivateKey(privateKey);
        return address;
    }

    public static String findWalletAddressByKeystore(String keystoreContent, String password) {
        WalletKeystore walletKeystore = validateKeystore(keystoreContent, password);
        byte[] prvKeyBytes = walletKeystore.decryptCiphertext(password);
        String address = new EthereumAddressCreator().fromPrivateKey(prvKeyBytes);
        return address;
    }

    public static String findWalletAddressByMnemonic(String mnemonic, String path) {
        List<String> mnemonicCodes = Arrays.asList(mnemonic.split(" "));
        MnemonicUtil.validateMnemonics(mnemonicCodes);
        DeterministicSeed seed = new DeterministicSeed(mnemonicCodes, null, "", 0L);
        DeterministicKeyChain keyChain = DeterministicKeyChain.builder().seed(seed).build();
        if (Strings.isNullOrEmpty(path)) {
            throw new TokenException(Messages.INVALID_MNEMONIC_PATH);
        }
        DeterministicKey key = keyChain.getKeyByPath(BIP44Util.generatePath(path), true);
        String address = new EthereumAddressCreator().fromPrivateKey(key.getPrivateKeyAsHex());
        return address;
    }

    public static String findPrivateKeyByMnemonic(String mnemonic, String path) {
        List<String> mnemonicCodes = Arrays.asList(mnemonic.split(" "));
        MnemonicUtil.validateMnemonics(mnemonicCodes);
        DeterministicSeed seed = new DeterministicSeed(mnemonicCodes, null, "", 0L);
        DeterministicKeyChain keyChain = DeterministicKeyChain.builder().seed(seed).build();
        if (Strings.isNullOrEmpty(path)) {
            throw new TokenException(Messages.INVALID_MNEMONIC_PATH);
        }
        DeterministicKey key = keyChain.getKeyByPath(BIP44Util.generatePath(path), true);
        return key.getPrivateKeyAsHex();

    }

    public static WalletInfo findWalletByAddress(String address) {
        WalletKeystore keystore = findKeystoreByAddress(address);
        if (keystore != null) {
            return new WalletInfo(keystore);
        }
        return null;
    }


    public static WalletKeystore findKeystoreByAddress(String address) {
        if (Strings.isNullOrEmpty(address)) return null;

        for (WalletKeystore keystore : keystoreMap.values()) {

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

    public static WalletKeystore getCurrentKeyStore() {
        String selectedId = WalletOtherInfoSharpreference.getInstance().getSelectedId();
        mCurrentDidHubKeyStore = keystoreMap.get(selectedId);
        if (mCurrentDidHubKeyStore == null && keystoreMap.size() > 0) {
            String key = keystoreMap.keySet().iterator().next();
            mCurrentDidHubKeyStore = keystoreMap.get(key);
            WalletOtherInfoSharpreference.getInstance().setSelectedId(mCurrentDidHubKeyStore.getId());
        }
        return mCurrentDidHubKeyStore;
    }

    public static WalletKeystore getKeyStore(String id) {
        WalletKeystore didHubKeyStore = keystoreMap.get(id);
        return didHubKeyStore;
    }

    public static String getDefaultAddress() {
        String address = "";
        if (keystoreMap.size() > 0) {
            for (WalletKeystore keystore : keystoreMap.values()) {
                if (keystore.getWallet().isDefaultAddress()) {
                    address = NumericUtil.prependHexPrefix(keystore.getAddress());
                }
            }
        }
        return address;
    }
    public static WalletKeystore getDefaultKeystore() {
        WalletKeystore walletKeystore = null;
        if (keystoreMap.size() > 0) {
            for (WalletKeystore keystore : keystoreMap.values()) {
                if (keystore.getWallet().isDefaultAddress()) {
                    walletKeystore =keystore;
                }
            }
        }
        return walletKeystore;
    }

    public static boolean delete(WalletKeystore keyStore, String password) {
        boolean b = keyStore.verifyPassword(password) && WalletManager.generateWalletFile(keyStore.getId()).delete();
        if (b)
            keystoreMap.remove(keyStore.getId());
        return b;
    }

    public static boolean delete(WalletKeystore keyStore) {
        boolean b =WalletManager.generateWalletFile(keyStore.getId()).delete();
        if (b)
            keystoreMap.remove(keyStore.getId());
        return b;
    }
}
