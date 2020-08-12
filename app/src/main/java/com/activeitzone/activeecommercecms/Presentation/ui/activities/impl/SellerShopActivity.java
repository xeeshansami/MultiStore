package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.Product;
import com.activeitzone.activeecommercecms.Models.Shop;
import com.activeitzone.activeecommercecms.Models.Shops;
import com.activeitzone.activeecommercecms.Presentation.presenters.ShopPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.SellerShopView;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.BestSellingAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.FeaturedProductAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.ProductListingAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.ProductClickListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.Utils.RecyclerViewMargin;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.List;

public class SellerShopActivity extends BaseActivity implements SellerShopView, ProductClickListener {
    private String shop_name, shop_link;
    private SliderLayout sliderLayout;
    private ShopPresenter shopPresenter;
    private ProgressBar progress_bar;
    private TextView featured, top_selling, new_arrival, shopAddressTxtView;
    private NestedScrollView shop_details;
    private Button btn_seller_products;
    private ImageView shopLogoImageView;
    RelativeLayout shopDetailsLayoutID;
    Shops shop;
    String breakUrlAndGetShopIndexID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_shop);
        shopDetailsLayoutID = findViewById(R.id.shopDetailsLayoutID);
        initializeActionBar();
        initviews();
        if (getIntent().hasExtra("shop_name")) {
//            shopDetailsLayoutID.setVisibility(View.GONE);
            shop_name = getIntent().getStringExtra("shop_name");
            shop_link = getIntent().getStringExtra("shop_link");
            setTitle(shop_name);
        } else if (getIntent().hasExtra("myshop_link")) {
            shop_link = getIntent().getExtras().getString("myshop_link");
        } else if (getIntent().hasExtra("shops")) {
            shop_link = "https://clients.moxols.com/quicker/api/v1/shops/details/" + getIntent().getExtras().getString("shops");
        } else {
            shop = (Shops) getIntent().getExtras().getSerializable("shops");
            shop_name = shop.getName();
//            shopDetailsLayoutID.setVisibility(View.VISIBLE);
            shopAddressTxtView.setText(shop.getAddress());
            Glide.with(this).load(AppConfig.ASSET_URL + shop.getLogo()).into(shopLogoImageView);
            breakUrlAndGetShopIndexID = shop.getLinks().getFeatured().substring(shop.getLinks().getFeatured().lastIndexOf("/") + 1);
            shop_link = "https://clients.moxols.com/quicker/api/v1/shops/details/" + breakUrlAndGetShopIndexID;
            setTitle(shop_name);
        }
        shop_details.setVisibility(View.GONE);
        progress_bar.setVisibility(View.VISIBLE);
        featured.setVisibility(View.GONE);
        top_selling.setVisibility(View.GONE);
        new_arrival.setVisibility(View.GONE);
        shopPresenter = new ShopPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        shopPresenter.getShopDetails(shop_link);
    }

    private void initviews() {
        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.stopAutoCycle();
        shopAddressTxtView = findViewById(R.id.shopAddressTxtView);
        shopLogoImageView = findViewById(R.id.shopLogoImageView);
        progress_bar = findViewById(R.id.item_progress_bar);
        featured = findViewById(R.id.featured_products_text);
        top_selling = findViewById(R.id.top_selling_products_text);
        new_arrival = findViewById(R.id.new_products_text);
        shop_details = findViewById(R.id.shop_details);
        btn_seller_products = findViewById(R.id.btnSellerProducts);
    }

    @Override
    public void onShopDetailsLoaded(Shop shop) {
        for (String photo : shop.getSliders()) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description("")
                    .image(AppConfig.ASSET_URL + photo)
                    .setScaleType(BaseSliderView.ScaleType.CenterInside);
            sliderLayout.addSlider(textSliderView);
        }
        shopAddressTxtView.setText(shop.getAddress());
        setTitle(shop.getName());
        Glide.with(this).load(AppConfig.ASSET_URL + shop.getLogo()).into(shopLogoImageView);
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        btn_seller_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerShopActivity.this, ProductListingActivity.class);
                intent.putExtra("title", shop.getName());
                intent.putExtra("url", shop.getLinks().getAll());
                startActivity(intent);
            }
        });

        new ShopPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).getFeaturedProducts(shop.getLinks().getFeatured());

        new ShopPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).getTopSellingProducts(shop.getLinks().getTop());

        new ShopPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).getNewProducts(shop.getLinks().getNew());
    }

    @Override
    public void setFeaturedProducts(List<Product> products) {
        featured.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = findViewById(R.id.featured_products);
        GridLayoutManager horizontalLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        FeaturedProductAdapter adapter = new FeaturedProductAdapter(this, products, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setTopSellingProducts(List<Product> products) {
        top_selling.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = findViewById(R.id.top_selling);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        BestSellingAdapter adapter = new BestSellingAdapter(this, products, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setNewProducts(List<Product> products) {
        new_arrival.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = findViewById(R.id.new_products);
        GridLayoutManager horizontalLayoutManager
                = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        //adapter.setClickListener(this);
        RecyclerViewMargin decoration = new RecyclerViewMargin(convertDpToPx(this, 10), 2);
        recyclerView.addItemDecoration(decoration);
        ProductListingAdapter adapter = new ProductListingAdapter(getApplicationContext(), products, this);
        recyclerView.setAdapter(adapter);
        progress_bar.setVisibility(View.GONE);
        shop_details.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProductItemClick(Product product) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("product_name", product.getName());
        intent.putExtra("link", product.getLinks().getDetails());
        intent.putExtra("top_selling", product.getLinks().getRelated());
        startActivity(intent);
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
}
