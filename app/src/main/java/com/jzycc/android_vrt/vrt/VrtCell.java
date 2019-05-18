package com.jzycc.android_vrt.vrt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.jzycc.android_vrt.model.VrtViewData;
import com.jzycc.android_vrt.vrt.constant.VrtComponentType;
import com.jzycc.android_vrt.vrt.helper.VrtViewRenderHelper;
import com.jzycc.android_vrt.vrt_js.manager.VRTJsManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Jzy
 * created by 2018/10/8
 */
public class VrtCell extends FrameLayout{
    private VrtViewData cell;
    private List<Point> points = new ArrayList<>();
    private VrtViewRenderHelper vrtViewRenderHelper;
    private int position;
    private int type;
    private VRTJsManager vrtJsManager;
    private String listId;


    public VrtCell(@NonNull Context context, VRTJsManager vrtJsManager, String listId,VrtViewData cell, int position) {
        super(context);
        this.cell = cell;
        this.vrtJsManager = vrtJsManager;
        this.position = position;
        this.listId = listId;
        initThis();
    }

    public VrtCell(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VrtCell(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        final int childCount = getChildCount();
        if (points != null && points.size() >= childCount){
            for(int i = 0 ;i < childCount; i++){
                VrtViewData vrtViewData = cell.getSubViews().get(i);
                View child = getChildAt(i);
                child.layout((int) (vrtViewData.get_x()*vrtJsManager.getScale()+0.5f),
                        (int)(vrtViewData.get_y()*vrtJsManager.getScale()+0.5f),
                        (int)(vrtViewData.get_x()*vrtJsManager.getScale()+0.5f)+getChildAt(i).getWidth(),
                        (int)(vrtViewData.get_y()*vrtJsManager.getScale()+0.5f)+getChildAt(i).getHeight());
            }
        }
    }

    private void initThis(){
        vrtViewRenderHelper = new VrtViewRenderHelper(this,vrtJsManager);
        vrtJsManager.setClickListenerForCell(this,listId,type,position);
        setMinimumWidth((int) cell.get_width());
        setMinimumHeight((int) cell.get_height());
        if(cell!=null){
            for(VrtViewData vrtView:cell.getSubViews()){
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
}
