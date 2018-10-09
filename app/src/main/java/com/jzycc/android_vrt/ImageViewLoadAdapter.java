package com.jzycc.android_vrt;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzycc.android_vrt.vrt.adapter.IVRTImageLoadAdapter;

/**
 * @author : Jzy
 * date   : 18-10-8
 */
public class ImageViewLoadAdapter implements IVRTImageLoadAdapter {
    @Override
    public void setImage(Context mContext, ImageView imageView, String imageUrl) {
        Glide.with(mContext).load(imageUrl).into(imageView);
    }
}
