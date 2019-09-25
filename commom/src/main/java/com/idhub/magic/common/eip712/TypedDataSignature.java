package com.idhub.magic.common.eip712;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;

public class TypedDataSignature {

    private TypedDataSignature() {
    }

    public static byte[] generateSignatureHash(List<TypedData> typedData) {
        System.out.printf("generateSignatureHash(typedData={}) ...", typedData);

        byte[] result = SolidityPackEncoder
                .soliditySHA3(Arrays.asList(new Bytes32(SolidityPackEncoder.soliditySHA3(buildSchema(typedData))),
                        new Bytes32(SolidityPackEncoder.soliditySHA3(buildTypes(typedData)))));

        System.out.printf("generateSignatureHash(typedData={}) => {}", typedData, result);

        return result;
    }

    private static List<Type> buildSchema(List<TypedData> typedData) {

        return typedData.stream().map((t) -> new Utf8String(t.getType() + " " + t.getName()))
                .collect(Collectors.toList());
    }

    private static List<Type> buildTypes(List<TypedData> typedData) {

        return typedData.stream().map((t) -> {
            Type type = null;
            switch (t.getType().toLowerCase()) {
            case "string":
                type = new Utf8String((String) t.getValue());
                break;

            case "uint":
                type = new Uint(new BigInteger(String.valueOf(t.getValue())));
                break;

            case "bool":
                type = new Bool((boolean) t.getValue());
                break;

            default:
            	System.out.printf("Unknow type [{}]", t.getType().toLowerCase());
                throw new IllegalArgumentException();
            }

            return type;
        }).collect(Collectors.toList());
    }

}
