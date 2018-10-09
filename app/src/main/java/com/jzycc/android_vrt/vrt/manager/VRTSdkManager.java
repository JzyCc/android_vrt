package com.jzycc.android_vrt.vrt.manager;

import android.content.Context;

import com.jzycc.android_vrt.vrt.adapter.IVRTImageLoadAdapter;
import com.jzycc.android_vrt.vrt_js.VRTJsEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jzy
 * date   : 18-10-8
 */
public class VRTSdkManager {

    private static volatile VRTSdkManager vrtSdkManager;

    private List<VRTJsEngine> VRTJsEngine = new ArrayList<>();
    private IVRTImageLoadAdapter IVRTImageLoadAdapter;
    private VRTClickableManager VRTClickableManager;

    public VRTSdkManager() {
        VRTClickableManager = new VRTClickableManager();
    }

    public static VRTSdkManager getInstance(){
        if(vrtSdkManager == null){
            synchronized (VRTSdkManager.class){
                if(vrtSdkManager == null){
                    vrtSdkManager = new VRTSdkManager();
                }
            }
        }
        return vrtSdkManager;
    }

    public void registerImageLoadAdapter(IVRTImageLoadAdapter IVRTImageLoadAdapter){
        this.IVRTImageLoadAdapter = IVRTImageLoadAdapter;
    }

    public IVRTImageLoadAdapter getIVRTImageLoadAdapter() {
        return IVRTImageLoadAdapter;
    }

    public VRTClickableManager getVRTClickableManager() {
        return VRTClickableManager;
    }

    public void requestRender(Context mContext){
    }

}
