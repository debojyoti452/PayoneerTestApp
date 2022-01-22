package com.example.sdk.interfaces;

import com.example.sdk.models.ApplicableItem;
import com.example.sdk.models.Response;

public interface NetworkCallback {
    void onSuccess(ApplicableItem response, String message);

    void onLoading(Integer progress);

    void onFailed(String message);
}
