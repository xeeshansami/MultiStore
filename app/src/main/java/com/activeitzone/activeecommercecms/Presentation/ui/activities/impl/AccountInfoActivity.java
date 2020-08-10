package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activeitzone.activeecommercecms.Models.User;
import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.activeitzone.activeecommercecms.Network.response.ProfileInfoUpdateResponse;
import com.activeitzone.activeecommercecms.Network.response.ShippingInfoUpdateResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.AccountInfoPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.AccountInfoView;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.CustomToast;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

public class AccountInfoActivity extends BaseActivity implements AccountInfoView {
    private AuthResponse authResponse;
    private EditText input_name, input_email, input_address, input_city, input_postal_code, input_country, input_phone;
    private Button save_profile_info, save_shipping_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        initializeActionBar();
        setTitle("Account Information");

        initviews();

        authResponse = new UserPrefs(this).getAuthPreferenceObjectJson("auth_response");
        if(authResponse != null && authResponse.getUser() != null){
            setUpData();
        }
    }

    private void initviews(){
        input_name = findViewById(R.id.input_name);
        input_email = findViewById(R.id.input_email);
        input_address = findViewById(R.id.input_address);
        input_city = findViewById(R.id.input_city);
        input_postal_code = findViewById(R.id.input_postal_code);
        input_country = findViewById(R.id.input_country);
        input_phone = findViewById(R.id.input_phone);
        save_profile_info = findViewById(R.id.save_profile_info);
        save_shipping_info = findViewById(R.id.save_shipping_info);
    }

    private void setUpData(){
        input_name.setText(authResponse.getUser().getName());
        input_email.setText(authResponse.getUser().getEmail());
        input_address.setText(authResponse.getUser().getAddress());
        input_city.setText(authResponse.getUser().getCity());
        input_postal_code.setText(authResponse.getUser().getPostalCode());
        input_country.setText(authResponse.getUser().getCountry());
        input_phone.setText(authResponse.getUser().getPhone());

        input_email.setEnabled(false);

        save_profile_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isValid = true;

                if(input_name.getText().toString().length() <= 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_name_layout);
                    til.setError("Name is required");
                    isValid = false;
                }

                if (isValid){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name", input_name.getText().toString());
                    jsonObject.addProperty("user_id", authResponse.getUser().getId());
                    new AccountInfoPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), AccountInfoActivity.this).sendUpdateProfileRequest(jsonObject, authResponse.getAccessToken());
                }
            }
        });

        save_shipping_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isValid = true;

                if(input_address.getText().toString().length() <= 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_address_layout);
                    til.setError("Address is required");
                    isValid = false;
                }
                if(input_city.getText().toString().length() <= 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_city_layout);
                    til.setError("City is required");
                    isValid = false;
                }

                if(input_postal_code.getText().toString().length() <= 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_postal_code_layout);
                    til.setError("Postal code is required");
                    isValid = false;
                }

                if(input_country.getText().toString().length() <= 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_country_layout);
                    til.setError("Country is required");
                    isValid = false;
                }

                if(input_phone.getText().toString().length() <= 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_phone_layout);
                    til.setError("Phone number is required");
                    isValid = false;
                }

                if (isValid){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("address", input_address.getText().toString());
                    jsonObject.addProperty("city", input_city.getText().toString());
                    jsonObject.addProperty("country", input_country.getText().toString());
                    jsonObject.addProperty("phone", input_phone.getText().toString());
                    jsonObject.addProperty("postal_code", input_postal_code.getText().toString());
                    jsonObject.addProperty("user_id", authResponse.getUser().getId());
                    new AccountInfoPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), AccountInfoActivity.this).sendUpdateShippingRequest(jsonObject, authResponse.getAccessToken());
                }
            }
        });
    }

    @Override
    public void onProfileInfoUpdated(ProfileInfoUpdateResponse profileInfoUpdateResponse) {
        CustomToast.showToast(this, profileInfoUpdateResponse.getMessage(), R.color.colorSuccess);
        new AccountInfoPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), AccountInfoActivity.this).getUpdatedUserInfo(authResponse.getUser().getId(), authResponse.getAccessToken());
    }

    @Override
    public void onShippingInfoUpdated(ShippingInfoUpdateResponse shippingInfoUpdateResponse) {
        CustomToast.showToast(this, shippingInfoUpdateResponse.getMessage(), R.color.colorSuccess);
        new AccountInfoPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), AccountInfoActivity.this).getUpdatedUserInfo(authResponse.getUser().getId(), authResponse.getAccessToken());
    }

    @Override
    public void setUpdatedUserInfo(User user) {
        authResponse.setUser(user);
        UserPrefs userPrefs = new UserPrefs(getApplicationContext());
        userPrefs.setAuthPreferenceObject(authResponse, "auth_response");
    }
}
