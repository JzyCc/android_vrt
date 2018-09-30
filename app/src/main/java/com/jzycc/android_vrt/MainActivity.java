package com.jzycc.android_vrt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class MainActivity extends AppCompatActivity {


    private static final String JS_CALL_JAVA_FUNCTION =
            "var ScriptAPI = java.lang.Class.forName(\"" + MainActivity.class.getName() + "\", true, javaLoader);" +
                    "var methodRead = ScriptAPI.getMethod(\"jsCallJava\", [java.lang.String]);" +
                    "function jsCallJava(url) {return methodRead.invoke(javaContext, url);}" +
                    "function Test(url){ return jsCallJava(url); }";

    private static final String JS_HEADER = "var ScriptAPI = java.lang.Class.forName(\"" + MainActivity.class.getName() + "\", true, javaLoader);" +
            "var methodRead = ScriptAPI.getMethod(\"native_commitVC\", [java.lang.Object]);\n"
            +Constant.JAVA_CALL_JS_FUNCTION;


    private static final String JS_TEST = "function Ob(){\n" +
            "    this.x = 0;\n" +
            "    this.y = 1;\n" +
            "\n" +
            "    this.func = function name(params) {\n" +
            "        return 1;\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "var ob = new Ob();\n" +
            "\n" +
            "var i = eval(ob);\n" +
            "\n" +
            "var str = JSON.stringify(ob)\n" +
            "\n" +
            "\n" +
            "function getOb(){\n" +
            "\n" +
            "    return ob;\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //text1.setText(runScript(JAVA_CALL_JS_FUNCTION, "Test", new String[] {}).toString());


        //text2.setText(runScript(JS_CALL_JAVA_FUNCTION, "Test",new Object[]{"JzyCc"}).toString());

        //Vrc vrc = (Vrc) runScript(JAVA_CALL_JS_FUNCTION,"getVc",new Object[]{});


        Object runObject=runScript(JS_TEST,"getOb",new Object[]{});
        Log.i("jzy111", "onCreate: "+runObject);

    }


    private Object runScript(String js, String functionName, Object[] functionParams){
        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        rhino.setOptimizationLevel(-1);

        try{
            Scriptable scope = rhino.initStandardObjects();

            ScriptableObject.putProperty(scope,"javaContext", org.mozilla.javascript.Context.javaToJS(MainActivity.this,scope));
            ScriptableObject.putProperty(scope,"javaLoader", org.mozilla.javascript.Context.javaToJS(MainActivity.class.getClassLoader(),scope));

            Object x = rhino.evaluateString(scope, js, "MainActivity", 1, null);

            Function function = (Function) scope.get(functionName,scope);

            Object result = function.call(rhino,scope,scope,functionParams);


            Log.i("jzy111", "runScript: "+result);


            return (Object) org.mozilla.javascript.Context.toObject(result,scope);

        }finally {
            org.mozilla.javascript.Context.exit();
        }
    }

    public static String jsCallJava(String url) {
        return url.toString();
    }

    public static Object native_commitVC(Object vc){
        Log.i("jzy111", "native_commitVC: "+vc);
        return vc;
    }

    public static Ob getOb(Ob ob){
        return ob;
    }
}
