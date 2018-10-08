package com.jzycc.android_vrt;

import android.app.Application;

import com.jzycc.android_vrt.vrt.VrtSdkManager;

/**
 * @author : Jzy
 * date   : 18-10-8
 */
public class MyApplication extends Application{

    private VrtSdkManager vrtSdkManager;

    @Override
    public void onCreate() {
        super.onCreate();
        vrtSdkManager = VrtSdkManager.getInstance();

        vrtSdkManager.registerImageLoadAdapter(new ImageViewLoadAdapter());
    }
}
