package com.idhub.magic.common.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.3.0.
 */
public class IdentityRegistryInterface extends Contract {
    private static final String BINARY = "0x";

    public static final String FUNC_ISSIGNED = "isSigned";

    public static final String FUNC_IDENTITYEXISTS = "identityExists";

    public static final String FUNC_HASIDENTITY = "hasIdentity";

    public static final String FUNC_GETEIN = "getEIN";

    public static final String FUNC_ISASSOCIATEDADDRESSFOR = "isAssociatedAddressFor";

    public static final String FUNC_ISPROVIDERFOR = "isProviderFor";

    public static final String FUNC_ISRESOLVERFOR = "isResolverFor";

    public static final String FUNC_GETIDENTITY = "getIdentity";

    public static final String FUNC_MAPUINT8 = "mapuint8";

    public static final String FUNC_MAPBYTES32 = "mapbytes32";

    public static final String FUNC_ERECOVER = "erecover";

    public static final String FUNC_ENCODE = "encode";

    public static final String FUNC_HASH = "hash";

    public static final String FUNC_CREATEIDENTITY = "createIdentity";

    public static final String FUNC_CREATEIDENTITYDELEGATED = "createIdentityDelegated";

    public static final String FUNC_ADDASSOCIATEDADDRESS = "addAssociatedAddress";

    public static final String FUNC_ADDASSOCIATEDADDRESSDELEGATED = "addAssociatedAddressDelegated";

    public static final String FUNC_REMOVEASSOCIATEDADDRESS = "removeAssociatedAddress";

    public static final String FUNC_REMOVEASSOCIATEDADDRESSDELEGATED = "removeAssociatedAddressDelegated";

    public static final String FUNC_ADDPROVIDERS = "addProviders";

    public static final String FUNC_ADDPROVIDERSFOR = "addProvidersFor";

    public static final String FUNC_REMOVEPROVIDERS = "removeProviders";

    public static final String FUNC_REMOVEPROVIDERSFOR = "removeProvidersFor";

    public static final String FUNC_ADDRESOLVERS = "addResolvers";

    public static final String FUNC_ADDRESOLVERSFOR = "addResolversFor";

    public static final String FUNC_REMOVERESOLVERS = "removeResolvers";

    public static final String FUNC_REMOVERESOLVERSFOR = "removeResolversFor";

    public static final String FUNC_TRIGGERRECOVERYADDRESSCHANGE = "triggerRecoveryAddressChange";

    public static final String FUNC_TRIGGERRECOVERYADDRESSCHANGEFOR = "triggerRecoveryAddressChangeFor";

    public static final String FUNC_TRIGGERRECOVERY = "triggerRecovery";

    public static final String FUNC_TRIGGERDESTRUCTION = "triggerDestruction";

    public static final Event IDENTITYCREATED_EVENT = new Event("IdentityCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event ASSOCIATEDADDRESSADDED_EVENT = new Event("AssociatedAddressAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event ASSOCIATEDADDRESSREMOVED_EVENT = new Event("AssociatedAddressRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event PROVIDERADDED_EVENT = new Event("ProviderAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event PROVIDERREMOVED_EVENT = new Event("ProviderRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event RESOLVERADDED_EVENT = new Event("ResolverAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event RESOLVERREMOVED_EVENT = new Event("ResolverRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event RECOVERYADDRESSCHANGETRIGGERED_EVENT = new Event("RecoveryAddressChangeTriggered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event RECOVERYTRIGGERED_EVENT = new Event("RecoveryTriggered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event IDENTITYDESTROYED_EVENT = new Event("IdentityDestroyed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected IdentityRegistryInterface(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IdentityRegistryInterface(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IdentityRegistryInterface(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IdentityRegistryInterface(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<IdentityCreatedEventResponse> getIdentityCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(IDENTITYCREATED_EVENT, transactionReceipt);
        ArrayList<IdentityCreatedEventResponse> responses = new ArrayList<IdentityCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            IdentityCreatedEventResponse typedResponse = new IdentityCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.associatedAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.recoveryAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.providers = (List<String>) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.resolvers = (List<String>) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<IdentityCreatedEventResponse> identityCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, IdentityCreatedEventResponse>() {
            @Override
            public IdentityCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(IDENTITYCREATED_EVENT, log);
                IdentityCreatedEventResponse typedResponse = new IdentityCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.associatedAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.recoveryAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.providers = (List<String>) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.resolvers = (List<String>) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<IdentityCreatedEventResponse> identityCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(IDENTITYCREATED_EVENT));
        return identityCreatedEventFlowable(filter);
    }

    public List<AssociatedAddressAddedEventResponse> getAssociatedAddressAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ASSOCIATEDADDRESSADDED_EVENT, transactionReceipt);
        ArrayList<AssociatedAddressAddedEventResponse> responses = new ArrayList<AssociatedAddressAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AssociatedAddressAddedEventResponse typedResponse = new AssociatedAddressAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approvingAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.addedAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AssociatedAddressAddedEventResponse> associatedAddressAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AssociatedAddressAddedEventResponse>() {
            @Override
            public AssociatedAddressAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ASSOCIATEDADDRESSADDED_EVENT, log);
                AssociatedAddressAddedEventResponse typedResponse = new AssociatedAddressAddedEventResponse();
                typedResponse.log = log;
                typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.approvingAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.addedAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AssociatedAddressAddedEventResponse> associatedAddressAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ASSOCIATEDADDRESSADDED_EVENT));
        return associatedAddressAddedEventFlowable(filter);
    }

    public List<AssociatedAddressRemovedEventResponse> getAssociatedAddressRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ASSOCIATEDADDRESSREMOVED_EVENT, transactionReceipt);
        ArrayList<AssociatedAddressRemovedEventResponse> responses = new ArrayList<AssociatedAddressRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AssociatedAddressRemovedEventResponse typedResponse = new AssociatedAddressRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.removedAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AssociatedAddressRemovedEventResponse> associatedAddressRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AssociatedAddressRemovedEventResponse>() {
            @Override
            public AssociatedAddressRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ASSOCIATEDADDRESSREMOVED_EVENT, log);
                AssociatedAddressRemovedEventResponse typedResponse = new AssociatedAddressRemovedEventResponse();
                typedResponse.log = log;
                typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.removedAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AssociatedAddressRemovedEventResponse> associatedAddressRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ASSOCIATEDADDRESSREMOVED_EVENT));
        return associatedAddressRemovedEventFlowable(filter);
    }

    public List<ProviderAddedEventResponse> getProviderAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PROVIDERADDED_EVENT, transactionReceipt);
        ArrayList<ProviderAddedEventResponse> responses = new ArrayList<ProviderAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ProviderAddedEventResponse typedResponse = new ProviderAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.provider = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ProviderAddedEventResponse> providerAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ProviderAddedEventResponse>() {
            @Override
            public ProviderAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PROVIDERADDED_EVENT, log);
                ProviderAddedEventResponse typedResponse = new ProviderAddedEventResponse();
                typedResponse.log = log;
                typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.provider = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ProviderAddedEventResponse> providerAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROVIDERADDED_EVENT));
        return providerAddedEventFlowable(filter);
    }

    public List<ProviderRemovedEventResponse> getProviderRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PROVIDERREMOVED_EVENT, transactionReceipt);
        ArrayList<ProviderRemovedEventResponse> responses = new ArrayList<ProviderRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ProviderRemovedEventResponse typedResponse = new ProviderRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.provider = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ProviderRemovedEventResponse> providerRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ProviderRemovedEventResponse>() {
            @Override
            public ProviderRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PROVIDERREMOVED_EVENT, log);
                ProviderRemovedEventResponse typedResponse = new ProviderRemovedEventResponse();
                typedResponse.log = log;
                typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.provider = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ProviderRemovedEventResponse> providerRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROVIDERREMOVED_EVENT));
        return providerRemovedEventFlowable(filter);
    }

    public List<ResolverAddedEventResponse> getResolverAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RESOLVERADDED_EVENT, transactionReceipt);
        ArrayList<ResolverAddedEventResponse> responses = new ArrayList<ResolverAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ResolverAddedEventResponse typedResponse = new ResolverAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.resolvers = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ResolverAddedEventResponse> resolverAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ResolverAddedEventResponse>() {
            @Override
            public ResolverAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RESOLVERADDED_EVENT, log);
                ResolverAddedEventResponse typedResponse = new ResolverAddedEventResponse();
                typedResponse.log = log;
                typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.resolvers = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ResolverAddedEventResponse> resolverAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RESOLVERADDED_EVENT));
        return resolverAddedEventFlowable(filter);
    }

    public List<ResolverRemovedEventResponse> getResolverRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RESOLVERREMOVED_EVENT, transactionReceipt);
        ArrayList<ResolverRemovedEventResponse> responses = new ArrayList<ResolverRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ResolverRemovedEventResponse typedResponse = new ResolverRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.resolvers = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ResolverRemovedEventResponse> resolverRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ResolverRemovedEventResponse>() {
            @Override
            public ResolverRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RESOLVERREMOVED_EVENT, log);
                ResolverRemovedEventResponse typedResponse = new ResolverRemovedEventResponse();
                typedResponse.log = log;
                typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.resolvers = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ResolverRemovedEventResponse> resolverRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RESOLVERREMOVED_EVENT));
        return resolverRemovedEventFlowable(filter);
    }

    public List<RecoveryAddressChangeTriggeredEventResponse> getRecoveryAddressChangeTriggeredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RECOVERYADDRESSCHANGETRIGGERED_EVENT, transactionReceipt);
        ArrayList<RecoveryAddressChangeTriggeredEventResponse> responses = new ArrayList<RecoveryAddressChangeTriggeredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RecoveryAddressChangeTriggeredEventResponse typedResponse = new RecoveryAddressChangeTriggeredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.oldRecoveryAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newRecoveryAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RecoveryAddressChangeTriggeredEventResponse> recoveryAddressChangeTriggeredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RecoveryAddressChangeTriggeredEventResponse>() {
            @Override
            public RecoveryAddressChangeTriggeredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RECOVERYADDRESSCHANGETRIGGERED_EVENT, log);
                RecoveryAddressChangeTriggeredEventResponse typedResponse = new RecoveryAddressChangeTriggeredEventResponse();
                typedResponse.log = log;
                typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.oldRecoveryAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newRecoveryAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.delegated = (Boolean) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RecoveryAddressChangeTriggeredEventResponse> recoveryAddressChangeTriggeredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RECOVERYADDRESSCHANGETRIGGERED_EVENT));
        return recoveryAddressChangeTriggeredEventFlowable(filter);
    }

    public List<RecoveryTriggeredEventResponse> getRecoveryTriggeredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RECOVERYTRIGGERED_EVENT, transactionReceipt);
        ArrayList<RecoveryTriggeredEventResponse> responses = new ArrayList<RecoveryTriggeredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RecoveryTriggeredEventResponse typedResponse = new RecoveryTriggeredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.oldAssociatedAddresses = (List<String>) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newAssociatedAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RecoveryTriggeredEventResponse> recoveryTriggeredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RecoveryTriggeredEventResponse>() {
            @Override
            public RecoveryTriggeredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RECOVERYTRIGGERED_EVENT, log);
                RecoveryTriggeredEventResponse typedResponse = new RecoveryTriggeredEventResponse();
                typedResponse.log = log;
                typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.oldAssociatedAddresses = (List<String>) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newAssociatedAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RecoveryTriggeredEventResponse> recoveryTriggeredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RECOVERYTRIGGERED_EVENT));
        return recoveryTriggeredEventFlowable(filter);
    }

    public List<IdentityDestroyedEventResponse> getIdentityDestroyedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(IDENTITYDESTROYED_EVENT, transactionReceipt);
        ArrayList<IdentityDestroyedEventResponse> responses = new ArrayList<IdentityDestroyedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            IdentityDestroyedEventResponse typedResponse = new IdentityDestroyedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.recoveryAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.resolversReset = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<IdentityDestroyedEventResponse> identityDestroyedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, IdentityDestroyedEventResponse>() {
            @Override
            public IdentityDestroyedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(IDENTITYDESTROYED_EVENT, log);
                IdentityDestroyedEventResponse typedResponse = new IdentityDestroyedEventResponse();
                typedResponse.log = log;
                typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.recoveryAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.resolversReset = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<IdentityDestroyedEventResponse> identityDestroyedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(IDENTITYDESTROYED_EVENT));
        return identityDestroyedEventFlowable(filter);
    }

    public RemoteCall<Boolean> isSigned(String _address, byte[] messageHash, BigInteger v, byte[] r, byte[] s) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISSIGNED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_address), 
                new org.web3j.abi.datatypes.generated.Bytes32(messageHash), 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Boolean> identityExists(BigInteger ein) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_IDENTITYEXISTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ein)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Boolean> hasIdentity(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_HASIDENTITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> getEIN(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETEIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> isAssociatedAddressFor(BigInteger ein, String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISASSOCIATEDADDRESSFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ein), 
                new org.web3j.abi.datatypes.Address(_address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Boolean> isProviderFor(BigInteger ein, String provider) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISPROVIDERFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ein), 
                new org.web3j.abi.datatypes.Address(provider)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Boolean> isResolverFor(BigInteger ein, String resolver) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISRESOLVERFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ein), 
                new org.web3j.abi.datatypes.Address(resolver)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Tuple4<String, List<String>, List<String>, List<String>>> getIdentity(BigInteger ein) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETIDENTITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ein)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteCall<Tuple4<String, List<String>, List<String>, List<String>>>(
                new Callable<Tuple4<String, List<String>, List<String>, List<String>>>() {
                    @Override
                    public Tuple4<String, List<String>, List<String>, List<String>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, List<String>, List<String>, List<String>>(
                                (String) results.get(0).getValue(), 
                                convertToNative((List<Address>) results.get(1).getValue()), 
                                convertToNative((List<Address>) results.get(2).getValue()), 
                                convertToNative((List<Address>) results.get(3).getValue()));
                    }
                });
    }

    public RemoteCall<BigInteger> mapuint8(BigInteger data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MAPUINT8, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(data)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<byte[]> mapbytes32(byte[] data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MAPBYTES32, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(data)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<String> erecover(String recoveryAddress, String associatedAddress, List<String> providers, List<String> resolvers, BigInteger v, byte[] r, byte[] s, BigInteger timestamp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ERECOVER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(recoveryAddress), 
                new org.web3j.abi.datatypes.Address(associatedAddress), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(providers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(resolvers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s), 
                new org.web3j.abi.datatypes.generated.Uint256(timestamp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<byte[]> encode(String recoveryAddress, String associatedAddress, List<String> providers, List<String> resolvers, BigInteger timestamp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ENCODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(recoveryAddress), 
                new org.web3j.abi.datatypes.Address(associatedAddress), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(providers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(resolvers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(timestamp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<byte[]> hash(String recoveryAddress, String associatedAddress, List<String> providers, List<String> resolvers, BigInteger timestamp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_HASH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(recoveryAddress), 
                new org.web3j.abi.datatypes.Address(associatedAddress), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(providers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(resolvers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(timestamp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> createIdentity(String recoveryAddress, List<String> providers, List<String> resolvers) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATEIDENTITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(recoveryAddress), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(providers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(resolvers, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> createIdentityDelegated(String recoveryAddress, String associatedAddress, List<String> providers, List<String> resolvers, BigInteger v, byte[] r, byte[] s, BigInteger timestamp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATEIDENTITYDELEGATED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(recoveryAddress), 
                new org.web3j.abi.datatypes.Address(associatedAddress), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(providers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(resolvers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s), 
                new org.web3j.abi.datatypes.generated.Uint256(timestamp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addAssociatedAddress(String approvingAddress, String addressToAdd, BigInteger v, byte[] r, byte[] s, BigInteger timestamp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDASSOCIATEDADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(approvingAddress), 
                new org.web3j.abi.datatypes.Address(addressToAdd), 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s), 
                new org.web3j.abi.datatypes.generated.Uint256(timestamp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addAssociatedAddressDelegated(String approvingAddress, String addressToAdd, List<BigInteger> v, List<byte[]> r, List<byte[]> s, List<BigInteger> timestamp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDASSOCIATEDADDRESSDELEGATED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(approvingAddress), 
                new org.web3j.abi.datatypes.Address(addressToAdd), 
                new org.web3j.abi.datatypes.generated.StaticArray2<org.web3j.abi.datatypes.generated.Uint8>(
                        org.web3j.abi.datatypes.generated.Uint8.class,
                        org.web3j.abi.Utils.typeMap(v, org.web3j.abi.datatypes.generated.Uint8.class)), 
                new org.web3j.abi.datatypes.generated.StaticArray2<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(r, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.generated.StaticArray2<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(s, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.generated.StaticArray2<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(timestamp, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> removeAssociatedAddress() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVEASSOCIATEDADDRESS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> removeAssociatedAddressDelegated(String addressToRemove, BigInteger v, byte[] r, byte[] s, BigInteger timestamp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVEASSOCIATEDADDRESSDELEGATED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addressToRemove), 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s), 
                new org.web3j.abi.datatypes.generated.Uint256(timestamp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addProviders(List<String> providers) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDPROVIDERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(providers, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addProvidersFor(BigInteger ein, List<String> providers) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDPROVIDERSFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ein), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(providers, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> removeProviders(List<String> providers) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVEPROVIDERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(providers, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> removeProvidersFor(BigInteger ein, List<String> providers) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVEPROVIDERSFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ein), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(providers, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addResolvers(List<String> resolvers) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDRESOLVERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(resolvers, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addResolversFor(BigInteger ein, List<String> resolvers) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDRESOLVERSFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ein), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(resolvers, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> removeResolvers(List<String> resolvers) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVERESOLVERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(resolvers, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> removeResolversFor(BigInteger ein, List<String> resolvers) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVERESOLVERSFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ein), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(resolvers, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> triggerRecoveryAddressChange(String newRecoveryAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRIGGERRECOVERYADDRESSCHANGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newRecoveryAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> triggerRecoveryAddressChangeFor(BigInteger ein, String newRecoveryAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRIGGERRECOVERYADDRESSCHANGEFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ein), 
                new org.web3j.abi.datatypes.Address(newRecoveryAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> triggerRecovery(BigInteger ein, String newAssociatedAddress, BigInteger v, byte[] r, byte[] s, BigInteger timestamp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRIGGERRECOVERY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ein), 
                new org.web3j.abi.datatypes.Address(newAssociatedAddress), 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s), 
                new org.web3j.abi.datatypes.generated.Uint256(timestamp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> triggerDestruction(BigInteger ein, List<String> firstChunk, List<String> lastChunk, Boolean resetResolvers) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRIGGERDESTRUCTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ein), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(firstChunk, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(lastChunk, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.Bool(resetResolvers)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IdentityRegistryInterface load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IdentityRegistryInterface(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IdentityRegistryInterface load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IdentityRegistryInterface(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IdentityRegistryInterface load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IdentityRegistryInterface(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IdentityRegistryInterface load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IdentityRegistryInterface(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IdentityRegistryInterface> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IdentityRegistryInterface.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IdentityRegistryInterface> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IdentityRegistryInterface.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<IdentityRegistryInterface> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IdentityRegistryInterface.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IdentityRegistryInterface> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IdentityRegistryInterface.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class IdentityCreatedEventResponse {
        public Log log;

        public String initiator;

        public BigInteger ein;

        public String associatedAddress;

        public String recoveryAddress;

        public List<String> providers;

        public List<String> resolvers;

        public Boolean delegated;
    }

    public static class AssociatedAddressAddedEventResponse {
        public Log log;

        public String initiator;

        public BigInteger ein;

        public String approvingAddress;

        public String addedAddress;

        public Boolean delegated;
    }

    public static class AssociatedAddressRemovedEventResponse {
        public Log log;

        public String initiator;

        public BigInteger ein;

        public String removedAddress;

        public Boolean delegated;
    }

    public static class ProviderAddedEventResponse {
        public Log log;

        public String initiator;

        public BigInteger ein;

        public String provider;

        public Boolean delegated;
    }

    public static class ProviderRemovedEventResponse {
        public Log log;

        public String initiator;

        public BigInteger ein;

        public String provider;

        public Boolean delegated;
    }

    public static class ResolverAddedEventResponse {
        public Log log;

        public String initiator;

        public BigInteger ein;

        public String resolvers;

        public Boolean delegated;
    }

    public static class ResolverRemovedEventResponse {
        public Log log;

        public String initiator;

        public BigInteger ein;

        public String resolvers;

        public Boolean delegated;
    }

    public static class RecoveryAddressChangeTriggeredEventResponse {
        public Log log;

        public String initiator;

        public BigInteger ein;

        public String oldRecoveryAddress;

        public String newRecoveryAddress;

        public Boolean delegated;
    }

    public static class RecoveryTriggeredEventResponse {
        public Log log;

        public String initiator;

        public BigInteger ein;

        public List<String> oldAssociatedAddresses;

        public String newAssociatedAddress;
    }

    public static class IdentityDestroyedEventResponse {
        public Log log;

        public String initiator;

        public BigInteger ein;

        public String recoveryAddress;

        public Boolean resolversReset;
    }
}
