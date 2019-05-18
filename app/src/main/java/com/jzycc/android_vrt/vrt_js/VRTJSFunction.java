package com.jzycc.android_vrt.vrt_js;

import java.util.Map;

public interface VRTJSFunction {

    void api_log(Object msg);

    void api_commitVC4Android(String jsonStr);

    void api_refreshView(String refreshMsg);

    void api_httpRequest(String  jsObjectJsonStr);

    Map<String,Object> api_getThisNavigationComp();

    void api_pushUrlWithParam(String jsonStrt);

    Object api_getPushedParam();

    void api_popThis();

    void api_refreshListData(String jsonStr);
}
