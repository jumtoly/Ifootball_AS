package com.ifootball.app.webservice;

import com.ifootball.app.http.BetterHttp;

import java.io.IOException;

/**
 * Created by liu.yao on 2015/12/4.
 */
public class BaseService {
    private static final String XNEWEGGAPPID_KEY = "x-newegg-app-id";
    private static final String XNEWEGGAPPID_VALUE = "i-tiqiu.com";
    private static final String REQUEST_COOKIE_KEY = "Cookie";
    private static final String RESPONSE_COOKIE_KEY = "Set-Cookie";

    public static final int EnvironmentType_LANTestRelease = 0;
    public static final int EnvironmentType_WWWTestRelease = 1;
    public static final int EnvironmentType_ProductionRelease = 2;

    public static final int CURRENTEN_ENVIRONMENT_TYPE = EnvironmentType_LANTestRelease;
    protected static final String URL_EXTENSION = ".egg";
    public static String RESTFUL_SERVICE_HOST_WWW = null;
    public static String RESTFUL_SERVICE_HOST_SSL = null;

    static {
        switch (CURRENTEN_ENVIRONMENT_TYPE) {

            case EnvironmentType_LANTestRelease:
                RESTFUL_SERVICE_HOST_WWW = "http://182.150.28.110:9100/";
                RESTFUL_SERVICE_HOST_SSL = "http://182.150.28.110:9100/";
            /*RESTFUL_SERVICE_HOST_WWW = "http://192.168.60.101:9100/";
            RESTFUL_SERVICE_HOST_SSL = "http://192.168.60.101:9100/";*/
                break;
            case EnvironmentType_WWWTestRelease:
                RESTFUL_SERVICE_HOST_WWW = "http://app.kjt.com.pre/";
                RESTFUL_SERVICE_HOST_SSL = "http://app.kjt.com.pre/";
                break;
            case EnvironmentType_ProductionRelease:
                RESTFUL_SERVICE_HOST_WWW = "http://app.kjt.com/";
                RESTFUL_SERVICE_HOST_SSL = "http://app.kjt.com/";
                break;
        }
    }

    protected static String read(String urlString) throws IOException {
        BetterHttp.get(urlString);
        return urlString;
    }

    protected static String create(String urlString, String bodyString) {

        return urlString;
    }

    protected static String create(String urlString, String bodyString, boolean isForm) {

        return urlString;
    }

    protected static String createRegister(String urlString, String bodyString) {

        return urlString;
    }

}
