package com.jzycc.android_vrt.model;

import java.util.HashMap;

public class VrtPushBody {
    private String url;
    private HashMap<String,Object> param;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, Object> getParam() {
        return param;
    }

    public void setParam(HashMap<String, Object> param) {
        this.param = param;
    }
}
