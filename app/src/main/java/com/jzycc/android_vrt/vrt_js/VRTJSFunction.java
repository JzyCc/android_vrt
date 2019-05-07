package com.jzycc.android_vrt.vrt_js;

import java.util.Map;

public interface VRTJSFunction {
    int api_getBaseViewWidth();

    int api_getBaseViewHeight();

    void api_log(String msg);

    void api_commitVC4Android(String jsonStr);

    void api_refreshView(String refreshMsg);

    void api_httpRequest(String  jsObjectJsonStr);

    void api_httpRequest_iKu(String jsObjectJsonStr);

    void api_addViewClick(String vrtId);

    Map<String,Object> api_getThisNavigationComp();

    void api_pushUrlWithParam(String jsonStrt);

    void api_getPushedParam();

    void api_popThis();

    void api_refreshListData(String jsonStr);
}
