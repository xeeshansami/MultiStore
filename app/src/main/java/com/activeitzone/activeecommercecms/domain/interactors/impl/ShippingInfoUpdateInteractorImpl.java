package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.ShippingInfoUpdateResponse;
import com.activeitzone.activeecommercecms.Network.services.ShippingInfoApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.ShippingInfoUpdateInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingInfoUpdateInteractorImpl extends AbstractInteractor {
    private ShippingInfoUpdateInteractor.CallBack mCallback;
    private ShippingInfoApiInterface apiService;
    private JsonObject jsonObject;
    private String auth_token;

    public ShippingInfoUpdateInteractorImpl(Executor threadExecutor, MainThread mainThread, ShippingInfoUpdateInteractor.CallBack callBack, JsonObject jsonObject, String auth_token) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.jsonObject = jsonObject;
        this.auth_token = "Bearer "+auth_token;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(ShippingInfoApiInterface.class);

        Call<ShippingInfoUpdateResponse> call = apiService.updateShippingInfo(auth_token, jsonObject);

        call.enqueue(new Callback<ShippingInfoUpdateResponse>() {
            @Override
            public void onResponse(Call<ShippingInfoUpdateResponse> call, Response<ShippingInfoUpdateResponse> response) {
                try {
                    //Log.d("Test", response.body().getVariant());
                    mCallback.onShippingInfoUpdated(response.body());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ShippingInfoUpdateResponse> call, Throwable t) {
                Log.d("Test", t.getMessage());
                mCallback.onShippingInfoUpdatedError();
            }
        });

    }
}
