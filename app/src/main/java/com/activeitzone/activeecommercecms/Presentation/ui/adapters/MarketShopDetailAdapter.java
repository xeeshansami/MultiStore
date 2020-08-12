package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.Markets;
import com.activeitzone.activeecommercecms.Models.Product;
import com.activeitzone.activeecommercecms.Network.response.MarketDetailsResponse;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.MarketShopDetailClickListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.ProductClickListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.bumptech.glide.Glide;

import java.util.List;

public class MarketShopDetailAdapter extends RecyclerView.Adapter<MarketShopDetailAdapter.ViewHolder> {

    private List<MarketDetailsResponse> mProducts;
    private LayoutInflater mInflater;
    private Context context;
    private MarketShopDetailClickListener productClickListener;

    // data is passed into the constructor
    public MarketShopDetailAdapter(Context context, List<MarketDetailsResponse> mProducts, MarketShopDetailClickListener productClickListener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mProducts = mProducts;
        this.productClickListener = productClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_box_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mProducts.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    // convenience method for getting data at click position
    public MarketDetailsResponse getItem(int id) {
        return mProducts.get(id);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView discounted_price, price;
        TextView name;
        TextView sales;
        RatingBar ratingBar;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            discounted_price = itemView.findViewById(R.id.product_discounted_price);
            price = itemView.findViewById(R.id.product_price);
            name = itemView.findViewById(R.id.product_name);
            ratingBar = itemView.findViewById(R.id.product_rating);
            sales = itemView.findViewById(R.id.product_rating_count);
        }

        public void bind(MarketDetailsResponse product) {
            Glide.with(context).load(AppConfig.ASSET_URL + product.getLogo()).into(image);
            discounted_price.setText("Slug");
            price.setText(product.getSlug());
            /*if (product.getBaseDiscountedPrice().equals(product.getBasePrice())) {
//                price.setVisibility(View.GONE);
                price.setVisibility(View.GONE);
            }*/
//            price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            name.setText(product.getName());
            ratingBar.setVisibility(View.GONE);
//            sales.setText("Shipping Cost:"+product.getShippingCost());
            sales.setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productClickListener.onMarketShopDetailtemClick(product);
                }
            });
        }
    }
}

