package com.ifootball.app.http;


import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by liu.yao on 2015/12/4.
 */
public class BetterHttp {
    static final String LOG_TAG = "BetterHttp";


    public static final String USER_AGENT = "User-Agent";
    public static final String X_HIGHRESOLUTION = "X-HighResolution";
    public static final String X_OSVERSION = "X-OSVersion";
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String ENCODING_GZIP = "gzip";

    public static final int DEFAULT_MAX_CONNECTIONS = 4;
    public static final int DEFAULT_SOCKET_TIMEOUT = 30 * 1000;
    public static final int DEFAULT_CONNECTION_TIMEOUT = 5 * 1000;
    public static final String DEFAULT_HTTP_USER_AGENT = "Android/DroidFu";


    private static int maxConnections = DEFAULT_MAX_CONNECTIONS;
    private static int socketTimeout = DEFAULT_SOCKET_TIMEOUT;
    private static String httpUserAgent = DEFAULT_HTTP_USER_AGENT;

    private static HashMap<String, String> defaultHeaders = new HashMap<String, String>();
    private static HttpURLConnection httpClient;
    private static Context appContext;
    private static CookieManager cookieManager;

    public static void setupHttpClient() {
        cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
       /* HttpCookie cookie = new HttpCookie("Cookie: ", strcoo);
        cookieManager.getCookieStore().add(,);*/
    }

    public static String get(String url) throws IOException {
        URL httpConnectUrl = new URL(url);
        httpClient = (HttpURLConnection) httpConnectUrl.openConnection();
        httpClient.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
        httpClient.setDoInput(true);
        httpClient.setDoOutput(true);
        httpClient.setUseCaches(false);
        httpClient.setRequestProperty(USER_AGENT, defaultHeaders.get(USER_AGENT));
        httpClient.setRequestProperty(X_HIGHRESOLUTION, defaultHeaders.get(X_HIGHRESOLUTION));
        httpClient.setRequestProperty(X_OSVERSION, defaultHeaders.get(X_OSVERSION));
        httpClient.setRequestProperty(ACCEPT_ENCODING, defaultHeaders.get(ACCEPT_ENCODING));
        httpClient.setRequestProperty(CONTENT_TYPE, "application/json");
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        HttpURLConnection.setFollowRedirects(false);
        InputStream is = httpClient.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String response = "";
        String readLine = null;
        while ((readLine = br.readLine()) != null) {
            //response = br.readLine();
            response = response + readLine;
        }
        is.close();
        br.close();
        httpClient.disconnect();
        return response;
    }


    public static void clearCookie() {
        cookieManager.getCookieStore().removeAll();
    }

    public static void setContext(Context context) {
        if (appContext != null) {
            return;
        }
        appContext = context.getApplicationContext();
        appContext.registerReceiver(new ConnectionChangedBroadcastReceiver(),
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public static void updateProxySettings() {
        if (appContext == null) {
            return;
        }
    }

    public static void setUserAgent(String userAgent) {
        BetterHttp.httpUserAgent = userAgent;
        defaultHeaders.put(USER_AGENT, userAgent);

    }

    public static void setDefaultHeader(String header, String value) {
        defaultHeaders.put(header, value);

    }

    public static void enableGZIPEncoding() {
        defaultHeaders.put(ACCEPT_ENCODING, ENCODING_GZIP);

    }

}
