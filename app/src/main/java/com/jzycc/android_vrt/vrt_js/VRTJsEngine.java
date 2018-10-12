package com.jzycc.android_vrt.vrt_js;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.jzycc.android_vrt.model.ViewController;
import com.jzycc.android_vrt.model.VrtRefreshMsg;
import com.jzycc.android_vrt.model.VrtRequestBody;
import com.jzycc.android_vrt.utils.CalculateUtils;
import com.jzycc.android_vrt.utils.FileUtils;
import com.jzycc.android_vrt.vrt.VrtViewParent;
import com.jzycc.android_vrt.vrt.manager.VRTSdkManager;
import com.jzycc.android_vrt.vrt.manager.VRTViewManager;
import com.jzycc.android_vrt.vrt_js.constant.Constant;
import com.jzycc.android_vrt.vrt_js.constant.FunctionName;
import com.jzycc.android_vrt.vrt_js.manager.VRTJsManager;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.File;
import java.util.HashMap;

/**
 * author Jzy(Xiaohuntun)
 * date 18-9-26
 */
public class VRTJsEngine implements VRTLifeCycle{

    private static final String TAG = "VRTJsEngine";

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
    private VRTViewManager vrtViewManager;
    private int viewHeight;
    private boolean isHasParent = false;
    private boolean isRender = false;
    private org.mozilla.javascript.Context rhino;
    private Scriptable scope;
    private String vcJsCode = Constant.JAVA_CALL_JS_FUNCTION;
    private Gson gson;

    private VRTJsManager vrtJsManager;

    public VRTJsEngine(Activity mContext) {
        this.mContext = mContext;
        this.clazz = VRTJsEngine.class;
        this.VRTRenderListener = (VRTRenderListener)mContext;
        this.vrtSdkManager = VRTSdkManager.getInstance();
        this.vrtViewManager = new VRTViewManager(new HashMap<String, View>());
        this.vrtJsManager = new VRTJsManager(mContext,this);
        windowManager = mContext.getWindowManager();
        this.gson = new Gson();
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
                requestRender(allFunctions);
            }
        });
    }

    public void requestRender(String jsCode){
        rhino = org.mozilla.javascript.Context.enter();
        rhino.setOptimizationLevel(-1);

        try{
            scope = rhino.initStandardObjects();

            ScriptableObject.putProperty(scope,"javaContext", org.mozilla.javascript.Context.javaToJS(this,scope));
            ScriptableObject.putProperty(scope,"javaLoader", org.mozilla.javascript.Context.javaToJS(clazz.getClassLoader(),scope));
            Object x = rhino.evaluateString(scope, allFunctions, clazz.getSimpleName(), 1, null);
            isRender = true;
            callBackViewDidLoad();
        }finally {

        }
    }

    /**
     * @param fileName
     */
    public void requestRenderByFile(String fileName){
        String vrtJsCode = Constant.JS_CODE_TEST;
        //String vrtJsCode = FileUtils.FiletoString(mContext, fileName);
        if(TextUtils.isEmpty(vrtJsCode)){
            Log.w(TAG, "requestRenderByFile: the file does not exist or get file faield");
        }else {
            String vrtjsAndroidFunction = FileUtils.FiletoString(mContext,"VRTJSAndroidFunction.js");
            String vrtJsFrameworkCode = FileUtils.FiletoString(mContext,"VRTJSFramework.js");

            String result = "var ScriptAPI = java.lang.Class.forName(\"" + VRTJsEngine.class.getName() + "\", true, javaLoader);\n" +
                    vrtjsAndroidFunction+
                    vrtJsFrameworkCode+
                    vrtJsCode;

            requestRender(result);
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
        vc = gson.fromJson(jsonStr, ViewController.class);
        setViewController(vc);
        Log.i("jzy111", "api_commitVC4Android: "+jsonStr);
    }

    public void api_refreshView(String refreshMsg){
        if(isRender){
            VrtRefreshMsg vrtRefreshMsg = gson.fromJson(refreshMsg,VrtRefreshMsg.class);
            vrtJsManager.refreshView(vrtRefreshMsg.get_vrtId(),vrtRefreshMsg.get_key(),vrtRefreshMsg.get_newValue());
        }

    }

    public void api_httpRequest(String  jsObjectJsonStr){
        VrtRequestBody vrtRequestBody = gson.fromJson(jsObjectJsonStr, VrtRequestBody.class);
        vrtJsManager.callbackHttpRequestForJs(vrtRequestBody.getUrl(),vrtRequestBody.get_param());
        Log.i("jzy111", "api_httpRequest: "+vrtRequestBody.get_param());
    }

    public void api_addViewClick(String vrtId){
        vrtJsManager.getClickableViewVrtIds().add(vrtId);
        Log.i("jzy111", "api_addViewClick: "+vrtId);
    }

    private void setViewController(ViewController vc){
        try{
            vrtView = new VrtViewParent(mContext,vrtJsManager,vc.getView());
            VRTRenderListener.renderSuccess(vrtView);
        }catch (Exception e){
            Log.e("VRTJsEngine", "setViewController: ",e );
        }

    }

    public void callFunction(String functionName,Object[] functionParams){
        Function function = (Function) scope.get(functionName,scope);
        Object result = function.call(rhino,scope,scope,functionParams);
    }

    @Override
    public void onStart() {
        if(isRender){
            callFunction(FunctionName.API_RESPONSE_BASIC_CALL_BACK,new Object[]{(vc.get_vrtId()+"CallBackViewWillAppear")});
        }
    }

    @Override
    public void onResume() {
        if(isRender){
            callFunction(FunctionName.API_RESPONSE_BASIC_CALL_BACK,new Object[]{(vc.get_vrtId()+"CallBackViewDidAppear")});
        }
    }

    @Override
    public void callBackViewDidLoad() {
        if(isRender){
            callFunction(FunctionName.API_RESPONSE_BASIC_CALL_BACK,new Object[]{(vc.get_vrtId()+"CallBackViewDidLoad")});
        }
    }

    @Override
    public void onPause() {
        if(isRender){
            callFunction(FunctionName.API_RESPONSE_BASIC_CALL_BACK,new Object[]{(vc.get_vrtId()+"CallBackViewWillDisappear")});
        }
    }

    @Override
    public void onDestroy() {
        org.mozilla.javascript.Context.exit();
    }
}
