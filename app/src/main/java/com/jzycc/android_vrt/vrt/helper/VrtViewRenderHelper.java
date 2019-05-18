package com.jzycc.android_vrt.vrt.helper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.jzycc.android_vrt.model.VrtColor;
import com.jzycc.android_vrt.model.VrtViewData;
import com.jzycc.android_vrt.vrt.VrtImageView;
import com.jzycc.android_vrt.vrt.manager.VRTSdkManager;
import com.jzycc.android_vrt.vrt.VrtViewParent;
import com.jzycc.android_vrt.vrt_js.manager.VRTJsManager;

import java.util.HashMap;


/**
 * @author Jzy
 * created by 2018/10/8
 *
 */
public class VrtViewRenderHelper implements VrtViewRenderService{

    private ViewGroup parent;
    private Context mContext;
    private VRTSdkManager vrtSdkManager;
    private HashMap<String,Object> dataMap = new HashMap<>();
    private VRTJsManager vrtJsManager;
    private int position;
    private int type;
    
    /**
     * @param vrtJsManager {@link VRTJsManager}
     * 此构造方法应该交由渲染根部构造
     */
    public VrtViewRenderHelper(Context context,VRTJsManager vrtJsManager){
        this.mContext = context;
        this.vrtJsManager = vrtJsManager;
    }

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
    public VrtViewParent setVRTRenderView(Context context, VrtViewData vrtViewData) {
        VrtViewParent vrtViewParent = new VrtViewParent(mContext,vrtJsManager,vrtViewData);
        vrtJsManager.getViewMap().put(vrtViewData.get_vrtId(),vrtViewParent);
        if (vrtViewData.get_enabledUserInteraction()){
            vrtJsManager.setClickListenerForView(vrtViewParent,vrtViewData.get_vrtId());
        }
        vrtViewParent.setBackgroundColor(getColor(vrtViewData.getBackgroundColor()));
        return vrtViewParent;
    }

    @Override
    public void setTextView(VrtViewData vrtView){
        TextView textView = initTextView(vrtView);
        if(!TextUtils.isEmpty(vrtView.getText())){
            textView.setText(vrtView.getText());
        }
        vrtJsManager.getViewMap().put(vrtView.get_vrtId(),textView);
        if (vrtView.get_enabledUserInteraction()){
            vrtJsManager.setClickListenerForView(textView,vrtView.get_vrtId());
        }
        parent.addView(textView,new ViewGroup.LayoutParams((int)(vrtView.get_width()*vrtJsManager.getScale()+0.5f),(int)(vrtView.get_height()*vrtJsManager.getScale()+0.5f)));
    }

    @Override
    public void setTextView(VrtViewData vrtView,HashMap<String,Object> data) {
        TextView textView = initTextView(vrtView);
        if(getBindContent(vrtView.getText(),data)!=null){
            textView.setText(getBindContent(vrtView.getText(),data).toString());
        }
        if (vrtView.get_enabledUserInteraction()){
            vrtJsManager.setClickListenerForView(textView,vrtView.get_vrtId());
        }
        parent.addView(textView,new ViewGroup.LayoutParams((int)(vrtView.get_width()*vrtJsManager.getScale()+0.5f),(int)(vrtView.get_height()*vrtJsManager.getScale()+0.5f)));
    }

    @Override
    public void setImageView(VrtViewData vrtView){
        VrtImageView vrtImageView = initImageView(vrtView);
        vrtJsManager.getViewMap().put(vrtView.get_vrtId(),vrtImageView);
        vrtJsManager.getViewDataMap().put(vrtView.get_vrtId(), vrtView);
        if (vrtView.get_enabledUserInteraction()){
            vrtJsManager.setClickListenerForView(vrtImageView,vrtView.get_vrtId());
        }
        parent.addView(vrtImageView,new ViewGroup.LayoutParams((int)(vrtView.get_width()*vrtJsManager.getScale()+0.5f),(int)(vrtView.get_height()*vrtJsManager.getScale()+0.5f)));
    }

    @Override
    public void setImageView(VrtViewData vrtView,HashMap<String,Object> data){
        VrtImageView imageView = initImageView(vrtView);
        if (vrtView.get_enabledUserInteraction()){
            vrtJsManager.setClickListenerForView(imageView,vrtView.get_vrtId());
        }
        parent.addView(imageView, new ViewGroup.LayoutParams((int)(vrtView.get_width()*vrtJsManager.getScale()+0.5f),(int)(vrtView.get_height()*vrtJsManager.getScale()+0.5f)));
    }


    @Override
    public void setViewParent(VrtViewData vrtView){
        View view = new VrtViewParent(mContext,vrtJsManager,vrtView);
        vrtJsManager.getViewMap().put(vrtView.get_vrtId(),view);
        vrtJsManager.getViewDataMap().put(vrtView.get_vrtId(), vrtView);
        if (vrtView.get_enabledUserInteraction()){
            vrtJsManager.setClickListenerForView(view,vrtView.get_vrtId());
        }
        view.setBackgroundColor(getColor(vrtView.getBackgroundColor()));
        parent.addView(view,new ViewGroup.LayoutParams((int)(vrtView.get_width()*vrtJsManager.getScale()+0.5f),(int)(vrtView.get_height()*vrtJsManager.getScale()+0.5f)));
    }

    @Override
    public void setViewParent(VrtViewData vrtView,HashMap<String,Object> data) {
        View view = new VrtViewParent(mContext,vrtJsManager,vrtView);
        if (vrtView.get_enabledUserInteraction()){
            vrtJsManager.setClickListenerForView(view,vrtView.get_vrtId());
        }
        view.setBackgroundColor(getColor(vrtView.getBackgroundColor()));
        parent.addView(view,new ViewGroup.LayoutParams((int)(vrtView.get_width()*vrtJsManager.getScale()+0.5f),(int)(vrtView.get_height()*vrtJsManager.getScale()+0.5f)));
    }

    @Override
    public void setEditText(final VrtViewData vrtView){
        EditText editText = initEditText(vrtView);
        editText.setText(vrtView.getText());
        vrtJsManager.getViewMap().put(vrtView.get_vrtId(),editText);
        if (vrtView.get_enabledUserInteraction()){
            vrtJsManager.setClickListenerForView(editText,vrtView.get_vrtId());
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                vrtJsManager.notifyTextChanged(vrtView.get_vrtId(), charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        parent.addView(editText,new ViewGroup.LayoutParams((int)(vrtView.get_width()*vrtJsManager.getScale()+0.5f),(int)(vrtView.get_height()*vrtJsManager.getScale()+0.5f)));
    }

    @Override
    public void setEditText(VrtViewData vrtView,HashMap<String,Object> data) {
        EditText editText = initEditText(vrtView);
        editText.setText(vrtView.getText());
        if (vrtView.get_enabledUserInteraction()){
            vrtJsManager.setClickListenerForView(editText,vrtView.get_vrtId());
        }
        parent.addView(editText,new ViewGroup.LayoutParams((int)(vrtView.get_width()*vrtJsManager.getScale()+0.5f),(int)(vrtView.get_height()*vrtJsManager.getScale()+0.5f)));
    }

    @Override
    public void setRecyclerView(VrtViewData vrtView){
        RecyclerView recyclerView = initRecyclerView(vrtView);
        vrtJsManager.getViewMap().put(vrtView.get_vrtId(),recyclerView);
        parent.addView(recyclerView,new ViewGroup.LayoutParams((int)(vrtView.get_width()*vrtJsManager.getScale()+0.5f),(int)(vrtView.get_height()*vrtJsManager.getScale()+0.5f)));
    }

    @Override
    public void setRecyclerView(VrtViewData vrtView,HashMap<String,Object> data) {
        RecyclerView recyclerView = initRecyclerView(vrtView);
        parent.addView(recyclerView,new ViewGroup.LayoutParams((int)(vrtView.get_width()*vrtJsManager.getScale()+0.5f),(int)(vrtView.get_height()*vrtJsManager.getScale()+0.5f)));
    }


    /**
     * @param vrtColor argb
     * @return int
     * get tht int value of the color by the argb value
     */
    public static int getColor(VrtColor vrtColor){
        if(vrtColor!=null){
            return Color.argb(vrtColor.getW(),vrtColor.getX(),vrtColor.getY(),vrtColor.getZ());
        }
        else {
            return Color.argb(255,255,255,255);
        }
    }

    /**
     * @param vrtView view params
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
            textView.setTextSize(16);
        }
        if(vrtView.getTextColor()!=null){
            textView.setTextColor(getColor(vrtView.getTextColor()));
        }
        if(vrtView.getBackgroundColor() != null){
            textView.setBackgroundColor(getColor(vrtView.getBackgroundColor()));
        }
        if(vrtView.getTextAlignment()!=null){
            if(vrtView.getTextAlignment().contains("Center")){
                textView.setGravity(Gravity.CENTER);
            }
        }
        return textView;
    }

    /**
     * @param vrtView view params
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
        if(vrtView.getNumberOfLines()!=null){
            editText.setMaxLines(vrtView.getNumberOfLines());
        }
        editText.setPadding(10,5,10,5);
        return editText;
    }

    /**
     * @param vrtView view params
     * @return {@link RecyclerView}
     * Initialize the RecyclerView with some necessary steps
     */
    private RecyclerView initRecyclerView(VrtViewData vrtView){
        RecyclerView recyclerView = new RecyclerView(mContext);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setBackgroundColor(getColor(vrtView.getBackgroundColor()));
        vrtJsManager.getVrtJsEngine().getVrtViewManager().getVrtViewMap().put(vrtView.get_vrtId(),recyclerView);
        return recyclerView;
    }

    /**
     * @param vrtView view params
     * @return {@link ImageView}
     * Initialize the ImageView with some necessary steps
     */
    private VrtImageView initImageView(VrtViewData vrtView){
        VrtImageView vrtImageView = new VrtImageView(mContext,vrtJsManager,vrtView);
        vrtImageView.setBackgroundColor(getColor(vrtView.getBackgroundColor()));
        vrtSdkManager.getIVRTImageLoadAdapter().setImage(mContext,vrtImageView.getImageView(),vrtView.getImageUrl());
        return vrtImageView;
    }

    /**
     * @param bindMsg it is key for get bind data
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
