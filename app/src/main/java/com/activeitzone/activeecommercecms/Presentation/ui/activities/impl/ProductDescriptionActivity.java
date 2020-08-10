package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.activeitzone.activeecommercecms.R;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

public class ProductDescriptionActivity extends BaseActivity {
    private  String product_name, description;
    private HtmlTextView product_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        product_name = getIntent().getStringExtra("product_name");
        description = getIntent().getStringExtra("description");

        initializeActionBar();
        setTitle(product_name);
        initviews();
    }

    private void initviews(){
        product_description = findViewById(R.id.product_description);
        product_description.setHtml(description);
    }
}
