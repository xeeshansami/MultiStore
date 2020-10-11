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
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.TopCategoriesAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.TopMarketsAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.TopShopsAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.HomeView;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.ItemsListView;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.HomeFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.CategoryClickListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.EndlessRecyclerOnScrollListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.MarketsClickListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.ProductClickListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.ShopsClickListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.Utils.RecyclerViewMargin;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.ArrayList;
import java.util.List;

public class LoadItemsActivity extends BaseActivity implements ItemsListView, MarketsClickListener, CategoryClickListener, ShopsClickListener {

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
        progressBar.setVisibility(View.VISIBLE);
        itemListPresenter = new ItemListPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        if (getIntent().getExtras().getInt("loadItem") == 0) {
            itemListPresenter.getTopMarkets();
        } else if (getIntent().getExtras().getInt("loadItem") == 1) {
            itemListPresenter.getTopCategories();
        } else if (getIntent().getExtras().getInt("loadItem") == 2) {
            itemListPresenter.getTopShops();
        }
    }

    public int convertDpToPx(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }


    @Override
    public void setTopCategories(List<Category> categories) {
        GridLayoutManager horizontalLayoutManager = new GridLayoutManager(LoadItemsActivity.this, 3);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        TopCategoriesAdapter adapter = new TopCategoriesAdapter(this, categories, this,0);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setTopShops(List<Shops> shops) {
        GridLayoutManager horizontalLayoutManager = new GridLayoutManager(LoadItemsActivity.this, 3);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        TopShopsAdapter adapter = new TopShopsAdapter(this, shops, this,0);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setTopMarkets(List<Markets> markets) {
        GridLayoutManager horizontalLayoutManager = new GridLayoutManager(LoadItemsActivity.this, 3);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        TopMarketsAdapter adapter = new TopMarketsAdapter(this, markets, this,0);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onMarketsItemClick(Markets markets) {
        Intent intent = new Intent(this, MarketShopListingActivity.class);
        intent.putExtra("title", markets.getName());
        intent.putExtra("id", markets.getId());
        startActivity(intent);
    }

    @Override
    public void onCategoryItemClick(Category category) {
        Intent intent = new Intent(this, ProductListingActivity.class);
        intent.putExtra("title", category.getName());
        intent.putExtra("url", category.getLinks().getProducts());
        startActivity(intent);
    }

    @Override
    public void onShopsItemClick(Shops shops) {
        Intent intent = new Intent(this, SellerShopActivity.class);
        intent.putExtra("shops",  shops);
        startActivity(intent);
    }
}
