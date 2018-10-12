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
import com.jzycc.android_vrt.vrt.helper.VrtViewRenderHelper;
import com.jzycc.android_vrt.vrt.manager.VRTSdkManager;
import com.jzycc.android_vrt.vrt_js.VRTJsEngine;
import com.jzycc.android_vrt.vrt_js.constant.FunctionName;

import java.io.IOException;
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


    public VRTJsManager(Context context,VRTJsEngine vrtJsEngine) {
        this.vrtJsEngine = vrtJsEngine;
        this.mContext = context;
        this.activity = (Activity)context;
        vrtSdkManager = VRTSdkManager.getInstance();
        vrtOkHttpManager = VRTOkHttpManager.getInstance();
        this.gson = new Gson();
    }

    private List<String> clickableViewVrtIds = new ArrayList<>();

    private HashMap<String, View> viewMap = new HashMap<>();

    public List<String> getClickableViewVrtIds() {
        return clickableViewVrtIds;
    }

    public HashMap<String, View> getViewMap() {
        return viewMap;
    }

    public void setClickableViewVrtIds(List<String> clickableViewVrtIds) {
        this.clickableViewVrtIds = clickableViewVrtIds;
    }


    public void setClickListenerForView(final View view, final String vrtId){
        for(String _vrtId: clickableViewVrtIds){
            if(vrtId.equals(_vrtId)){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vrtJsEngine.callFunction(FunctionName.API_RESPONSE_BASIC_CALL_BACK,new Object[]{vrtId});
                    }
                });
            }
        }
    }

    public void setClickListenerForCell(final View view, final String vrtId, final int type, final int position){
        if(view!=null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("jzy", "onClick: "+type+"，"+position);
                    vrtJsEngine.callFunction(FunctionName.API_RESPONSE_LIST_DID_SELECT_ROW,new Object[]{vrtId,type,position});
                }
            });
        }
    }

    public void refreshView(String vrtId, String attributeName, Object param){
        Log.i("jzy111", "refreshView: "+vrtId+","+attributeName+","+param);
        if(viewMap.get(vrtId)!=null&&param!=null){
            View view = viewMap.get(vrtId);
            switch (attributeName){
                case "backgroundColor":
                    VrtColor color = (VrtColor)param;
                    view.setBackgroundColor(VrtViewRenderHelper.getColor(color));
                    break;
                case "_x":
                    break;
                case "_y":
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

    public void callbackHttpRequestForJs(final String url, Object dataMap){
        String json = gson.toJson(dataMap);
        vrtOkHttpManager.getClient().newCall(VRTOkHttpManager.getHttpRequest(url,json)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if(Looper.getMainLooper().getThread() == Thread.currentThread()){
                }else {
                    Log.i(TAG, "onResponse: "+"is not currentThread");
                    Map map = new HashMap();
                    map = gson.fromJson(response.body().string(),HashMap.class);
                    final Map reponseMap = map;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "onResponse: "+reponseMap);
                            vrtJsEngine.callFunction(FunctionName.API_HTTP_RESPONSE,new Object[]{url,reponseMap,""});
                        }
                    });
                }


            }
        });
    }
}
