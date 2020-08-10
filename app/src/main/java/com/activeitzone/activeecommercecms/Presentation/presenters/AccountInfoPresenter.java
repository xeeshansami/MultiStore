package com.activeitzone.activeecommercecms.Presentation.presenters;

import com.activeitzone.activeecommercecms.Models.User;
import com.activeitzone.activeecommercecms.Network.response.ProfileInfoUpdateResponse;
import com.activeitzone.activeecommercecms.Network.response.ShippingInfoUpdateResponse;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.AccountInfoView;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.ProfileInfoUpdateInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.ShippingInfoUpdateInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.UserInfoInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.ProfileInfoUpdateInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.ShippingInfoUpdateInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.UserInfoInteractorImpl;
import com.google.gson.JsonObject;

public class AccountInfoPresenter extends AbstractPresenter implements ProfileInfoUpdateInteractor.CallBack, ShippingInfoUpdateInteractor.CallBack, UserInfoInteractor.CallBack {
    private AccountInfoView accountInfoView;

    public AccountInfoPresenter(Executor executor, MainThread mainThread, AccountInfoView accountInfoView) {
        super(executor, mainThread);
        this.accountInfoView = accountInfoView;
    }

    public void sendUpdateProfileRequest(JsonObject jsonObject, String token) {
        new ProfileInfoUpdateInteractorImpl(mExecutor, mMainThread, this, jsonObject, token).execute();
    }

    public void sendUpdateShippingRequest(JsonObject jsonObject, String token) {
        new ShippingInfoUpdateInteractorImpl(mExecutor, mMainThread, this, jsonObject, token).execute();
    }

    public void getUpdatedUserInfo(int user_id, String token) {
        new UserInfoInteractorImpl(mExecutor, mMainThread, this, user_id, token).execute();
    }

    @Override
    public void onProfileInfoUpdated(ProfileInfoUpdateResponse profileInfoUpdateResponse) {
        if (accountInfoView != null){
            accountInfoView.onProfileInfoUpdated(profileInfoUpdateResponse);
        }
    }

    @Override
    public void onProfileInfoUpdatedError() {

    }

    @Override
    public void onShippingInfoUpdated(ShippingInfoUpdateResponse shippingInfoUpdateResponse) {
        if (accountInfoView != null){
            accountInfoView.onShippingInfoUpdated(shippingInfoUpdateResponse);
        }
    }

    @Override
    public void onShippingInfoUpdatedError() {

    }

    @Override
    public void onUserInfoLodaded(User user) {
        if (accountInfoView != null){
            accountInfoView.setUpdatedUserInfo(user);
        }
    }

    @Override
    public void onUserInfoError() {

    }
}
