package com.idhub.magic.common.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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
public class ERC1056ResolverInterface extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405161215c38038061215c8339818101604052604081101561003357600080fd5b508051602090910151600080546001600160a01b039384166001600160a01b031991821617909155600180549390921692169190911790556120e28061007a6000396000f3fe608060405234801561001057600080fd5b50600436106101005760003560e01c8063a343766a11610097578063d0bcc73c11610066578063d0bcc73c14610532578063d702b8a51461056d578063d862913f1461059c578063e9e831561461064957610100565b8063a343766a1461045a578063a6f9dae11461049f578063c7cadeb0146104c5578063ca2825e7146104f757610100565b806359d3dee2116100d357806359d3dee214610242578063699561a91461027d5780636e03276514610344578063853c4a581461038d57610100565b806307d52085146101055780632646efe31461013e57806345c9959b146101eb57806352efea6e1461023a575b600080fd5b6101226004803603602081101561011b57600080fd5b5035610675565b604080516001600160a01b039092168252519081900360200190f35b6101e96004803603604081101561015457600080fd5b81359190810190604081016020820135600160201b81111561017557600080fd5b82018360208201111561018757600080fd5b803590602001918460018302840111600160201b831117156101a857600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610690945050505050565b005b6101e9600480360360e081101561020157600080fd5b506001600160a01b0381358116916020810135916040820135169060608101359060ff6080820135169060a08101359060c00135610733565b6101e961094e565b6101e96004803603608081101561025857600080fd5b506001600160a01b038135169060ff6020820135169060408101359060600135610a71565b6101e9600480360360c081101561029357600080fd5b6001600160a01b0382351691602081013591810190606081016040820135600160201b8111156102c257600080fd5b8201836020820111156102d457600080fd5b803590602001918460018302840111600160201b831117156102f557600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295505060ff8335169350505060208101359060400135610c3e565b6101e9600480360360c081101561035a57600080fd5b506001600160a01b0381358116916020810135916040820135169060ff6060820135169060808101359060a00135610f06565b6101e9600480360360e08110156103a357600080fd5b6001600160a01b0382351691602081013591810190606081016040820135600160201b8111156103d257600080fd5b8201836020820111156103e457600080fd5b803590602001918460018302840111600160201b8311171561040557600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955050823593505060ff602083013516916040810135915060600135611117565b6101e9600480360360a081101561047057600080fd5b506001600160a01b03813581169160208101359091169060ff60408201351690606081013590608001356113e3565b6101e9600480360360208110156104b557600080fd5b50356001600160a01b03166115ea565b6101e9600480360360608110156104db57600080fd5b508035906001600160a01b03602082013516906040013561168b565b6101e96004803603608081101561050d57600080fd5b506001600160a01b038135169060ff6020820135169060408101359060600135611730565b6101e96004803603608081101561054857600080fd5b506001600160a01b038135169060ff60208201351690604081013590606001356118ff565b61058a6004803603602081101561058357600080fd5b5035611aaf565b60408051918252519081900360200190f35b6101e9600480360360608110156105b257600080fd5b81359190810190604081016020820135600160201b8111156105d357600080fd5b8201836020820111156105e557600080fd5b803590602001918460018302840111600160201b8311171561060657600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295505091359250611ac1915050565b6101e96004803603604081101561065f57600080fd5b50803590602001356001600160a01b0316611b60565b6002602052600090815260409020546001600160a01b031681565b60008054604080516305c62c2f60e01b815233600482015290516001600160a01b03909216916305c62c2f91602480820192602092909190829003018186803b1580156106dc57600080fd5b505afa1580156106f0573d6000803e3d6000fd5b505050506040513d602081101561070657600080fd5b505160008181526002602052604090205490915061072e906001600160a01b03168484611bfe565b505050565b60008054604080516305c62c2f60e01b81526001600160a01b038b81166004830152915191909216916305c62c2f916024808301926020929190829003018186803b15801561078157600080fd5b505afa158015610795573d6000803e3d6000fd5b505050506040513d60208110156107ab57600080fd5b505160008054828252600360209081526040808420548151601960f81b81850152602181019590955230606090811b60228701527318591911195b1959d85d1951195b1959d85d195960621b6036870152604a86018e90528c901b6001600160601b031916606a860152607e85018b9052609e808601919091528151808603909101815260be85018083528151918401919091206310cefd7d60e31b9091526001600160a01b038e811660c287015260e286019190915260ff8a1661010286015261012285018990526101428501889052905194955090911692638677ebe892610162808201939291829003018186803b1580156108a857600080fd5b505afa1580156108bc573d6000803e3d6000fd5b505050506040513d60208110156108d257600080fd5b505161090f5760405162461bcd60e51b815260040180806020018281038252602981526020018061203f6029913960400191505060405180910390fd5b6000818152600360209081526040808320805460010190556002909152902054610944906001600160a01b0316888888611d1c565b5050505050505050565b60008054604080516305c62c2f60e01b815233600482015290516001600160a01b03909216916305c62c2f91602480820192602092909190829003018186803b15801561099a57600080fd5b505afa1580156109ae573d6000803e3d6000fd5b505050506040513d60208110156109c457600080fd5b50516000818152600260205260409020549091506001600160a01b0316610a1c5760405162461bcd60e51b81526004018080602001828103825260218152602001806120686021913960400191505060405180910390fd5b60008181526002602090815260409182902080546001600160a01b03191690558151838152915133927ff5b83915251053d4548a496c24058f7404d3f8a98087bbd4dee37a1a997b5d1992908290030190a250565b60008054604080516305c62c2f60e01b815233600482015290516001600160a01b03909216916305c62c2f91602480820192602092909190829003018186803b158015610abd57600080fd5b505afa158015610ad1573d6000803e3d6000fd5b505050506040513d6020811015610ae757600080fd5b50516000818152600260205260409020549091506001600160a01b0316610b3f5760405162461bcd60e51b81526004018080602001828103825260218152602001806120686021913960400191505060405180910390fd5b60008181526002602052604080822054600154825163120678fd60e11b81526001600160a01b038a8116600483015260ff8a1660248301526044820189905260648201889052306084830152935192841694939091169263240cf1fa9260a48084019382900301818387803b158015610bb757600080fd5b505af1158015610bcb573d6000803e3d6000fd5b50505060008381526002602090815260409182902080546001600160a01b0319166001600160a01b038b8116918217909255835190815292519085169350859233927fb9842f58d3add4b3111f01090178c302e2a9fde3c76d8f640ef0d70e179d55ec92918290030190a4505050505050565b60008054604080516305c62c2f60e01b81526001600160a01b038a81166004830152915191909216916305c62c2f916024808301926020929190829003018186803b158015610c8c57600080fd5b505afa158015610ca0573d6000803e3d6000fd5b505050506040513d6020811015610cb657600080fd5b810190808051906020019092919050505090506000809054906101000a90046001600160a01b03166001600160a01b0316638677ebe888601960f81b600060f81b308b8b600360008a81526020019081526020016000205460405160200180876001600160f81b0319166001600160f81b0319168152600101866001600160f81b0319166001600160f81b0319168152600101856001600160a01b03166001600160a01b031660601b8152601401807f7265766f6b6541747472696275746544656c656761746564000000000000000081525060180184815260200183805190602001908083835b60208310610dbd5780518252601f199092019160209182019101610d9e565b51815160209384036101000a600019018019909216911617905292019384525060408051808503815284830180835281519184019190912060e08d901b6001600160e01b0319169091526001600160a01b039a909a166024850152604484019990995260ff8f166064840152608483018e905260a483018d9052975160c480840199985090965091869003909101935084925088915050803b158015610e6257600080fd5b505afa158015610e76573d6000803e3d6000fd5b505050506040513d6020811015610e8c57600080fd5b5051610ec95760405162461bcd60e51b815260040180806020018281038252602981526020018061203f6029913960400191505060405180910390fd5b6000818152600360209081526040808320805460010190556002909152902054610efd906001600160a01b03168787611bfe565b50505050505050565b60008054604080516305c62c2f60e01b81526001600160a01b038a81166004830152915191909216916305c62c2f916024808301926020929190829003018186803b158015610f5457600080fd5b505afa158015610f68573d6000803e3d6000fd5b505050506040513d6020811015610f7e57600080fd5b505160008054828252600360209081526040808420548151601960f81b81850152602181019590955230606090811b60228701527f7265766f6b6544656c656761746544656c6567617465640000000000000000006036870152604d86018d90528b901b6001600160601b031916606d8601526081808601919091528151808603909101815260a185018083528151918401919091206310cefd7d60e31b9091526001600160a01b038d811660a587015260c586019190915260ff8a1660e586015261010585018990526101258501889052905194955090911692638677ebe892610145808201939291829003018186803b15801561107c57600080fd5b505afa158015611090573d6000803e3d6000fd5b505050506040513d60208110156110a657600080fd5b50516110e35760405162461bcd60e51b815260040180806020018281038252602981526020018061203f6029913960400191505060405180910390fd5b6000818152600360209081526040808320805460010190556002909152902054610efd906001600160a01b03168787611dd9565b60008054604080516305c62c2f60e01b81526001600160a01b038b81166004830152915191909216916305c62c2f916024808301926020929190829003018186803b15801561116557600080fd5b505afa158015611179573d6000803e3d6000fd5b505050506040513d602081101561118f57600080fd5b810190808051906020019092919050505090506000809054906101000a90046001600160a01b03166001600160a01b0316638677ebe889601960f81b600060f81b308c8c8c600360008b81526020019081526020016000205460405160200180886001600160f81b0319166001600160f81b0319168152600101876001600160f81b0319166001600160f81b0319168152600101866001600160a01b03166001600160a01b031660601b815260140180741cd95d105d1d1c9a589d5d1951195b1959d85d1959605a1b81525060150185815260200184805190602001908083835b6020831061128f5780518252601f199092019160209182019101611270565b6001836020036101000a038019825116818451168082178552505050505050905001838152602001828152602001975050505050505050604051602081830303815290604052805190602001208787876040518663ffffffff1660e01b815260040180866001600160a01b03166001600160a01b031681526020018581526020018460ff1660ff1681526020018381526020018281526020019550505050505060206040518083038186803b15801561134757600080fd5b505afa15801561135b573d6000803e3d6000fd5b505050506040513d602081101561137157600080fd5b50516113ae5760405162461bcd60e51b815260040180806020018281038252602981526020018061203f6029913960400191505060405180910390fd5b6000818152600360209081526040808320805460010190556002909152902054610944906001600160a01b0316888888611e7b565b60008054604080516305c62c2f60e01b81526001600160a01b038981166004830152915191909216916305c62c2f916024808301926020929190829003018186803b15801561143157600080fd5b505afa158015611445573d6000803e3d6000fd5b505050506040513d602081101561145b57600080fd5b505160008054828252600360209081526040808420548151601960f81b81850152602181019590955230606090811b60228701527318da185b99d953dddb995c91195b1959d85d195960621b60368701528b901b6001600160601b031916604a860152605e8086019190915281518086039091018152607e85018083528151918401919091206310cefd7d60e31b9091526001600160a01b038c8116608287015260a286019190915260ff8a1660c286015260e285018990526101028501889052905194955090911692638677ebe892610122808201939291829003018186803b15801561154857600080fd5b505afa15801561155c573d6000803e3d6000fd5b505050506040513d602081101561157257600080fd5b50516115af5760405162461bcd60e51b815260040180806020018281038252602981526020018061203f6029913960400191505060405180910390fd5b60008181526003602090815260408083208054600101905560029091529020546115e2906001600160a01b031686611f8f565b505050505050565b60008054604080516305c62c2f60e01b815233600482015290516001600160a01b03909216916305c62c2f91602480820192602092909190829003018186803b15801561163657600080fd5b505afa15801561164a573d6000803e3d6000fd5b505050506040513d602081101561166057600080fd5b5051600081815260026020526040902054909150611687906001600160a01b031683611f8f565b5050565b60008054604080516305c62c2f60e01b815233600482015290516001600160a01b03909216916305c62c2f91602480820192602092909190829003018186803b1580156116d757600080fd5b505afa1580156116eb573d6000803e3d6000fd5b505050506040513d602081101561170157600080fd5b505160008181526002602052604090205490915061172a906001600160a01b0316858585611d1c565b50505050565b60008054604080516305c62c2f60e01b81526001600160a01b038881166004830152915191909216916305c62c2f916024808301926020929190829003018186803b15801561177e57600080fd5b505afa158015611792573d6000803e3d6000fd5b505050506040513d60208110156117a857600080fd5b50516000818152600260205260409020549091506001600160a01b03166118005760405162461bcd60e51b81526004018080602001828103825260218152602001806120686021913960400191505060405180910390fd5b60008181526002602052604080822054600154825163120678fd60e11b81526001600160a01b038a8116600483015260ff8a1660248301526044820189905260648201889052306084830152935192841694939091169263240cf1fa9260a48084019382900301818387803b15801561187857600080fd5b505af115801561188c573d6000803e3d6000fd5b50505060008381526002602090815260409182902080546001600160a01b0319166001600160a01b038b8116918217909255835190815292519085169350859284927fb9842f58d3add4b3111f01090178c302e2a9fde3c76d8f640ef0d70e179d55ec92918290030190a4505050505050565b60008054604080516305c62c2f60e01b815233600482015290516001600160a01b03909216916305c62c2f91602480820192602092909190829003018186803b15801561194b57600080fd5b505afa15801561195f573d6000803e3d6000fd5b505050506040513d602081101561197557600080fd5b50516000818152600260205260409020549091506001600160a01b0316156119ce5760405162461bcd60e51b81526004018080602001828103825260258152602001806120896025913960400191505060405180910390fd5b6001546040805163120678fd60e11b81526001600160a01b03888116600483015260ff8816602483015260448201879052606482018690523060848301529151919092169163240cf1fa9160a480830192600092919082900301818387803b158015611a3957600080fd5b505af1158015611a4d573d6000803e3d6000fd5b50505060008281526002602052604080822080546001600160a01b0319166001600160a01b038a169081179091559051909250839133917f04200d59e6448cc5a97e2e089e6b35bac8a336b173d770c815d6f3c5c06f30049190a45050505050565b60036020526000908152604090205481565b60008054604080516305c62c2f60e01b815233600482015290516001600160a01b03909216916305c62c2f91602480820192602092909190829003018186803b158015611b0d57600080fd5b505afa158015611b21573d6000803e3d6000fd5b505050506040513d6020811015611b3757600080fd5b505160008181526002602052604090205490915061172a906001600160a01b0316858585611e7b565b60008054604080516305c62c2f60e01b815233600482015290516001600160a01b03909216916305c62c2f91602480820192602092909190829003018186803b158015611bac57600080fd5b505afa158015611bc0573d6000803e3d6000fd5b505050506040513d6020811015611bd657600080fd5b505160008181526002602052604090205490915061072e906001600160a01b03168484611dd9565b6001600160a01b038316611c435760405162461bcd60e51b81526004018080602001828103825260218152602001806120686021913960400191505060405180910390fd5b600154604051626011ed60e11b81526001600160a01b038581166004830190815260248301869052606060448401908152855160648501528551929094169362c023da9388938893889390929091608490910190602085019080838360005b83811015611cba578181015183820152602001611ca2565b50505050905090810190601f168015611ce75780820380516001836020036101000a031916815260200191505b50945050505050600060405180830381600087803b158015611d0857600080fd5b505af1158015610efd573d6000803e3d6000fd5b6001600160a01b038416611d615760405162461bcd60e51b81526004018080602001828103825260218152602001806120686021913960400191505060405180910390fd5b6001546040805163538346b360e11b81526001600160a01b038781166004830152602482018790528581166044830152606482018590529151919092169163a7068d6691608480830192600092919082900301818387803b158015611dc557600080fd5b505af1158015610944573d6000803e3d6000fd5b6001600160a01b038316611e1e5760405162461bcd60e51b81526004018080602001828103825260218152602001806120686021913960400191505060405180910390fd5b6001546040805163202ca7df60e21b81526001600160a01b038681166004830152602482018690528481166044830152915191909216916380b29f7c91606480830192600092919082900301818387803b158015611d0857600080fd5b6001600160a01b038416611ec05760405162461bcd60e51b81526004018080602001828103825260218152602001806120686021913960400191505060405180910390fd5b600154604051631eb52c2960e21b81526001600160a01b038681166004830190815260248301879052606483018590526080604484019081528651608485015286519290941693637ad4b0a49389938993899389939192909160a40190602086019080838360005b83811015611f40578181015183820152602001611f28565b50505050905090810190601f168015611f6d5780820380516001836020036101000a031916815260200191505b5095505050505050600060405180830381600087803b158015611dc557600080fd5b6001600160a01b038216611fd45760405162461bcd60e51b81526004018080602001828103825260218152602001806120686021913960400191505060405180910390fd5b6001546040805163f00d4b5d60e01b81526001600160a01b03858116600483015284811660248301529151919092169163f00d4b5d91604480830192600092919082900301818387803b15801561202a57600080fd5b505af11580156115e2573d6000803e3d6000fdfe46756e6374696f6e20657865637574696f6e20697320696e636f72726563746c79207369676e65642e546869732045494e20686173206e6f74206265656e20696e697469616c697a6564546869732045494e2068617320616c7265616479206265656e20696e697469616c697a6564a265627a7a72315820b55bb4433c94154b2bd33cbe353627ffcdf7489d44df07259df84eaaeeb3773264736f6c634300050b0032";

    public static final String FUNC_EINTODID = "einToDID";

    public static final String FUNC_REVOKEATTRIBUTE = "revokeAttribute";

    public static final String FUNC_ADDDELEGATEDELEGATED = "addDelegateDelegated";

    public static final String FUNC_CLEAR = "clear";

    public static final String FUNC_RESET = "reset";

    public static final String FUNC_REVOKEATTRIBUTEDELEGATED = "revokeAttributeDelegated";

    public static final String FUNC_REVOKEDELEGATEDELEGATED = "revokeDelegateDelegated";

    public static final String FUNC_SETATTRIBUTEDELEGATED = "setAttributeDelegated";

    public static final String FUNC_CHANGEOWNERDELEGATED = "changeOwnerDelegated";

    public static final String FUNC_CHANGEOWNER = "changeOwner";

    public static final String FUNC_ADDDELEGATE = "addDelegate";

    public static final String FUNC_RECOVERY = "recovery";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_ACTIONNONCE = "actionNonce";

    public static final String FUNC_SETATTRIBUTE = "setAttribute";

    public static final String FUNC_REVOKEDELEGATE = "revokeDelegate";

    public static final Event IDENTITYINITIALIZED_EVENT = new Event("IdentityInitialized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event IDENTITYCLEARED_EVENT = new Event("IdentityCleared", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event IDENTITYRESETED_EVENT = new Event("IdentityReseted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected ERC1056ResolverInterface(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ERC1056ResolverInterface(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ERC1056ResolverInterface(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ERC1056ResolverInterface(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> einToDID(BigInteger param0) {
        final Function function = new Function(FUNC_EINTODID, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> revokeAttribute(byte[] name, byte[] value) {
        final Function function = new Function(
                FUNC_REVOKEATTRIBUTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(name), 
                new org.web3j.abi.datatypes.DynamicBytes(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addDelegateDelegated(String approvingAddress, byte[] delegateType, String delegate, BigInteger validity, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_ADDDELEGATEDELEGATED, 
                Arrays.<Type>asList(new Address(approvingAddress),
                new org.web3j.abi.datatypes.generated.Bytes32(delegateType), 
                new Address(delegate),
                new Uint256(validity),
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> clear() {
        final Function function = new Function(
                FUNC_CLEAR, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> reset(String newIdentity, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_RESET, 
                Arrays.<Type>asList(new Address(newIdentity),
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> revokeAttributeDelegated(String approvingAddress, byte[] name, byte[] value, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_REVOKEATTRIBUTEDELEGATED, 
                Arrays.<Type>asList(new Address(approvingAddress),
                new org.web3j.abi.datatypes.generated.Bytes32(name), 
                new org.web3j.abi.datatypes.DynamicBytes(value), 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> revokeDelegateDelegated(String approvingAddress, byte[] delegateType, String delegate, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_REVOKEDELEGATEDELEGATED, 
                Arrays.<Type>asList(new Address(approvingAddress),
                new org.web3j.abi.datatypes.generated.Bytes32(delegateType), 
                new Address(delegate),
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setAttributeDelegated(String approvingAddress, byte[] name, byte[] value, BigInteger validity, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_SETATTRIBUTEDELEGATED, 
                Arrays.<Type>asList(new Address(approvingAddress),
                new org.web3j.abi.datatypes.generated.Bytes32(name), 
                new org.web3j.abi.datatypes.DynamicBytes(value), 
                new Uint256(validity),
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> changeOwnerDelegated(String approvingAddress, String newOwner, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_CHANGEOWNERDELEGATED, 
                Arrays.<Type>asList(new Address(approvingAddress),
                new Address(newOwner),
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> changeOwner(String newOwner) {
        final Function function = new Function(
                FUNC_CHANGEOWNER, 
                Arrays.<Type>asList(new Address(newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addDelegate(byte[] delegateType, String delegate, BigInteger validity) {
        final Function function = new Function(
                FUNC_ADDDELEGATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(delegateType), 
                new Address(delegate),
                new Uint256(validity)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> recovery(String newIdentity, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_RECOVERY, 
                Arrays.<Type>asList(new Address(newIdentity),
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> initialize(String identity, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_INITIALIZE, 
                Arrays.<Type>asList(new Address(identity),
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> actionNonce(BigInteger param0) {
        final Function function = new Function(FUNC_ACTIONNONCE, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> setAttribute(byte[] name, byte[] value, BigInteger validity) {
        final Function function = new Function(
                FUNC_SETATTRIBUTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(name), 
                new org.web3j.abi.datatypes.DynamicBytes(value), 
                new Uint256(validity)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> revokeDelegate(byte[] delegateType, String delegate) {
        final Function function = new Function(
                FUNC_REVOKEDELEGATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(delegateType), 
                new Address(delegate)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<IdentityInitializedEventResponse> getIdentityInitializedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(IDENTITYINITIALIZED_EVENT, transactionReceipt);
        ArrayList<IdentityInitializedEventResponse> responses = new ArrayList<IdentityInitializedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IdentityInitializedEventResponse typedResponse = new IdentityInitializedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.indeitity = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<IdentityInitializedEventResponse> identityInitializedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, IdentityInitializedEventResponse>() {
            @Override
            public IdentityInitializedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(IDENTITYINITIALIZED_EVENT, log);
                IdentityInitializedEventResponse typedResponse = new IdentityInitializedEventResponse();
                typedResponse.log = log;
                typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.indeitity = (String) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<IdentityInitializedEventResponse> identityInitializedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(IDENTITYINITIALIZED_EVENT));
        return identityInitializedEventFlowable(filter);
    }

    public List<IdentityClearedEventResponse> getIdentityClearedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(IDENTITYCLEARED_EVENT, transactionReceipt);
        ArrayList<IdentityClearedEventResponse> responses = new ArrayList<IdentityClearedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IdentityClearedEventResponse typedResponse = new IdentityClearedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.ein = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<IdentityClearedEventResponse> identityClearedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, IdentityClearedEventResponse>() {
            @Override
            public IdentityClearedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(IDENTITYCLEARED_EVENT, log);
                IdentityClearedEventResponse typedResponse = new IdentityClearedEventResponse();
                typedResponse.log = log;
                typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.ein = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<IdentityClearedEventResponse> identityClearedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(IDENTITYCLEARED_EVENT));
        return identityClearedEventFlowable(filter);
    }

    public List<IdentityResetedEventResponse> getIdentityResetedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(IDENTITYRESETED_EVENT, transactionReceipt);
        ArrayList<IdentityResetedEventResponse> responses = new ArrayList<IdentityResetedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IdentityResetedEventResponse typedResponse = new IdentityResetedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.oldIdentity = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.newIndeitity = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<IdentityResetedEventResponse> identityResetedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, IdentityResetedEventResponse>() {
            @Override
            public IdentityResetedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(IDENTITYRESETED_EVENT, log);
                IdentityResetedEventResponse typedResponse = new IdentityResetedEventResponse();
                typedResponse.log = log;
                typedResponse.initiator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.ein = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.oldIdentity = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.newIndeitity = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<IdentityResetedEventResponse> identityResetedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(IDENTITYRESETED_EVENT));
        return identityResetedEventFlowable(filter);
    }

    @Deprecated
    public static ERC1056ResolverInterface load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC1056ResolverInterface(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ERC1056ResolverInterface load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC1056ResolverInterface(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ERC1056ResolverInterface load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ERC1056ResolverInterface(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ERC1056ResolverInterface load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ERC1056ResolverInterface(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ERC1056ResolverInterface> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String identityRegistryAddress, String ethereumDIDRegistryAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(identityRegistryAddress),
                new Address(ethereumDIDRegistryAddress)));
        return deployRemoteCall(ERC1056ResolverInterface.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ERC1056ResolverInterface> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String identityRegistryAddress, String ethereumDIDRegistryAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(identityRegistryAddress),
                new Address(ethereumDIDRegistryAddress)));
        return deployRemoteCall(ERC1056ResolverInterface.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ERC1056ResolverInterface> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String identityRegistryAddress, String ethereumDIDRegistryAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(identityRegistryAddress),
                new Address(ethereumDIDRegistryAddress)));
        return deployRemoteCall(ERC1056ResolverInterface.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ERC1056ResolverInterface> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String identityRegistryAddress, String ethereumDIDRegistryAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(identityRegistryAddress),
                new Address(ethereumDIDRegistryAddress)));
        return deployRemoteCall(ERC1056ResolverInterface.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class IdentityInitializedEventResponse {
        public Log log;

        public String initiator;

        public BigInteger ein;

        public String indeitity;
    }

    public static class IdentityClearedEventResponse {
        public Log log;

        public String initiator;

        public BigInteger ein;
    }

    public static class IdentityResetedEventResponse {
        public Log log;

        public String initiator;

        public BigInteger ein;

        public String oldIdentity;

        public String newIndeitity;
    }
}
