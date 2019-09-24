package com.idhub.magic.clientlib.local;

import com.idhub.magic.clientlib.ProviderFactory;
import com.idhub.magic.clientlib.parameter.AddAssociatedAddressParam;
import com.idhub.magic.clientlib.parameter.InitializeIdentityParam;
import com.idhub.magic.clientlib.parameter.RecoveryIdentityParam;
import com.idhub.magic.clientlib.parameter.ResetIdentityParam;
import com.idhub.magic.common.service.DeployedContractAddress;
import com.idhub.magic.common.util.CryptoUtil;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Uint;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class ClientEncoderLocal {

    public static RecoveryIdentityParam recoveryIdentityEncoder(RecoveryIdentityParam param) {

        Address contract = new Address(DeployedContractAddress.IdentityRegistryInterface);
        Address newAssociatedAddress = new Address(param.newAssociationAddress);

        byte[] hexMessage = CryptoUtil.encodePacked(
                (byte) 0x19, (byte) 0, contract,
                "I authorize being added to this Identity via recovery.",
                param.ein, newAssociatedAddress, param.timestamp);

        Credentials credentials =Credentials.create(param.privateKey);
        ECKeyPair pair = credentials.getEcKeyPair();
        Sign.SignatureData sm = Sign.signMessage(hexMessage, pair);
        param.r = sm.getR();
        param.s = sm.getS();
        param.v =new BigInteger(String.valueOf(sm.getV()));
        return param;
    }


    public static AddAssociatedAddressParam addAssociatedAddressEncoder(AddAssociatedAddressParam addAssociatedAddressParam) {

        Address contract = new Address(DeployedContractAddress.IdentityRegistryInterface);
        Address addressToAdd = new Address(addAssociatedAddressParam.addressToAdd);

        byte[] encodeMessage = CryptoUtil.encodePacked(
                (byte) 0x19, (byte) 0, contract,
                "I authorize being added to this Identity.",
                addAssociatedAddressParam.ein, addressToAdd, addAssociatedAddressParam.timestamp);

        Credentials credentials = Credentials.create(addAssociatedAddressParam.privateKey);
        ECKeyPair pair = credentials.getEcKeyPair();
        Sign.SignatureData sm = Sign.signMessage(encodeMessage, pair);
        addAssociatedAddressParam.r = sm.getR();
        addAssociatedAddressParam.s = sm.getS();
        addAssociatedAddressParam.v = new BigInteger(String.valueOf(sm.getV()));
        return addAssociatedAddressParam;
    }

    public static InitializeIdentityParam initializeIdentity(InitializeIdentityParam initializeIdentityParam) {
        Address contract = new Address(DeployedContractAddress.ERC1056ResolverInterface);
        Address ethereumDIDRegistryContract = new Address(DeployedContractAddress.EthereumDIDRegistryInterface);
        Address identityAddress = new Address(initializeIdentityParam.identity);
        byte[] encodeMessage = CryptoUtil.encodePacked(
                (byte) 0x19, (byte) 0, ethereumDIDRegistryContract, initializeIdentityParam.noce, identityAddress, "changeOwner", contract);

        Credentials credentials = ProviderFactory.getProvider().getDefaultCredentials();
        ECKeyPair pair = credentials.getEcKeyPair();
        Sign.SignatureData sm = Sign.signMessage(encodeMessage, pair);
        initializeIdentityParam.r = sm.getR();
        initializeIdentityParam.s = sm.getS();
        initializeIdentityParam.v = new BigInteger(String.valueOf(sm.getV()));
        return initializeIdentityParam;
    }

    public static ResetIdentityParam resetIdentity(ResetIdentityParam resetIdentityParam) {
        Address contract = new Address(DeployedContractAddress.ERC1056ResolverInterface);
        Address ethereumDIDRegistryContract = new Address(DeployedContractAddress.EthereumDIDRegistryInterface);
        Address identityAddress = new Address(resetIdentityParam.identity);
        byte[] encodeMessage = CryptoUtil.encodePacked(
                (byte) 0x19, (byte) 0, ethereumDIDRegistryContract, resetIdentityParam.noce, identityAddress, "changeOwner", contract);

        Credentials credentials = Credentials.create(resetIdentityParam.privateKey);
        ECKeyPair pair = credentials.getEcKeyPair();
        Sign.SignatureData sm = Sign.signMessage(encodeMessage, pair);
        resetIdentityParam.r = sm.getR();
        resetIdentityParam.s = sm.getS();
        resetIdentityParam.v = new BigInteger(String.valueOf(sm.getV()));
        return resetIdentityParam;
    }

}
