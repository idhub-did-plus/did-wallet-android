package com.idhub.wallet.net.collectiables;

import com.idhub.wallet.net.collectiables.model.Collection;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CollectionService {

    @GET(CollectionContent.COLLECTIONS)
    Flowable<List<Collection>> collections(@Query("asset_owner") String asset_owner);


}
