package com.jzycc.android_vrt.vrt.helper;

import com.jzycc.android_vrt.model.VrtViewData;

import java.util.HashMap;

/**
 * @author Jzy
 * created by 2018/10/5
 */
public interface VrtViewRenderService {

    /**
     * @param vrtView
     *
     * creat a {@link android.widget.TextView}
     */
    void setTextView(VrtViewData vrtView);

    /**
     * @param vrtView
     * @param data
     *
     * creat a {@link android.widget.TextView} in cell
     */
    void setTextView(VrtViewData vrtView,HashMap<String,Object> data);

    /**
     * @param vrtView
     * creat a {@link android.widget.ImageView}
     */
    void setImageView(VrtViewData vrtView);

    /**
     * @param vrtView
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
     * @param vrtView
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
     * @param vrtView
     * @param data
     * creat a {@link android.view.ViewGroup} in cell
     */
    void setEditText(VrtViewData vrtView,HashMap<String,Object> data);

    /**
     * @param vrtView
     * creat a {@link android.support.v7.widget.RecyclerView}
     */
    void setRecyclerView(VrtViewData vrtView);

    /**
     * @param vrtView
     * @param data
     * creat a {@link android.view.ViewGroup} in cell
     */
    void setRecyclerView(VrtViewData vrtView,HashMap<String,Object> data);

}
