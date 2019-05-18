package com.jzycc.android_vrt;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jzycc.android_vrt.utils.FileUtils;
import com.jzycc.android_vrt.vrt_js.constant.Constant;
import com.jzycc.android_vrt.vrt_js.VRTJsEngine;
import com.jzycc.android_vrt.vrt_js.VRTRenderListener;
import com.jzycc.android_vrt.vrt_js.manager.VRTJSNativeContract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements VRTRenderListener, VRTJSNativeContract {

    private VRTJsEngine vrtJsEngine;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vrtJsEngine = new VRTJsEngine(this);
        frameLayout = findViewById(R.id.fl_layout);
        vrtJsEngine.requestRenderByUrl("https://21xa689434.imwork.net:23333/public/tmp/VRTJSFramework/code/VRTDebugger/VRTDebugger.js",frameLayout);
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
        return vrtJsEngine.getNativeJsonObject("");
    }
}
