package com.activeitzone.activeecommercecms.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.MainActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.ProductDetailsActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.SellerShopActivity;
import com.activeitzone.activeecommercecms.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    ZXingScannerView zXingScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zXingScannerView = new ZXingScannerView(this);
        setContentView(zXingScannerView);
    }

    @Override
    public void handleResult(Result result) {
        if (result.toString().startsWith("http://quicker.com.pk/api/v1/shops/details/")) {
            Intent intent = new Intent(this, SellerShopActivity.class);
            intent.putExtra("myshop_link", result.getText());
            CustomToast.showToast(this, "QR Code Scan Succeeded", R.color.colorSuccess);
            startActivity(intent);
            finish();

        } else {
            CustomToast.showToast(this, "Invalid QR Code Of Shop, please try different.", R.color.colorSuccess);
            onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }
}
