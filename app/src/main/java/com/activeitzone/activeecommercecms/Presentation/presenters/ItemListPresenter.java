package com.activeitzone.activeecommercecms.Presentation.presenters;

import com.activeitzone.activeecommercecms.Models.AuctionProduct;
import com.activeitzone.activeecommercecms.Models.Banner;
import com.activeitzone.activeecommercecms.Models.Brand;
import com.activeitzone.activeecommercecms.Models.Category;
import com.activeitzone.activeecommercecms.Models.FlashDeal;
import com.activeitzone.activeecommercecms.Models.Markets;
import com.activeitzone.activeecommercecms.Models.Product;
import com.activeitzone.activeecommercecms.Models.Shops;
import com.activeitzone.activeecommercecms.Models.SliderImage;
import com.activeitzone.activeecommercecms.Network.response.AppSettingsResponse;
import com.activeitzone.activeecommercecms.Network.response.AuctionBidResponse;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.HomeView;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.ItemsListView;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.AppSettingsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.AuctionBidInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.AuctionProductInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.BannerInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.BestSellingInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.BrandInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.FeaturedProductInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.FlashDealInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.HomeCategoriesInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.SliderInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.TodaysDealInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.TopCategoryInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.TopMarketsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.TopShopsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.AppSettingsInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.AuctionBidInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.AuctionProductInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.BannerInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.BestSellingInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.BrandInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.FeaturedProductInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.FlashDealInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.HomeCategoriesInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.SliderInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.TodaysDealInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.TopCategoriesInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.TopMarketsInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.TopShopsInteractorImpl;
import com.google.gson.JsonObject;

import java.util.List;

public class ItemListPresenter extends AbstractPresenter implements AppSettingsInteractor.CallBack, SliderInteractor.CallBack, HomeCategoriesInteractor.CallBack, TodaysDealInteractor.CallBack, FlashDealInteractor.CallBack, BestSellingInteractor.CallBack, BannerInteractor.CallBack, FeaturedProductInteractor.CallBack, BrandInteractor.CallBack, TopCategoryInteractor.CallBack, AuctionProductInteractor.CallBack, AuctionBidInteractor.CallBack,TopShopsInteractor.CallBack, TopMarketsInteractor.CallBack{
    private ItemsListView itemsListView;

    public ItemListPresenter(Executor executor, MainThread mainThread, ItemsListView itemsListView) {
        super(executor, mainThread);
        this.itemsListView = itemsListView;
    }
    public void getFeaturedProducts() {
        new FeaturedProductInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getTopCategories() {
        new TopCategoriesInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getTopShops() {
        new TopShopsInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getTopMarkets() {
        new TopMarketsInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getPopularbrands() {
        new BrandInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getAuctionProducts() {
        new AuctionProductInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void submitBid(JsonObject jsonObject, String token){
        new AuctionBidInteractorImpl(mExecutor, mMainThread, this, jsonObject, token).execute();
    }


    @Override
    public void onFeaturedProductDownloaded(List<Product> products) {

    }

    @Override
    public void onFeaturedProductDownloadError() {

    }

    @Override
    public void onTopCategoriesDownloaded(List<Category> categories) {
        if (itemsListView != null) {
            itemsListView.setTopCategories(categories);
        }
    }

    @Override
    public void onTopCategoriesDownloadError() {

    }


    @Override
    public void onFlashDealProductDownloaded(FlashDeal flashDeal) {

    }

    @Override
    public void onFlashDealProductDownloadError() {

    }

    @Override
    public void onTopShopsDownloaded(List<Shops> shops) {
        if (itemsListView != null) {
            itemsListView.setTopShops(shops);
        }
    }

    @Override
    public void onTopShopsDownloadError() {

    }

    @Override
    public void onTopMarketsDownloaded(List<Markets> markets) {
        if (itemsListView != null) {
            itemsListView.setTopMarkets(markets);
        }
    }

    @Override
    public void onTopMarketsDownloadError() {

    }

    @Override
    public void onAppSettingsLoaded(AppSettingsResponse appSettingsResponse) {

    }

    @Override
    public void onAppSettingsLoadedError() {

    }

    @Override
    public void onBidSubmitted(AuctionBidResponse auctionBidResponse) {

    }

    @Override
    public void onBidSubmittedError() {

    }

    @Override
    public void onAuctionProductDownloaded(List<AuctionProduct> auctionProducts) {

    }

    @Override
    public void onAuctionProductDownloadError() {

    }

    @Override
    public void onBannersDownloaded(List<Banner> banners) {

    }

    @Override
    public void onBannersDownloadError() {

    }

    @Override
    public void onBestSellingProductDownloaded(List<Product> products) {

    }

    @Override
    public void onBestSellingProductDownloadError() {

    }

    @Override
    public void onBrandsDownloaded(List<Brand> brands) {

    }

    @Override
    public void onBrandsDownloadError() {

    }

    @Override
    public void onHomeCategoriesDownloaded(List<Category> categories) {

    }

    @Override
    public void onHomeCategoriesDownloadError() {

    }

    @Override
    public void onSliderDownloaded(List<SliderImage> sliderImages) {

    }

    @Override
    public void onSliderDownloadError() {

    }

    @Override
    public void onTodaysDealProductDownloaded(List<Product> products) {

    }

    @Override
    public void onTodaysDealProductDownloadError() {

    }
}