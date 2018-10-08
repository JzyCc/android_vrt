package com.jzycc.android_vrt.vrt_js;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.jzycc.android_vrt.model.ViewController;
import com.jzycc.android_vrt.utils.CalculateUtils;
import com.jzycc.android_vrt.vrt.VrtViewParent;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * author Jzy(Xiaohuntun)
 * date 18-9-26
 */
public class JsEngine{
    private Context mContext;
    private Class clazz;
    private String allFunctions = "";
    private WindowManager windowManager;
    public final static String API_COMMITVC = "api_commitVC";
    private ViewController vc;
    private View vrtView;
    private VrtRenderListener vrtRenderListener;

    private String vcJsCode = Constant.JAVA_CALL_JS_FUNCTION;

    public JsEngine(Context mContext, WindowManager windowManager) {
        this.mContext = mContext;
        this.windowManager = windowManager;
        this.clazz = JsEngine.class;
        this.vrtRenderListener = (VrtRenderListener)mContext;
        initJSStr();
    }

    private void initJSStr(){
        allFunctions = "var ScriptAPI = java.lang.Class.forName(\"" + JsEngine.class.getName() + "\", true, javaLoader);\n" +
                Constant.JS_CODE_4ANDROID+
                Constant.JAVA_CALL_JS_FUNCTION+
                Constant.JS_CODE_TEST;
        vc = new ViewController();
    }

    public String runScript(String functionName, Object[] functionParams){
        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        rhino.setOptimizationLevel(-1);
        try{
            Scriptable scope = rhino.initStandardObjects();

            ScriptableObject.putProperty(scope,"javaContext", org.mozilla.javascript.Context.javaToJS(this,scope));
            ScriptableObject.putProperty(scope,"javaLoader", org.mozilla.javascript.Context.javaToJS(clazz.getClassLoader(),scope));

            Object x = rhino.evaluateString(scope, allFunctions, clazz.getSimpleName(), 1, null);

//            Function function = (Function) scope.get(functionName,scope);
//
//            Object result = function.call(rhino,scope,scope,functionParams);

            return  org.mozilla.javascript.Context.toString(x);

        }finally {
            org.mozilla.javascript.Context.exit();
        }
    }

    public int  api_getBaseViewWidth(){
        return CalculateUtils.getWindowWidth(windowManager);
    }

    public int api_getBaseViewHeight(){
        return  CalculateUtils.getWindowHeight(windowManager);
    }

    public void api_log(String msg){
        Log.i("jzy111", "api_log: "+msg);
    }

    public void api_commitVC4Android(String jsonStr){
        Gson gson = new Gson();
        vc = gson.fromJson(jsonStr, ViewController.class);
        setViewController(vc);
        Log.i("jzy111", "api_commitVC4Android: "+jsonStr);
    }

    private void setViewController(ViewController vc){
        try{
            Log.i("jzy111", "setViewController: "+mContext);
            vrtView = new VrtViewParent(mContext,vc.getView());
            vrtRenderListener.renderSuccess(vrtView);
        }catch (Exception e){
            Log.e("JsEngine", "setViewController: ",e );
        }

    }
}
