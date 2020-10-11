package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.Markets;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.LoadItemsActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.MarketsClickListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.bumptech.glide.Glide;

import java.util.List;

public class TopMarketsAdapter extends RecyclerView.Adapter<TopMarketsAdapter.ViewHolder> {

    private Context context;
    private List<Markets> mCategories;
    private LayoutInflater mInflater;
    private MarketsClickListener mClickListener;
    private final int limit = 3;
    private int button=0;

    // data is passed into the constructor
    public TopMarketsAdapter(Context context, List<Markets> categories, MarketsClickListener listener,int button) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mCategories = categories;
        this.mClickListener = listener;
        this.button=button;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.top_category_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mCategories.get(position));
        //This is necessary even if you mention view gone in xml file.
        holder.viewAllBtnIDOfMarkets.setVisibility(View.GONE);
        //Compare size and add button at buttom of view,ie arraylist size
        if (position == mCategories.size() - 1&& this.button==1) {
            holder.viewAllBtnIDOfMarkets.setVisibility(View.VISIBLE);
        }
        holder.viewAllBtnIDOfMarkets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Write code
                Intent  intent=new Intent(context, LoadItemsActivity.class);
                intent.putExtra("loadItem",0);
                intent.putExtra("title","All Markets");
                context.startActivity(intent);
            }
        });
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        Button viewAllBtnIDOfMarkets;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.category_icon);
            textView = itemView.findViewById(R.id.category_name);
            viewAllBtnIDOfMarkets = itemView.findViewById(R.id.viewAllBtnIDOfMarkets);
        }

        public void bind(final Markets markets) {
            Glide.with(context).load(AppConfig.ASSET_URL + markets.getLogo()).into(imageView);
            textView.setText(markets.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.onMarketsItemClick(markets);
                }
            });
        }
    }
}
