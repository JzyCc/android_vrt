package com.jzycc.android_vrt;

import android.util.Log;
import android.widget.ImageView;

import com.jzycc.android_vrt.vrt.adapter.VrtImageLoadAdapter;

/**
 * @author : Jzy
 * date   : 18-10-8
 */
public class ImageViewLoadAdapter implements VrtImageLoadAdapter{
    @Override
    public void setImage(ImageView imageView, String imageUrl) {
        Log.i("jzy111", "setImage: "+imageUrl);
    }
}
