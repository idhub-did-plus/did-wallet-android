package com.idhub.wallet.wallet.token;

import android.os.Parcel;
import android.os.Parcelable;

public class PartitionEntity implements Parcelable {
    public byte[] name;
    public String balance;

    public PartitionEntity() {
    }

    protected PartitionEntity(Parcel in) {
        name = in.createByteArray();
        balance = in.readString();
    }

    public static final Creator<PartitionEntity> CREATOR = new Creator<PartitionEntity>() {
        @Override
        public PartitionEntity createFromParcel(Parcel in) {
            return new PartitionEntity(in);
        }

        @Override
        public PartitionEntity[] newArray(int size) {
            return new PartitionEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(name);
        dest.writeString(balance);
    }
}
