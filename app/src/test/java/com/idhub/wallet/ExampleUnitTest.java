package com.idhub.wallet;

import android.text.TextUtils;
import android.util.Log;

import com.google.common.base.Strings;
import com.idhub.base.App;
import com.idhub.base.node.WalletNoteSharedPreferences;
import com.idhub.magic.common.contracts.ERC1056ResolverInterface;
import com.idhub.magic.common.contracts.IdentityRegistryInterface;
import com.idhub.magic.common.service.DeployedContractAddress;
import com.idhub.wallet.contract.EIP20Interface;
import com.idhub.wallet.contract.ERC1400;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.address.ETHAddressValidator;
import com.idhub.wallet.didhub.address.EthereumAddressCreator;
import com.idhub.wallet.didhub.model.Messages;
import com.idhub.wallet.didhub.model.TokenException;
import com.idhub.wallet.didhub.transaction.EthereumSign;
import com.idhub.wallet.didhub.util.BIP44Util;
import com.idhub.wallet.didhub.util.MnemonicUtil;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.setting.LanguagesManager;

import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByNumber;
import org.web3j.protocol.core.methods.response.EthHashrate;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.idhub.wallet.net.Web3Api.sNode;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        String mnemonic = "panic coil pet life actress label into husband sea submit zone cluster";
//        List<String> mnemonicCodes = Arrays.asList(mnemonic.split(" "));
//        MnemonicUtil.validateMnemonics(mnemonicCodes);
//        DeterministicSeed seed = new DeterministicSeed(mnemonicCodes, null, "", 0L);
//        DeterministicKeyChain keyChain = DeterministicKeyChain.builder().seed(seed).build();
//        DeterministicKey key = keyChain.getKeyByPath(BIP44Util.generatePath(BIP44Util.ETHEREUM_PATH), true);
//        String address = new EthereumAddressCreator().fromPrivateKey(key.getPrivateKeyAsHex());
//        System.out.println(address);

//        Credentials credentials = Credentials.create("0");
//        ContractGasProvider contractGasProvider = new DefaultGasProvider();
//        Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io"));
//
//        IdentityRegistryInterface  registry1484 = IdentityRegistryInterface.load("0x90e1B1C7B8C829b3d0b1C09eD961e46f5AeeD184",
//                web3j,
//                credentials,
//                contractGasProvider
//        );
//        ERC1056ResolverInterface erc1056 = ERC1056ResolverInterface.load("0x6d0f04B6Ca0217323af7fB7147a63C97Ef910617",
//                web3j,
//                credentials,
//                contractGasProvider
//        );
//        String send1 = erc1056.einToDID(new BigInteger("40")).send();
//        System.out.println(send1);
//        Tuple4<String, List<String>, List<String>, List<String>> send = registry1484.getIdentity(new BigInteger("40")).send();
//        System.out.println(send.getValue1());
//        List<String> value2 = send.getValue2();
//        System.out.println(value2.size());
//        if (value2.size() > 0) {
//            for (String s : value2) {
//                System.out.println(s);
//            }
//        }
//        boolean hasIdentity = registry1484.hasIdentity("0x4c000E507bE6663e264a1A21507a69Bfa5035D95").send();
//        BigInteger bigInteger = registry1484.getEIN("0x4c000E507bE6663e264a1A21507a69Bfa5035D95").send();
//        System.out.println(hasIdentity);
//        System.out.println(bigInteger);


//        ERC1400 ierc20 = ERC1400.load("0x4c000e507be6663e264a1a21507a69bfa5035d95", web3j, credentials, contractGasProvider);
//        ERC1400 erc1400 = ERC1400.load(contractAddress, mWeb3j, credentials, defaultGasProvider);
        //过滤
//        List list = ierc20.totalPartitions().send();
//        String name = ierc20.name().send();
//        BigInteger decimal = ierc20.decimals().send();
//        String symbol = ierc20.symbol().send();
//        System.out.println(symbol);
        String data = "0x3E01C8A3F80ea37cFBb7D9064FD919Df9cF641131575975615175complianceManager";
        String signature = "0x676feafc6deca97019c295963e619410a66fd15366f012815ea7129db0d471297bd0a33e17da04a4303fb9fa161033bd1801ec346dc0d67cb00e2eb7239b578d1c";
        String address = EthereumSign.recoverAddress(data, signature);
        BigInteger bigInteger = EthereumSign.ecRecover(data, signature);
        System.out.println(bigInteger.toString());
        System.out.println(address);
//        boolean validAddress = ETHAddressValidator.isValidAddress("0x5f9eB509EfC563b55fCF959B6E19E6D3342D87Da");
//        Map<String, String> languages = new LinkedHashMap<>();
//            languages.put("", "Auto");
//            languages.put(Locale.ENGLISH.toLanguageTag(), "ENGLISH");
//            languages.put(Locale.CHINESE.toLanguageTag(), "CHINESE");
//        String s = languages.get("");
//        System.out.println(s);
    }
}