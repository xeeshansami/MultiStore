package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.MarketsResponse;
import com.activeitzone.activeecommercecms.Network.response.ShopsResponse;
import com.activeitzone.activeecommercecms.Network.services.TopMarketsApiInterface;
import com.activeitzone.activeecommercecms.Network.services.TopShopsApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.TopMarketsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.TopShopsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopMarketsInteractorImpl extends AbstractInteractor {
    private TopMarketsInteractor.CallBack mCallback;
    private TopMarketsApiInterface apiService;

    public TopMarketsInteractorImpl(Executor threadExecutor, MainThread mainThread, TopMarketsInteractor.CallBack callBack) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(TopMarketsApiInterface.class);
        Call<MarketsResponse> call = apiService.getTopMarkets();
        call.enqueue(new Callback<MarketsResponse>() {
            @Override
            public void onResponse(Call<MarketsResponse> call, Response<MarketsResponse> response) {
                try {
                    mCallback.onTopMarketsDownloaded(response.body().getData());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<MarketsResponse> call, Throwable t) {
                mCallback.onTopMarketsDownloadError();
            }
        });
    }
}
