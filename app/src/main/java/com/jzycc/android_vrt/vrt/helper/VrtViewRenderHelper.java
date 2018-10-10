package com.jzycc.android_vrt.vrt.helper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzycc.android_vrt.model.VrtColor;
import com.jzycc.android_vrt.model.VrtViewData;
import com.jzycc.android_vrt.vrt.manager.VRTSdkManager;
import com.jzycc.android_vrt.vrt.adapter.VrtListAdapter;
import com.jzycc.android_vrt.vrt.VrtViewParent;
import com.jzycc.android_vrt.vrt_js.manager.VRTJsManager;

import java.util.HashMap;


/**
 * @author Jzy
 * created by 2018/10/8
 */
public class VrtViewRenderHelper implements VrtViewRenderService{

    private ViewGroup parent;
    private Context mContext;
    private VRTSdkManager vrtSdkManager;
    private HashMap<String,Object> dataMap = new HashMap<>();
    private VRTJsManager vrtJsManager;
    private int position;
    private int type;

    public VrtViewRenderHelper(ViewGroup parent, VRTJsManager vrtJsManager) {
        this.parent = parent;
        mContext = parent.getContext();
        this.vrtJsManager = vrtJsManager;
        vrtSdkManager = VRTSdkManager.getInstance();
    }

    public VrtViewRenderHelper(ViewGroup parent, VRTJsManager vrtJsManager,int type, int position) {
        this.parent = parent;
        mContext = parent.getContext();
        this.vrtJsManager = vrtJsManager;
        vrtSdkManager = VRTSdkManager.getInstance();
        this.type = type;
        this.position = position;
    }
    @Override
    public void setTextView(VrtViewData vrtView){
        TextView textView = initTextView(vrtView);
        if(!TextUtils.isEmpty(textView.getText())){
            textView.setText(vrtView.getText());
        }
        vrtJsManager.setClickListenerForView(textView,vrtView.get_vrtId());
        parent.addView(textView,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    @Override
    public void setTextView(VrtViewData vrtView,HashMap<String,Object> data) {
        TextView textView = initTextView(vrtView);
        if(getBindContent(vrtView.getText(),data)!=null){
            textView.setText(getBindContent(vrtView.getText(),data).toString());
        }
        vrtJsManager.setClickListenerForCell(textView,vrtView.get_vrtId(),type,position);
        parent.addView(textView,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    @Override
    public void setImageView(VrtViewData vrtView){
        ImageView imageView = initImageView(vrtView);
        vrtJsManager.setClickListenerForView(imageView,vrtView.get_vrtId());
        parent.addView(imageView, new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    @Override
    public void setImageView(VrtViewData vrtView,HashMap<String,Object> data){
        ImageView imageView = initImageView(vrtView);
        vrtJsManager.setClickListenerForCell(imageView,vrtView.get_vrtId(),type,position);
        parent.addView(imageView, new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }


    @Override
    public void setViewParent(VrtViewData vrtView){
        View view = new VrtViewParent(mContext,vrtJsManager,vrtView);
        view.setBackgroundColor(getColor(vrtView.getBackgroundColor()));
        parent.addView(view,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    @Override
    public void setViewParent(VrtViewData vrtView,HashMap<String,Object> data) {
        View view = new VrtViewParent(mContext,vrtJsManager,vrtView);
        view.setBackgroundColor(getColor(vrtView.getBackgroundColor()));
        parent.addView(view,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    @Override
    public void setEditText(VrtViewData vrtView){
        EditText editText = initEditText(vrtView);
        editText.setText(vrtView.getText());
        parent.addView(editText,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    @Override
    public void setEditText(VrtViewData vrtView,HashMap<String,Object> data) {
        EditText editText = initEditText(vrtView);
        editText.setText(vrtView.getText());
        parent.addView(editText,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    @Override
    public void setRecyclerView(VrtViewData vrtView){
        RecyclerView recyclerView = initRecyclerView(vrtView);
        parent.addView(recyclerView,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }

    @Override
    public void setRecyclerView(VrtViewData vrtView,HashMap<String,Object> data) {
        RecyclerView recyclerView = initRecyclerView(vrtView);
        parent.addView(recyclerView,new ViewGroup.LayoutParams((int)vrtView.get_width(),(int)vrtView.get_height()));
    }


    /**
     * @param vrtColor
     * @return int
     * get tht int value of the color by the argb value
     */
    public int getColor(VrtColor vrtColor){
        if(vrtColor!=null){
            return Color.argb(vrtColor.getW(),vrtColor.getX(),vrtColor.getY(),vrtColor.getZ());
        }
        else {
            return Color.argb(0,255,255,255);
        }
    }

    /**
     * @param vrtView
     * @return {@link TextView}
     *
     * Initialize the TextView with some necessary steps
     */
    private TextView initTextView(VrtViewData vrtView){
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

    /**
     * @param vrtView
     * @return {@link EditText}
     *
     * Initialize the EditText with some necessary steps
     */
    private EditText initEditText(VrtViewData vrtView){
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

    /**
     * @param vrtView
     * @return {@link RecyclerView}
     * Initialize the RecyclerView with some necessary steps
     */
    private RecyclerView initRecyclerView(VrtViewData vrtView){
        RecyclerView recyclerView = new RecyclerView(mContext);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setBackgroundColor(getColor(vrtView.getBackgroundColor()));
        VrtListAdapter vrtListAdapter = new VrtListAdapter(mContext,vrtJsManager,vrtView);
        recyclerView.setAdapter(vrtListAdapter);
        return recyclerView;
    }

    /**
     * @param vrtView
     * @return {@link ImageView}
     * Initialize the ImageView with some necessary steps
     */
    private ImageView initImageView(VrtViewData vrtView){
        ImageView imageView = new ImageView(mContext);
        imageView.setBackgroundColor(getColor(vrtView.getBackgroundColor()));
        vrtSdkManager.getIVRTImageLoadAdapter().setImage(mContext,imageView,vrtView.getImageUrl());
        return imageView;
    }

    /**
     * @param bindMsg
     * @return key
     * get bindKey by bindMsg
     */
    private Object getBindContent(String bindMsg,HashMap<String,Object> data){
        if(!TextUtils.isEmpty(bindMsg)){
            String key = getBindKey(bindMsg);
            if(!TextUtils.isEmpty(key)){
                if(data.get(key)!=null){
                    return data.get(key);
                }
            }
        }
        return null;
    }

    private String getBindKey(String bindMsg){
        String key = "";
        if(bindMsg.contains("bindKey:")){
            key = bindMsg.substring(8);
        }
        return key;
    }

}
