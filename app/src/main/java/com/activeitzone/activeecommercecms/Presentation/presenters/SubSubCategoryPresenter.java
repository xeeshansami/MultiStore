package com.activeitzone.activeecommercecms.Presentation.presenters;

import com.activeitzone.activeecommercecms.Models.SubCategory;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.SubSubCategoryView;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.SubSubCategoryInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.SubSubCategoryInteractorImpl;

import java.util.List;

public class SubSubCategoryPresenter extends AbstractPresenter implements SubSubCategoryInteractor.CallBack {
    private SubSubCategoryView subSubCategoryView;

    public SubSubCategoryPresenter(Executor executor, MainThread mainThread, SubSubCategoryView subSubCategoryView) {
        super(executor, mainThread);
        this.subSubCategoryView =subSubCategoryView;
    }

    public void getSubSubCategories(String url){
        new SubSubCategoryInteractorImpl(mExecutor, mMainThread, this, url).execute();
    }

    @Override
    public void onSubSubCategoriesDownloaded(List<SubCategory> subCategories) {
        if (subSubCategoryView != null){
            subSubCategoryView.setSubSubCategories(subCategories);
        }
    }

    @Override
    public void onSubSubCategoriesDownloadError() {

    }
}
