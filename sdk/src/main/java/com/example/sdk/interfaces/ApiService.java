package com.example.sdk.interfaces;

import com.example.sdk.constants.UrlConstants;
import com.example.sdk.interfaces.annotations.HTTP_GET;
import com.example.sdk.models.Response;

public interface ApiService {

    @HTTP_GET(UrlConstants.getList)
    Response getDataList();
}
