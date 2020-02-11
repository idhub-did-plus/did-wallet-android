package com.idhub.base.greendao.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AssetsContractAddress implements Parcelable {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "node")
    private String node;
    @Property(nameInDb = "contractAddress")
    private String contractAddress;
    @Property(nameInDb = "assetsId")
    private Long assetsId;


    protected AssetsContractAddress(Parcel in) {
        if (in.readByte() == 0) {
            id = 0L;
        } else {
            id = in.readLong();
        }
        node = in.readString();
        contractAddress = in.readString();
        if (in.readByte() == 0) {
            assetsId = null;
        } else {
            assetsId = in.readLong();
        }
    }

    @Generated(hash = 641665983)
    public AssetsContractAddress(Long id, String node, String contractAddress, Long assetsId) {
        this.id = id;
        this.node = node;
        this.contractAddress = contractAddress;
        this.assetsId = assetsId;
    }

    @Generated(hash = 1741219018)
    public AssetsContractAddress() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(node);
        dest.writeString(contractAddress);
        if (assetsId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(assetsId);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNode() {
        return this.node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getContractAddress() {
        return this.contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public Long getAssetsId() {
        return this.assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
    }

    public static final Creator<AssetsContractAddress> CREATOR = new Creator<AssetsContractAddress>() {
        @Override
        public AssetsContractAddress createFromParcel(Parcel in) {
            return new AssetsContractAddress(in);
        }

        @Override
        public AssetsContractAddress[] newArray(int size) {
            return new AssetsContractAddress[size];
        }
    };
}
