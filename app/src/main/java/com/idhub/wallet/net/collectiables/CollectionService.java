package com.idhub.wallet.net.collectiables;

import com.idhub.wallet.net.collectiables.model.AssetsCollections;
import com.idhub.wallet.net.collectiables.model.Collections;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CollectionService {

    @GET(CollectionContent.COLLECTIONS)
    Flowable<List<Collections>> collections(@Query("asset_owner") String asset_owner);

    @GET(CollectionContent.ASSETS)
    Flowable<AssetsCollections> assets(@Query("limit") String limit, @Query("owner") String owner);

}
