package com.jzycc.android_vrt.vrt.manager;

import android.view.View;

import com.jzycc.android_vrt.model.VrtViewData;

import java.util.HashMap;

/**
 * @author : Jzy
 * date   : 18-10-9
 */
public class VRTViewManager {

    private HashMap<String,View> vrtViewMap;

    private HashMap<String, VrtViewData> vrtViewDataMap;

    public VRTViewManager(HashMap<String, View> vrtViewMap, HashMap<String, VrtViewData> vrtViewDataMap) {
        this.vrtViewMap = vrtViewMap;
        this.vrtViewDataMap = vrtViewDataMap;
    }

    public HashMap<String, View> getVrtViewMap() {
        return vrtViewMap;
    }

    public void setVrtViewMap(HashMap<String, View> vrtViewMap) {
        this.vrtViewMap = vrtViewMap;
    }

    public HashMap<String, VrtViewData> getvrtViewDataMap() {
        return vrtViewDataMap;
    }

    public void setvrtViewDataMap(HashMap<String, VrtViewData> vrtViewDataMap) {
        this.vrtViewDataMap = vrtViewDataMap;
    }
}

