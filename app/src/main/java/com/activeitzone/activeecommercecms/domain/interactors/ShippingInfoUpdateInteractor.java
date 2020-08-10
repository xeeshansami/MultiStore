package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Network.response.ShippingInfoUpdateResponse;

public interface ShippingInfoUpdateInteractor {
    interface CallBack {

        void onShippingInfoUpdated(ShippingInfoUpdateResponse shippingInfoUpdateResponse);

        void onShippingInfoUpdatedError();
    }
}
