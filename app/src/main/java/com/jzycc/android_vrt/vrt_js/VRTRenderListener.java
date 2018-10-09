package com.jzycc.android_vrt.vrt_js;

import android.view.View;

/**
 * @author Jzy
 * created by 2018/10/5
 */
public interface VRTRenderListener {
    void renderSuccess(View view);

    void setImageByUrl(View imageView, String imageUrl);
}
