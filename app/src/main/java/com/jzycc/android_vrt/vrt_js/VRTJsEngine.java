package com.jzycc.android_vrt.vrt_js;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.jzycc.android_vrt.model.ViewControllerData;
import com.jzycc.android_vrt.model.VrtRefreshMsg;
import com.jzycc.android_vrt.model.VrtRequestBody;
import com.jzycc.android_vrt.utils.CalculateUtils;
import com.jzycc.android_vrt.utils.FileUtils;
import com.jzycc.android_vrt.vrt.ViewController;
import com.jzycc.android_vrt.vrt.VrtViewParent;
import com.jzycc.android_vrt.vrt.manager.VRTSdkManager;
import com.jzycc.android_vrt.vrt.manager.VRTViewManager;
import com.jzycc.android_vrt.vrt_js.constant.Constant;
import com.jzycc.android_vrt.vrt_js.constant.FunctionName;
import com.jzycc.android_vrt.vrt_js.manager.VRTJsManager;
import com.jzycc.android_vrt.vrt_js.manager.VRTOkHttpManager;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    private ViewControllerData vc;
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
    private VRTOkHttpManager okHttpManager = VRTOkHttpManager.getInstance();
    private Activity activity;
    private VRTJsManager vrtJsManager;
    private String url = "";

    public VRTJsEngine(Activity mContext) {
        this.mContext = mContext;
        this.activity = mContext;
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
        vc = new ViewControllerData();
    }

    private void requestRenderInParent(final String jsCode, final ViewGroup parent){
        isHasParent = true;
        parent.post(new Runnable() {
            @Override
            public void run() {
                viewWidth = parent.getMeasuredWidth();
                viewHeight = parent.getMeasuredHeight();
                String vrtjsAndroidFunction = FileUtils.FiletoString(mContext,"VRTJSAndroidFunction.js");
                String vrtJsFrameworkCode = FileUtils.FiletoString(mContext,"VRTJSFramework.js");

                String result = "var ScriptAPI = java.lang.Class.forName(\"" + VRTJsEngine.class.getName() + "\", true, javaLoader);\n" +
                        vrtjsAndroidFunction+
                        vrtJsFrameworkCode+
                        jsCode;

                requestRender(result);
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
            Object x = rhino.evaluateString(scope, jsCode, clazz.getSimpleName(), 1, null);
            isRender = true;
            callBackViewDidLoad();
        }finally {

        }
    }

    /**
     * @param fileName
     */
    public void requestRenderByFile(String fileName){
        String vrtJsCode = FileUtils.FiletoString(mContext, fileName);
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
    
    public void requestRenderByUrl(String url, final ViewGroup parent){
        this.url = url;
        okHttpManager.getClient().newCall(VRTOkHttpManager.getJsByUrl(url)).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: ",e );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                
                final String jsCode = response.body().string();
                if(!TextUtils.isEmpty(jsCode)){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(parent != null){
                                requestRenderInParent(jsCode,parent);
                            }else {
                                requestRender(jsCode);
                            }
                        }
                    });
                }else {
                    Log.e(TAG, "onResponse: no jscode");
                }
            }
        }); 
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
    
    public int api_getBaseViewWidth(){
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
        vc = gson.fromJson(jsonStr, ViewControllerData.class);
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

    public void api_httpRequest_iKu(String jsObjectJsonStr){
        Log.i(TAG, "api_httpRequest_iKu: "+jsObjectJsonStr);
        VrtRequestBody vrtRequestBody = gson.fromJson(jsObjectJsonStr, VrtRequestBody.class);
        Log.i(TAG, "api_httpRequest_iKu: "+vrtRequestBody);
    }

    public void api_addViewClick(String vrtId){
        vrtJsManager.getClickableViewVrtIds().add(vrtId);
        Log.i("jzy111", "api_addViewClick: "+vrtId);
    }

    private void setViewController(ViewControllerData vc){
        try{
            ViewController viewController = new ViewController(mContext,vrtJsManager,vc);
            vrtView = viewController.getVRTRenderView();
            VRTRenderListener.renderSuccess(vrtView);
        }catch (Exception e){
            Log.e("VRTJsEngine", "setViewController: ",e );
        }

    }

    public void callFunction(String functionName,Object[] functionParams){
        Function function = (Function) scope.get(functionName,scope);
        function.call(rhino,scope,scope,functionParams);
    }

    public Map<String,Object> api_getThisNavigationComp(){
        Map<String, Object> map = new HashMap<>();
        map.put("url",url);
        return  map;
    }

    public void api_pushUrlWithParam(String jsonStrt){
        Map param = gson.fromJson(jsonStrt,Map.class);
        Log.i(TAG, "api_pushUrlWithParam: "+param);
    }

    public void api_getPushedParam(){

    }

    public void api_popThis(){

    }

    public void api_refreshListData(String jsonStr){
        Log.i(TAG, "api_refreshListData: "+jsonStr);
    }

    public void api_commitCell(String jsonStr){
        Log.i(TAG, "api_commitCell: "+jsonStr);
    }
}
