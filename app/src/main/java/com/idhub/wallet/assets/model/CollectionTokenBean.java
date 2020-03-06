package com.idhub.wallet.assets.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.idhub.wallet.net.collectiables.model.AssetsCollectionItem;

import java.util.List;

public class CollectionTokenBean  implements Parcelable {
    public String contractAddress;
    public String image_url;
    public String description;
    public String name;
    public List<AssetsCollectionItem> assetsCollectionItem;

    public CollectionTokenBean(String contractAddress,String image_url, String description, String name, List<AssetsCollectionItem> assetsCollectionItem) {
        this.contractAddress = contractAddress;
        this.image_url = image_url;
        this.description = description;
        this.name = name;
        this.assetsCollectionItem = assetsCollectionItem;
    }

    protected CollectionTokenBean(Parcel in) {
        contractAddress = in.readString();
        image_url = in.readString();
        description = in.readString();
        name = in.readString();
        assetsCollectionItem = in.createTypedArrayList(AssetsCollectionItem.CREATOR);
    }

    public static final Creator<CollectionTokenBean> CREATOR = new Creator<CollectionTokenBean>() {
        @Override
        public CollectionTokenBean createFromParcel(Parcel in) {
            return new CollectionTokenBean(in);
        }

        @Override
        public CollectionTokenBean[] newArray(int size) {
            return new CollectionTokenBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contractAddress);
        dest.writeString(image_url);
        dest.writeString(description);
        dest.writeString(name);
        dest.writeTypedList(assetsCollectionItem);
    }
}
