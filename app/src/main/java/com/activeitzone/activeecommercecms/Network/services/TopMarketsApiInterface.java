package com.activeitzone.activeecommercecms.Network.services;

import com.activeitzone.activeecommercecms.Network.response.MarketsResponse;
import com.activeitzone.activeecommercecms.Network.response.ShopsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TopMarketsApiInterface {
    @GET("markets")
    Call<MarketsResponse> getTopMarkets();
}
