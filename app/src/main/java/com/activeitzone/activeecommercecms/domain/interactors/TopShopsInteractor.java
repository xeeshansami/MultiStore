package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Models.Category;
import com.activeitzone.activeecommercecms.Models.Shops;

import java.util.List;

public interface TopShopsInteractor {
    interface CallBack {

        void onTopShopsDownloaded(List<Shops> shops);

        void onTopShopsDownloadError();
    }
}
