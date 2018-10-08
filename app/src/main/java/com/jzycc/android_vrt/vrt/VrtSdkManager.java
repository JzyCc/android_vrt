package com.jzycc.android_vrt.vrt;

import com.jzycc.android_vrt.vrt.adapter.VrtImageLoadAdapter;

/**
 * @author : Jzy
 * date   : 18-10-8
 */
public class VrtSdkManager {

    private static volatile VrtSdkManager vrtSdkManager;

    private VrtImageLoadAdapter vrtImageLoadAdapter;

    public VrtSdkManager() {

    }

    public static VrtSdkManager getInstance(){
        if(vrtSdkManager == null){
            synchronized (VrtSdkManager.class){
                if(vrtSdkManager == null){
                    vrtSdkManager = new VrtSdkManager();
                }
            }
        }
        return vrtSdkManager;
    }

    public void registerImageLoadAdapter(VrtImageLoadAdapter vrtImageLoadAdapter){
        this.vrtImageLoadAdapter = vrtImageLoadAdapter;
    }

    public VrtImageLoadAdapter getVrtImageLoadAdapter() {
        return vrtImageLoadAdapter;
    }

    public void setVrtImageLoadAdapter(VrtImageLoadAdapter vrtImageLoadAdapter) {
        this.vrtImageLoadAdapter = vrtImageLoadAdapter;
    }
}
