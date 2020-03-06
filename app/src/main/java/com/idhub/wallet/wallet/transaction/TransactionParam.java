package com.idhub.wallet.wallet.transaction;

import android.os.Parcel;
import android.os.Parcelable;

import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.wallet.dapp.entity.Transaction;
import com.idhub.wallet.net.collectiables.model.AssetsCollectionItem;


public class TransactionParam  implements Parcelable {
    public String value;
    public String toAddress;
    public String type;
    public Transaction transaction;//用做dapp交易
    public AssetsModel assetsModel; //用做资产交易
    public AssetsCollectionItem assetsCollectionItem;//erc721交易

    public TransactionParam() {
    }

    protected TransactionParam(Parcel in) {
        value = in.readString();
        toAddress = in.readString();
        type = in.readString();
        transaction = in.readParcelable(Transaction.class.getClassLoader());
        assetsModel = in.readParcelable(AssetsModel.class.getClassLoader());
        assetsCollectionItem = in.readParcelable(AssetsCollectionItem.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
        dest.writeString(toAddress);
        dest.writeString(type);
        dest.writeParcelable(transaction, flags);
        dest.writeParcelable(assetsModel, flags);
        dest.writeParcelable(assetsCollectionItem, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TransactionParam> CREATOR = new Creator<TransactionParam>() {
        @Override
        public TransactionParam createFromParcel(Parcel in) {
            return new TransactionParam(in);
        }

        @Override
        public TransactionParam[] newArray(int size) {
            return new TransactionParam[size];
        }
    };
}
