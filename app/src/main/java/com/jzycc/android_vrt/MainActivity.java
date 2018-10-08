package com.jzycc.android_vrt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jzycc.android_vrt.vrt_js.Constant;
import com.jzycc.android_vrt.vrt_js.JsEngine;
import com.jzycc.android_vrt.vrt_js.VrtRenderListener;

public class MainActivity extends AppCompatActivity implements VrtRenderListener {

    private JsEngine jsEngine;

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
        //setContentView(R.layout.activity_main);

        TextView textView = (TextView)findViewById(R.id.tv_text);

        jsEngine = new JsEngine(this,this.getWindowManager());

        String json = jsEngine.runScript(JsEngine.API_COMMITVC,new Object[]{});

    }

    @Override
    public void renderSuccess(View view) {
        setContentView(view);
    }

    @Override
    public void setImageByUrl(View imageView, String imageUrl) {

    }

}
