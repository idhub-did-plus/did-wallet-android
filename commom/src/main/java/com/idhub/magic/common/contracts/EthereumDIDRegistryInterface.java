package com.idhub.magic.common.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class EthereumDIDRegistryInterface extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50611337806100206000396000f3fe608060405234801561001057600080fd5b50600436106100ff5760003560e01c806380b29f7c11610097578063a7068d6611610066578063a7068d6614610580578063e476af5c146105ba578063f00d4b5d14610689578063f96d0f9f146106b7576100ff565b806380b29f7c1461048a5780638733d4e8146104c057806393072684146104e65780639c2c1b2b1461052f576100ff565b8063240cf1fa116100d3578063240cf1fa1461031a578063622b2a3c1461035f57806370ae92d2146103a95780637ad4b0a4146103cf576100ff565b8062c023da14610104578063022914a7146101bf5780630d44625b14610201578063123b5e9814610249575b600080fd5b6101bd6004803603606081101561011a57600080fd5b6001600160a01b0382351691602081013591810190606081016040820135600160201b81111561014957600080fd5b82018360208201111561015b57600080fd5b803590602001918460018302840111600160201b8311171561017c57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506106dd945050505050565b005b6101e5600480360360208110156101d557600080fd5b50356001600160a01b03166106ee565b604080516001600160a01b039092168252519081900360200190f35b6102376004803603606081101561021757600080fd5b506001600160a01b03813581169160208101359160409091013516610709565b60408051918252519081900360200190f35b6101bd600480360360e081101561025f57600080fd5b6001600160a01b038235169160ff602082013516916040820135916060810135916080820135919081019060c0810160a0820135600160201b8111156102a457600080fd5b8201836020820111156102b657600080fd5b803590602001918460018302840111600160201b831117156102d757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550509135925061072c915050565b6101bd600480360360a081101561033057600080fd5b506001600160a01b03813581169160ff6020820135169160408201359160608101359160809091013516610892565b6103956004803603606081101561037557600080fd5b506001600160a01b0381358116916020810135916040909101351661095d565b604080519115158252519081900360200190f35b610237600480360360208110156103bf57600080fd5b50356001600160a01b03166109af565b6101bd600480360360808110156103e557600080fd5b6001600160a01b0382351691602081013591810190606081016040820135600160201b81111561041457600080fd5b82018360208201111561042657600080fd5b803590602001918460018302840111600160201b8311171561044757600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955050913592506109c1915050565b6101bd600480360360608110156104a057600080fd5b506001600160a01b038135811691602081013591604090910135166109d4565b6101e5600480360360208110156104d657600080fd5b50356001600160a01b03166109e0565b6101bd600480360360c08110156104fc57600080fd5b506001600160a01b03813581169160ff6020820135169160408201359160608101359160808201359160a0013516610a14565b6101bd600480360360e081101561054557600080fd5b506001600160a01b03813581169160ff6020820135169160408201359160608101359160808201359160a08101359091169060c00135610aeb565b6101bd6004803603608081101561059657600080fd5b506001600160a01b0381358116916020810135916040820135169060600135610bbf565b6101bd600480360360c08110156105d057600080fd5b6001600160a01b038235169160ff602082013516916040820135916060810135916080820135919081019060c0810160a0820135600160201b81111561061557600080fd5b82018360208201111561062757600080fd5b803590602001918460018302840111600160201b8311171561064857600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610bcc945050505050565b6101bd6004803603604081101561069f57600080fd5b506001600160a01b0381358116916020013516610d22565b610237600480360360208110156106cd57600080fd5b50356001600160a01b0316610d31565b6106e983338484610d43565b505050565b6000602081905290815260409020546001600160a01b031681565b600160209081526000938452604080852082529284528284209052825290205481565b6000601960f81b81306003826107418d6109e0565b6001600160a01b03166001600160a01b03168152602001908152602001600020548b88888860405160200180896001600160f81b0319166001600160f81b0319168152600101886001600160f81b0319166001600160f81b0319168152600101876001600160a01b03166001600160a01b031660601b8152601401868152602001856001600160a01b03166001600160a01b031660601b8152601401806b73657441747472696275746560a01b815250600c0184815260200183805190602001908083835b602083106108255780518252601f199092019160209182019101610806565b6001836020036101000a03801982511681845116808217855250505050505090500182815260200198505050505050505050604051602081830303815290604052805190602001209050610888886108808a8a8a8a87610e6f565b868686610f2a565b5050505050505050565b6000601960f81b81306003826108a78b6109e0565b6001600160a01b0316815260208082019290925260409081016000205481516001600160f81b0319968716818501529490951660218501526001600160601b0319606093841b8116602286015260368501959095528a831b851660568501526a31b430b733b2a7bbb732b960a91b606a8501529186901b90931660758301528051606981840301815260899092019052805191012090506109558661094f8188888887610e6f565b84611058565b505050505050565b6001600160a01b03808416600090815260016020908152604080832081518084018890528251808203850181529083018352805190840120845282528083209385168352929052205442109392505050565b60036020526000908152604090205481565b6109ce8433858585610f2a565b50505050565b6106e98333848461110f565b6001600160a01b038082166000908152602081905260408120549091168015610a0a579050610a0f565b829150505b919050565b6000601960f81b8130600382610a298c6109e0565b6001600160a01b0316815260208082019290925260409081016000205481516001600160f81b0319968716818501529490951660218501526001600160601b0319606093841b8116602286015260368501959095528b831b851660568501526d7265766f6b6544656c656761746560901b606a850152607884018890529186901b90931660988301528051608c81840301815260ac909201905280519101209050610ae287610adb8189898987610e6f565b858561110f565b50505050505050565b6000601960f81b8130600382610b008d6109e0565b6001600160a01b0316815260208082019290925260409081016000205481516001600160f81b0319968716818501529490951660218501526001600160601b0319606093841b8116602286015260368501959095528c831b851660568501526a61646444656c656761746560a81b606a850152607584018990529187901b909316609583015260a98083018690528151808403909101815260c990920190528051910120905061088888610bb7818a8a8a87610e6f565b868686611207565b6109ce8433858585611207565b6000601960f81b8130600382610be18c6109e0565b6001600160a01b03166001600160a01b03168152602001908152602001600020548a878760405160200180886001600160f81b0319166001600160f81b0319168152600101876001600160f81b0319166001600160f81b0319168152600101866001600160a01b03166001600160a01b031660601b8152601401858152602001846001600160a01b03166001600160a01b031660601b8152601401806e7265766f6b6541747472696275746560881b815250600f0183815260200182805190602001908083835b60208310610cc75780518252601f199092019160209182019101610ca8565b6001836020036101000a038019825116818451168082178552505050505050905001975050505050505050604051602081830303815290604052805190602001209050610ae287610d1b8989898987610e6f565b8585610d43565b610d2d823383611058565b5050565b60026020526000908152604090205481565b8383610d4e826109e0565b6001600160a01b0316816001600160a01b031614610d6b57600080fd5b856001600160a01b03167f18ab6b2ae3d64306c00ce663125f2bd680e441a098de1635bd7ad8b0d44965e485856000600260008c6001600160a01b03166001600160a01b03168152602001908152602001600020546040518085815260200180602001848152602001838152602001828103825285818151815260200191508051906020019080838360005b83811015610e0f578181015183820152602001610df7565b50505050905090810190601f168015610e3c5780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a25050506001600160a01b0390921660009081526002602052604090204390555050565b60008060018387878760405160008152602001604052604051808581526020018460ff1660ff1681526020018381526020018281526020019450505050506020604051602081039080840390855afa158015610ecf573d6000803e3d6000fd5b505050602060405103519050610ee4876109e0565b6001600160a01b0316816001600160a01b031614610f0157600080fd5b6001600160a01b0381166000908152600360205260409020805460010190559695505050505050565b8484610f35826109e0565b6001600160a01b0316816001600160a01b031614610f5257600080fd5b866001600160a01b03167f18ab6b2ae3d64306c00ce663125f2bd680e441a098de1635bd7ad8b0d44965e48686864201600260008d6001600160a01b03166001600160a01b03168152602001908152602001600020546040518085815260200180602001848152602001838152602001828103825285818151815260200191508051906020019080838360005b83811015610ff7578181015183820152602001610fdf565b50505050905090810190601f1680156110245780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a25050506001600160a01b039093166000908152600260205260409020439055505050565b8282611063826109e0565b6001600160a01b0316816001600160a01b03161461108057600080fd5b6001600160a01b0385811660008181526020818152604080832080546001600160a01b0319169589169586179055600282529182902054825194855290840152805191927f38a5a6e68f30ed1ab45860a4afb34bcb2fc00f22ca462d249b8a8d40cda6f7a3929081900390910190a2505050506001600160a01b03166000908152600260205260409020439055565b838361111a826109e0565b6001600160a01b0316816001600160a01b03161461113757600080fd5b6001600160a01b03808716600081815260016020908152604080832081518084018b90528251808203850181528184018085528151918601919091208652918452828520968a16808652968452828520429081905586865260029094529382902054908a90526060840195909552608083019190915260a0820193909352915190917f5a5084339536bcab65f20799fcc58724588145ca054bd2be626174b27ba156f7919081900360c00190a25050506001600160a01b0390921660009081526002602052604090204390555050565b8484611212826109e0565b6001600160a01b0316816001600160a01b03161461122f57600080fd5b6001600160a01b03808816600081815260016020908152604080832081518084018c90528251808203850181528184018085528151918601919091208652918452828520968b16808652968452828520428b019081905586865260029094529382902054908b90526060840195909552608083019190915260a0820193909352915190917f5a5084339536bcab65f20799fcc58724588145ca054bd2be626174b27ba156f7919081900360c00190a25050506001600160a01b03909316600090815260026020526040902043905550505056fea265627a7a723158207794ab6323e82f9c80a1d7d32888d6acdbd0f155407ef05466b9a461379ccd2564736f6c634300050b0032";

    public static final String FUNC_REVOKEATTRIBUTE = "revokeAttribute";

    public static final String FUNC_OWNERS = "owners";

    public static final String FUNC_DELEGATES = "delegates";

    public static final String FUNC_SETATTRIBUTESIGNED = "setAttributeSigned";

    public static final String FUNC_CHANGEOWNERSIGNED = "changeOwnerSigned";

    public static final String FUNC_VALIDDELEGATE = "validDelegate";

    public static final String FUNC_NONCE = "nonce";

    public static final String FUNC_SETATTRIBUTE = "setAttribute";

    public static final String FUNC_REVOKEDELEGATE = "revokeDelegate";

    public static final String FUNC_IDENTITYOWNER = "identityOwner";

    public static final String FUNC_REVOKEDELEGATESIGNED = "revokeDelegateSigned";

    public static final String FUNC_ADDDELEGATESIGNED = "addDelegateSigned";

    public static final String FUNC_ADDDELEGATE = "addDelegate";

    public static final String FUNC_REVOKEATTRIBUTESIGNED = "revokeAttributeSigned";

    public static final String FUNC_CHANGEOWNER = "changeOwner";

    public static final String FUNC_CHANGED = "changed";

    public static final Event DIDOWNERCHANGED_EVENT = new Event("DIDOwnerChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DIDDELEGATECHANGED_EVENT = new Event("DIDDelegateChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DIDATTRIBUTECHANGED_EVENT = new Event("DIDAttributeChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected EthereumDIDRegistryInterface(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EthereumDIDRegistryInterface(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EthereumDIDRegistryInterface(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EthereumDIDRegistryInterface(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> revokeAttribute(String identity, byte[] name, byte[] value) {
        final Function function = new Function(
                FUNC_REVOKEATTRIBUTE, 
                Arrays.<Type>asList(new Address(identity),
                new Bytes32(name),
                new DynamicBytes(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owners(String param0) {
        final Function function = new Function(FUNC_OWNERS, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> delegates(String param0, byte[] param1, String param2) {
        final Function function = new Function(FUNC_DELEGATES, 
                Arrays.<Type>asList(new Address(param0),
                new Bytes32(param1),
                new Address(param2)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> setAttributeSigned(String identity, BigInteger sigV, byte[] sigR, byte[] sigS, byte[] name, byte[] value, BigInteger validity) {
        final Function function = new Function(
                FUNC_SETATTRIBUTESIGNED, 
                Arrays.<Type>asList(new Address(identity),
                new org.web3j.abi.datatypes.generated.Uint8(sigV), 
                new Bytes32(sigR),
                new Bytes32(sigS),
                new Bytes32(name),
                new DynamicBytes(value),
                new Uint256(validity)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> changeOwnerSigned(String identity, BigInteger sigV, byte[] sigR, byte[] sigS, String newOwner) {
        final Function function = new Function(
                FUNC_CHANGEOWNERSIGNED, 
                Arrays.<Type>asList(new Address(identity),
                new org.web3j.abi.datatypes.generated.Uint8(sigV), 
                new Bytes32(sigR),
                new Bytes32(sigS),
                new Address(newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> validDelegate(String identity, byte[] delegateType, String delegate) {
        final Function function = new Function(FUNC_VALIDDELEGATE, 
                Arrays.<Type>asList(new Address(identity),
                new Bytes32(delegateType),
                new Address(delegate)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> nonce(String param0) {
        final Function function = new Function(FUNC_NONCE, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> setAttribute(String identity, byte[] name, byte[] value, BigInteger validity) {
        final Function function = new Function(
                FUNC_SETATTRIBUTE, 
                Arrays.<Type>asList(new Address(identity),
                new Bytes32(name),
                new DynamicBytes(value),
                new Uint256(validity)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> revokeDelegate(String identity, byte[] delegateType, String delegate) {
        final Function function = new Function(
                FUNC_REVOKEDELEGATE, 
                Arrays.<Type>asList(new Address(identity),
                new Bytes32(delegateType),
                new Address(delegate)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> identityOwner(String identity) {
        final Function function = new Function(FUNC_IDENTITYOWNER, 
                Arrays.<Type>asList(new Address(identity)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> revokeDelegateSigned(String identity, BigInteger sigV, byte[] sigR, byte[] sigS, byte[] delegateType, String delegate) {
        final Function function = new Function(
                FUNC_REVOKEDELEGATESIGNED, 
                Arrays.<Type>asList(new Address(identity),
                new org.web3j.abi.datatypes.generated.Uint8(sigV), 
                new Bytes32(sigR),
                new Bytes32(sigS),
                new Bytes32(delegateType),
                new Address(delegate)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addDelegateSigned(String identity, BigInteger sigV, byte[] sigR, byte[] sigS, byte[] delegateType, String delegate, BigInteger validity) {
        final Function function = new Function(
                FUNC_ADDDELEGATESIGNED, 
                Arrays.<Type>asList(new Address(identity),
                new org.web3j.abi.datatypes.generated.Uint8(sigV), 
                new Bytes32(sigR),
                new Bytes32(sigS),
                new Bytes32(delegateType),
                new Address(delegate),
                new Uint256(validity)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addDelegate(String identity, byte[] delegateType, String delegate, BigInteger validity) {
        final Function function = new Function(
                FUNC_ADDDELEGATE, 
                Arrays.<Type>asList(new Address(identity),
                new Bytes32(delegateType),
                new Address(delegate),
                new Uint256(validity)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> revokeAttributeSigned(String identity, BigInteger sigV, byte[] sigR, byte[] sigS, byte[] name, byte[] value) {
        final Function function = new Function(
                FUNC_REVOKEATTRIBUTESIGNED, 
                Arrays.<Type>asList(new Address(identity),
                new org.web3j.abi.datatypes.generated.Uint8(sigV), 
                new Bytes32(sigR),
                new Bytes32(sigS),
                new Bytes32(name),
                new DynamicBytes(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> changeOwner(String identity, String newOwner) {
        final Function function = new Function(
                FUNC_CHANGEOWNER, 
                Arrays.<Type>asList(new Address(identity),
                new Address(newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> changed(String param0) {
        final Function function = new Function(FUNC_CHANGED, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<DIDOwnerChangedEventResponse> getDIDOwnerChangedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(DIDOWNERCHANGED_EVENT, transactionReceipt);
        ArrayList<DIDOwnerChangedEventResponse> responses = new ArrayList<DIDOwnerChangedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            DIDOwnerChangedEventResponse typedResponse = new DIDOwnerChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.identity = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.previousChange = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DIDOwnerChangedEventResponse> dIDOwnerChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, DIDOwnerChangedEventResponse>() {
            @Override
            public DIDOwnerChangedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(DIDOWNERCHANGED_EVENT, log);
                DIDOwnerChangedEventResponse typedResponse = new DIDOwnerChangedEventResponse();
                typedResponse.log = log;
                typedResponse.identity = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.previousChange = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DIDOwnerChangedEventResponse> dIDOwnerChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DIDOWNERCHANGED_EVENT));
        return dIDOwnerChangedEventFlowable(filter);
    }

    public List<DIDDelegateChangedEventResponse> getDIDDelegateChangedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(DIDDELEGATECHANGED_EVENT, transactionReceipt);
        ArrayList<DIDDelegateChangedEventResponse> responses = new ArrayList<DIDDelegateChangedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            DIDDelegateChangedEventResponse typedResponse = new DIDDelegateChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.identity = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.delegateType = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.delegate = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.validTo = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.previousChange = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DIDDelegateChangedEventResponse> dIDDelegateChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, DIDDelegateChangedEventResponse>() {
            @Override
            public DIDDelegateChangedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(DIDDELEGATECHANGED_EVENT, log);
                DIDDelegateChangedEventResponse typedResponse = new DIDDelegateChangedEventResponse();
                typedResponse.log = log;
                typedResponse.identity = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.delegateType = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.delegate = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.validTo = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.previousChange = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DIDDelegateChangedEventResponse> dIDDelegateChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DIDDELEGATECHANGED_EVENT));
        return dIDDelegateChangedEventFlowable(filter);
    }

    public List<DIDAttributeChangedEventResponse> getDIDAttributeChangedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(DIDATTRIBUTECHANGED_EVENT, transactionReceipt);
        ArrayList<DIDAttributeChangedEventResponse> responses = new ArrayList<DIDAttributeChangedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            DIDAttributeChangedEventResponse typedResponse = new DIDAttributeChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.identity = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.name = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.value = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.validTo = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.previousChange = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DIDAttributeChangedEventResponse> dIDAttributeChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, DIDAttributeChangedEventResponse>() {
            @Override
            public DIDAttributeChangedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(DIDATTRIBUTECHANGED_EVENT, log);
                DIDAttributeChangedEventResponse typedResponse = new DIDAttributeChangedEventResponse();
                typedResponse.log = log;
                typedResponse.identity = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.name = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.value = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.validTo = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.previousChange = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DIDAttributeChangedEventResponse> dIDAttributeChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DIDATTRIBUTECHANGED_EVENT));
        return dIDAttributeChangedEventFlowable(filter);
    }

    @Deprecated
    public static EthereumDIDRegistryInterface load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EthereumDIDRegistryInterface(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EthereumDIDRegistryInterface load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EthereumDIDRegistryInterface(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EthereumDIDRegistryInterface load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EthereumDIDRegistryInterface(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EthereumDIDRegistryInterface load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EthereumDIDRegistryInterface(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EthereumDIDRegistryInterface> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EthereumDIDRegistryInterface.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EthereumDIDRegistryInterface> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EthereumDIDRegistryInterface.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<EthereumDIDRegistryInterface> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EthereumDIDRegistryInterface.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EthereumDIDRegistryInterface> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EthereumDIDRegistryInterface.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class DIDOwnerChangedEventResponse {
        public Log log;

        public String identity;

        public String owner;

        public BigInteger previousChange;
    }

    public static class DIDDelegateChangedEventResponse {
        public Log log;

        public String identity;

        public byte[] delegateType;

        public String delegate;

        public BigInteger validTo;

        public BigInteger previousChange;
    }

    public static class DIDAttributeChangedEventResponse {
        public Log log;

        public String identity;

        public byte[] name;

        public byte[] value;

        public BigInteger validTo;

        public BigInteger previousChange;
    }
}
