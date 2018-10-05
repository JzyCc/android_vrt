package com.jzycc.android_vrt.vrt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author Jzy
 * created by 2018/10/5
 */
public class VrtViewChild extends FrameLayout {
    public VrtViewChild(@NonNull Context context) {
        super(context);
    }

    public VrtViewChild(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VrtViewChild(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
