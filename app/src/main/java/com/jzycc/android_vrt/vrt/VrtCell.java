package com.jzycc.android_vrt.vrt;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.jzycc.android_vrt.model.VrtViewData;
import com.jzycc.android_vrt.vrt.helper.VrtViewRenderHelper;

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
    private HashMap<String,Object> data = new HashMap<>();

    public VrtCell(@NonNull Context context,VrtViewData cell,Object data) {
        super(context);
        this.cell = cell;
        Gson gson = new Gson();
        String jsonStr = gson.toJson(data);
        this.data = gson.fromJson(jsonStr,HashMap.class);
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
        for(int i = 0 ;i < childCount; i++){
            View child = getChildAt(i);
            child.layout(points.get(i).x,points.get(i).y,points.get(i).x+getChildAt(i).getWidth(),points.get(i).y+getChildAt(i).getHeight());
        }
    }

    private void initThis(){
        vrtViewRenderHelper = new VrtViewRenderHelper(this);
        setBackgroundColor(vrtViewRenderHelper.getColor(cell.getBackgroundColor()));
        if(cell!=null){
            for(VrtViewData vrtView:cell.getSubViews()){
                switch (vrtView.get_clsName()){
                    case VrtComponentType.LABEL:
                        vrtViewRenderHelper.setTextView(vrtView,data);
                        break;
                    case VrtComponentType.IMGVIEW:
                        vrtViewRenderHelper.setImageView(vrtView,data);
                        break;
                    case VrtComponentType.LIST:
                        vrtViewRenderHelper.setRecyclerView(vrtView,data);
                        break;
                    case VrtComponentType.TEXTFIELD:
                        vrtViewRenderHelper.setEditText(vrtView,data);
                        break;
                    case VrtComponentType.VIEW:
                        vrtViewRenderHelper.setViewParent(vrtView,data);
                        break;
                }
                points.add(new Point((int) vrtView.get_x(),(int) vrtView.get_y()));
            }
        }
    }
}
