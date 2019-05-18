package com.jzycc.android_vrt.vrt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jzycc.android_vrt.R;
import com.jzycc.android_vrt.vrt_js.VRTJsEngine;
import com.jzycc.android_vrt.vrt_js.VRTRenderListener;
import com.jzycc.android_vrt.vrt_js.manager.VRTJSNativeContract;

public class VRTActivity extends AppCompatActivity implements VRTRenderListener, VRTJSNativeContract {
    public static void start(Context context, String url, Object jsonParams) {
        Intent starter = new Intent(context, VRTActivity.class);
        starter.putExtra("url", url);
        starter.putExtra("jsonParams", new Gson().toJson(jsonParams));
        context.startActivity(starter);
    }

    private VRTJsEngine vrtJsEngine;
    private FrameLayout frameLayout;

    private String url = "";
    private String jsonParams = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIntentData();
        vrtJsEngine = new VRTJsEngine(this);
        frameLayout = findViewById(R.id.fl_layout);
        vrtJsEngine.requestRenderByUrl(url,frameLayout);
        //VRTJsEngine.requestRenderByFile("VRTJSCode.js");
    }

    private void getIntentData(){
        url = getIntent().getStringExtra("url");
        jsonParams = getIntent().getStringExtra("jsonParams");
    }

    @Override
    public void renderSuccess(View view) {
        frameLayout.addView(view,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public Object getPushParams() {
        Log.i("jzy111", "getPushParams: "+jsonParams);
        //Object o = new JsonParser().parse(jsonParams).getAsJsonObject();
        //Log.i("jzy111", "getPushParams: "+o);
        return vrtJsEngine.getNativeJsonObject(jsonParams);
    }
}
