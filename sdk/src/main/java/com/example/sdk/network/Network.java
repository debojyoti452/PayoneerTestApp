package com.example.sdk.network;

import com.example.sdk.base.BaseHttpClient;
import com.example.sdk.interfaces.ApiService;
import com.example.sdk.interfaces.NetworkCallback;
import com.example.sdk.interfaces.annotations.HTTP_GET;
import com.example.sdk.interfaces.annotations.HTTP_POST;
import com.example.sdk.models.BaseNetworkModel;
import com.example.sdk.models.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import needle.Needle;
import needle.UiRelatedTask;

public final class Network {

    protected BaseHttpClient baseHttpClient;
    final Gson gson = new GsonBuilder().create();

    public Network(Builder builder) {
        baseHttpClient = BaseHttpClient.getInstance(
                builder.baseUrl
        );
    }

    public List<BaseNetworkModel> apply(final Class<?> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("Class is not an Interface");
        }

        List<BaseNetworkModel> urlList = new ArrayList<>();

        for (Method method : service.getDeclaredMethods()) {
            Annotation[] annotationList = method.getAnnotations();
            Objects.requireNonNull(annotationList);

            for (Annotation annotation : annotationList) {
                BaseNetworkModel baseNetworkModel = new BaseNetworkModel();
                if (annotation instanceof HTTP_GET) {
                    baseNetworkModel.setName(method.getName());
                    baseNetworkModel.setMethod("GET");
                    baseNetworkModel.setUrl(((HTTP_GET) annotation).value());
                } else if (annotation instanceof HTTP_POST) {
                    baseNetworkModel.setName(method.getName());
                    baseNetworkModel.setMethod("POST");
                    baseNetworkModel.setUrl(((HTTP_POST) annotation).value());
                }
                urlList.add(baseNetworkModel);
            }
        }

        return urlList;
    }

    public void verifyCardType(String query, NetworkCallback networkCallback) {
        Needle.onBackgroundThread().execute(new UiRelatedTask<String>() {
            @Override
            protected String doWork() {
                String response;
                try {
                    response = baseHttpClient.getMethod(apply(ApiService.class).get(0));
                } catch (IOException e) {
                    response = e.getMessage();
                    networkCallback.onFailed("Failed: " + response);
                }
                return response;
            }

            @Override
            protected void thenDoUiRelatedWork(String result) {
                networkCallback.onSuccess(gson.fromJson(result, Response.class));
            }
        });
    }

    public static class Builder {
        private String baseUrl;

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Network build() {
            return new Network(this);
        }
    }
}
