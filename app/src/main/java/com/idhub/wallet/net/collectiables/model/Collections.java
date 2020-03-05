package com.idhub.wallet.net.collectiables.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Collections {

    /**
     * primary_asset_contracts : [{"address":"0xe5bd43166a3761628ac777bb1a42bcab13ca9495","asset_contract_type":"non-fungible","created_date":"2019-11-08T02:12:55.301292","name":"J1","nft_version":"3.0","opensea_version":null,"owner":33531,"schema_name":"ERC721","symbol":"","total_supply":"1","description":"Tradable, Collectable, Non-Fungible, Sneakrs. Powered by ethAIReum","external_link":"http://sneakrcred.com/","image_url":"https://rinkeby-storage.opensea.io/j1-1573181432.png","default_to_fiat":false,"dev_buyer_fee_basis_points":0,"dev_seller_fee_basis_points":0,"only_proxied_transfers":false,"opensea_buyer_fee_basis_points":0,"opensea_seller_fee_basis_points":250,"buyer_fee_basis_points":0,"seller_fee_basis_points":250,"payout_address":"0xfd99b5306135f3f6e1b5d18e1257875bf540eadd"}]
     * traits : {"boost attribute":{"min":1,"max":2046}}
     * stats : {"seven_day_volume":0,"seven_day_change":-1,"total_volume":40.5,"count":731,"num_owners":24,"market_cap":913.75,"average_price":0.987804878048781,"items_sold":41}
     * banner_image_url : null
     * chat_url : null
     * created_date : 2019-11-08T02:12:56.061937
     * default_to_fiat : false
     * description : Tradable, Collectable, Non-Fungible, Sneakrs. Powered by ethAIReum
     * dev_buyer_fee_basis_points : 0
     * dev_seller_fee_basis_points : 0
     * display_data : {"card_display_style":"padded","images":["https://rinkeby-storage.opensea.io/0xe5bd43166a3761628ac777bb1a42bcab13ca9495/22None-1573182281.","https://rinkeby-storage.opensea.io/0xe5bd43166a3761628ac777bb1a42bcab13ca9495/15None-1573182225.","https://rinkeby-storage.opensea.io/0xe5bd43166a3761628ac777bb1a42bcab13ca9495/13None-1573182211.","https://rinkeby-storage.opensea.io/0xe5bd43166a3761628ac777bb1a42bcab13ca9495/11None-1573182191.","https://rinkeby-storage.opensea.io/0xe5bd43166a3761628ac777bb1a42bcab13ca9495/1None-1573182109.","https://rinkeby-storage.opensea.io/0xe5bd43166a3761628ac777bb1a42bcab13ca9495/5None-1573182142."]}
     * external_url : http://sneakrcred.com/
     * featured : false
     * featured_image_url : null
     * hidden : true
     * image_url : https://rinkeby-storage.opensea.io/j1-1573181432.png
     * is_subject_to_whitelist : false
     * large_image_url : https://rinkeby-storage.opensea.io/j1-large-1573181433.png
     * name : Sneakrcred Rinkeby
     * only_proxied_transfers : false
     * opensea_buyer_fee_basis_points : 0
     * opensea_seller_fee_basis_points : 250
     * payout_address : 0xfd99b5306135f3f6e1b5d18e1257875bf540eadd
     * require_email : false
     * short_description : null
     * slug : j1
     * wiki_url : null
     * owned_asset_count : 1
     */

    public List<PrimaryAssetContractsBean> primary_asset_contracts;
    public TraitsBean traits;
    public StatsBean stats;
    public DisplayDataBean display_data;

    public String banner_image_url = "";
    public String chat_url = "";
    public String created_date = "";
    public boolean default_to_fiat;
    public String description = "";
    public String dev_buyer_fee_basis_points = "";
    public String dev_seller_fee_basis_points = "";
    public String external_url = "";
    public boolean featured;
    public String featured_image_url = "";
    public boolean hidden;
    public String image_url = "";
    public boolean is_subject_to_whitelist;
    public String large_image_url = "";
    public String name = "";
    public boolean only_proxied_transfers;
    public String opensea_buyer_fee_basis_points = "";
    public String opensea_seller_fee_basis_points = "";
    public String payout_address = "";
    public boolean require_email;
    public String short_description = "";
    public String slug = "";
    public String wiki_url = "";
    public double owned_asset_count;


    public class  StatsBean{

        /**
         * seven_day_volume : 0
         * seven_day_change : -1
         * total_volume : 40.5
         * count : 731
         * num_owners : 24
         * market_cap : 913.75
         * average_price : 0.987804878048781
         * items_sold : 41
         */

        public double seven_day_volume;
        public double seven_day_change;
        public double total_volume;
        public double count;
        public double num_owners;
        public double market_cap;
        public double average_price;
        public double items_sold;
    }

    // FIXME generate failure  field _$BoostAttribute182
    public class TraitsBean{

        @SerializedName("boost attribute")
        public BoostAttributeBean boostAttribute;

        public class BoostAttributeBean {
            /**
             * min : 1
             * max : 2046
             */

            public double min;
            public double max;

        }
    }

    public class DisplayDataBean{

        /**
         * card_display_style : padded
         * images : ["https://rinkeby-storage.opensea.io/0xe5bd43166a3761628ac777bb1a42bcab13ca9495/22None-1573182281.","https://rinkeby-storage.opensea.io/0xe5bd43166a3761628ac777bb1a42bcab13ca9495/15None-1573182225.","https://rinkeby-storage.opensea.io/0xe5bd43166a3761628ac777bb1a42bcab13ca9495/13None-1573182211.","https://rinkeby-storage.opensea.io/0xe5bd43166a3761628ac777bb1a42bcab13ca9495/11None-1573182191.","https://rinkeby-storage.opensea.io/0xe5bd43166a3761628ac777bb1a42bcab13ca9495/1None-1573182109.","https://rinkeby-storage.opensea.io/0xe5bd43166a3761628ac777bb1a42bcab13ca9495/5None-1573182142."]
         */

        public String card_display_style;
        public List<String> images;

    }

    public class PrimaryAssetContractsBean{


        /**
         * address : 0xe5bd43166a3761628ac777bb1a42bcab13ca9495
         * asset_contract_type : non-fungible
         * created_date : 2019-11-08T02:12:55.301292
         * name : J1
         * nft_version : 3.0
         * opensea_version : null
         * owner : 33531
         * schema_name : ERC721
         * symbol :
         * total_supply : 1
         * description : Tradable, Collectable, Non-Fungible, Sneakrs. Powered by ethAIReum
         * external_link : http://sneakrcred.com/
         * image_url : https://rinkeby-storage.opensea.io/j1-1573181432.png
         * default_to_fiat : false
         * dev_buyer_fee_basis_points : 0
         * dev_seller_fee_basis_points : 0
         * only_proxied_transfers : false
         * opensea_buyer_fee_basis_points : 0
         * opensea_seller_fee_basis_points : 250
         * buyer_fee_basis_points : 0
         * seller_fee_basis_points : 250
         * payout_address : 0xfd99b5306135f3f6e1b5d18e1257875bf540eadd
         */

        private String address = "";
        private String asset_contract_type = "";
        private String created_date = "";
        private String name = "";
        private String nft_version = "";
        private String opensea_version = "";
        private double owner;
        private String schema_name = "";
        private String symbol = "";
        private String total_supply = "";
        private String description = "";
        private String external_link = "";
        private String image_url = "";
        private boolean default_to_fiat;
        private double dev_buyer_fee_basis_points;
        private double dev_seller_fee_basis_points;
        private boolean only_proxied_transfers;
        private double opensea_buyer_fee_basis_points;
        private double opensea_seller_fee_basis_points;
        private double buyer_fee_basis_points;
        private double seller_fee_basis_points;
        private String payout_address = "";
    }
}
