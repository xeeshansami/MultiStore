package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Network.response.MarketDetailsResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;

import java.util.List;

public interface MarketShopDetailsInteractor {
    interface CallBack {

        void onMarketShopDetailsDownloaded(List<MarketDetailsResponse> productListingResponse);

        void onMarketShopDetailsDownloadError();
    }
}
