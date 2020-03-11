package com.idhub.wallet;

import com.idhub.wallet.contract.EIP20Interface;
import com.idhub.wallet.contract.ERC721;

import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
//        Web3j web3j = Web3j.build(new HttpService("http://47.103.62.205:8001"));
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/70d406df88af453e8bc8da633b14b70c"));
//        EthTransaction send = web3j.ethGetTransactionByHash("0xfc2572cfdc312fc2c80273ee858693ed8af1fec6f78566e06786a2c7e22dc3d6").send();

//        EthGetBalance ethGetBalance = web3j.ethGetBalance("0x70d76f5B27ee05CCF0e0fa67aE72EcB203069b77", DefaultBlockParameterName.LATEST).send();
//        System.out.println(ethGetBalance.getBalance());

//        ERC721 erc721 = ERC721.deploy(mWeb3j, credentials, contractGasProvider).send();
//        ERC721Enumerable erc721Enumerable = ERC721Enumerable.deploy(mWeb3j, credentials, contractGasProvider).send();
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
//        EIP20Interface load = EIP20Interface.load("0xe5bd43166a3761628ac777bb1a42bcab13ca9495", web3j, credentials, new DefaultGasProvider());

        ERC721 erc721 = ERC721.load("0x16baf0de678e52367adc69fd067e5edd1d33e3bf", web3j, credentials, new DefaultGasProvider());
        BigInteger integer = erc721.balanceOf("0x00fe6efe5565e76905f39d603d8da3bf96e316e4").send();
        System.out.println(integer);
//        EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
//        String s = ethGasPrice.getGasPrice().toString();
//        System.out.println(s);
    }


    @Test
    public void testThread(){
        long l = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(3);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            Thread command = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Random random = new Random();
                        int i1 = random.nextInt(3);
                        System.out.println(i1);
                        Thread.sleep(i1 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
            exec.execute(command);
        }
        try {
            System.out.println("begin");
            countDownLatch.await();
            long l1 = System.currentTimeMillis() - l;
            System.out.println(l1);
            System.out.println("end");
            System.out.println("123");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exec.shutdown();
    }
}