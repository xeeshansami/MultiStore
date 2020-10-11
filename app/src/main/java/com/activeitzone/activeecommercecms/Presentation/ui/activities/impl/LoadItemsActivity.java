package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.HomePresenter;
import com.activeitzone.activeecommercecms.Presentation.presenters.ItemListPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.ProductListingView;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.ProductListingAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.TopMarketsAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.HomeView;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.ItemsListView;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.HomeFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.EndlessRecyclerOnScrollListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.MarketsClickListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.ProductClickListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.Utils.RecyclerViewMargin;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.ArrayList;
import java.util.List;

public class LoadItemsActivity extends BaseActivity implements ItemsListView, MarketsClickListener {

    private List<Product> mProducts = new ArrayList<>();
    private ProductListingResponse productListingResponse = null;
    private ProductListingAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView products_empty_text;
    private ItemListPresenter itemListPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_item_listing);
        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        initializeActionBar();
        setTitle(title);
        recyclerView = findViewById(R.id.loadListItems);
        progressBar = findViewById(R.id.item_progress_bar);
        GridLayoutManager horizontalLayoutManager = new GridLayoutManager(LoadItemsActivity.this, 3);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        //adapter.setClickListener(this);
        RecyclerViewMargin decoration = new RecyclerViewMargin(convertDpToPx(this,10), 2);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
//        progressBar.setVisibility(View.VISIBLE);
        itemListPresenter = new ItemListPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        itemListPresenter.getTopMarkets();
    }

    public int convertDpToPx(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }


    @Override
    public void setTopCategories(List<Category> categories) {

    }

    @Override
    public void setTopShops(List<Shops> shops) {

    }

    @Override
    public void setTopMarkets(List<Markets> markets) {
        RecyclerView recyclerView = findViewById(R.id.top_markets);
        GridLayoutManager horizontalLayoutManager
                = new GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        TopMarketsAdapter adapter = new TopMarketsAdapter(this, markets, this);
//        recyclerView.addItemDecoration( new LayoutMarginDecoration( 1,  AppConfig.convertDpToPx(this, 10)) );
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onMarketsItemClick(Markets markets) {
        Intent intent = new Intent(this, MarketShopListingActivity.class);
        intent.putExtra("title", markets.getName());
        intent.putExtra("id", markets.getId());
        startActivity(intent);
    }
}
