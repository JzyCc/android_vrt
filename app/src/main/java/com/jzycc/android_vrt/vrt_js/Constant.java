package com.jzycc.android_vrt.vrt_js;

/**
 * author Jzy(Xiaohuntun)
 * date 18-9-27
 */
public class Constant {
    public static final String JAVA_CALL_JS_FUNCTION = "Object.prototype._clsName = null;\n" +
            "\n" +
            "var vrtViewIndex = 0;\n" +
            "\n" +
            "function Vec2(x,y)\n" +
            "{\n" +
            "    this.x = x;\n" +
            "    this.y = y;\n" +
            "}\n" +
            "\n" +
            "function Vec4(x,y,z,w)\n" +
            "{\n" +
            "    this.x = x;\n" +
            "    this.y = y;\n" +
            "    this.z = z;\n" +
            "    this.w = w;\n" +
            "}\n" +
            "\n" +
            "var redColor = new Vec4(1,0,0,1);\n" +
            "var greenColor = new Vec4(0,1,0,1);\n" +
            "var blackColor = new Vec4(0,0,0,1);\n" +
            "var whiteColor = new Vec4(1,1,1,1);\n" +
            "var clearColor = new Vec4(0,0,0,0);\n" +
            "\n" +
            "function createColorWithRGBA(r,g,b,a)\n" +
            "{\n" +
            "    return new Vec4(r,g,b,a);\n" +
            "}\n" +
            "\n" +
            "\n" +
            "var vrtRectZero = new Vec4(0,0,0,0);\n" +
            "\n" +
            "function createFrameWithRect(x,y,width,height)\n" +
            "{\n" +
            "    return new Vec4(x,y,width,height);\n" +
            "}\n" +
            "\n" +
            "var _vrt_kvo_responserCache = {};\n" +
            "var _vrt_kvo_targetCache = {};\n" +
            "\n" +
            "function vrt_kvo_bind(target,key,func)\n" +
            "{\n" +
            "    var hashKey = key + \" KVO \" + target._vrtId;\n" +
            "    if(_vrt_kvo_targetCache[hashKey] == undefined)\n" +
            "    {\n" +
            "        var vrt_kvo_target = new Object();\n" +
            "        vrt_kvo_target.target = target;\n" +
            "        vrt_kvo_target.targetValue = target[key];\n" +
            "        _vrt_kvo_targetCache[hashKey] = vrt_kvo_target;\n" +
            "    }\n" +
            "    \n" +
            "    var vrt_kvo_responser = new Object();\n" +
            "    vrt_kvo_responser.callBack = func;\n" +
            "    \n" +
            "    if(_vrt_kvo_responserCache[hashKey] == undefined)\n" +
            "    {\n" +
            "        _vrt_kvo_responserCache[hashKey] = new Array();\n" +
            "        \n" +
            "        Object.defineProperty(target,key,{\n" +
            "                              get : function(){\n" +
            "                              return vrt_kvo_target.targetValue;\n" +
            "                              },\n" +
            "                              set : function(value){\n" +
            "                              var tmpArray;\n" +
            "                              if(_vrt_kvo_responserCache[hashKey] != undefined)\n" +
            "                              {\n" +
            "                              tmpArray = _vrt_kvo_responserCache[hashKey];\n" +
            "                              tmpArray.forEach(element => {\n" +
            "                                               element.callBack(vrt_kvo_target.target,key,vrt_kvo_target.targetValue,value);\n" +
            "                                               });\n" +
            "                              }\n" +
            "                              vrt_kvo_target.targetValue = value;\n" +
            "                              }\n" +
            "                              });\n" +
            "    }\n" +
            "    \n" +
            "    _vrt_kvo_responserCache[hashKey].push(vrt_kvo_responser);\n" +
            "}\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "var _vrt_callback_cache = {};\n" +
            "\n" +
            "function addCallBack(key,func)\n" +
            "{\n" +
            "    _vrt_callback_cache[key] = func;\n" +
            "}\n" +
            "\n" +
            "function _api_responseBasicCallBack(key)\n" +
            "{\n" +
            "    var func = _vrt_callback_cache[key];\n" +
            "    if(func != null || func != undefined)\n" +
            "        func();\n" +
            "}\n" +
            "\n" +
            "function _api_responseListDidSelectRow(key,section,row)\n" +
            "{\n" +
            "    var func = _vrt_callback_cache[key];\n" +
            "    if(func != null || func != undefined)\n" +
            "        func(section,row);\n" +
            "}\n" +
            "\n" +
            "function _api_responseTextFieldReturn(key,text)\n" +
            "{\n" +
            "    var func = _vrt_callback_cache[key];\n" +
            "    if(func != null || func != undefined)\n" +
            "        func(text);\n" +
            "}\n" +
            "\n" +
            "\n" +
            "/**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**/\n" +
            "function vrt_layout(){\n" +
            "    \n" +
            "    this.masterView = null;\n" +
            "    this.pendCenterX = null;\n" +
            "    this.pendCenterY = null;\n" +
            "\n" +
            "    this._resolvePendCenterX = function()\n" +
            "    {\n" +
            "        if(this.pendCenterX != null)\n" +
            "        {\n" +
            "            this.masterView._x = this.pendCenterX - this.masterView._width/2.0;\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    this._resolvePendCenterY = function()\n" +
            "    {\n" +
            "        if(this.pendCenterY != null)\n" +
            "        {\n" +
            "            this.masterView._y = this.pendCenterY - this.masterView._height/2.0;\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    this.heightIs = function(height){\n" +
            "        this.masterView._height = height;\n" +
            "        this._resolvePendCenterY();\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    this.widthIs = function(width){\n" +
            "        this.masterView._width = width;\n" +
            "        this._resolvePendCenterX();\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    this.widthEqualToHeight = function(){\n" +
            "        this.masterView._width = this.masterView.height();\n" +
            "        this._resolvePendCenterX();\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    this.heightEqualToWidth = function(){\n" +
            "        this.masterView._height = this.masterView.width();\n" +
            "        this._resolvePendCenterY();\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    this.heightRatioToView = function(view,scale){\n" +
            "        if(view == null)\n" +
            "            view = this.masterView.superView;\n" +
            "        this.masterView._height = view.height() * scale;\n" +
            "        this._resolvePendCenterY();\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    this.widthRatioToView = function(view,scale){\n" +
            "        if(view == null)\n" +
            "            view = this.masterView.superView;\n" +
            "        this.masterView._width = view.width() * scale;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    this.centerYEqualToView = function(view)\n" +
            "    {\n" +
            "        if(view == null)\n" +
            "            view = this.masterView.superView;\n" +
            "        if(this.masterView.superView == view)\n" +
            "            this.pendCenterY = view.height()/2.0;\n" +
            "        else \n" +
            "            this.pendCenterY = view.center().y;\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.centerYIs = function(y)\n" +
            "    {\n" +
            "        this.pendCenterY = y;\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.topEqualToView = function(view){\n" +
            "        if(view == null)\n" +
            "            view = this.masterView.superView;\n" +
            "        this.pendCenterY = null;\n" +
            "        if(this.masterView.superView == view)\n" +
            "            this.masterView._y = 0;\n" +
            "        else\n" +
            "            this.masterView._y = view._y;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    this.topSpaceToView = function(view,space){\n" +
            "        if(view == null)\n" +
            "            view = this.masterView.superView;\n" +
            "        this.pendCenterY = null;\n" +
            "        if(this.masterView.superView == view)\n" +
            "            this.masterView._y = space;\n" +
            "        else\n" +
            "            this.masterView._y = space + view.bottom();\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.centerXEqualToView = function(view)\n" +
            "    {\n" +
            "        if(view == null)\n" +
            "            view = this.masterView.superView;\n" +
            "        if(this.masterView.superView == view)\n" +
            "            this.pendCenterX = view.width()/2.0;\n" +
            "        else \n" +
            "            this.pendCenterX = view.center().x;\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.centerXIs = function(x)\n" +
            "    {\n" +
            "        this.pendCenterX = x;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    this.leftEqualToView = function(view){\n" +
            "        if(view == null)\n" +
            "            view = this.masterView.superView;\n" +
            "        this.pendCenterX = null;\n" +
            "        if(this.masterView.superView == view)\n" +
            "            this.masterView._x = 0;\n" +
            "        else\n" +
            "            this.masterView._x = view.left();\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    this.leftSpaceToView = function(view,space){\n" +
            "        if(view == null)\n" +
            "            view = this.masterView.superView;\n" +
            "        this.pendCenterX = null;\n" +
            "        if(this.masterView.superView == view)\n" +
            "            this.masterView._x = space;\n" +
            "        else\n" +
            "            this.masterView._x = view.right() + space;\n" +
            "        return this;\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "\n" +
            "function View()\n" +
            "{\n" +
            "    this._vrtId = vrtViewIndex++;\n" +
            "    \n" +
            "    this._x = null;\n" +
            "    this._y = null;\n" +
            "    this._height = null;\n" +
            "    this._width = null;\n" +
            "    \n" +
            "    this.superView = null;\n" +
            "    this._clsName = 'View';\n" +
            "    this.subViews = new Array();\n" +
            "    this.backgroundColor = clearColor;\n" +
            "    this.vrt_layout = new vrt_layout();\n" +
            "    this.vrt_layout.masterView = this;\n" +
            "\n" +
            "    this.cornerRadius = 0;\n" +
            "    \n" +
            "    vrt_kvo_bind(this,'backgroundColor',function(target,key,oldVlaue,newValue){\n" +
            "//                  api_refreshView(target._vrtId,key,value);\n" +
            "                 //        console.log(\"KVO \" + key + \" old \" + oldVlaue + \" new \" + newValue);\n" +
            "                 });\n" +
            "    \n" +
            "    vrt_kvo_bind(this,'_height',function(target,key,oldVlaue,newValue){\n" +
            "                //  api_refreshView(target._vrtId,key,value);\n" +
            "                 //        console.log(\"KVO \" + key + \" old \" + oldVlaue + \" new \" + newValue);\n" +
            "                 });\n" +
            "    \n" +
            "    \n" +
            "    this._setFrame = function(x,y,width,height)\n" +
            "    {\n" +
            "        this._x = x;\n" +
            "        this._y = y;\n" +
            "        this._width = width;\n" +
            "        this._height = height;\n" +
            "    }\n" +
            "\n" +
            "    this.addClick = function(view,func)\n" +
            "    {\n" +
            "        addCallBack(view._vrtId,func);\n" +
            "    }\n" +
            "    \n" +
            "    this.addSubView = function(subView){\n" +
            "        if(subView == this || subView == null)\n" +
            "        {\n" +
            "            return;\n" +
            "        }\n" +
            "        this.subViews.push(subView);\n" +
            "        subView.superView = this;\n" +
            "    }\n" +
            "    \n" +
            "    this.top = function()\n" +
            "    {\n" +
            "        if(this._y == null)\n" +
            "            this._y = this.vrt_layout._getTop();\n" +
            "        return this._y;\n" +
            "    }\n" +
            "    \n" +
            "    this.left = function()\n" +
            "    {\n" +
            "        if(this._x == null)\n" +
            "            this._x = this.vrt_layout._getLeft();\n" +
            "        return this._x;\n" +
            "    }\n" +
            "    \n" +
            "    this.right = function()\n" +
            "    {\n" +
            "        return this.left() + this.width();\n" +
            "    }\n" +
            "    \n" +
            "    this.bottom = function()\n" +
            "    {\n" +
            "        return this.top() + this.height();\n" +
            "    }\n" +
            "    \n" +
            "    this.height = function()\n" +
            "    {\n" +
            "        if(this._height == null)\n" +
            "            this._height = this.vrt_layout._getHeight();\n" +
            "        return this._height;\n" +
            "    }\n" +
            "    \n" +
            "    this.width = function()\n" +
            "    {\n" +
            "        if(this._width == null)\n" +
            "            this._width = this.vrt_layout._getWidth();\n" +
            "        return this._width;\n" +
            "    }\n" +
            "    \n" +
            "    this.center = function()\n" +
            "    {\n" +
            "        return new Vec2((this.left() + this.right())/2.0 , (this.top() + this.bottom())/2.0);\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "function ViewController()\n" +
            "{\n" +
            "    this._vrtId = -1;\n" +
            "    this.view = new View();\n" +
            "    this.view._setFrame(0,0,375,554);//667 full screen  554 with Navigation Bar & Tab Bar\n" +
            "    this.view.backgroundColor = whiteColor;\n" +
            "    this.view.superView = null;\n" +
            "    this._clsName = 'ViewController';\n" +
            "    this.title = \"title\";\n" +
            "    \n" +
            "    this.addCallBackViewDidLoad = function(func){\n" +
            "        addCallBack(this._vrtId + \"CallBackViewDidLoad\",func);\n" +
            "    }\n" +
            "    \n" +
            "    this.addCallBackViewWillAppear = function(func){\n" +
            "        addCallBack(this._vrtId + \"CallBackViewWillAppear\",func);\n" +
            "    }\n" +
            "    \n" +
            "    this.addCallBackViewDidAppear = function(func){\n" +
            "        addCallBack(this._vrtId + \"CallBackViewDidAppear\",func);\n" +
            "    }\n" +
            "    \n" +
            "    this.addCallBackViewWillDisappear = function(func){\n" +
            "        addCallBack(this._vrtId + \"CallBackViewWillDisappear\",func);\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "function Label()\n" +
            "{\n" +
            "    View.call(this);\n" +
            "    this.control = null;\n" +
            "    this._clsName = 'Label';\n" +
            "    this.text = '';\n" +
            "    this.fontSize = 12;\n" +
            "    this.textColor = blackColor;\n" +
            "    this.numberOfLines = 1;\n" +
            "}\n" +
            "\n" +
            "function ImgView()\n" +
            "{\n" +
            "    View.call(this);\n" +
            "    this._clsName = 'ImgView';\n" +
            "    this.control = null;\n" +
            "    this.imageUrl = null;\n" +
            "}\n" +
            "\n" +
            "function List()\n" +
            "{\n" +
            "    View.call(this);\n" +
            "    this._clsName = 'List';\n" +
            "    \n" +
            "    this._cell = {};\n" +
            "    this._dataSource = {};\n" +
            "    \n" +
            "    this.setCellAtSection = function(section,Celltmp)\n" +
            "    {\n" +
            "        this._cell[section] = Celltmp;\n" +
            "    }\n" +
            "\n" +
            "    this.setDataSourceAtSection = function(section,data)\n" +
            "    {\n" +
            "        this._dataSource[section] = data;\n" +
            "    }\n" +
            "\n" +
            "    this.addCallBackDidSelectRowAtIndexPath = function(func)\n" +
            "    {\n" +
            "        addCallBack(this._vrtId + \"CallBackDidSelectRowAtIndexPath\",func);\n" +
            "    }\n" +
            "\n" +
            "    this.reloadData = function()\n" +
            "    {\n" +
            "        api_refreshList(this._vrtId);\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "function Cell()\n" +
            "{\n" +
            "    View.call(this);\n" +
            "    this._clsName = 'Cell';\n" +
            "    this.setCellFixHeight = function(fixHeight)\n" +
            "    {\n" +
            "        this._setFrame(0,0,0,fixHeight);\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "function TextField()\n" +
            "{\n" +
            "    View.call(this);\n" +
            "    this._clsName = \"TextField\";\n" +
            "    this.text = \"\";\n" +
            "    this.fontSize = 12;\n" +
            "    this.textColor = blackColor;\n" +
            "\n" +
            "    this.addCallBackDidReturn = function(func)\n" +
            "    {\n" +
            "        addCallBack(this._vrtId + \"CallBackDidReturn\",func);\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "function commitVC(vc)\n" +
            "{\n" +
            "    var platform = api_platform();\n" +
            "    //var platform = \"iOS\";\n" +
            "    \n" +
            "    if(platform == \"iOS\")\n" +
            "    {\n" +
            "        api_commitVC(vc);\n" +
            "    }\n" +
            "    else if(platform == \"Android\")\n" +
            "    {\n" +
            "        \n" +
            "        api_commitVC(vc);\n" +
            "    }\n" +
            "}";

    public static final String JS_TEST_CODE = "" +
            "var mainVC = new ViewController();\n" +
            "var label = new Label;\n" +
            "label.vrt_layout.heightIs(60).widthIs(100);\n" +
            "label.text = \"test\";\n" +
            "label.textColor = blackColor;\n" +
            "label._x = 100;\n" +
            "label._y = 100;\n" +
            "mainVC.view.addSubView(label);\n" +
            "commitVC(mainVC)";

    public static final String JS_CODE_4ANDROID = "/**\n" +
            " * Created by JzyCc on 2018/10/3.\n" +
            " */\n" +
            "\n" +
            "\n" +
            "//api commit\n" +
            "var method_Api_commitVC4Android = ScriptAPI.getMethod(\"api_commitVC4Android\",[java.lang.String])\n" +
            "function api_commitVC() {\n" +
            "\n" +
            "    var vc1 = new ViewController()\n" +
            "\n" +
            "    vc1 = mainVC\n" +
            "\n" +
            "    getVcForAndroid([vc1.view])\n" +
            "\n" +
            "    var str = JSON.stringify(vc1)\n" +
            "\n" +
            "    method_Api_commitVC4Android.invoke(javaContext,str);\n" +
            "\n" +
            "}\n" +
            "\n" +
            "function getVcForAndroid(v){\n" +
            "    if(v!=null){\n" +
            "        for(var i =0;i < v.length; i++)\n" +
            "        {\n" +
            "            v[i].superView = null; \n" +
            "            v[i].vrt_layout.masterView = null;\n" +
            "            if(v[i]._clsName == \"List\")\n" +
            "            {\n" +
            "                for(var key in v[i]._cell)\n" +
            "                {\n" +
            "                    var cell = v[i]._cell[key];\n" +
            "                    if(key == \"_clsName\" || cell._clsName != \"Cell\")\n" +
            "                        continue;\n" +
            "                    cell.superView = null;\n" +
            "                    cell.vrt_layout.masterView = null;\n" +
            "                    getVcForAndroid(cell.subViews)\n" +
            "                }\n" +
            "            }\n" +
            "            getVcForAndroid(v[i].subViews);\n" +
            "        }\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "//log\n" +
            "var method_Api_log = ScriptAPI.getMethod(\"api_log\",[java.lang.String])\n" +
            "function api_log(msg){\n" +
            "    method_Api_log.invoke(javaContext,msg)\n" +
            "}\n" +
            "\n" +
            "//获取本地高\n" +
            "var method_Api_getBaseViewWidth = ScriptAPI.getMethod(\"api_getBaseViewWidth\")\n" +
            "function api_getBaseViewWidth(){\n" +
            "    return method_Api_getBaseViewWidth.invoke(javaContext)\n" +
            "}\n" +
            "\n" +
            "\n" +
            "//获取本地宽\n" +
            "var method_Api_getBaseViewHeight = ScriptAPI.getMethod(\"api_getBaseViewHeight\")\n" +
            "function api_getBaseViewHeight(){\n" +
            "    return method_Api_getBaseViewHeight.invoke(javaContext)\n" +
            "}\n" +
            "\n" +
            "//获取设备系统名\n" +
            "function api_platform(){\n" +
            "    return \"Android\"\n" +
            "}\n" +
            "\n" +
            "//点击事件\n" +
            "//var method_Api_responseBasicCallBack = ScriptAPI.getMethod(\"_api_responseBasicCallBack\")\n" +
            "// function _api_responseBasicCallBack(key){\n" +
            "//     return method_Api_responseBasicCallBack.invoke(javaContext,key)\n" +
            "// }\n" +
            "\n" +
            "\n" +
            "// //var method_Api_responseListDidSelectRow = ScriptAPI.getMethod(\"_api_responseListDidSelectRow\")\n" +
            "// function _api_responseListDidSelectRow(key,section,row){\n" +
            "    \n" +
            "// }\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n";
}
