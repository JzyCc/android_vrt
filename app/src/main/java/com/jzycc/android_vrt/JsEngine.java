package com.jzycc.android_vrt;

/**
 * author Jzy(Xiaohuntun)
 * date 18-9-26
 */
public class JsEngine {
    private Class clazz;
    private String allFunctions = "";

    public JsEngine(Class clazz) {
        this.clazz = clazz;
    }

    private void initJSStr(){
        allFunctions = "var ScriptAPI = java.lang.Class.forName(\"" + MainActivity.class.getName() + "\", true, javaLoader);" +
                "var methodRead = ScriptAPI.getMethod(\"jsCallJava\", [java.lang.String]);";
    }



}
