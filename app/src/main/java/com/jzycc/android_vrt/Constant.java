package com.jzycc.android_vrt;

/**
 * author Jzy(Xiaohuntun)
 * date 18-9-27
 */
public class Constant {
    public static final String JAVA_CALL_JS_FUNCTION = "Object.prototype._clsName = null;\n" +
            "\n" +
            "var vrtViewIndex = 0;\n" +
            "var currentVC = null;\n" +
            "\n" +
            "function commitVC(vc)\n" +
            "{\n" +
            "    currentVC = vc;\n" +
            "    // native_commitVC(vc);\n" +
            "}\n" +
            "\n" +
            "\n" +
            "function vec2(x,y)\n" +
            "{\n" +
            "    this.x = x;\n" +
            "    this.y = y;\n" +
            "}\n" +
            "\n" +
            "function vec4(x,y,z,w)\n" +
            "{\n" +
            "    this.x = x;\n" +
            "    this.y = y;\n" +
            "    this.z = z;\n" +
            "    this.w = w;\n" +
            "}\n" +
            "\n" +
            "const redColor = new vec4(1,0,0,1);\n" +
            "const greenColor = new vec4(0,1,0,1);\n" +
            "const blackColor = new vec4(0,0,0,1);\n" +
            "const whiteColor = new vec4(1,1,1,1);\n" +
            "const clearColor = new vec4(0,0,0,0);\n" +
            "\n" +
            "function createColorWithRGBA(r,g,b,a)\n" +
            "{\n" +
            "    return new vec4(r,g,b,a);\n" +
            "}\n" +
            "\n" +
            "\n" +
            "const vrtRectZero = new vec4(0,0,0,0);\n" +
            "\n" +
            "function createFrameWithRect(x,y,width,height)\n" +
            "{\n" +
            "    return new vec4(x,y,width,height);\n" +
            "}\n" +
            "\n" +
            "function vrt_layout(){\n" +
            "    \n" +
            "    this._leftView = null;\n" +
            "    this._leftSpaceToView = 0;\n" +
            "\n" +
            "    this._topView = null;\n" +
            "    this._topSpaceToView = 0;\n" +
            "\n" +
            "    this._rightView = null;\n" +
            "    this._rightSpaceToView = 0;\n" +
            "\n" +
            "    this._bottomView = null;\n" +
            "    this._bootomSpaceToView = 0;\n" +
            "\n" +
            "    this._height = 0;\n" +
            "    this._width = 0;\n" +
            "\n" +
            "    this._heightView = null;\n" +
            "    this._widthView = null;\n" +
            "\n" +
            "\n" +
            "    this._getLeft = function(){\n" +
            "\n" +
            "        if(this.superView == _leftView)\n" +
            "        {\n" +
            "            return this._leftSpaceToView;\n" +
            "        }\n" +
            "\n" +
            "        if(this._leftSpaceToView != 0)\n" +
            "            return this._leftView.right() + this._leftSpaceToView;\n" +
            "        else \n" +
            "            return this._leftView.left();\n" +
            "    }\n" +
            "\n" +
            "    this._getTop = function()\n" +
            "    {\n" +
            "        if(this.superView == this._topView)\n" +
            "        {\n" +
            "            return this._topSpaceToView;\n" +
            "        }\n" +
            "\n" +
            "        if(this._topSpaceToView != 0)\n" +
            "            return this._topView.bottom() - this._topSpaceToView;\n" +
            "        else \n" +
            "            return this._topView.top();\n" +
            "    }\n" +
            "\n" +
            "    this._getWidth = function()\n" +
            "    {\n" +
            "        if(this._widthView == null)\n" +
            "            return this._width;\n" +
            "        else \n" +
            "            return this._widthView.width() * this._width;\n" +
            "    }\n" +
            "\n" +
            "    this._getHeight = function()\n" +
            "    {\n" +
            "        if(this._heightView == null)\n" +
            "            return this._height;\n" +
            "        else \n" +
            "            return this._heightView.height() * this._height;\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "    this.heightIs = function(height){\n" +
            "        this._height = height;\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.widthIs = function(width){\n" +
            "        this._width = width;\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.widthEqualToHeight = function(){\n" +
            "        this._width = this._getHeight();\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.heightEqualToWidth = function(){\n" +
            "        this.height = this._getWidth();\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.heightRatioToView = function(view,scale){\n" +
            "        this._heightView = view;\n" +
            "        this._height = scale;\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.widthRatioToView = function(view,scale){\n" +
            "        this._widthView = view;\n" +
            "        this._width = scale;\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.leftEqualToView = function(view){\n" +
            "        this._leftView = view;\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.leftSpaceToView = function(view,space){\n" +
            "        this._leftView = view;\n" +
            "        this._leftSpaceToView = space;\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.topEqualToView = function(view){\n" +
            "        this._topView = view;\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.topSpaceToView = function(view,space){\n" +
            "        this._topView = view;\n" +
            "        this._topSpaceToView = space;\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.rightEqualToView = function(view){\n" +
            "        this._rightView = view;\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.rightSpaceToView = function(view,space){\n" +
            "        this._rightView = view;\n" +
            "        this._rightSpaceToView = space;\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.bottomEqualToView = function(view){\n" +
            "        this._bottomView = view;\n" +
            "        return this;\n" +
            "    }\n" +
            "\n" +
            "    this.bottomSpaceToView = function(view,space){\n" +
            "        this._bottomView = view;\n" +
            "        this._bootomSpaceToView = space;\n" +
            "        return this;\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "var _vrt_kvo_array = new Array();\n" +
            "\n" +
            "function vrt_kvo_bind(target,key,func)\n" +
            "{\n" +
            "    var vrt_kvo_obj = new Object();\n" +
            "    vrt_kvo_obj.target = target;\n" +
            "    vrt_kvo_obj[key] = target[key];\n" +
            "\n" +
            "    _vrt_kvo_array.push(vrt_kvo_obj);\n" +
            "\n" +
            "    Object.defineProperty(target,key,{\n" +
            "        get : function(){\n" +
            "            return vrt_kvo_obj[key];\n" +
            "        },\n" +
            "        set : function(value){\n" +
            "            vrt_kvo_obj[key] = value;\n" +
            "            func(vrt_kvo_obj.target,key,value);\n" +
            "        }\n" +
            "    });\n" +
            "}\n" +
            "\n" +
            "function view()\n" +
            "{\n" +
            "    this._vrtId = vrtViewIndex++;\n" +
            "    this._refreshTag = true;\n" +
            "\n" +
            "    this._x = null;\n" +
            "    this._y = null;\n" +
            "    this._height = null;\n" +
            "    this._width = null;\n" +
            "\n" +
            "    this.superView = null;\n" +
            "    this._clsName = 'view';\n" +
            "    this.subViews = new Array();\n" +
            "    this.backgroundColor = clearColor;\n" +
            "    this.vrt_layout = new vrt_layout();\n" +
            "\n" +
            "    vrt_kvo_bind(this,'backgroundColor',function(target,key,value){\n" +
            "        // native_refreshView(target._vrtId,key,value);\n" +
            "    });\n" +
            "\n" +
            "    this._setFrame = function(x,y,width,height)\n" +
            "    {\n" +
            "        this._x = x;\n" +
            "        this._y = y;\n" +
            "        this._width = width;\n" +
            "        this._height = height;\n" +
            "    }\n" +
            "\n" +
            "    this.addSubview = function(subview){\n" +
            "        if(subview == this || subview == null)\n" +
            "        {\n" +
            "            return;\n" +
            "        }\n" +
            "        this.subViews.push(subview);\n" +
            "        subview.superView = this;\n" +
            "    }\n" +
            "\n" +
            "    this.top = function()\n" +
            "    {\n" +
            "        if(this._y == null)\n" +
            "            this._y = this.vrt_layout._getTop();\n" +
            "        return this._y;\n" +
            "    }\n" +
            "\n" +
            "    this.left = function()\n" +
            "    {\n" +
            "        if(this._x == null)\n" +
            "            this._x = this.vrt_layout._getLeft();\n" +
            "        return this._x;\n" +
            "    }\n" +
            "\n" +
            "    this.right = function()\n" +
            "    {\n" +
            "        return this.left() + this.width();\n" +
            "    }\n" +
            "\n" +
            "    this.bottom = function()\n" +
            "    {\n" +
            "        return this.top() + this.height();\n" +
            "    }\n" +
            "\n" +
            "    this.height = function()\n" +
            "    {\n" +
            "        if(this._height == null)\n" +
            "             this._height = this.vrt_layout._getHeight();\n" +
            "        return this._height;\n" +
            "    }\n" +
            "\n" +
            "    this.width = function()\n" +
            "    {\n" +
            "        if(this._width == null)\n" +
            "             this._width = this.vrt_layout._getWidth();\n" +
            "        return this._width;\n" +
            "    }\n" +
            "\n" +
            "    this.center = function()\n" +
            "    {\n" +
            "        return new vec2((this.left() + this.right())/2.0 , (this.top() + this.bottom())/2.0);\n" +
            "    }\n" +
            "\n" +
            "}\n" +
            "\n" +
            "function viewController()\n" +
            "{\n" +
            "    this.view = new view();\n" +
            "    this.view._setFrame(0,0,1024,800);\n" +
            "    this.view.backgroundColor = whiteColor;\n" +
            "    this._clsName = 'viewController';\n" +
            "    this.title = \"title\";\n" +
            "\n" +
            "    this.viewDidLoad = function(){}\n" +
            "\n" +
            "    this.viewWillAppear = function(){}\n" +
            "\n" +
            "    this.viewDidAppear = function(){}\n" +
            "\n" +
            "    this.viewWillDisappear = function(){}\n" +
            "}\n" +
            "\n" +
            "\n" +
            "function vrtControl()\n" +
            "{\n" +
            "    this.click = null;\n" +
            "    this.clsName = 'vrtControl';\n" +
            "}\n" +
            "\n" +
            "function label()\n" +
            "{\n" +
            "    view.call(this);\n" +
            "    this.control = null;\n" +
            "    this.clsName = 'label';\n" +
            "    this.text = '';\n" +
            "    this.fontSize = 12;\n" +
            "}\n" +
            "\n" +
            "function imgView()\n" +
            "{\n" +
            "    view.call(this);\n" +
            "    this.clsName = 'imgView';\n" +
            "    this.control = null;\n" +
            "    this.imageUrl = null;\n" +
            "}\n" +
            "\n" +
            "function list()\n" +
            "{\n" +
            "    view.call(this);\n" +
            "    this.clsName = 'list';\n" +
            "    \n" +
            "    this.cell = null;\n" +
            "    this.dataSource = null;\n" +
            "    \n" +
            "    this.reloadData = function()\n" +
            "    {\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "function cell()\n" +
            "{\n" +
            "    view.call(this);\n" +
            "    this.clsName = 'cell';\n" +
            "    \n" +
            "}\n" +
            "\n" +
            "var dataSource = new Array();\n" +
            "\n" +
            "function model4User()\n" +
            "{\n" +
            "    this.userName = '';\n" +
            "}\n" +
            "\n" +
            "for(var i =0;i<10;i++)\n" +
            "{\n" +
            "    var model = new model4User();\n" +
            "    model.userName = 'userName '+i;\n" +
            "    \n" +
            "    dataSource.push(model);\n" +
            "}\n" +
            "\n" +
            "var cell = new cell();\n" +
            "\n" +
            "var label = new label();\n" +
            "label.text = 'bindKey:userName';\n" +
            "\n" +
            "cell.addSubview(label);\n" +
            "\n" +
            "var tableView = new list();\n" +
            "tableView.cell = cell;\n" +
            "tableView.dataSource = dataSource;\n" +
            "\n" +
            "var vc = new viewController();\n" +
            "\n" +
            "\n" +
            "var view1 = new view();\n" +
            "vc.view.addSubview(view1);\n" +
            "view1.vrt_layout.topEqualToView(vc.view).leftEqualToView(vc.view).heightRatioToView(vc.view,0.5).widthEqualToHeight();\n" +
            "view1.backgroundColor = redColor;\n" +
            "\n" +
            "vc.view.addSubview(tableView);\n" +
            "\n" +
            "function native_commitVC(vc){\n" +
            "    return ;\n" +
            "}\n" +
            "\n" +
            "native_commitVC(vc);";
}
