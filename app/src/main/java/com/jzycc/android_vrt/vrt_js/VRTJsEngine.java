package com.jzycc.android_vrt.vrt_js;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.jzycc.android_vrt.model.ViewController;
import com.jzycc.android_vrt.utils.CalculateUtils;
import com.jzycc.android_vrt.vrt.VrtViewParent;
import com.jzycc.android_vrt.vrt.manager.VRTSdkManager;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * author Jzy(Xiaohuntun)
 * date 18-9-26
 */
public class VRTJsEngine {
    private Context mContext;
    private Class clazz;
    private String allFunctions = "";
    public final static String API_COMMITVC = "api_commitVC";
    private WindowManager windowManager;
    private ViewController vc;
    private View vrtView;
    private VRTRenderListener VRTRenderListener;
    private VRTSdkManager vrtSdkManager;
    private int viewWidth;
    private int viewHeight;
    private boolean isHasParent = false;
    private boolean isRender = false;

    private String vcJsCode = Constant.JAVA_CALL_JS_FUNCTION;

    public VRTJsEngine(Activity mContext) {
        this.mContext = mContext;
        this.clazz = VRTJsEngine.class;
        this.VRTRenderListener = (VRTRenderListener)mContext;
        this.vrtSdkManager = VRTSdkManager.getInstance();
        windowManager = mContext.getWindowManager();
        initJSStr();
    }

    private void initJSStr(){
        allFunctions = "var ScriptAPI = java.lang.Class.forName(\"" + VRTJsEngine.class.getName() + "\", true, javaLoader);\n" +
                Constant.JS_CODE_4ANDROID+
                Constant.JAVA_CALL_JS_FUNCTION+
                Constant.JS_CODE_TEST;
        vc = new ViewController();
    }

    public void requestRenderInParent(final ViewGroup parent){
        isHasParent = true;
        parent.post(new Runnable() {
            @Override
            public void run() {
                viewWidth = parent.getMeasuredWidth();
                viewHeight = parent.getMeasuredHeight();
                requestRender();
            }
        });
    }

    public void requestRender(){
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

            isRender = true;

        }finally {
            org.mozilla.javascript.Context.exit();
        }
    }

    public int  api_getBaseViewWidth(){
        if(isHasParent){
            return viewWidth;
        }else {
            return CalculateUtils.getWindowWidth(windowManager);
        }
    }

    public int api_getBaseViewHeight(){
        if(isHasParent){
            return viewHeight;
        }else {
            return CalculateUtils.getWindowHeight(windowManager);
        }
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

    public void api_refreshView(String array){
        Log.i("jzy111", "api_refreshView: "+array);

    }

    public void api_httpRequest(String  jsReponse){
        Log.i("jzy111", "api_httpRequest: "+jsReponse);
    }

    public void api_addViewClick(String vrtId){
        vrtSdkManager.getVRTClickableManager().getVrtIds().add(vrtId);
        Log.i("jzy111", "api_addViewClick: "+vrtId);
    }

    private void setViewController(ViewController vc){
        try{
            vrtView = new VrtViewParent(mContext,vc.getView());
            VRTRenderListener.renderSuccess(vrtView);
        }catch (Exception e){
            Log.e("VRTJsEngine", "setViewController: ",e );
        }

    }
}
