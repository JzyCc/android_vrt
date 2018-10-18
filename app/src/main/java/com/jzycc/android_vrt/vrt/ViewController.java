package com.jzycc.android_vrt.vrt;

import android.content.Context;
import android.view.View;

import com.jzycc.android_vrt.model.ViewControllerData;
import com.jzycc.android_vrt.vrt.helper.VrtViewRenderHelper;
import com.jzycc.android_vrt.vrt_js.manager.VRTJsManager;

/**
 * @author : Jzy
 * date   : 18-10-17
 */
public class ViewController {

    private ViewControllerData viewControllerData;
    private VRTJsManager vrtJsManager;
    private Context context;
    private VrtViewRenderHelper vrtViewRenderHelper;

    public ViewController(Context context, VRTJsManager vrtJsManager, ViewControllerData viewControllerData) {
        this.context = context;
        this.viewControllerData = viewControllerData;
        this.vrtJsManager = vrtJsManager;
        this.vrtViewRenderHelper = new VrtViewRenderHelper(context,vrtJsManager);
    }

    public View getVRTRenderView(){
        return vrtViewRenderHelper.setVRTRenderView(context,viewControllerData.getView());
    }
}
