package com.jzycc.android_vrt.vrt;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.jzycc.android_vrt.model.VrtViewData;
import com.jzycc.android_vrt.vrt.helper.VrtViewRenderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jzy
 * created by 2018/10/5
 */
public class VrtViewParent extends FrameLayout{
    private VrtViewData vc;
    private List<Point> points = new ArrayList<>();
    private VrtViewRenderHelper vrtViewRenderHelper;

    public VrtViewParent(@NonNull Context context,VrtViewData vc) {
        super(context);
        this.vc = vc;
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
            Log.i("jzy111", "onLayout: "+points.get(i).x+","+points.get(i).y);
            child.layout(points.get(i).x,points.get(i).y,points.get(i).x+getChildAt(i).getWidth(),points.get(i).y+getChildAt(i).getHeight());
        }
    }

    private void initThis() {
        vrtViewRenderHelper = new VrtViewRenderHelper(this,vc);

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
