package com.jzycc.android_vrt.vrt_js.manager;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jzycc.android_vrt.model.VrtColor;
import com.jzycc.android_vrt.model.VrtRequestBody;
import com.jzycc.android_vrt.model.VrtViewData;
import com.jzycc.android_vrt.vrt.helper.VrtViewRenderHelper;
import com.jzycc.android_vrt.vrt.manager.VRTSdkManager;
import com.jzycc.android_vrt.vrt.manager.VRTViewManager;
import com.jzycc.android_vrt.vrt_js.VRTJsEngine;
import com.jzycc.android_vrt.vrt_js.constant.FunctionName;

import org.mozilla.javascript.NativeJSON;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author : Jzy
 * date   : 18-10-10
 */
public class VRTJsManager {

    private static final String TAG = "VRTJsManager";

    private VRTJsEngine vrtJsEngine;
    private VRTSdkManager vrtSdkManager;
    private Context mContext;
    private VRTOkHttpManager vrtOkHttpManager;
    private Gson gson;
    private Activity activity;

    private float defaultWidth = 750f;
    private float defaultHeight = 1334f;
    private float currentWidth = 750f;

    public void setCurrentWidth(float currentWidth) {
        this.currentWidth = currentWidth;
    }

    public float getScale(){
        return currentWidth/defaultWidth;
    }



    public VRTJsManager(Context context,VRTJsEngine vrtJsEngine) {
        this.vrtJsEngine = vrtJsEngine;
        this.mContext = context;
        this.activity = (Activity)context;
        vrtSdkManager = VRTSdkManager.getInstance();
        vrtOkHttpManager = VRTOkHttpManager.getInstance();
        this.gson = new Gson();
        this.vrtViewManager = vrtJsEngine.getVrtViewManager();
    }

    private VRTViewManager vrtViewManager;

    public HashMap<String, View> getViewMap() {
        return vrtViewManager.getVrtViewMap();
    }

    public HashMap<String, VrtViewData> getViewDataMap(){
        return vrtViewManager.getvrtViewDataMap();
    }

    public void setClickListenerForView(final View view, final String vrtId){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vrtJsEngine.callFunction(FunctionName.API_RESPONSE_BASIC_CALL_BACK,new Object[]{vrtId});
            }
        });
    }

    public void setClickListenerForCell(final View view, final String vrtId, final int type, final int position){
        if(view!=null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("jzy111", "onClick: "+vrtId);
                    vrtJsEngine.callFunction(FunctionName.API_RESPONSE_LIST_DID_SELECT_ROW,new Object[]{vrtId + "CallBackDidSelectRowAtIndexPath",type,position});
                }
            });
        }
    }

    public void notifyTextChanged(final String vrtId, String text){
        vrtJsEngine.callFunction(FunctionName.API_RESPONSE_TEXT_FIELD_RETURN,new Object[]{vrtId+"CallBackDidChange", text});
    }

    public void refreshView(String vrtId, String attributeName, Object param){
        if(vrtJsEngine.getVrtViewManager().getVrtViewMap().get(vrtId)!=null && param!=null){
            View view = vrtViewManager.getVrtViewMap().get(vrtId);
            VrtViewData vrtViewData = vrtViewManager.getvrtViewDataMap().get(vrtId);
            view.bringToFront();
            switch (attributeName){
                case "backgroundColor":
                    VrtColor color = (VrtColor)param;
                    view.setBackgroundColor(VrtViewRenderHelper.getColor(color));
                    break;
                case "_x":
                    int valueX = Double.valueOf(param.toString()).intValue();
                    view.layout((int) valueX, view.getTop(), view.getMeasuredWidth()+ valueX ,view.getBottom());
                    view.invalidate();
                    break;
                case "_y":
                    int valueY = Double.valueOf(param.toString()).intValue();
                    vrtViewData.set_y(valueY);
                    view.layout(view.getLeft(), valueY, view.getRight() ,valueY + view.getMeasuredHeight());
                    view.invalidate();
                    view.getParent().requestLayout();
                    break;
                case "_width":
                    break;
                case "_height":
                    break;
                case "cornerRadius":
                    break;
            }
            if(view instanceof EditText){
                EditText editText = (EditText)view;
                switch (attributeName) {
                    case "text":
                        Log.i("jzy111", "refreshView: "+"text");
                        editText.setText(param.toString());
                        break;
                    case "fontSize":
                        editText.setTextSize((float) param);
                        break;
                    case "textColor":
                        editText.setTextColor(VrtViewRenderHelper.getColor((VrtColor) param));
                        break;
                    case "numberOfLines":
                        editText.setMaxLines((int) param);
                        break;
                }

            } else if(view instanceof TextView){
                TextView textView = (TextView)view;
                switch (attributeName){
                    case "text":
                        textView.setText(param.toString());
                        break;
                    case "fontSize":
                        textView.setTextSize((float)param);
                        break;
                    case "textColor":
                        textView.setTextColor(VrtViewRenderHelper.getColor((VrtColor)param));
                        break;
                    case "numberOfLines":
                        textView.setMaxLines((int)param);
                        break;
                }
            } else if(view instanceof ImageView){
                ImageView imageView = (ImageView)view;
                switch (attributeName){
                    case "imageUrl":
                        vrtSdkManager.getIVRTImageLoadAdapter().setImage(mContext,imageView,param.toString());
                        break;
                }
            } else if(view instanceof RecyclerView){
                RecyclerView recyclerView = (RecyclerView) view;
            }
        }
    }

    public void callbackHttpRequestForJs(final String url, HashMap<String,Object> dataMap){
        String json = gson.toJson(dataMap);
        StringBuilder urlsb = new StringBuilder(url);
        urlsb.append("?");
        boolean isFirstParam = true;
        for ( String key : dataMap.keySet()){
            if (isFirstParam){
                isFirstParam = false;
                urlsb.append(key).append("=").append(dataMap.get(key));
            }else {
                urlsb.append("&").append(key).append("=").append(dataMap.get(key));
            }
        }
        vrtOkHttpManager.getClient().newCall(VRTOkHttpManager.getJsByUrl(urlsb.toString())).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if(Looper.getMainLooper().getThread() == Thread.currentThread()){

                }else {
                    Log.i("jzy111", "onResponse: "+"is not currentThread ");
                    final String reponseBody = response.body().string();
                    Log.i(TAG, "onResponse: "+reponseBody);
                    //final  Map reponseMap = gson.fromJson(reponseBody,HashMap.class);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Object o = vrtJsEngine.getNativeJsonObject(reponseBody);
                            vrtJsEngine.callFunction(FunctionName.API_HTTP_RESPONSE,new Object[]{url, o, ""});
                        }
                    });
                }
            }
        });
    }

    public VRTJsEngine getVrtJsEngine() {
        return vrtJsEngine;
    }
}
