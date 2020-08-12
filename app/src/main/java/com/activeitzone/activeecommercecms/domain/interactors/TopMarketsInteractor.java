package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Models.Markets;
import com.activeitzone.activeecommercecms.Models.Shops;

import java.util.List;

public interface TopMarketsInteractor {
    interface CallBack {

        void onTopMarketsDownloaded(List<Markets> markets);

        void onTopMarketsDownloadError();
    }
}
