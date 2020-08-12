package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.MarketDetailsResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;
import com.activeitzone.activeecommercecms.Network.services.MarketShopDetailsApiInterface;
import com.activeitzone.activeecommercecms.Network.services.ProductListingApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.MarketShopDetailsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.ProductListingInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketShopDetailsInteractorImpl extends AbstractInteractor {
    private MarketShopDetailsInteractor.CallBack mCallback;
    private MarketShopDetailsApiInterface apiService;
    private String url;

    public MarketShopDetailsInteractorImpl(Executor threadExecutor, MainThread mainThread, MarketShopDetailsInteractor.CallBack callBack, String url) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.url = url;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(MarketShopDetailsApiInterface.class);
        Call<List<MarketDetailsResponse>> call = apiService.getMarketShopsDetails(url);
        call.enqueue(new Callback<List<MarketDetailsResponse>>() {
            @Override
            public void onResponse(Call<List<MarketDetailsResponse>> call, Response<List<MarketDetailsResponse>> response) {
                try {
                    mCallback.onMarketShopDetailsDownloaded(response.body());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<MarketDetailsResponse>> call, Throwable t) {
                mCallback.onMarketShopDetailsDownloadError();
            }
        });

    }
}
