package com.example.payoneertestapp.presentation.features.checkout;

import android.os.Bundle;

import com.example.payoneertestapp.databinding.ActivityCheckoutBinding;
import com.example.payoneertestapp.presentation.base.AbstractBaseActivity;

public class CheckoutActivity extends AbstractBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ActivityCheckoutBinding.inflate(getLayoutInflater()).getRoot());
    }

    @Override
    public void setVMObserver() {

    }
}
