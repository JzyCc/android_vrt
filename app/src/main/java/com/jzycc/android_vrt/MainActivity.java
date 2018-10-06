package com.jzycc.android_vrt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

        jsEngine = new JsEngine(this,this.getWindowManager());

        String json = jsEngine.runScript(JsEngine.API_COMMITVC,new Object[]{});

        Log.i("jzy111", "onCreate: "+json);
    }

    @Override
    public void renderSuccess(View view) {
        //setContentView(view);
    }

    @Override
    public void setImageByUrl(View imageView, String imageUrl) {

    }


//    private Object runScript(String js, String functionName, Object[] functionParams){
//        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
//        rhino.setOptimizationLevel(-1);
//
//        try{
//            Scriptable scope = rhino.initStandardObjects();
//
//            ScriptableObject.putProperty(scope,"javaContext", org.mozilla.javascript.Context.javaToJS(MainActivity.this,scope));
//            ScriptableObject.putProperty(scope,"javaLoader", org.mozilla.javascript.Context.javaToJS(MainActivity.class.getClassLoader(),scope));
//
//            Object x = rhino.evaluateString(scope, js, "MainActivity", 1, null);
//
//            Function function = (Function) scope.get(functionName,scope);
//
//            Object result = function.call(rhino,scope,scope,functionParams);
//
//
//            Log.i("jzy111", "runScript: "+result);
//
//
//            return (Object) org.mozilla.javascript.Context.toObject(result,scope);
//
//        }finally {
//            org.mozilla.javascript.Context.exit();
//        }
//    }
//
//    public static String jsCallJava(String url) {
//        return url.toString();
//    }
//
//    public static Object native_commitVC(Object vc){
//        Log.i("jzy111", "native_commitVC: "+vc);
//        return vc;
//    }
//
//    public static Ob getOb(Ob ob){
//        return ob;
//    }
}
