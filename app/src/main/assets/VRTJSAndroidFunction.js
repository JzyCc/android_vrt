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
    return method_Api_getBaseViewWidth.invoke(javaContext)*1;
}

//获取本地宽
var method_Api_getBaseViewHeight = ScriptAPI.getMethod("api_getBaseViewHeight")
function api_getBaseViewHeight(){
    return method_Api_getBaseViewHeight.invoke(javaContext)*1;
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

function RefreshModel(){
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

//无必要的网络请求 ：）
var method_Api_httpRequest_iku = ScriptAPI.getMethod("api_httpRequest_iKu",[java.lang.String])
function api_httpRequest_iKu(jsObject){
    var jsonStr = JSON.stringify(jsObject)
    method_Api_httpRequest_iku.invoke(javaContext,jsonStr)
}

//点击回调
var method_Api_addViewClick = ScriptAPI.getMethod("api_addViewClick",[java.lang.String])
function api_addViewClick(vrtId){
    method_Api_addViewClick.invoke(javaContext,vrtId+"")
}

//导航栏

var method_Api_getThisNavigationComp = ScriptAPI.getMethod("api_getThisNavigationComp")
function api_getThisNavigationComp(){
    return method_Api_getThisNavigationComp.invoke(javaContext)
}

//跳转
var method_Api_pushUrlWithParam = ScriptAPI.getMethod("api_pushUrlWithParam",[java.lang.String])
function api_pushUrlWithParam(url, param){
    var pushModel = new PushModel()
    pushModel.url = url
    pushModel.param = param
    var jsonStr = JSON.stringify(pushModel)
    method_Api_pushUrlWithParam.invoke(javaContext,jsonStr)
}


function PushModel(){
    var url
    var param
}

//返回上一个页面
var method_Api_popThis = ScriptAPI.getMethod("api_popThis")
function api_popThis(){
    method_Api_popThis.invoke(javaContext)
}

//获取跳转参数
var method_Api_getPushedParam = ScriptAPI.getMethod("api_getPushedParam")
function api_getPushedParam(){
    return method_Api_getPushedParam.invoke(javaContext)
}

//显示弹窗
function api_showAlert(){

}

//动画
function api_dispatchWithAnimation(duration,func){

}

//更新list
var method_Api_refreshListData = ScriptAPI.getMethod("api_refreshListData",[java.lang.String])
function api_refreshListData(_vrtId,numberOfSections,numberOfRowsAtSection){
   var section = new Section()
    section.numberOfSections = numberOfSections;
    section.numberOfRowsAtSection = numberOfRowsAtSection;
    section._vrtId = _vrtId;

    var str = JSON.stringify(section)

    method_Api_refreshListData(javaContext,str)
}
function Section(){
        var _vrtId
        var numberOfSections
        var numberOfRowsAtSection
}

var method_Api_commitCell = ScriptAPI.getMethod("api_commitCell",[java.lang.String])
function api_commitCell(cell){
    var vc1 = new View()

    vc1 = cell

    getVcForAndroid([vc1])

    var str = JSON.stringify(vc1)

    method_Api_commitCell.invoke(javaContext,str)
}


