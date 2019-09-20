package com.idhub.wallet;

import android.util.Log;
import android.view.View;
import com.idhub.wallet.contract.ERC1400;
import com.idhub.wallet.createmanager.UpgradeActivity;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.net.IDHubCredentialProvider;

import org.junit.Test;

import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import wallet.idhub.com.clientlib.ApiFactory;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        Web3j mWeb3j = Web3j.build(new HttpService("https://ropsten.infura.io"));
//        DefaultGasProvider defaultGasProvider = new DefaultGasProvider();
//        Credentials credentials = Credentials.create("0");
//
//        ERC1400 erc1400 = ERC1400.load("0xde4a569ef69c323e06336bfaae13e9d15370e1f0", mWeb3j, credentials, new DefaultGasProvider());
//        BigDecimal decimal = NumericUtil.valueFormatByDecimal("1", 18);
//
//        byte[] bytes = Numeric.hexStringToByteArray("0x577be1ce1198d8ed29459cd6b9ec35e86aeac3bb0ecfa3eb193ad78c69bd9d24");
//        Tuple3<byte[], byte[], byte[]> send = erc1400.canTransferByPartition(bytes, "0x4c000e507be6663e264a1a21507a69bfa5035d95","0xe260afb7f3fb77652a224a39fe150868875c0f2b", decimal.toBigInteger(),new byte[]{0x0}).send();
//        byte[] value1 = send.getValue1();
//        System.out.println(Numeric.toHexString(value1));
//        byte[] value3 = send.getValue3();
//        System.out.println(Numeric.toHexString(value3));



//        DefaultGasProvider defaultGasProvider = new DefaultGasProvider();
//
//        Credentials credentials = Credentials.create("0");
//        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
//        IDHubCredentialProvider.setDefaultCredentials(String.valueOf(privateKey));
//        Boolean hasIdentity = ApiFactory.getIdentityChainLocal().hasIdentity("14bc3096faef7a011ffd655f4a0fd2b534c09d19");
//        System.out.println(hasIdentity);
//        IDHubCredentialProvider.setDefaultCredentials("cc6e640d3f3ca7226975723917139d290ec99ed188122901bf3a13796f1d0cd2");
//
//        ApiFactory.getIdentityChainLocal().initialize("14bc3096faef7a011ffd655f4a0fd2b534c09d19").subscribe(new DisposableObserver<ERC1056ResolverInterface.IdentityInitializedEventResponse>() {
//            @Override
//            public void onNext(ERC1056ResolverInterface.IdentityInitializedEventResponse identityInitializedEventResponse) {
//                String initiator = identityInitializedEventResponse.initiator;
//                String indeitity = identityInitializedEventResponse.indeitity;
//                BigInteger ein1 = identityInitializedEventResponse.ein;
//                System.out.println("1");
//                System.out.println(indeitity);
//                System.out.println(initiator);
//                System.out.println(ein1);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("2");
//                System.out.println(e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//            }
//        });



    }
}