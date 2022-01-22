package com.example.sdk.base;

import android.text.TextUtils;

import com.example.sdk.models.BaseNetworkModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class BaseHttpClient {

    private static volatile BaseHttpClient baseHttpClient = null;
    private final String baseUrl;
    final static String HEADER_AUTHORIZATION = "Authorization";
    final static String HEADER_ACCEPT = "Accept";
    final static String HEADER_CONTENT_TYPE = "Content-Type";
    final static String VALUE_APP_JSON = "application/json;charset=UTF-8";
    private final static int TIMEOUT_CONNECT = 5000;
    private final static int TIMEOUT_READ = 30000;
    private final static String HEADER_USER_AGENT = "User-Agent";
    private final static String CONTENT_TYPE_JSON = "application/json";
    private static final String USER_AGENT = "Mozilla/5.0";

    public BaseHttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public synchronized static BaseHttpClient getInstance(String baseUrl) {
        if (baseHttpClient == null) {
            baseHttpClient = new BaseHttpClient(baseUrl);
        }
        return baseHttpClient;
    }

    public String getMethod(BaseNetworkModel baseNetworkModel) throws IOException {
        URL url = new URL(baseUrl + baseNetworkModel.getUrl());
        String result;
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        setConnProperties(con);
        con.setRequestMethod(baseNetworkModel.getMethod());
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setDoOutput(false);
        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            result = getStringFromInputStream(con);
        } else {
            result = "Failed with response code: " + responseCode;
        }
        
        con.disconnect();
        return result;
    }

    public void postMethod() {

    }

    private String getStringFromInputStream(final HttpURLConnection connection) throws IOException {
        try (InputStream inputStream = connection.getInputStream()) {
            InputStreamReader ir = new InputStreamReader(inputStream);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }
    }

    private void setConnProperties(final HttpURLConnection conn) {
        conn.setConnectTimeout(TIMEOUT_CONNECT);
        conn.setReadTimeout(TIMEOUT_READ);

        if (!TextUtils.isEmpty(USER_AGENT)) {
            conn.setRequestProperty(HEADER_USER_AGENT, USER_AGENT);
        }
    }

}
