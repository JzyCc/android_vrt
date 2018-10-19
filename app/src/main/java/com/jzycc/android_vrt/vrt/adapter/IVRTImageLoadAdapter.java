package com.jzycc.android_vrt.vrt.adapter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;

/**
 * @author : Jzy
 * date   : 18-10-8
 */
public interface IVRTImageLoadAdapter {

    void setImage(Context mContext, ImageView imageView, String imageUrl);
}
