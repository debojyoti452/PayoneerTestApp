package com.example.sdk.interfaces;

import com.example.sdk.models.Response;

public interface NetworkCallback {
    void onSuccess(Response response);

    void onFailed(String message);
}
