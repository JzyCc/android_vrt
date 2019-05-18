package com.jzycc.android_vrt.vrt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzycc.android_vrt.model.VrtViewData;
import com.jzycc.android_vrt.vrt.constant.VrtComponentType;
import com.jzycc.android_vrt.vrt.helper.VrtViewRenderHelper;
import com.jzycc.android_vrt.vrt.manager.VRTSdkManager;
import com.jzycc.android_vrt.vrt_js.manager.VRTJsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jzy
 * date   : 18-10-17
 */
@SuppressLint("ViewConstructor")
public class VrtImageView extends FrameLayout{

    private VrtViewData vc;
    private List<Point> points = new ArrayList<>();
    private VRTSdkManager vrtSdkManager;
    private VrtViewRenderHelper vrtViewRenderHelper;
    private VRTJsManager vrtJsManager;

    private ImageView imageView;
    public VrtImageView(@NonNull Context context, VRTJsManager vrtJsManager, VrtViewData vc) {
        super(context);
        this.vc = vc;
        this.vrtJsManager = vrtJsManager;
        vrtSdkManager = VRTSdkManager.getInstance();
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initThis();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        final int childCount = getChildCount();
        if (points != null && points.size() >= childCount){
            for(int i = 0 ;i < childCount; i++){
                VrtViewData vrtViewData = vc.getSubViews().get(i);
                View child = getChildAt(i);
                child.layout((int) (vrtViewData.get_x()*vrtJsManager.getScale()+0.5f),
                        (int)(vrtViewData.get_y()*vrtJsManager.getScale()+0.5f),
                        (int)(vrtViewData.get_x()*vrtJsManager.getScale()+0.5f)+getChildAt(i).getWidth(),
                        (int)(vrtViewData.get_y()*vrtJsManager.getScale()+0.5f)+getChildAt(i).getHeight());
            }
        }
    }

    private void initThis() {
        vrtViewRenderHelper = new VrtViewRenderHelper(this,vrtJsManager);
        if(vc!=null){
            for(VrtViewData vrtView:vc.getSubViews()){
                switch (vrtView.get_clsName()){
                    case VrtComponentType.LABEL:
                        vrtViewRenderHelper.setTextView(vrtView);
                        break;
                    case VrtComponentType.IMGVIEW:
                        vrtViewRenderHelper.setImageView(vrtView);
                        break;
                    case VrtComponentType.LIST:
                        vrtViewRenderHelper.setRecyclerView(vrtView);
                        break;
                    case VrtComponentType.TEXTFIELD:
                        vrtViewRenderHelper.setEditText(vrtView);
                        break;
                    case VrtComponentType.VIEW:
                        vrtViewRenderHelper.setViewParent(vrtView);
                        break;
                }
                points.add(new Point((int) (vrtView.get_x()*vrtJsManager.getScale()+0.5f),(int) (vrtView.get_y()*vrtJsManager.getScale()+0.5f)));
            }
        }
    }

    public ImageView getImageView() {
        return imageView;
    }
}
