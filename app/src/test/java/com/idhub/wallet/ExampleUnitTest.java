package com.idhub.wallet;

import android.util.Log;
import android.view.View;

import com.idhub.magic.center.contracts.ERC1056ResolverInterface;
import com.idhub.wallet.contract.ERC1400;
import com.idhub.wallet.createmanager.UpgradeActivity;
import com.idhub.wallet.net.IDHubCredentialProvider;

import org.junit.Test;

import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
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
//
//        Credentials credentials = Credentials.create("0");
//        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
//        IDHubCredentialProvider.setDefaultCredentials(String.valueOf(privateKey));
//        Boolean hasIdentity = ApiFactory.getIdentityChainLocal().hasIdentity("14bc3096faef7a011ffd655f4a0fd2b534c09d19");
//        System.out.println(hasIdentity);
        IDHubCredentialProvider.setDefaultCredentials("cc6e640d3f3ca7226975723917139d290ec99ed188122901bf3a13796f1d0cd2");

        ApiFactory.getIdentityChainLocal().initialize("14bc3096faef7a011ffd655f4a0fd2b534c09d19").subscribe(new DisposableObserver<ERC1056ResolverInterface.IdentityInitializedEventResponse>() {
            @Override
            public void onNext(ERC1056ResolverInterface.IdentityInitializedEventResponse identityInitializedEventResponse) {
                String initiator = identityInitializedEventResponse.initiator;
                String indeitity = identityInitializedEventResponse.indeitity;
                BigInteger ein1 = identityInitializedEventResponse.ein;
                System.out.println("1");
                System.out.println(indeitity);
                System.out.println(initiator);
                System.out.println(ein1);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("2");
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {
            }
        });
    }
}