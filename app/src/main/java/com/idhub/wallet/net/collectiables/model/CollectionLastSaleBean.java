package com.idhub.wallet.net.collectiables.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class CollectionLastSaleBean implements Parcelable {

    /**
     * event_type : successful
     * event_timestamp : 2020-02-24T07:44:38
     * auction_type : null
     * total_price : 110000000000000000
     */

    public String event_type;
    public String event_timestamp;
    public String total_price;

    protected CollectionLastSaleBean(Parcel in) {
        event_type = in.readString();
        event_timestamp = in.readString();
        total_price = in.readString();
    }

    public static final Creator<CollectionLastSaleBean> CREATOR = new Creator<CollectionLastSaleBean>() {
        @Override
        public CollectionLastSaleBean createFromParcel(Parcel in) {
            return new CollectionLastSaleBean(in);
        }

        @Override
        public CollectionLastSaleBean[] newArray(int size) {
            return new CollectionLastSaleBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(event_type);
        dest.writeString(event_timestamp);
        dest.writeString(total_price);
    }
}
