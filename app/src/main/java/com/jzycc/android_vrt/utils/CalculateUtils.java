package com.jzycc.android_vrt.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * @author Jzy
 * created by 2018/10/3
 */
public class CalculateUtils {
    public static int getWindowWidth(WindowManager windowManager){

        int width = windowManager.getDefaultDisplay().getWidth();

        return width;
    }

    public static int getWindowHeight(WindowManager windowManager){

        int height = windowManager.getDefaultDisplay().getHeight();

        return height;
    }
}
