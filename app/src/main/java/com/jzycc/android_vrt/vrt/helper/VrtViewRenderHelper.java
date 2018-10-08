package com.jzycc.android_vrt.vrt.helper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzycc.android_vrt.model.VrtColor;
import com.jzycc.android_vrt.model.VrtViewData;
import com.jzycc.android_vrt.vrt.VrtSdkManager;
import com.jzycc.android_vrt.vrt.adapter.VrtImageLoadAdapter;
import com.jzycc.android_vrt.vrt.adapter.VrtListAdapter;
import com.jzycc.android_vrt.vrt.VrtViewParent;


/**
 * @author Jzy
 * created by 2018/10/8
 */
public class VrtViewRenderHelper implements VrtViewRenderService{

    private VrtViewData vrtView;
    private ViewGroup parent;
    private Context mContext;
    private VrtSdkManager vrtSdkManager;

    public VrtViewRenderHelper(ViewGroup parent, VrtViewData vrtView) {
        this.vrtView = vrtView;
        this.parent = parent;
        mContext = parent.getContext();
        vrtSdkManager = VrtSdkManager.getInstance();
    }

    @Override
    public void setTextView(VrtViewData vrtView){
        TextView textView = initTextView();
        textView.setText(vrtView.getText());
        parent.addView(textView,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    @Override
    public void setTextView(VrtViewData vrtView, int position,int type) {
        TextView textView = initTextView();
        parent.addView(textView,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    @Override
    public void setImageView(VrtViewData vrtView){
        ImageView imageView = initImageView(vrtView);
        parent.addView(imageView, new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    @Override
    public void setImageView(VrtViewData vrtView,int position, int type){
        ImageView imageView = initImageView(vrtView);
        parent.addView(imageView, new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }


    @Override
    public void setViewParent(VrtViewData vrtView){
        View view = new VrtViewParent(mContext,vrtView);
        view.setBackgroundColor(getColor(vrtView.getBackgroundColor()));
        parent.addView(view,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    @Override
    public void setViewParent(VrtViewData vrtView, int position, int type) {

    }

    @Override
    public void setEditText(VrtViewData vrtView){
        EditText editText = initEditText();

        parent.addView(editText,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    @Override
    public void setEditText(VrtViewData vrtView, int position, int type) {

    }

    @Override
    public void setRecyclerView(VrtViewData vrtView){
        RecyclerView recyclerView = initRecyclerView(vrtView);
        parent.addView(recyclerView,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    @Override
    public void setRecyclerView(VrtViewData vrtView, int position, int type) {
        RecyclerView recyclerView = initRecyclerView(vrtView);
        parent.addView(recyclerView,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }


    private int getColor(VrtColor vrtColor){
        return Color.argb(100,vrtColor.getX(),vrtColor.getY(),vrtColor.getZ());
    }

    private TextView initTextView(){
        TextView textView = new TextView(mContext);
        if(vrtView.getNumberOfLines()!=null){
            textView.setMaxLines(vrtView.getNumberOfLines());
        }
        if(vrtView.getFontSize() != null){
            textView.setTextSize(vrtView.getFontSize());
        }
        if(vrtView.getTextColor()!=null){
            textView.setTextColor(getColor(vrtView.getTextColor()));
        }
        if(vrtView.getBackgroundColor() != null){
            textView.setBackgroundColor(getColor(vrtView.getBackgroundColor()));
        }
        return textView;
    }

    private EditText initEditText(){
        EditText editText = new EditText(mContext);
        if(vrtView.getFontSize() != null){
            editText.setTextSize(vrtView.getFontSize());
        }
        if(vrtView.getTextColor() != null){
            editText.setTextColor(getColor(vrtView.getTextColor()));
        }
        if(vrtView.getBackgroundColor() != null){
            editText.setBackgroundColor(getColor(vrtView.getBackgroundColor()));
        }
        return editText;
    }

    private RecyclerView initRecyclerView(VrtViewData vrtView){
        RecyclerView recyclerView = new RecyclerView(mContext);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setBackgroundColor(getColor(vrtView.getBackgroundColor()));
        VrtListAdapter vrtListAdapter = new VrtListAdapter(mContext,vrtView);
        recyclerView.setAdapter(vrtListAdapter);
        return recyclerView;
    }

    private ImageView initImageView(VrtViewData vrtView){
        ImageView imageView = new ImageView(mContext);
        //imageView.setBackgroundColor(getColor(vrtView.getBackgroundColor()));
        vrtSdkManager.getVrtImageLoadAdapter().setImage(imageView,vrtView.getImageUrl());
        return imageView;
    }

    private String getBindKey(String bindMsg){
        String key = "";
        if(bindMsg.contains("bindKey:")){
            key = bindMsg.substring(7);
        }
        return key;
    }

}
