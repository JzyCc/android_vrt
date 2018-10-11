package com.jzycc.android_vrt.vrt;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.jzycc.android_vrt.model.VrtViewData;
import com.jzycc.android_vrt.vrt.constant.VrtComponentType;
import com.jzycc.android_vrt.vrt.helper.VrtViewRenderHelper;
import com.jzycc.android_vrt.vrt.manager.VRTSdkManager;
import com.jzycc.android_vrt.vrt_js.manager.VRTJsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jzy
 * created by 2018/10/5
 */
public class VrtViewParent extends FrameLayout{
    private VrtViewData vc;
    private List<Point> points = new ArrayList<>();
    private VRTSdkManager vrtSdkManager;
    private VrtViewRenderHelper vrtViewRenderHelper;
    private VRTJsManager vrtJsManager;

    public VrtViewParent(@NonNull Context context, VRTJsManager vrtJsManager, VrtViewData vc) {
        super(context);
        this.vc = vc;
        this.vrtJsManager = vrtJsManager;
        vrtSdkManager = VRTSdkManager.getInstance();
        initThis();
    }

    public VrtViewParent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VrtViewParent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
        final int childCount = getChildCount();
        for(int i = 0 ;i < childCount; i++){
            View child = getChildAt(i);
            child.layout(points.get(i).x,points.get(i).y,points.get(i).x+getChildAt(i).getWidth(),points.get(i).y+getChildAt(i).getHeight());
        }
    }

    private void initThis() {
        //保存对象
        vrtJsManager.getViewMap().put(vc.get_vrtId(),this);
        //判断是否需要添加监听
        vrtJsManager.setClickListenerForView(this,vc.get_vrtId());
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
                points.add(new Point((int) vrtView.get_x(),(int) vrtView.get_y()));
            }
        }
    }


}
