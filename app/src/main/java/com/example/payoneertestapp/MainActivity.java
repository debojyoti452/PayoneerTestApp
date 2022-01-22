package com.example.payoneertestapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sdk.Checkout;
import com.example.sdk.interfaces.NetworkCallback;
import com.example.sdk.models.Response;

public class MainActivity extends AppCompatActivity implements NetworkCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Checkout.init();
    }

    public void getAll(View view) {
        new Checkout().open("FIND", MainActivity.this);
    }

    public void onSuccess(Response response) {
        Log.d("MAIN_ACTIVITY: ", response.getNetworks().getApplicable().get(0).getCode());
    }

    @Override
    public void onFailed(String message) {
        Log.d("MAIN_ACTIVITY: ", message);
    }
}
