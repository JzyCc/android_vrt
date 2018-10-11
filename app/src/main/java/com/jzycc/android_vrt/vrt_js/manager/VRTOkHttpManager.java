package com.jzycc.android_vrt.vrt_js.manager;

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

    public VRTOkHttpManager() {
        client = new OkHttpClient();
    }

    public OkHttpClient getClient() {
        return client;
    }

    public static Request getHttpRequest(String url, String json){
        RequestBody body = RequestBody.create(JSON, json);
        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }
}
