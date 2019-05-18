package com.jzycc.android_vrt.vrt_js.manager;

import android.util.Log;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author : Jzy
 * date   : 18-10-11
 */
public class VRTOkHttpManager {
    private OkHttpClient client;
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static volatile VRTOkHttpManager vrtOkHttpManager = VRTOkHttpManager.getInstance();

    public static VRTOkHttpManager getInstance(){
        if(vrtOkHttpManager==null){
            synchronized (VRTOkHttpManager.class){
                if(vrtOkHttpManager == null){
                    vrtOkHttpManager = new VRTOkHttpManager();
                }
            }
        }
        return vrtOkHttpManager;
    }

    private VRTOkHttpManager() {
        TrustAllManager trustAllManager = new TrustAllManager();
        client = new OkHttpClient.Builder()
                .sslSocketFactory(createTrustAllSSLFactory(trustAllManager),trustAllManager)
                .hostnameVerifier(createTrustAllHostnameVerifier())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();
    }


    public OkHttpClient getClient() {
        return client;
    }

    public static Request getHttpRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

    public static Request getJsByUrl(String url){
        return new Request.Builder()
                .url(url)
                .build();
    }

    private SSLSocketFactory createTrustAllSSLFactory(TrustAllManager trustAllManager) {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{trustAllManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return ssfFactory;
    }

    //获取HostnameVerifier
    private HostnameVerifier createTrustAllHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

}
