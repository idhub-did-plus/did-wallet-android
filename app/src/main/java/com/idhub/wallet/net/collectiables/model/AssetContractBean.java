package com.idhub.wallet.net.collectiables.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class AssetContractBean implements Parcelable {
    public String address;
    public String asset_contract_type;
    public String created_date;
    public String name;
    public String nft_version;
    public String opensea_version;
    public double owner;
    public String schema_name;
    public String symbol;
    public String total_supply;
    public String description;
    public String external_link;
    public String image_url;
    public boolean default_to_fiat;
    public double dev_buyer_fee_basis_points;
    public double dev_seller_fee_basis_points;
    public boolean only_proxied_transfers;
    public double opensea_buyer_fee_basis_points;
    public double opensea_seller_fee_basis_points;
    public double buyer_fee_basis_points;
    public double seller_fee_basis_points;
    public String payout_address;

    protected AssetContractBean(Parcel in) {
        address = in.readString();
        asset_contract_type = in.readString();
        created_date = in.readString();
        name = in.readString();
        nft_version = in.readString();
        opensea_version = in.readString();
        owner = in.readDouble();
        schema_name = in.readString();
        symbol = in.readString();
        total_supply = in.readString();
        description = in.readString();
        external_link = in.readString();
        image_url = in.readString();
        default_to_fiat = in.readByte() != 0;
        dev_buyer_fee_basis_points = in.readDouble();
        dev_seller_fee_basis_points = in.readDouble();
        only_proxied_transfers = in.readByte() != 0;
        opensea_buyer_fee_basis_points = in.readDouble();
        opensea_seller_fee_basis_points = in.readDouble();
        buyer_fee_basis_points = in.readDouble();
        seller_fee_basis_points = in.readDouble();
        payout_address = in.readString();
    }

    public static final Creator<AssetContractBean> CREATOR = new Creator<AssetContractBean>() {
        @Override
        public AssetContractBean createFromParcel(Parcel in) {
            return new AssetContractBean(in);
        }

        @Override
        public AssetContractBean[] newArray(int size) {
            return new AssetContractBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(asset_contract_type);
        dest.writeString(created_date);
        dest.writeString(name);
        dest.writeString(nft_version);
        dest.writeString(opensea_version);
        dest.writeDouble(owner);
        dest.writeString(schema_name);
        dest.writeString(symbol);
        dest.writeString(total_supply);
        dest.writeString(description);
        dest.writeString(external_link);
        dest.writeString(image_url);
        dest.writeByte((byte) (default_to_fiat ? 1 : 0));
        dest.writeDouble(dev_buyer_fee_basis_points);
        dest.writeDouble(dev_seller_fee_basis_points);
        dest.writeByte((byte) (only_proxied_transfers ? 1 : 0));
        dest.writeDouble(opensea_buyer_fee_basis_points);
        dest.writeDouble(opensea_seller_fee_basis_points);
        dest.writeDouble(buyer_fee_basis_points);
        dest.writeDouble(seller_fee_basis_points);
        dest.writeString(payout_address);
    }
}
