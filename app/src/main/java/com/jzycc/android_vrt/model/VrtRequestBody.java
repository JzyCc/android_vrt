package com.jzycc.android_vrt.model;

import java.util.HashMap;

/**
 * @author : Jzy
 * date   : 18-10-11
 */
public class VrtRequestBody {
    private String url;
    private HashMap<String,Object> _param;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, Object> get_param() {
        return _param;
    }

    public void set_param(HashMap<String, Object> _param) {
        this._param = _param;
    }
}
