package com.example.payoneertestapp.presentation.features.viewmodel;

import android.util.Log;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sdk.base.Checkout;
import com.example.sdk.interfaces.NetworkCallback;
import com.example.sdk.models.ApplicableItem;

public class CheckoutViewModel extends ViewModel {

    private final MutableLiveData<Pair<ApplicableItem, String>> responseMutableLiveData = new MutableLiveData<>();

    public LiveData<Pair<ApplicableItem, String>> getLiveApplicableData() {
        return responseMutableLiveData;
    }

    public void openSDK(String query) {
        new Checkout().open(query, new NetworkCallback() {
            @Override
            public void onSuccess(ApplicableItem response, String message) {
                responseMutableLiveData.setValue(new Pair<>(response, message));
            }

            @Override
            public void onLoading(Integer progress) {
                Log.d("PROGRESS: ", progress.toString());
            }

            @Override
            public void onFailed(String message) {
                responseMutableLiveData.setValue(new Pair<>(null, message));
            }
        });
    }
}
