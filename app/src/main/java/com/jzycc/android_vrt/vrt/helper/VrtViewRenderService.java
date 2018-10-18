package com.jzycc.android_vrt.vrt.helper;

import android.content.Context;

import com.jzycc.android_vrt.model.VrtViewData;
import com.jzycc.android_vrt.vrt.VrtViewParent;

import java.util.HashMap;

/**
 * @author Jzy
 * created by 2018/10/5
 */
public interface VrtViewRenderService {


    /**
     * @param context {@link Context}
     * @param vrtViewData {@link VrtViewData}
     *
     * 渲染整个View
     */
    VrtViewParent setVRTRenderView(Context context, VrtViewData vrtViewData);

    /**
     * @param vrtView
     *
     * creat a {@link android.widget.TextView}
     */
    void setTextView(VrtViewData vrtView);

    /**
     * @param vrtView {@link VrtViewData}
     * @param data
     *
     * creat a {@link android.widget.TextView} in cell
     */
    void setTextView(VrtViewData vrtView,HashMap<String,Object> data);

    /**
     * @param vrtView  {@link VrtViewData}
     * creat a {@link android.widget.ImageView}
     */
    void setImageView(VrtViewData vrtView);

    /**
     * @param vrtView  {@link VrtViewData}
     * @param data
     * creat a {@link android.widget.ImageView} in cell
     */
    void setImageView(VrtViewData vrtView,HashMap<String,Object> data);

    /**
     * @param vrtView
     * creat a {@link android.view.ViewGroup}
     */
    void setViewParent(VrtViewData vrtView);

    /**
     * @param vrtView  {@link VrtViewData}
     * @param data
     * creat a {@link android.view.ViewGroup} in cell
     */
    void setViewParent(VrtViewData vrtView,HashMap<String,Object> data);

    /**
     * @param vrtView
     * creat a {@link android.widget.EditText}
     */
    void setEditText(VrtViewData vrtView);

    /**
     * @param vrtView  {@link VrtViewData}
     * @param data
     * creat a {@link android.view.ViewGroup} in cell
     */
    void setEditText(VrtViewData vrtView,HashMap<String,Object> data);

    /**
     * @param vrtView  {@link VrtViewData}
     * creat a {@link android.support.v7.widget.RecyclerView}
     */
    void setRecyclerView(VrtViewData vrtView);

    /**
     * @param vrtView  {@link VrtViewData}
     * @param data
     * creat a {@link android.view.ViewGroup} in cell
     */
    void setRecyclerView(VrtViewData vrtView,HashMap<String,Object> data);

}
