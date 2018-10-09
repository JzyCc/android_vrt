package com.jzycc.android_vrt;

import android.app.Application;

import com.jzycc.android_vrt.vrt.manager.VRTSdkManager;

/**
 * @author : Jzy
 * date   : 18-10-8
 */
public class MyApplication extends Application{

    private VRTSdkManager vrtSdkManager;

    @Override
    public void onCreate() {
        super.onCreate();
        vrtSdkManager = VRTSdkManager.getInstance();
        vrtSdkManager.registerImageLoadAdapter(new ImageViewLoadAdapter());
    }
}
