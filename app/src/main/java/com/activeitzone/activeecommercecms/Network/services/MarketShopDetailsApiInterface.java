package com.activeitzone.activeecommercecms.Network.services;

import com.activeitzone.activeecommercecms.Network.response.MarketDetailsResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MarketShopDetailsApiInterface {
    @GET
    Call<List<MarketDetailsResponse>> getMarketShopsDetails(@Url String url);
}
