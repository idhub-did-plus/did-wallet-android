package com.idhub.wallet;

import com.google.common.base.Strings;
import com.idhub.magic.common.contracts.ERC1056ResolverInterface;
import com.idhub.magic.common.contracts.IdentityRegistryInterface;
import com.idhub.magic.common.service.DeployedContractAddress;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.address.EthereumAddressCreator;
import com.idhub.wallet.didhub.model.Messages;
import com.idhub.wallet.didhub.model.TokenException;
import com.idhub.wallet.didhub.util.BIP44Util;
import com.idhub.wallet.didhub.util.MnemonicUtil;
import com.idhub.wallet.net.IDHubCredentialProvider;

import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

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
        Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io"));
        IdentityRegistryInterface  registry1484 = IdentityRegistryInterface.load("0x90e1B1C7B8C829b3d0b1C09eD961e46f5AeeD184",
                web3j,
                credentials,
                contractGasProvider
        );
        ERC1056ResolverInterface erc1056 = ERC1056ResolverInterface.load("0x6d0f04B6Ca0217323af7fB7147a63C97Ef910617",
                web3j,
                credentials,
                contractGasProvider
        );
        String send1 = erc1056.einToDID(new BigInteger("19")).send();
        System.out.println(send1);
        Tuple4<String, List<String>, List<String>, List<String>> send = registry1484.getIdentity(new BigInteger("19")).send();
        System.out.println(send.getValue1());
        List<String> value2 = send.getValue2();
        System.out.println(value2.size());
        if (value2.size() > 0) {
            for (String s : value2) {
                System.out.println(s);
            }
        }
//        boolean hasIdentity = registry1484.hasIdentity("0x4c000E507bE6663e264a1A21507a69Bfa5035D95").send();
//        BigInteger bigInteger = registry1484.getEIN("0x4c000E507bE6663e264a1A21507a69Bfa5035D95").send();
//        System.out.println(hasIdentity);
//        System.out.println(bigInteger);
    }
}