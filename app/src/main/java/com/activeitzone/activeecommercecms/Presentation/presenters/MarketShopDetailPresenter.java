package com.activeitzone.activeecommercecms.Presentation.presenters;

import com.activeitzone.activeecommercecms.Network.response.MarketDetailsResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.MarketShopsDetailsView;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.ProductListingView;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.MarketShopDetailsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.ProductListingInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.MarketShopDetailsInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.ProductListingInteractorImpl;

import java.util.List;

public class MarketShopDetailPresenter extends AbstractPresenter implements MarketShopDetailsInteractor.CallBack {
    private MarketShopsDetailsView marketPresenterView;

    public MarketShopDetailPresenter(Executor executor, MainThread mainThread, MarketShopsDetailsView productListingView) {
        super(executor, mainThread);
        this.marketPresenterView = productListingView;
    }

    public void getMarketShopDetails(String id) {
        new MarketShopDetailsInteractorImpl(mExecutor, mMainThread, this, id).execute();
    }


    @Override
    public void onMarketShopDetailsDownloaded(List<MarketDetailsResponse> productListingResponse) {
        if (marketPresenterView != null) {
            marketPresenterView.setMarketShopsDetails(productListingResponse);
        }
    }

    @Override
    public void onMarketShopDetailsDownloadError() {

    }
}
