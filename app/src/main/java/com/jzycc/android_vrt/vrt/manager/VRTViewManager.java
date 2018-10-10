package com.jzycc.android_vrt.vrt.manager;

import android.view.View;

import java.util.HashMap;

/**
 * @author : Jzy
 * date   : 18-10-9
 */
public class VRTViewManager {

    private HashMap<String,View> vrtViewMap;

    public VRTViewManager(HashMap<String, View> vrtViewMap) {
        this.vrtViewMap = vrtViewMap;
    }

    public HashMap<String, View> getVrtViewMap() {
        return vrtViewMap;
    }

    public void setVrtViewMap(HashMap<String, View> vrtViewMap) {
        this.vrtViewMap = vrtViewMap;
    }
}
