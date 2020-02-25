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
import com.idhub.wallet.contract.ERC721;
import com.idhub.wallet.contract.ERC721Enumerable;
import com.idhub.wallet.didhub.WalletInfo;
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
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByNumber;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthHashrate;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.idhub.wallet.net.Web3Api.mWeb3j;
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

        Credentials credentials = Credentials.create("0");
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
//        Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io"));
        Web3j web3j = Web3j.build(new HttpService("http://47.103.62.205:8001"));
//        EthTransaction send = web3j.ethGetTransactionByHash("0xfc2572cfdc312fc2c80273ee858693ed8af1fec6f78566e06786a2c7e22dc3d6").send();

        EthGetBalance ethGetBalance = web3j.ethGetBalance("0x70d76f5B27ee05CCF0e0fa67aE72EcB203069b77", DefaultBlockParameterName.LATEST).send();
        System.out.println(ethGetBalance.getBalance());

//        ERC721 erc721 = ERC721.deploy(mWeb3j, credentials, contractGasProvider).send();
        ERC721Enumerable erc721Enumerable = ERC721Enumerable.deploy(mWeb3j, credentials, contractGasProvider).send();
//        Credentials credentials1 = Credentials.create("CBCB558CFB5CA8EF584D6BF5AD2744B9D6352209F5E0721B362946C8B5FF5F42");
//
//        EthGetTransactionCount send = web3j.ethGetTransactionCount("0x70d76f5B27ee05CCF0e0fa67aE72EcB203069b77", DefaultBlockParameterName.LATEST).send();//获取noce
//        BigInteger transactionCount = send.getTransactionCount();
//        BigInteger value = Convert.toWei("12", Convert.Unit.ETHER).toBigInteger();
//        RawTransaction etherTransaction = RawTransaction.createEtherTransaction(transactionCount, contractGasProvider.getGasPrice(), contractGasProvider.getGasLimit(), "0xc78E1668D36514E31378518583fdC36C9B758086", value);
//        byte[] signedMessage = TransactionEncoder.signMessage(etherTransaction, credentials1);
//        String hexValue = Numeric.toHexString(signedMessage);
//        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
//        System.out.println(etherTransaction.getValue());

//        IdentityRegistryInterface  registry1484 = IdentityRegistryInterface.load("0x8Dacc7FBE27c24aB69402f479eEb62Da5699aB76",
//                web3j,
//                credentials,
//                contractGasProvider
//        );
//        Boolean send = registry1484.hasIdentity("0x6b3fD23615015aFa2b7A63f037227413a4944D51").send();
//        System.out.println(send);

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
//        boolean hasIdentity = registry1484.hasIdentity("0x5f9eB509EfC563b55fCF959B6E19E6D3342D87Da").send();
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
//        String data = "0x3E01C8A3F80ea37cFBb7D9064FD919Df9cF641131575975615175complianceManager";
//        String signature = "0x676feafc6deca97019c295963e619410a66fd15366f012815ea7129db0d471297bd0a33e17da04a4303fb9fa161033bd1801ec346dc0d67cb00e2eb7239b578d1c";
//        String address = EthereumSign.recoverAddress(data, signature);
//        BigInteger bigInteger = EthereumSign.ecRecover(data, signature);
//        System.out.println(bigInteger.toString());
//        System.out.println(address);
//        boolean validAddress = ETHAddressValidator.isValidAddress("0x5f9eB509EfC563b55fCF959B6E19E6D3342D87Da");
//        Map<String, String> languages = new LinkedHashMap<>();
//            languages.put("", "Auto");
//            languages.put(Locale.ENGLISH.toLanguageTag(), "ENGLISH");
//            languages.put(Locale.CHINESE.toLanguageTag(), "CHINESE");
//        String s = languages.get("");
//        System.out.println(s);
    }
}