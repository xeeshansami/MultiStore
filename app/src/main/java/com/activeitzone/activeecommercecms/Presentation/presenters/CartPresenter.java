package com.activeitzone.activeecommercecms.Presentation.presenters;

import android.app.Activity;
import android.content.Context;

import com.activeitzone.activeecommercecms.Models.CartModel;
import com.activeitzone.activeecommercecms.Network.response.CartQuantityUpdateResponse;
import com.activeitzone.activeecommercecms.Network.response.RemoveCartResponse;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.CartView;
import com.activeitzone.activeecommercecms.Utils.CustomToast;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.CartInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.CartQuantityInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.RemoveCartInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.CartInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.CartQuantityInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.RemoveCartInteractorImpl;

import java.util.List;

public class CartPresenter extends AbstractPresenter implements CartInteractor.CallBack, RemoveCartInteractor.CallBack, CartQuantityInteractor.CallBack {
    private CartView cartView;
    Context context;
    public CartPresenter(Executor executor, MainThread mainThread, CartView cartView,Context context) {
        super(executor, mainThread);
        this.cartView = cartView;
        this.context=context;
    }

    public void getCartItems(int id, String token) {
        new CartInteractorImpl(mExecutor, mMainThread, this, id, token).execute();
    }

    public void removeCartItem(int id, String token){
        new RemoveCartInteractorImpl(mExecutor, mMainThread, this, id, token).execute();
    }

    public void updateCartQuantity(int id, int quantity, String token) {
        new CartQuantityInteractorImpl(mExecutor, mMainThread, this, id, quantity, token).execute();
    }

    @Override
    public void onCartLodaded(List<CartModel> cartModels) {
        if(cartView != null){
            cartView.setCartItems(cartModels);
        }
    }

    @Override
    public void onCartError(String message) {
        CustomToast.showToast((Activity) context,message,context.getResources().getColor(android.R.color.holo_red_dark));
    }

    @Override
    public void onCartItemRemoved(RemoveCartResponse removeCartResponse) {
        if(cartView != null){
            cartView.showRemoveCartMessage(removeCartResponse);
        }
    }

    @Override
    public void onCartItemRemovedError() {

    }

    @Override
    public void onCartQuantityUpdated(CartQuantityUpdateResponse cartQuantityUpdateResponse) {
        if(cartView != null){
            cartView.showCartQuantityUpdateMessage(cartQuantityUpdateResponse);
        }
    }

    @Override
    public void onCartQuantityUpdatedError() {

    }
}
