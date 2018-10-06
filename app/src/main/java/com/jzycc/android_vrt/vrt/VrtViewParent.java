package com.jzycc.android_vrt.vrt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzycc.android_vrt.model.ViewController;
import com.jzycc.android_vrt.model.VrtColor;
import com.jzycc.android_vrt.model.VrtViewData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jzy
 * created by 2018/10/5
 */
public class VrtViewParent extends FrameLayout{
    private VrtViewData vc;
    private List<Point> points = new ArrayList<>();

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
            child.layout(points.get(i).x,points.get(i).y,points.get(i).x+getChildAt(i).getWidth(),points.get(i).y+getChildAt(i).getHeight());
        }
    }

    private void initThis() {
        if(vc!=null){
            switch (vc.get_clsName()){
                case VrtComponentType.LABEL:
                    break;
                case VrtComponentType.IMGVIEW:
                    break;
                case VrtComponentType.LIST:
                    break;
                case VrtComponentType.TEXTFIELD:
                    break;
                case VrtComponentType.VIEW:
                    for(VrtViewData vrtView:vc.getSubViews()){
                        switch (vrtView.get_clsName()){
                            case VrtComponentType.LABEL:
                                setTextView(vrtView);
                                break;
                            case VrtComponentType.IMGVIEW:
                                setImageView(vrtView);
                                break;
                            case VrtComponentType.LIST:
                                break;
                            case VrtComponentType.TEXTFIELD:
                                break;
                            case VrtComponentType.VIEW:
                                setViewParent(vrtView);
                                break;
                        }
                        points.add(new Point((int) vrtView.get_x(),(int) vrtView.get_y()));
                    }
                    break;
            }
        }
    }

    private void setTextView(VrtViewData vrtView){
        TextView textView = new TextView(getContext());
        textView.setText(vrtView.getText());
        textView.setMaxLines(vrtView.getNumberOfLines());
        textView.setTextSize(vrtView.getFontSize());
        textView.setTextColor(getColor(vrtView.getTextColor()));
        this.addView(textView,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    private void setImageView(VrtViewData vrtView){
        ImageView imageView = new ImageView(getContext());

        this.addView(imageView, new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    private void setViewParent(VrtViewData vrtView){
        View view = new VrtViewParent(getContext(),vrtView);
        this.addView(view,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    private void setRecyclerView(VrtViewData vrtView){
        RecyclerView recyclerView = new RecyclerView(getContext());
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

    }
    private int getColor(VrtColor vrtColor){
       return Color.argb(100,vrtColor.getX(),vrtColor.getY(),vrtColor.getZ());
    }
}
