package com.example.payoneertestapp.presentation.base;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class AbstractBaseActivity extends AppCompatActivity {
    public abstract void setVMObserver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVMObserver();
    }

    protected void showLog(String msg) {
        Log.d(getClass().getSimpleName(), msg);
    }
}
