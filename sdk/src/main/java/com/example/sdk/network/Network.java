package com.example.sdk.network;

import android.os.Build;
import android.util.Pair;

import com.example.sdk.interfaces.ApiService;
import com.example.sdk.interfaces.NetworkCallback;
import com.example.sdk.interfaces.annotations.HTTP_GET;
import com.example.sdk.interfaces.annotations.HTTP_POST;
import com.example.sdk.models.ApplicableItem;
import com.example.sdk.models.BaseNetworkModel;
import com.example.sdk.models.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import needle.Needle;
import needle.UiRelatedProgressTask;

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
        Needle.onBackgroundThread().execute(new UiRelatedProgressTask<Pair<ApplicableItem, String>, Integer>() {
            @Override
            protected Pair<ApplicableItem, String> doWork() {
                Pair<ApplicableItem, String> responsePair = null;
                try {
                    Response response =
                            gson.fromJson(baseHttpClient
                                    .getMethod(apply(ApiService.class)
                                            .get(0)), Response.class);

                    ApplicableItem applicableItem =
                            isQueryFound(response.getNetworks().getApplicable(), query);

                    if (applicableItem != null) {
                        responsePair = new Pair<>(applicableItem, "Success");
                    } else {
                        responsePair = new Pair<>(null, "Code Not Found");
                    }
                } catch (IOException e) {
                    e.getMessage();
                    responsePair = new Pair<>(null, "Exception: " + e.getMessage());
                }
                return responsePair;
            }

            @Override
            protected void thenDoUiRelatedWork(Pair<ApplicableItem, String> result) {
                if (result.first != null) {
                    networkCallback.onSuccess(result.first, result.second);
                } else {
                    networkCallback.onFailed(result.second);
                }
            }

            @Override
            protected void onProgressUpdate(Integer progress) {
                networkCallback.onLoading(progress);
            }
        });
    }

    private ApplicableItem isQueryFound(List<ApplicableItem> applicableItemList, String query) {
        if (!applicableItemList.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return applicableItemList.stream()
                        .filter(applicableItem -> applicableItem.getCode().toLowerCase(Locale.getDefault())
                                .equals(query.toLowerCase(Locale.getDefault())))
                        .findAny()
                        .orElse(null);
            } else {
                for (ApplicableItem applicableItem : applicableItemList) {
                    if (applicableItem.getCode().toLowerCase(Locale.getDefault())
                            .equals(query.toLowerCase(Locale.getDefault()))) {
                        return applicableItem;
                    }
                }
            }
        }
        return null;
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
