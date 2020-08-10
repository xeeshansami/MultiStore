package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.CategoryResponse;
import com.activeitzone.activeecommercecms.Network.response.ShopsResponse;
import com.activeitzone.activeecommercecms.Network.services.TopCategoryApiInterface;
import com.activeitzone.activeecommercecms.Network.services.TopShopsApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.TopCategoryInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.TopShopsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopShopsInteractorImpl extends AbstractInteractor {
    private TopShopsInteractor.CallBack mCallback;
    private TopShopsApiInterface apiService;

    public TopShopsInteractorImpl(Executor threadExecutor, MainThread mainThread, TopShopsInteractor.CallBack callBack) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(TopShopsApiInterface.class);
        Call<ShopsResponse> call = apiService.getTopShops();
        call.enqueue(new Callback<ShopsResponse>() {
            @Override
            public void onResponse(Call<ShopsResponse> call, Response<ShopsResponse> response) {
                try {
                    mCallback.onTopShopsDownloaded(response.body().getData());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ShopsResponse> call, Throwable t) {
                mCallback.onTopShopsDownloadError();
            }
        });
    }
}
