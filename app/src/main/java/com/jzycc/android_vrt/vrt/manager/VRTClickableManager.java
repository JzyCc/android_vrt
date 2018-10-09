package com.jzycc.android_vrt.vrt.manager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jzy
 * date   : 18-10-9
 */
public class VRTClickableManager {
    /**
     * save the vrtId that can clicked
     */
    private List<String> vrtIds = new ArrayList<>();

    public List<String> getVrtIds() {
        return vrtIds;
    }

    public void setVrtIds(List<String> vrtIds) {
        this.vrtIds = vrtIds;
    }
}
