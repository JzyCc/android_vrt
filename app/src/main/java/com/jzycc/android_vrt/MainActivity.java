package com.jzycc.android_vrt;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.jzycc.android_vrt.utils.FileUtils;
import com.jzycc.android_vrt.vrt_js.constant.Constant;
import com.jzycc.android_vrt.vrt_js.VRTJsEngine;
import com.jzycc.android_vrt.vrt_js.VRTRenderListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements VRTRenderListener {

    private VRTJsEngine VRTJsEngine;
    private FrameLayout frameLayout;

    private static final String JS_CALL_JAVA_FUNCTION =
            "var ScriptAPI = java.lang.Class.forName(\"" + MainActivity.class.getName() + "\", true, javaLoader);" +
                    "var methodRead = ScriptAPI.getMethod(\"jsCallJava\", [java.lang.String]);" +
                    "function jsCallJava(url) {return methodRead.invoke(javaContext, url);}" +
                    "function Test(url){ return jsCallJava(url); }";

    private static final String JS_HEADER = "var ScriptAPI = java.lang.Class.forName(\"" + MainActivity.class.getName() + "\", true, javaLoader);" +
            "var methodRead = ScriptAPI.getMethod(\"native_commitVC\", [java.lang.Object]);\n"
            + Constant.JAVA_CALL_JS_FUNCTION;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VRTJsEngine = new VRTJsEngine(this);
        frameLayout = (FrameLayout)findViewById(R.id.fl_layout);
        VRTJsEngine.requestRenderByUrl("http://21xa689434.imwork.net:8090/public/tmp/personalHomePage.js",frameLayout);
        //VRTJsEngine.requestRenderByFile("VRTJSCode.js");
    }

    @Override
    public void renderSuccess(View view) {
        frameLayout.addView(view);
        VRTJsEngine.onStart();
        VRTJsEngine.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        VRTJsEngine.onStart();
    }
}
