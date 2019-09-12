package com.idhub.wallet.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes1;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.3.0.
 */
public class IERC1400 extends Contract {
    private static final String BINARY = "";

    public static final String FUNC_SETDOCUMENT = "setDocument";

    public static final String FUNC_AUTHORIZEOPERATORBYPARTITION = "authorizeOperatorByPartition";

    public static final String FUNC_OPERATORREDEEMBYPARTITION = "operatorRedeemByPartition";

    public static final String FUNC_REVOKEOPERATORBYPARTITION = "revokeOperatorByPartition";

    public static final String FUNC_ISISSUABLE = "isIssuable";

    public static final String FUNC_BALANCEOFBYPARTITION = "balanceOfByPartition";

    public static final String FUNC_ISCONTROLLABLE = "isControllable";

    public static final String FUNC_ISSUEBYPARTITION = "issueByPartition";

    public static final String FUNC_REDEEMBYPARTITION = "redeemByPartition";

    public static final String FUNC_ISOPERATORFORPARTITION = "isOperatorForPartition";

    public static final String FUNC_PARTITIONSOF = "partitionsOf";

    public static final String FUNC_CONTROLLERREDEEM = "controllerRedeem";

    public static final String FUNC_OPERATORTRANSFERBYPARTITION = "operatorTransferByPartition";

    public static final String FUNC_AUTHORIZEOPERATOR = "authorizeOperator";

    public static final String FUNC_CONTROLLERTRANSFER = "controllerTransfer";

    public static final String FUNC_GETDOCUMENT = "getDocument";

    public static final String FUNC_ISOPERATOR = "isOperator";

    public static final String FUNC_CANTRANSFERBYPARTITION = "canTransferByPartition";

    public static final String FUNC_TRANSFERBYPARTITION = "transferByPartition";

    public static final String FUNC_REVOKEOPERATOR = "revokeOperator";

    public static final Event CONTROLLERTRANSFER_EVENT = new Event("ControllerTransfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event CONTROLLERREDEMPTION_EVENT = new Event("ControllerRedemption", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event DOCUMENT_EVENT = new Event("Document", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event TRANSFERBYPARTITION_EVENT = new Event("TransferByPartition", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event CHANGEDPARTITION_EVENT = new Event("ChangedPartition", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event AUTHORIZEDOPERATOR_EVENT = new Event("AuthorizedOperator", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event REVOKEDOPERATOR_EVENT = new Event("RevokedOperator", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event AUTHORIZEDOPERATORBYPARTITION_EVENT = new Event("AuthorizedOperatorByPartition", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event REVOKEDOPERATORBYPARTITION_EVENT = new Event("RevokedOperatorByPartition", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event ISSUED_EVENT = new Event("Issued", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event REDEEMED_EVENT = new Event("Redeemed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event ISSUEDBYPARTITION_EVENT = new Event("IssuedByPartition", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event REDEEMEDBYPARTITION_EVENT = new Event("RedeemedByPartition", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    @Deprecated
    protected IERC1400(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IERC1400(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IERC1400(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IERC1400(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> setDocument(byte[] _name, String _uri, byte[] _documentHash) {
        final Function function = new Function(
                FUNC_SETDOCUMENT, 
                Arrays.<Type>asList(new Bytes32(_name),
                new Utf8String(_uri),
                new Bytes32(_documentHash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> authorizeOperatorByPartition(byte[] _partition, String _operator) {
        final Function function = new Function(
                FUNC_AUTHORIZEOPERATORBYPARTITION, 
                Arrays.<Type>asList(new Bytes32(_partition),
                new Address(_operator)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> operatorRedeemByPartition(byte[] _partition, String _tokenHolder, BigInteger _value, byte[] _data, byte[] _operatorData) {
        final Function function = new Function(
                FUNC_OPERATORREDEEMBYPARTITION, 
                Arrays.<Type>asList(new Bytes32(_partition),
                new Address(_tokenHolder),
                new Uint256(_value),
                new DynamicBytes(_data),
                new DynamicBytes(_operatorData)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> revokeOperatorByPartition(byte[] _partition, String _operator) {
        final Function function = new Function(
                FUNC_REVOKEOPERATORBYPARTITION, 
                Arrays.<Type>asList(new Bytes32(_partition),
                new Address(_operator)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> isIssuable() {
        final Function function = new Function(FUNC_ISISSUABLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> balanceOfByPartition(byte[] _partition, String _tokenHolder) {
        final Function function = new Function(FUNC_BALANCEOFBYPARTITION, 
                Arrays.<Type>asList(new Bytes32(_partition),
                new Address(_tokenHolder)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> isControllable() {
        final Function function = new Function(FUNC_ISCONTROLLABLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> issueByPartition(byte[] _partition, String _tokenHolder, BigInteger _value, byte[] _data, BigInteger _Day) {
        final Function function = new Function(
                FUNC_ISSUEBYPARTITION, 
                Arrays.<Type>asList(new Bytes32(_partition),
                new Address(_tokenHolder),
                new Uint256(_value),
                new DynamicBytes(_data),
                new Uint256(_Day)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> redeemByPartition(byte[] _partition, BigInteger _value, byte[] _data) {
        final Function function = new Function(
                FUNC_REDEEMBYPARTITION, 
                Arrays.<Type>asList(new Bytes32(_partition),
                new Uint256(_value),
                new DynamicBytes(_data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> isOperatorForPartition(byte[] _partition, String _operator, String _tokenHolder) {
        final Function function = new Function(FUNC_ISOPERATORFORPARTITION, 
                Arrays.<Type>asList(new Bytes32(_partition),
                new Address(_operator),
                new Address(_tokenHolder)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<List> partitionsOf(String _tokenHolder) {
        final Function function = new Function(FUNC_PARTITIONSOF, 
                Arrays.<Type>asList(new Address(_tokenHolder)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<TransactionReceipt> controllerRedeem(byte[] _partition, String _tokenHolder, BigInteger _value, byte[] _data, byte[] _operatorData) {
        final Function function = new Function(
                FUNC_CONTROLLERREDEEM, 
                Arrays.<Type>asList(new Bytes32(_partition),
                new Address(_tokenHolder),
                new Uint256(_value),
                new DynamicBytes(_data),
                new DynamicBytes(_operatorData)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> operatorTransferByPartition(byte[] _partition, String _from, String _to, BigInteger _value, byte[] _data, byte[] _operatorData) {
        final Function function = new Function(
                FUNC_OPERATORTRANSFERBYPARTITION, 
                Arrays.<Type>asList(new Bytes32(_partition),
                new Address(_from),
                new Address(_to),
                new Uint256(_value),
                new DynamicBytes(_data),
                new DynamicBytes(_operatorData)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> authorizeOperator(String _operator) {
        final Function function = new Function(
                FUNC_AUTHORIZEOPERATOR, 
                Arrays.<Type>asList(new Address(_operator)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> controllerTransfer(byte[] _partition, String _from, String _to, BigInteger _value, byte[] _data, byte[] _operatorData) {
        final Function function = new Function(
                FUNC_CONTROLLERTRANSFER, 
                Arrays.<Type>asList(new Bytes32(_partition),
                new Address(_from),
                new Address(_to),
                new Uint256(_value),
                new DynamicBytes(_data),
                new DynamicBytes(_operatorData)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple2<String, byte[]>> getDocument(byte[] _name) {
        final Function function = new Function(FUNC_GETDOCUMENT, 
                Arrays.<Type>asList(new Bytes32(_name)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteCall<Tuple2<String, byte[]>>(
                new Callable<Tuple2<String, byte[]>>() {
                    @Override
                    public Tuple2<String, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, byte[]>(
                                (String) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<Boolean> isOperator(String _operator, String _tokenHolder) {
        final Function function = new Function(FUNC_ISOPERATOR, 
                Arrays.<Type>asList(new Address(_operator),
                new Address(_tokenHolder)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Tuple3<byte[], byte[], byte[]>> canTransferByPartition(byte[] _partition, String _from, String _to, BigInteger _value, byte[] _data) {
        final Function function = new Function(FUNC_CANTRANSFERBYPARTITION, 
                Arrays.<Type>asList(new Bytes32(_partition),
                new Address(_from),
                new Address(_to),
                new Uint256(_value),
                new DynamicBytes(_data)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes1>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteCall<Tuple3<byte[], byte[], byte[]>>(
                new Callable<Tuple3<byte[], byte[], byte[]>>() {
                    @Override
                    public Tuple3<byte[], byte[], byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<byte[], byte[], byte[]>(
                                (byte[]) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> transferByPartition(byte[] _partition, String _to, BigInteger _value, byte[] _data) {
        final Function function = new Function(
                FUNC_TRANSFERBYPARTITION, 
                Arrays.<Type>asList(new Bytes32(_partition),
                new Address(_to),
                new Uint256(_value),
                new DynamicBytes(_data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> revokeOperator(String _operator) {
        final Function function = new Function(
                FUNC_REVOKEOPERATOR, 
                Arrays.<Type>asList(new Address(_operator)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<ControllerTransferEventResponse> getControllerTransferEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CONTROLLERTRANSFER_EVENT, transactionReceipt);
        ArrayList<ControllerTransferEventResponse> responses = new ArrayList<ControllerTransferEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ControllerTransferEventResponse typedResponse = new ControllerTransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._controller = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._data = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._operatorData = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ControllerTransferEventResponse> controllerTransferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ControllerTransferEventResponse>() {
            @Override
            public ControllerTransferEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CONTROLLERTRANSFER_EVENT, log);
                ControllerTransferEventResponse typedResponse = new ControllerTransferEventResponse();
                typedResponse.log = log;
                typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._controller = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._data = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._operatorData = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ControllerTransferEventResponse> controllerTransferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTROLLERTRANSFER_EVENT));
        return controllerTransferEventFlowable(filter);
    }

    public List<ControllerRedemptionEventResponse> getControllerRedemptionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CONTROLLERREDEMPTION_EVENT, transactionReceipt);
        ArrayList<ControllerRedemptionEventResponse> responses = new ArrayList<ControllerRedemptionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ControllerRedemptionEventResponse typedResponse = new ControllerRedemptionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._tokenHolder = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._controller = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._data = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._operatorData = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ControllerRedemptionEventResponse> controllerRedemptionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ControllerRedemptionEventResponse>() {
            @Override
            public ControllerRedemptionEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CONTROLLERREDEMPTION_EVENT, log);
                ControllerRedemptionEventResponse typedResponse = new ControllerRedemptionEventResponse();
                typedResponse.log = log;
                typedResponse._tokenHolder = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._controller = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._data = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._operatorData = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ControllerRedemptionEventResponse> controllerRedemptionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTROLLERREDEMPTION_EVENT));
        return controllerRedemptionEventFlowable(filter);
    }

    public List<DocumentEventResponse> getDocumentEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(DOCUMENT_EVENT, transactionReceipt);
        ArrayList<DocumentEventResponse> responses = new ArrayList<DocumentEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            DocumentEventResponse typedResponse = new DocumentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._name = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._uri = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._documentHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DocumentEventResponse> documentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, DocumentEventResponse>() {
            @Override
            public DocumentEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(DOCUMENT_EVENT, log);
                DocumentEventResponse typedResponse = new DocumentEventResponse();
                typedResponse.log = log;
                typedResponse._name = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._uri = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._documentHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DocumentEventResponse> documentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DOCUMENT_EVENT));
        return documentEventFlowable(filter);
    }

    public List<TransferByPartitionEventResponse> getTransferByPartitionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFERBYPARTITION_EVENT, transactionReceipt);
        ArrayList<TransferByPartitionEventResponse> responses = new ArrayList<TransferByPartitionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TransferByPartitionEventResponse typedResponse = new TransferByPartitionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._fromPartition = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._from = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._to = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse._operator = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._data = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._operatorData = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferByPartitionEventResponse> transferByPartitionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TransferByPartitionEventResponse>() {
            @Override
            public TransferByPartitionEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFERBYPARTITION_EVENT, log);
                TransferByPartitionEventResponse typedResponse = new TransferByPartitionEventResponse();
                typedResponse.log = log;
                typedResponse._fromPartition = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._from = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._to = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse._operator = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._data = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._operatorData = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferByPartitionEventResponse> transferByPartitionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFERBYPARTITION_EVENT));
        return transferByPartitionEventFlowable(filter);
    }

    public List<ChangedPartitionEventResponse> getChangedPartitionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CHANGEDPARTITION_EVENT, transactionReceipt);
        ArrayList<ChangedPartitionEventResponse> responses = new ArrayList<ChangedPartitionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ChangedPartitionEventResponse typedResponse = new ChangedPartitionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._fromPartition = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._toPartition = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChangedPartitionEventResponse> changedPartitionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ChangedPartitionEventResponse>() {
            @Override
            public ChangedPartitionEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CHANGEDPARTITION_EVENT, log);
                ChangedPartitionEventResponse typedResponse = new ChangedPartitionEventResponse();
                typedResponse.log = log;
                typedResponse._fromPartition = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._toPartition = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChangedPartitionEventResponse> changedPartitionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANGEDPARTITION_EVENT));
        return changedPartitionEventFlowable(filter);
    }

    public List<AuthorizedOperatorEventResponse> getAuthorizedOperatorEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(AUTHORIZEDOPERATOR_EVENT, transactionReceipt);
        ArrayList<AuthorizedOperatorEventResponse> responses = new ArrayList<AuthorizedOperatorEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AuthorizedOperatorEventResponse typedResponse = new AuthorizedOperatorEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._operator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._tokenHolder = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AuthorizedOperatorEventResponse> authorizedOperatorEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AuthorizedOperatorEventResponse>() {
            @Override
            public AuthorizedOperatorEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(AUTHORIZEDOPERATOR_EVENT, log);
                AuthorizedOperatorEventResponse typedResponse = new AuthorizedOperatorEventResponse();
                typedResponse.log = log;
                typedResponse._operator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._tokenHolder = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AuthorizedOperatorEventResponse> authorizedOperatorEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUTHORIZEDOPERATOR_EVENT));
        return authorizedOperatorEventFlowable(filter);
    }

    public List<RevokedOperatorEventResponse> getRevokedOperatorEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REVOKEDOPERATOR_EVENT, transactionReceipt);
        ArrayList<RevokedOperatorEventResponse> responses = new ArrayList<RevokedOperatorEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RevokedOperatorEventResponse typedResponse = new RevokedOperatorEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._operator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._tokenHolder = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RevokedOperatorEventResponse> revokedOperatorEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, RevokedOperatorEventResponse>() {
            @Override
            public RevokedOperatorEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(REVOKEDOPERATOR_EVENT, log);
                RevokedOperatorEventResponse typedResponse = new RevokedOperatorEventResponse();
                typedResponse.log = log;
                typedResponse._operator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._tokenHolder = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RevokedOperatorEventResponse> revokedOperatorEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REVOKEDOPERATOR_EVENT));
        return revokedOperatorEventFlowable(filter);
    }

    public List<AuthorizedOperatorByPartitionEventResponse> getAuthorizedOperatorByPartitionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(AUTHORIZEDOPERATORBYPARTITION_EVENT, transactionReceipt);
        ArrayList<AuthorizedOperatorByPartitionEventResponse> responses = new ArrayList<AuthorizedOperatorByPartitionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AuthorizedOperatorByPartitionEventResponse typedResponse = new AuthorizedOperatorByPartitionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._partition = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._tokenHolder = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AuthorizedOperatorByPartitionEventResponse> authorizedOperatorByPartitionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AuthorizedOperatorByPartitionEventResponse>() {
            @Override
            public AuthorizedOperatorByPartitionEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(AUTHORIZEDOPERATORBYPARTITION_EVENT, log);
                AuthorizedOperatorByPartitionEventResponse typedResponse = new AuthorizedOperatorByPartitionEventResponse();
                typedResponse.log = log;
                typedResponse._partition = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._tokenHolder = (String) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AuthorizedOperatorByPartitionEventResponse> authorizedOperatorByPartitionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUTHORIZEDOPERATORBYPARTITION_EVENT));
        return authorizedOperatorByPartitionEventFlowable(filter);
    }

    public List<RevokedOperatorByPartitionEventResponse> getRevokedOperatorByPartitionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REVOKEDOPERATORBYPARTITION_EVENT, transactionReceipt);
        ArrayList<RevokedOperatorByPartitionEventResponse> responses = new ArrayList<RevokedOperatorByPartitionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RevokedOperatorByPartitionEventResponse typedResponse = new RevokedOperatorByPartitionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._partition = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._tokenHolder = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RevokedOperatorByPartitionEventResponse> revokedOperatorByPartitionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, RevokedOperatorByPartitionEventResponse>() {
            @Override
            public RevokedOperatorByPartitionEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(REVOKEDOPERATORBYPARTITION_EVENT, log);
                RevokedOperatorByPartitionEventResponse typedResponse = new RevokedOperatorByPartitionEventResponse();
                typedResponse.log = log;
                typedResponse._partition = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._tokenHolder = (String) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RevokedOperatorByPartitionEventResponse> revokedOperatorByPartitionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REVOKEDOPERATORBYPARTITION_EVENT));
        return revokedOperatorByPartitionEventFlowable(filter);
    }

    public List<IssuedEventResponse> getIssuedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ISSUED_EVENT, transactionReceipt);
        ArrayList<IssuedEventResponse> responses = new ArrayList<IssuedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IssuedEventResponse typedResponse = new IssuedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._operator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._data = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<IssuedEventResponse> issuedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, IssuedEventResponse>() {
            @Override
            public IssuedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ISSUED_EVENT, log);
                IssuedEventResponse typedResponse = new IssuedEventResponse();
                typedResponse.log = log;
                typedResponse._operator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._data = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<IssuedEventResponse> issuedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ISSUED_EVENT));
        return issuedEventFlowable(filter);
    }

    public List<RedeemedEventResponse> getRedeemedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REDEEMED_EVENT, transactionReceipt);
        ArrayList<RedeemedEventResponse> responses = new ArrayList<RedeemedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RedeemedEventResponse typedResponse = new RedeemedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._operator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._from = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._data = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RedeemedEventResponse> redeemedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, RedeemedEventResponse>() {
            @Override
            public RedeemedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(REDEEMED_EVENT, log);
                RedeemedEventResponse typedResponse = new RedeemedEventResponse();
                typedResponse.log = log;
                typedResponse._operator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._from = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._data = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RedeemedEventResponse> redeemedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REDEEMED_EVENT));
        return redeemedEventFlowable(filter);
    }

    public List<IssuedByPartitionEventResponse> getIssuedByPartitionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ISSUEDBYPARTITION_EVENT, transactionReceipt);
        ArrayList<IssuedByPartitionEventResponse> responses = new ArrayList<IssuedByPartitionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IssuedByPartitionEventResponse typedResponse = new IssuedByPartitionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._partition = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._to = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._data = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._operatorData = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<IssuedByPartitionEventResponse> issuedByPartitionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, IssuedByPartitionEventResponse>() {
            @Override
            public IssuedByPartitionEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ISSUEDBYPARTITION_EVENT, log);
                IssuedByPartitionEventResponse typedResponse = new IssuedByPartitionEventResponse();
                typedResponse.log = log;
                typedResponse._partition = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._to = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._data = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._operatorData = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<IssuedByPartitionEventResponse> issuedByPartitionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ISSUEDBYPARTITION_EVENT));
        return issuedByPartitionEventFlowable(filter);
    }

    public List<RedeemedByPartitionEventResponse> getRedeemedByPartitionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REDEEMEDBYPARTITION_EVENT, transactionReceipt);
        ArrayList<RedeemedByPartitionEventResponse> responses = new ArrayList<RedeemedByPartitionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RedeemedByPartitionEventResponse typedResponse = new RedeemedByPartitionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._partition = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._from = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._operatorData = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RedeemedByPartitionEventResponse> redeemedByPartitionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, RedeemedByPartitionEventResponse>() {
            @Override
            public RedeemedByPartitionEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(REDEEMEDBYPARTITION_EVENT, log);
                RedeemedByPartitionEventResponse typedResponse = new RedeemedByPartitionEventResponse();
                typedResponse.log = log;
                typedResponse._partition = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._from = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._operatorData = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RedeemedByPartitionEventResponse> redeemedByPartitionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REDEEMEDBYPARTITION_EVENT));
        return redeemedByPartitionEventFlowable(filter);
    }

    @Deprecated
    public static IERC1400 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IERC1400(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IERC1400 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IERC1400(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IERC1400 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IERC1400(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IERC1400 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IERC1400(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IERC1400> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IERC1400.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IERC1400> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IERC1400.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<IERC1400> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IERC1400.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IERC1400> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IERC1400.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ControllerTransferEventResponse {
        public Log log;

        public String _from;

        public String _to;

        public String _controller;

        public BigInteger _value;

        public byte[] _data;

        public byte[] _operatorData;
    }

    public static class ControllerRedemptionEventResponse {
        public Log log;

        public String _tokenHolder;

        public String _controller;

        public BigInteger _value;

        public byte[] _data;

        public byte[] _operatorData;
    }

    public static class DocumentEventResponse {
        public Log log;

        public byte[] _name;

        public String _uri;

        public byte[] _documentHash;
    }

    public static class TransferByPartitionEventResponse {
        public Log log;

        public byte[] _fromPartition;

        public String _from;

        public String _to;

        public String _operator;

        public BigInteger _value;

        public byte[] _data;

        public byte[] _operatorData;
    }

    public static class ChangedPartitionEventResponse {
        public Log log;

        public byte[] _fromPartition;

        public byte[] _toPartition;

        public BigInteger _value;
    }

    public static class AuthorizedOperatorEventResponse {
        public Log log;

        public String _operator;

        public String _tokenHolder;
    }

    public static class RevokedOperatorEventResponse {
        public Log log;

        public String _operator;

        public String _tokenHolder;
    }

    public static class AuthorizedOperatorByPartitionEventResponse {
        public Log log;

        public byte[] _partition;

        public String _operator;

        public String _tokenHolder;
    }

    public static class RevokedOperatorByPartitionEventResponse {
        public Log log;

        public byte[] _partition;

        public String _operator;

        public String _tokenHolder;
    }

    public static class IssuedEventResponse {
        public Log log;

        public String _operator;

        public String _to;

        public BigInteger _value;

        public byte[] _data;
    }

    public static class RedeemedEventResponse {
        public Log log;

        public String _operator;

        public String _from;

        public BigInteger _value;

        public byte[] _data;
    }

    public static class IssuedByPartitionEventResponse {
        public Log log;

        public byte[] _partition;

        public String _operator;

        public String _to;

        public BigInteger _value;

        public byte[] _data;

        public byte[] _operatorData;
    }

    public static class RedeemedByPartitionEventResponse {
        public Log log;

        public byte[] _partition;

        public String _operator;

        public String _from;

        public BigInteger _value;

        public byte[] _operatorData;
    }
}
