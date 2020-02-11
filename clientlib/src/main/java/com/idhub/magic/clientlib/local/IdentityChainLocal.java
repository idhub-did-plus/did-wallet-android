package com.idhub.magic.clientlib.local;


import android.text.TextUtils;
import android.util.Log;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


import com.idhub.magic.clientlib.parameter.AddAssociatedAddressParam;
import com.idhub.magic.clientlib.parameter.InitializeIdentityParam;
import com.idhub.magic.clientlib.parameter.RecoveryIdentityParam;
import com.idhub.magic.clientlib.parameter.ResetIdentityParam;
import com.idhub.magic.common.contracts.ERC1056ResolverInterface;
import com.idhub.magic.common.contracts.IdentityRegistryInterface;

import io.reactivex.Observable;
import java8.util.concurrent.CompletableFuture;
import java8.util.function.Consumer;

import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;

import com.idhub.magic.clientlib.ProviderFactory;
import com.idhub.magic.clientlib.interfaces.ExceptionListener;
import com.idhub.magic.clientlib.interfaces.Identity;
import com.idhub.magic.clientlib.interfaces.IdentityChain;
import com.idhub.magic.clientlib.interfaces.IdentityChainViewer;
import com.idhub.magic.clientlib.interfaces.Listen;
import com.idhub.magic.clientlib.interfaces.ResultListener;
import com.idhub.magic.common.contracts.IdentityRegistryInterface.IdentityCreatedEventResponse;
import com.idhub.magic.common.service.DeployedContractAddress;
import com.tencent.bugly.crashreport.CrashReport;


public class IdentityChainLocal implements IdentityChain, IdentityChainViewer {

    public Listen<BigInteger> getEIN(String associate) {

        CompletableFuture<BigInteger> data = ContractManager.getRegistry1484().getEIN(associate).sendAsync();
        return new Listen<BigInteger>() {

            @Override
            public void listen(ResultListener<BigInteger> l, ExceptionListener el) {
                data.thenAccept(new Consumer<BigInteger>() {
                    @Override
                    public void accept(BigInteger bigInteger) {
                        l.result(bigInteger);
                    }
                }).exceptionally(transactionReceipt -> {
                    el.error(transactionReceipt.getMessage());

                    return null;
                });

            }
        };

    }

    public Listen<Identity> getIdentity(long ein) {

        CompletableFuture<Tuple4<String, List<String>, List<String>, List<String>>> data = ContractManager
                .getRegistry1484().getIdentity(BigInteger.valueOf(ein)).sendAsync();
        return new Listen<Identity>() {

            @Override
            public void listen(ResultListener<Identity> l, ExceptionListener el) {
                data.thenAccept(id -> {
                    Identity identity = new Identity(id.getValue1(), id.getValue2(), id.getValue3(), id.getValue4());
                    l.result(identity);

                }).exceptionally(transactionReceipt -> {
                    el.error(transactionReceipt.getMessage());
                    return null;
                });
            }
        };

    }

    @Override
    public Identity getIdentitySync(long ein) throws Exception {
        Tuple4<String, List<String>, List<String>, List<String>> send = ContractManager
                .getRegistry1484().getIdentity(BigInteger.valueOf(ein)).send();
        return new Identity(send.getValue1(), send.getValue2(), send.getValue3(), send.getValue4());
    }

    @Override
    public BigInteger getEINSync(String associate) throws Exception {
        return ContractManager.getRegistry1484().getEIN(associate).send();
    }

    @Override
    public Boolean hasIdentity(String address) throws Exception {
        Boolean aBoolean = ContractManager.getRegistry1484().hasIdentity(address).send();
        return aBoolean;
    }

    @Override
    public String einToDID(String ein) throws Exception {
        String send = ContractManager.getResolver1056().einToDID(new BigInteger(ein)).send();
        return send;
    }


    @Override
    public Listen<IdentityCreatedEventResponse> createIdentity() {
        String rec = ProviderFactory.getProvider().getRecoverAddress();
        String asso = ProviderFactory.getProvider().getDefaultCredentials().getAddress();
        List<String> ps = new ArrayList<String>();
        List<String> rs = new ArrayList<String>();
        rs.add(DeployedContractAddress.ERC1056ResolverInterface);

        return createIdentity(rec, asso, ps, rs);

    }

    @Override
    public Listen<IdentityCreatedEventResponse> createIdentity(String recovery, String associate,
                                                               List<String> providers, List<String> resolvers) {
        CompletableFuture<TransactionReceipt> data = ContractManager.getRegistry1484()
                .createIdentity(recovery, providers, resolvers).sendAsync();
        return new Listen<IdentityCreatedEventResponse>() {

            @Override
            public void listen(ResultListener listener, ExceptionListener el) {
                data.thenAccept(transactionReceipt -> {
                    if (TextUtils.isEmpty(transactionReceipt.getTransactionHash())) {
                        el.error("");
                    } else {
                        List<IdentityCreatedEventResponse> es = ContractManager.getRegistry1484()
                                .getIdentityCreatedEvents(transactionReceipt);
                        if (es != null) {
                            Log.e("LYW", "listen: " + es.size());
                        } else {
                            Log.e("LYW", "listen:null " );
                        }
                        listener.result(es.get(0));
                    }
                }).exceptionally(transactionReceipt -> {
                    Log.e("LYW", "listen: " + transactionReceipt.getMessage() );
                    el.error(transactionReceipt.getMessage());
                    return null;
                });
            }
        };
    }

    @Override
    public Observable<IdentityRegistryInterface.RecoveryTriggeredEventResponse> recoveryIdentity(String ein, String newAssociationAddress, String privateKey) {

        return Observable.create(observableEmitter -> {
            RecoveryIdentityParam recoveryIdentityParam = new RecoveryIdentityParam();
            recoveryIdentityParam.ein = new BigInteger(ein);
            recoveryIdentityParam.privateKey = privateKey;
            recoveryIdentityParam.newAssociationAddress = newAssociationAddress;
            EthBlock ethBlock = ContractManager.web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send();
            recoveryIdentityParam.timestamp = ethBlock.getBlock().getTimestamp();
            RecoveryIdentityParam param = ClientEncoderLocal.recoveryIdentityEncoder(recoveryIdentityParam);
            BigInteger timestamp = param.timestamp;
            Log.e("LYW", "recoveryIdentity: " + timestamp);
            TransactionReceipt transactionReceipt = ContractManager.getRegistry1484().triggerRecovery(param.ein, param.newAssociationAddress, param.v, param.r, param.s, timestamp).send();
            List<IdentityRegistryInterface.RecoveryTriggeredEventResponse> recoveryTriggeredEvents = ContractManager.getRegistry1484()
                                .getRecoveryTriggeredEvents(transactionReceipt);
            observableEmitter.onNext(recoveryTriggeredEvents.get(0));
            observableEmitter.onComplete();
//            CompletableFuture<TransactionReceipt> future = ContractManager.getRegistry1484().triggerRecovery(param.ein, param.newAssociationAddress, param.v, param.r, param.s, timestamp).sendAsync();
//
//            return new Listen<IdentityRegistryInterface.RecoveryTriggeredEventResponse>() {
//                @Override
//                public void listen(ResultListener l, ExceptionListener el) {
//                    future.thenAccept(transactionReceipt -> {
//                        List<IdentityRegistryInterface.RecoveryTriggeredEventResponse> recoveryTriggeredEvents = ContractManager.getRegistry1484()
//                                .getRecoveryTriggeredEvents(transactionReceipt);
//                        l.result(recoveryTriggeredEvents.get(0));
//                    }).exceptionally(throwable -> {
//                        el.error(throwable.getMessage());
//                        return null;
//                    });
//                }
//            };
        });
    }

    @Override
    public Observable<IdentityRegistryInterface.AssociatedAddressAddedEventResponse> addAssociatedAddress(BigInteger ein, String approvingAddress, String addressToAdd, String privateKey) {
        return Observable.create(observableEmitter -> {
            AddAssociatedAddressParam addAssociatedAddressParam = new AddAssociatedAddressParam();
            addAssociatedAddressParam.addressToAdd = addressToAdd;
            addAssociatedAddressParam.ein = ein;
            addAssociatedAddressParam.privateKey = privateKey;
            addAssociatedAddressParam.approvingAddress = approvingAddress;
            EthBlock ethBlock = ContractManager.web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send();
            addAssociatedAddressParam.timestamp = ethBlock.getBlock().getTimestamp();
            AddAssociatedAddressParam param = ClientEncoderLocal.addAssociatedAddressEncoder(addAssociatedAddressParam);

            BigInteger timestamp1 = param.timestamp;
            String approvingAddress1 = param.approvingAddress;
            String addressToAdd1 = param.addressToAdd;
            Log.e("LYW", "addAssociatedAddress:timestamp1 " + timestamp1 + "  approvingAddress1  " + approvingAddress1 + " addressToAdd1 " + addressToAdd1);
            TransactionReceipt transactionReceipt = ContractManager.getRegistry1484().addAssociatedAddress(approvingAddress1, addressToAdd1, param.v, param.r, param.s, timestamp1).send();
            List<IdentityRegistryInterface.AssociatedAddressAddedEventResponse> associatedAddressAddedEvents = ContractManager.getRegistry1484()
                    .getAssociatedAddressAddedEvents(transactionReceipt);
//            CompletableFuture<TransactionReceipt> future = ContractManager.getRegistry1484().addAssociatedAddress(approvingAddress1, addressToAdd1, param.v, param.r, param.s, timestamp1).sendAsync();
//
//            return new Listen<IdentityRegistryInterface.AssociatedAddressAddedEventResponse>() {
//                @Override
//                public void listen(ResultListener l, ExceptionListener el) {
//                    future.thenAccept(transactionReceipt -> {
//                        List<IdentityRegistryInterface.AssociatedAddressAddedEventResponse> associatedAddressAddedEvents = ContractManager.getRegistry1484()
//                                .getAssociatedAddressAddedEvents(transactionReceipt);
//                        l.result(associatedAddressAddedEvents.get(0));
//                    }).exceptionally(throwable -> {
//                        el.error(throwable.getMessage());
//                        return null;
//                    });
//                }
//            };

            observableEmitter.onNext(associatedAddressAddedEvents.get(0));
            observableEmitter.onComplete();
        });
    }

    @Override
    public Observable<ERC1056ResolverInterface.IdentityResetedEventResponse> reset(String newIdentity, String privateKey) {
        return Observable.create(observableEmitter -> {
            ResetIdentityParam resetIdentityParam = new ResetIdentityParam();
            resetIdentityParam.privateKey = privateKey;
            resetIdentityParam.identity = newIdentity;
            String identityOwner = ContractManager.getRegistry1056().identityOwner(newIdentity).send();
            resetIdentityParam.noce = ContractManager.getRegistry1056().nonce(identityOwner).send();
            ResetIdentityParam param = ClientEncoderLocal.resetIdentity(resetIdentityParam);
            TransactionReceipt receipt = ContractManager.getResolver1056().recovery(param.identity, param.v, param.r, param.s).send();
            List<ERC1056ResolverInterface.IdentityResetedEventResponse> identityResetedEvents = ContractManager.getResolver1056().getIdentityResetedEvents(receipt);
            ERC1056ResolverInterface.IdentityResetedEventResponse resetedEventResponse = identityResetedEvents.get(0);
            observableEmitter.onNext(resetedEventResponse);
            observableEmitter.onComplete();
        });
    }

    @Override
    public Observable<ERC1056ResolverInterface.IdentityInitializedEventResponse> initialize(String identity) {
        return Observable.create(observableEmitter -> {
            InitializeIdentityParam initializeIdentityParam = new InitializeIdentityParam();
            initializeIdentityParam.identity = identity;
            String identityOwner = ContractManager.getRegistry1056().identityOwner(identity).send();
            initializeIdentityParam.noce = ContractManager.getRegistry1056().nonce(identityOwner).send();
            InitializeIdentityParam param = ClientEncoderLocal.initializeIdentity(initializeIdentityParam);
            TransactionReceipt receipt = ContractManager.getResolver1056().initialize(param.identity, param.v, param.r, param.s).send();
            List<ERC1056ResolverInterface.IdentityInitializedEventResponse> identityInitializedEvents = ContractManager.getResolver1056().getIdentityInitializedEvents(receipt);
            ERC1056ResolverInterface.IdentityInitializedEventResponse initializedEventResponse = identityInitializedEvents.get(0);
            observableEmitter.onNext(initializedEventResponse);
            observableEmitter.onComplete();
        });
    }


}