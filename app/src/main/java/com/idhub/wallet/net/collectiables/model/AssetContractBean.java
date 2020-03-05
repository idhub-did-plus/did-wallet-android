package com.idhub.wallet.net.collectiables.model;

import java.io.Serializable;

public class AssetContractBean implements Serializable {
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
}
