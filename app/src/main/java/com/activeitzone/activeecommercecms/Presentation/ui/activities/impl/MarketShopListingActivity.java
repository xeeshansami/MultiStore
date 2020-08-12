package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.Markets;
import com.activeitzone.activeecommercecms.Models.Product;
import com.activeitzone.activeecommercecms.Network.response.MarketDetailsResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.MarketShopDetailPresenter;
import com.activeitzone.activeecommercecms.Presentation.presenters.ProductListingPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.MarketShopsDetailsView;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.ProductListingView;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.MarketShopDetailAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.ProductListingAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.EndlessRecyclerOnScrollListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.MarketShopDetailClickListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.ProductClickListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.RecyclerViewMargin;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;

import java.util.ArrayList;
import java.util.List;

public class MarketShopListingActivity extends BaseActivity implements  MarketShopsDetailsView, MarketShopDetailClickListener {

    private List<MarketDetailsResponse> mProducts = new ArrayList<>();
    private MarketDetailsResponse productListingResponse = null;
    private MarketShopDetailPresenter productListingPresenter;
    private MarketShopDetailAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView products_empty_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing);

        String title = getIntent().getStringExtra("title");
        int id = getIntent().getIntExtra("id",0);

        initializeActionBar();
        setTitle(title);

        adapter = new MarketShopDetailAdapter(getApplicationContext(), mProducts, this);
        recyclerView = findViewById(R.id.product_list);
        progressBar = findViewById(R.id.item_progress_bar);
        products_empty_text = findViewById(R.id.products_empty_text);
        GridLayoutManager horizontalLayoutManager = new GridLayoutManager(MarketShopListingActivity.this, 2);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        //adapter.setClickListener(this);
        RecyclerViewMargin decoration = new RecyclerViewMargin(convertDpToPx(this,10), 2);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                    addDataToList(productListingResponse);
            }
        });

        progressBar.setVisibility(View.VISIBLE);
        productListingPresenter = new MarketShopDetailPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        productListingPresenter.getMarketShopDetails("https://clients.moxols.com/quicker/api/v1/markets/details/"+String.valueOf(id));
    }


    public void addDataToList(MarketDetailsResponse productListingResponse){
//        if (productListingResponse != null && productListingResponse.getMeta() != null && !productListingResponse.getMeta().getCurrentPage().equals(productListingResponse.getMeta().getLastPage())){
//            progressBar.setVisibility(View.VISIBLE);
//            productListingPresenter.getProducts(productListingResponse.getLinks().getNext().toString());
//        }
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public int convertDpToPx(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void setMarketShopsDetails(List<MarketDetailsResponse> marketShopsDetails) {
        mProducts.addAll(marketShopsDetails);
        this.productListingResponse = productListingResponse;
        progressBar.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();

        if (mProducts.size() <= 0){
            products_empty_text.setVisibility(View.VISIBLE);
            products_empty_text.setText(getResources().getString(R.string.no_shops_found));
        }
    }

    @Override
    public void onMarketShopDetailtemClick(MarketDetailsResponse markets) {
        Intent intent = new Intent(this, SellerShopActivity.class);
        intent.putExtra("shops",  markets.getId());
        startActivity(intent);
    }
}
