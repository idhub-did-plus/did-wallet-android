package com.idhub.base.node;

import android.text.TextUtils;
import android.widget.Toast;

import com.idhub.base.App;
import com.idhub.base.R;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ContractAddressEmptyHandler implements InvocationHandler {

    String contractAddress = "";

    public ContractAddressEmptyHandler(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (checkContractAddressEmpty()) {
            contractEmptyToast();
            return null;
        }
        return method.invoke(contractAddress, args);
    }

    private boolean checkContractAddressEmpty() {
        if (TextUtils.isEmpty(contractAddress)) {
            return true;
        }
        return false;
    }

    private void contractEmptyToast(){
        Toast.makeText(App.getInstance(), App.getInstance().getString(R.string.contract_empty_toast), Toast.LENGTH_SHORT).show();
    }
}
