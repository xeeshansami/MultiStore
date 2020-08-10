package com.activeitzone.activeecommercecms.Presentation.ui.activities;

import com.activeitzone.activeecommercecms.Models.User;
import com.activeitzone.activeecommercecms.Network.response.ProfileInfoUpdateResponse;
import com.activeitzone.activeecommercecms.Network.response.ShippingInfoUpdateResponse;

public interface AccountInfoView {
    void onProfileInfoUpdated(ProfileInfoUpdateResponse profileInfoUpdateResponse);
    void onShippingInfoUpdated(ShippingInfoUpdateResponse shippingInfoUpdateResponse);
    void setUpdatedUserInfo(User user);
}
