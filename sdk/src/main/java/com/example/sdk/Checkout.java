package com.example.sdk;

import com.example.sdk.constants.UrlConstants;
import com.example.sdk.interfaces.NetworkCallback;
import com.example.sdk.network.Network;

import java.lang.ref.WeakReference;

public final class Checkout {
    private static Network network = null;

    public static void init() {
        //instantiate
        getNetwork();
    }

    public Checkout() {
    }

    public void open(String query, NetworkCallback networkCallback) {
        if (networkCallback == null) {
            throw new NullPointerException(NetworkCallback.class.getSimpleName() + " Interface in not instantiate");
        }
        network.verifyCardType(query, new WeakReference<>(networkCallback).get());
    }

    private static void getNetwork() {
        if (network == null) {
            network = new Network.Builder()
                    .setBaseUrl(UrlConstants.baseUrl)
                    .build();
        }
    }
}
