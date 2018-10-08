package com.jzycc.android_vrt.vrt.helper;

import com.jzycc.android_vrt.model.VrtViewData;

/**
 * @author Jzy
 * created by 2018/10/8
 */
public interface VrtViewRenderService {

    void setTextView(VrtViewData vrtView);

    void setTextView(VrtViewData vrtView,int position, int type);

    void setImageView(VrtViewData vrtView);
    void setImageView(VrtViewData vrtView,int position, int type);

    void setViewParent(VrtViewData vrtView);
    void setViewParent(VrtViewData vrtView,int position, int type);

    void setEditText(VrtViewData vrtView);
    void setEditText(VrtViewData vrtView,int position, int type);

    void setRecyclerView(VrtViewData vrtView);
    void setRecyclerView(VrtViewData vrtView,int position, int type);

}
