package com.jzycc.android_vrt.vrt_js;

/**
 * @author : Jzy
 * date   : 18-10-10
 */
public interface VRTLifeCycle {

    /**
     *CallBackViewWillAppear
     */
    void onStart();

    /**
     * CallBackViewDidAppear
     */
    void onResume();

    void callBackViewDidLoad();

    /**
     * CallBackViewWillDisappear
     */
    void onPause();

    /**
     * destroyJsContext
     */
    void onDestroy();
}
