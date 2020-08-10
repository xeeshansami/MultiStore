package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

public class ShippingActivity extends BaseActivity {
    private AuthResponse authResponse;
    private EditText input_name, input_email, input_address, input_city, input_postal_code, input_country, input_phone;
    private Button payment;
    private Double total = 0.0, shipping = 0.0, tax= 0.0;
    private boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        total = getIntent().getDoubleExtra("total", 0.0);
        shipping = getIntent().getDoubleExtra("shipping", 0.0);
        tax = getIntent().getDoubleExtra("tax", 0.0);

        initializeActionBar();
        setTitle("Shipping Information");
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
        payment = findViewById(R.id.payment);
    }

    private void setUpData(){
        input_name.setText(authResponse.getUser().getName());
        input_email.setText(authResponse.getUser().getEmail());
        input_address.setText(authResponse.getUser().getAddress());
        input_city.setText(authResponse.getUser().getCity());
        input_postal_code.setText(authResponse.getUser().getPostalCode());
        input_country.setText(authResponse.getUser().getCountry());
        input_phone.setText(authResponse.getUser().getPhone());

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isValid = true;

                if(input_name.getText().toString().length() <= 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_name_layout);
                    til.setError("Name is required");
                    isValid = false;
                }

                if(input_email.getText().toString().length() <= 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_email_layout);
                    til.setError("Email is required");
                    isValid = false;
                }

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
                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra("total", total);
                    intent.putExtra("shipping", shipping);
                    intent.putExtra("tax", tax);

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name", input_name.getText().toString());
                    jsonObject.addProperty("email", input_email.getText().toString());
                    jsonObject.addProperty("address", input_address.getText().toString());
                    jsonObject.addProperty("country", input_country.getText().toString());
                    jsonObject.addProperty("city", input_city.getText().toString());
                    jsonObject.addProperty("postal_code", input_postal_code.getText().toString());
                    jsonObject.addProperty("phone", input_phone.getText().toString());
                    jsonObject.addProperty("checkout_type", "logged");

                    intent.putExtra("shipping_address", jsonObject.toString());

                    startActivity(intent);
                }
            }
        });
    }
}
