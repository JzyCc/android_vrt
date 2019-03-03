/**
 * Created by JzyCc on 2018/10/3.
 */


//api commit
var method_Api_commitVC4Android = ScriptAPI.getMethod("api_commitVC4Android",[java.lang.String])
function api_commitVC() {

    var vc1 = new ViewController()

    vc1 = mainVC

    getVcForAndroid([vc1.view])

    var str = JSON.stringify(vc1)

    method_Api_commitVC4Android.invoke(javaContext,str);

}

function getVcForAndroid(v){
    if(v!=null){
        for(var i =0;i < v.length; i++)
        {
            v[i].superView = null;
            v[i].vrt_layout.masterView = null;
            if(v[i]._clsName == "List")
            {
                for(var key in v[i]._cell)
                {
                    var cell = v[i]._cell[key];
                    if(key == "_clsName" || cell._clsName != "Cell")
                        continue;
                    cell.superView = null;
                    cell.vrt_layout.masterView = null;
                    getVcForAndroid(cell.subViews)
                }
            }
            getVcForAndroid(v[i].subViews);
        }
    }
}

//log
var method_Api_log = ScriptAPI.getMethod("api_log",[java.lang.String])
function api_log(msg){
    method_Api_log.invoke(javaContext,msg)
}

//获取本地高
var method_Api_getBaseViewWidth = ScriptAPI.getMethod("api_getBaseViewWidth")
function api_getBaseViewWidth(){
    return method_Api_getBaseViewWidth.invoke(javaContext)
}

//获取本地宽
var method_Api_getBaseViewHeight = ScriptAPI.getMethod("api_getBaseViewHeight")
function api_getBaseViewHeight(){
    return method_Api_getBaseViewHeight.invoke(javaContext)
}

//获取设备系统名
function api_platform(){
    return "Android"
}

//刷新view
var method_Api_refreshView = ScriptAPI.getMethod("api_refreshView",[java.lang.String])
function api_refreshView(vrtId, key, newValue){
    var refreshModel = new RefreshModel();
    refreshModel._vrtId = vrtId;
    refreshModel._key = key;
    refreshModel._newValue = newValue;
    var jsonStr = JSON.stringify(refreshModel)
    method_Api_refreshView.invoke(javaContext,jsonStr)
}

function RefreshModel{
    var _vrtId;
    var _key;
    var _newValue;
}

//网络请求
var method_Api_httpRequest = ScriptAPI.getMethod("api_httpRequest",[java.lang.String])
function api_httpRequest(jsObject){
    var jsonStr = JSON.stringify(jsObject)
    method_Api_httpRequest.invoke(javaContext,jsonStr)
}

//点击回调
var method_Api_addViewClick = ScriptAPI.getMethod("api_addViewClick",[java.lang.String])
function api_addViewClick(vrtId){
    method_Api_addViewClick.invoke(javaContext,vrtId+"")
}



