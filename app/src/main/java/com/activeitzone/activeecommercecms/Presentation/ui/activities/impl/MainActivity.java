package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.activeitzone.activeecommercecms.Network.response.AppSettingsResponse;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.AccountFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.CartFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.CategoriesFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.HomeFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.ProductSearchFragment;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.CustomToast;
import com.activeitzone.activeecommercecms.Utils.ScannerActivity;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.activeitzone.activeecommercecms.domain.interactors.AppSettingsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.AppSettingsInteractorImpl;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity implements AppSettingsInteractor.CallBack {
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET};
    final Fragment homeFragment = new HomeFragment();
    final Fragment categoriesFragment = new CategoriesFragment();
    private Fragment cartFragment = new CartFragment();
    public int removeCart = 0;
    private Fragment accountFragment = new AccountFragment();
    private Fragment searchFragment = new ProductSearchFragment();
    final FragmentManager fm = getSupportFragmentManager();
    private Fragment active = homeFragment;
    public static BottomNavigationView navView;
    private ImageButton cart, search, action_qr_scanner;
    private TextView title;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    title.setText(R.string.app_name);
                    loadFragment(homeFragment);
                    break;
                case R.id.navigation_categories:
                    title.setText("Categories");
                    loadFragment(categoriesFragment);
                    break;
                case R.id.navigation_search:
                    title.setText("Search");
                    loadFragment(searchFragment);
                    break;
                case R.id.navigation_cart:
                    title.setText("Shopping Cart");
                    loadFragment(cartFragment);
                    break;
                case R.id.navigation_account:
                    title.setText("My Account");
                    loadFragment(accountFragment);
                    break;
            }
            return true;
        }
    };

    private void checkForPermissions() {
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
                Intent intent = new Intent(getApplication(), ScannerActivity.class);
                startActivity(intent);
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                CustomToast.showToast((Activity) context, "Permission denied", R.color.colorSuccess);
                onBackPressed();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);

        View view = getSupportActionBar().getCustomView();

        action_qr_scanner = view.findViewById(R.id.action_qr_scanner);
        cart = view.findViewById(R.id.action_bar_cart);
        search = view.findViewById(R.id.action_bar_search);
        title = view.findViewById(R.id.nav_title);

        title.setText(R.string.app_name);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navView.setSelectedItemId(R.id.navigation_cart);
                loadFragment(cartFragment);
            }
        });
        action_qr_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForPermissions();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navView.setSelectedItemId(R.id.navigation_search);
                loadFragment(searchFragment);
            }
        });

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(3); // number of menu from left
        new QBadgeView(this).bindTarget(v).setBadgeText(String.valueOf(0)).setShowShadow(false);

        fm.beginTransaction().add(R.id.fragment_container, categoriesFragment, "categories").hide(categoriesFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, searchFragment, "search").hide(searchFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, cartFragment, "cart").hide(cartFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, accountFragment, "account").hide(accountFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, homeFragment, "home").commit();

        loadFragment(homeFragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            if (fragment != homeFragment) {
                action_qr_scanner.setVisibility(View.GONE);
                cart.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
            } else {
                action_qr_scanner.setVisibility(View.VISIBLE);
                cart.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
            }
            if (fragment == cartFragment) {
                cartFragment = new CartFragment();
                fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
                fm.beginTransaction().add(R.id.fragment_container, cartFragment, "cart").hide(cartFragment).commitAllowingStateLoss();
                fm.beginTransaction().hide(active).show(cartFragment).commitAllowingStateLoss();
                active = cartFragment;
            } else if (fragment == accountFragment) {
                accountFragment = new AccountFragment();
                fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
                fm.beginTransaction().add(R.id.fragment_container, accountFragment, "account").hide(accountFragment).commitAllowingStateLoss();
                fm.beginTransaction().hide(active).show(accountFragment).commitAllowingStateLoss();
                active = accountFragment;
            } else {
                fm.beginTransaction().hide(active).show(fragment).commit();
                active = fragment;
            }
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getExtras() != null) {
            String message = getIntent().getStringExtra("message");
            String position = getIntent().getStringExtra("position");
            removeCart = getIntent().getIntExtra("removeCart", 0);

            CustomToast.showToast(this, message, R.color.colorSuccess);
            getIntent().removeExtra("message");
            getIntent().removeExtra("position");

            if (position.equals("cart")) {
                loadFragment(cartFragment);
                navView.setSelectedItemId(R.id.navigation_cart);
            } else if (position.equals("account")) {
                loadFragment(accountFragment);
                navView.setSelectedItemId(R.id.navigation_account);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (active == homeFragment) {
            super.onBackPressed();
        } else {
            loadFragment(homeFragment);
            navView.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            new AppSettingsInteractorImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).execute();
        }
        if (resultCode == Activity.RESULT_CANCELED) {

        }

    }

    @Override
    public void onAppSettingsLoaded(AppSettingsResponse appSettingsResponse) {
        UserPrefs userPrefs = new UserPrefs(this);
        userPrefs.setAppSettingsPreferenceObject(appSettingsResponse, "app_settings_response");

        accountFragment = new AccountFragment();
        fm.beginTransaction().remove(accountFragment).commitAllowingStateLoss();
        fm.beginTransaction().add(R.id.fragment_container, accountFragment, "account").hide(accountFragment).commitAllowingStateLoss();
        fm.beginTransaction().hide(active).show(accountFragment).commitAllowingStateLoss();
        active = accountFragment;
    }

    @Override
    public void onAppSettingsLoadedError() {

    }
}