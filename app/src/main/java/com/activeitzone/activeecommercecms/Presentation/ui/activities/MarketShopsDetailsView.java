package com.activeitzone.activeecommercecms.Presentation.ui.activities;

import com.activeitzone.activeecommercecms.Network.response.MarketDetailsResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;

import java.util.List;

public interface MarketShopsDetailsView {
    void setMarketShopsDetails(List<MarketDetailsResponse> marketShopsDetails);
}
