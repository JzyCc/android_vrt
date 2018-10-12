Object.prototype._clsName = null;

var _vrtViewIndex = 0;

var _kHeightScale = 0;
var _kWidthScale = 0;
var kFontScale = 0;

function Vec2(x,y)
{
    this.x = x;
    this.y = y;
}

function Vec4(x,y,z,w)
{
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
}

var redColor = new Vec4(255,0,0,255);
var greenColor = new Vec4(0,255,0,255);
var blackColor = new Vec4(0,0,0,255);
var whiteColor = new Vec4(255,255,255,255);
var clearColor = new Vec4(0,0,0,0);

function createColorWithRGBA(r,g,b,a)
{
    return new Vec4(r,g,b,a);
}

function createFrameWithRect(x,y,width,height)
{
    return new Vec4(x,y,width,height);
}

var _vrt_kvo_responserIndex = 0;
var _vrt_kvo_responserCache = {};
var _vrt_kvo_targetCache = {};

function vrt_kvo_bind(target,key,func)
{
    var hashKey = key + " KVO " + target._vrtId;
    if(_vrt_kvo_targetCache[hashKey] == undefined)
    {
        var vrt_kvo_target = new Object();
        vrt_kvo_target.target = target;
        vrt_kvo_target.targetValue = target[key];
        _vrt_kvo_targetCache[hashKey] = vrt_kvo_target;
    }
    
    var repHashKey = hashKey + "REPINDEXMASK" + _vrt_kvo_responserIndex++;
    var vrt_kvo_responser = new Object();
    vrt_kvo_responser.callBack = func;
    
    if(_vrt_kvo_responserCache[repHashKey] == undefined)
    {
        _vrt_kvo_responserCache[repHashKey] = new Array();
        
        Object.defineProperty(target,key,{
                              get : function(){
                              return _vrt_kvo_targetCache[hashKey].targetValue;
                              },
                              set : function(value){
                              var tmpArray;
                              if(_vrt_kvo_responserCache[repHashKey] != undefined)
                              {
                              tmpArray = _vrt_kvo_responserCache[repHashKey];
                              tmpArray.forEach(element => {
                                               element.callBack(_vrt_kvo_targetCache[hashKey].target,key,_vrt_kvo_targetCache[hashKey].targetValue,value);
                                               });
                              }
                              _vrt_kvo_targetCache[hashKey].targetValue = value;
                              }
                              });
    }
    
    _vrt_kvo_responserCache[repHashKey].push(vrt_kvo_responser);
}





var _vrt_callback_cache = {};

function addCallBack(key,func)
{
    _vrt_callback_cache[key] = func;
}

function _api_responseBasicCallBack(key)
{
    var func = _vrt_callback_cache[key];
    if(func != null && func != undefined)
        func();
}

function _api_responseListDidSelectRow(key,section,row)
{
    var func = _vrt_callback_cache[key];
    if(func != null && func != undefined)
        func(section,row);
}

function _api_responseTextFieldReturn(key,text)
{
    var func = _vrt_callback_cache[key];
    if(func != null && func != undefined)
        func(text);
}


/**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**/
function vrt_layout(){
    
    this.masterView = null;
    this.pendCenterX = null;
    this.pendCenterY = null;
    
    this._resolvePendCenterX = function()
    {
        if(this.pendCenterX != null)
        {
            this.masterView._x = this.pendCenterX - this.masterView._width/2.0;
        }
    }
    
    this._resolvePendCenterY = function()
    {
        if(this.pendCenterY != null)
        {
            this.masterView._y = this.pendCenterY - this.masterView._height/2.0;
        }
    }
    
    this.heightIs = function(height){
        this.masterView._height = height * _kHeightScale;
        this._resolvePendCenterY();
        return this;
    }
    
    this.widthIs = function(width){
        this.masterView._width = width * _kWidthScale;
        this._resolvePendCenterX();
        return this;
    }
    
    this.widthEqualToHeight = function(){
        this.masterView._width = this.masterView.height();
        this._resolvePendCenterX();
        return this;
    }
    
    this.heightEqualToWidth = function(){
        this.masterView._height = this.masterView.width();
        this._resolvePendCenterY();
        return this;
    }
    
    this.heightRatioToView = function(view,scale){
        if(view == null)
            view = this.masterView.superView;
        this.masterView._height = view.height() * scale;
        this._resolvePendCenterY();
        return this;
    }
    
    this.widthRatioToView = function(view,scale){
        if(view == null)
            view = this.masterView.superView;
        this.masterView._width = view.width() * scale;
        return this;
    }
    
    this.centerYEqualToView = function(view)
    {
        if(view == null)
            view = this.masterView.superView;
        if(this.masterView.superView == view)
            this.pendCenterY = view.height()/2.0;
        else
            this.pendCenterY = view.center().y;
        return this;
    }
    
    this.centerYIs = function(y)
    {
        this.pendCenterY = y * _kHeightScale;
        return this;
    }
    
    this.topEqualToView = function(view){
        if(view == null)
            view = this.masterView.superView;
        this.pendCenterY = null;
        if(this.masterView.superView == view)
            this.masterView._y = 0;
        else
            this.masterView._y = view._y;
        return this;
    }
    
    this.topSpaceToView = function(view,space){
        space = space * _kHeightScale;
        if(view == null)
            view = this.masterView.superView;
        this.pendCenterY = null;
        if(this.masterView.superView == view)
            this.masterView._y = space;
        else
            this.masterView._y = space + view.bottom();
        return this;
    }
    
    this.centerXEqualToView = function(view)
    {
        if(view == null)
            view = this.masterView.superView;
        if(this.masterView.superView == view)
            this.pendCenterX = view.width()/2.0;
        else
            this.pendCenterX = view.center().x;
        return this;
    }
    
    this.centerXIs = function(x)
    {
        this.pendCenterX = x * _kWidthScale;
        return this;
    }
    
    this.leftEqualToView = function(view){
        if(view == null)
            view = this.masterView.superView;
        this.pendCenterX = null;
        if(this.masterView.superView == view)
            this.masterView._x = 0;
        else
            this.masterView._x = view.left();
        return this;
    }
    
    this.leftSpaceToView = function(view,space){
        space = space * _kWidthScale;
        if(view == null)
            view = this.masterView.superView;
        this.pendCenterX = null;
        if(this.masterView.superView == view)
            this.masterView._x = space;
        else
            this.masterView._x = view.right() + space;
        return this;
    }
}

function _kvo_add_refresh_prop(view,key)
{
    vrt_kvo_bind(view,key,function(target,key,oldVlaue,newValue){
                 api_refreshView(target._vrtId,key,newValue);
                 });
}

function _registerRefreshView(view)
{
    _kvo_add_refresh_prop(view,'_x');
    _kvo_add_refresh_prop(view,'_y');
    _kvo_add_refresh_prop(view,'_height');
    _kvo_add_refresh_prop(view,'_width');
    
    _kvo_add_refresh_prop(view,'backgroundColor');
    _kvo_add_refresh_prop(view,'cornerRadius');
}

function View()
{
    this._vrtId = _vrtViewIndex++;
    
    this._x = null;
    this._y = null;
    this._height = null;
    this._width = null;
    
    this.superView = null;
    this._clsName = 'View';
    this.subViews = new Array();
    this.backgroundColor = clearColor;
    this.vrt_layout = new vrt_layout();
    this.vrt_layout.masterView = this;
    
    this.cornerRadius = 0;
    
    _registerRefreshView(this);
    
    this.setFrame = function(x,y,width,height)
    {
        this._x = x;
        this._y = y;
        this._width = width;
        this._height = height;
    }
    
    this.addClick = function(view,func)
    {
        addCallBack(view._vrtId,func);
        api_addViewClick(view._vrtId);
    }
    
    this.addSubView = function(subView){
        if(subView == this || subView == null)
        {
            return;
        }
        this.subViews.push(subView);
        subView.superView = this;
    }
    
    this.top = function()
    {
        if(this._y == null)
            this._y = this.vrt_layout._getTop();
        return this._y;
    }
    
    this.left = function()
    {
        if(this._x == null)
            this._x = this.vrt_layout._getLeft();
        return this._x;
    }
    
    this.right = function()
    {
        return this.left() + this.width();
    }
    
    this.bottom = function()
    {
        return this.top() + this.height();
    }
    
    this.height = function()
    {
        if(this._height == null)
            this._height = this.vrt_layout._getHeight();
        return this._height;
    }
    
    this.width = function()
    {
        if(this._width == null)
            this._width = this.vrt_layout._getWidth();
        return this._width;
    }
    
    this.center = function()
    {
        return new Vec2((this.left() + this.right())/2.0 , (this.top() + this.bottom())/2.0);
    }
}


function ViewController(hasNavigationBar,hasTabBar)
{
    this._vrtId = -1;
    this.view = new View();
    this.view.setFrame(0,0,api_getBaseViewWidth() * 1,api_getBaseViewHeight(hasNavigationBar,hasTabBar) * 1);//667 full screen  554 with Navigation Bar & Tab Bar
    _kHeightScale = this.view._height/667.0;
    _kWidthScale = this.view._width/375.0;
    kFontScale = _kWidthScale;
    this.view.backgroundColor = whiteColor;
    this.view.superView = null;
    this._clsName = 'ViewController';
    this.title = "title";
    
    _kvo_add_refresh_prop(this,'title');
    
    this.addCallBackViewDidLoad = function(func){
        addCallBack(this._vrtId + "CallBackViewDidLoad",func);
    }
    
    this.addCallBackViewWillAppear = function(func){
        addCallBack(this._vrtId + "CallBackViewWillAppear",func);
    }
    
    this.addCallBackViewDidAppear = function(func){
        addCallBack(this._vrtId + "CallBackViewDidAppear",func);
    }
    
    this.addCallBackViewWillDisappear = function(func){
        addCallBack(this._vrtId + "CallBackViewWillDisappear",func);
    }
}


function Label()
{
    View.call(this);
    this._clsName = 'Label';
    this.text = '';
    this.fontSize = 12 * kFontScale;
    this.textColor = blackColor;
    this.numberOfLines = 1;
    
    _kvo_add_refresh_prop(this,'text');
    _kvo_add_refresh_prop(this,'fontSize');
    _kvo_add_refresh_prop(this,'textColor');
    _kvo_add_refresh_prop(this,'numberOfLines');
}

function ImgView()
{
    View.call(this);
    this._clsName = 'ImgView';
    this.imageUrl = null;
    _kvo_add_refresh_prop(this,'imageUrl');
}

function List()
{
    View.call(this);
    this._clsName = 'List';
    
    this._cell = {};
    this._dataSource = {};
    
    this.setCellAtSection = function(section,Celltmp)
    {
        this._cell[section] = Celltmp;
    }
    
    this.setDataSourceAtSection = function(section,data)
    {
        this._dataSource[section] = data;
    }
    
    this.addCallBackDidSelectRowAtIndexPath = function(func)
    {
        addCallBack(this._vrtId + "CallBackDidSelectRowAtIndexPath",func);
    }
    
    this.reloadData = function()
    {
        api_refreshList(this._vrtId);
    }
}

function bindKey(key)
{
    return "bindKey:" + key;
}

function Cell()
{
    View.call(this);
    this._clsName = 'Cell';
    this.setCellFixHeight = function(fixHeight)
    {
        this.setFrame(0,0,0,fixHeight);
    }
}

function TextField()
{
    View.call(this);
    this._clsName = "TextField";
    this.text = "";
    this.fontSize = 12 * kFontScale;
    this.textColor = blackColor;
    
    _kvo_add_refresh_prop(this,'text');
    _kvo_add_refresh_prop(this,'fontSize');
    _kvo_add_refresh_prop(this,'textColor');
    
    this.addCallBackDidReturn = function(func)
    {
        addCallBack(this._vrtId + "CallBackDidReturn",func);
    }
}


var thisNavigetion = null;
function getThisNavigation()
{
    if(thisNavigetion == null)
    {
        var naDic = api_getThisNavigationComp();
        thisNavigetion = new Navigation();
        thisNavigetion.url = naDic.url;
    }
    return thisNavigetion;
    
}

function Navigation()
{
    this._clsName = "Navigation";
    this.url = "";

    this.pushUrlWithParam = function(url,param)
    {
        api_pushUrlWithParam(url,param);
    }

    this.popThis = function()
    {
        api_popThis();
    }

    this.getPushedParam = function()
    {
        return api_getPushedParam();
    }
}


var _alertActionCache = {};
var _curShowedAlert = null;

function _api_didSelectAlertAction(acId)
{
    var func = _alertActionCache[acId];
    if(func != null && func != undefined)
    {
        func();
    }
    _curShowedAlert = null;

}

function Alert()
{
    this._clsName = "Alert";
    this.title = "";
    this.msg = "";

    this._actions = new Array();

    this.addAction = function(action,func)
    {
        action._acId = this._actions.length;
        _alertActionCache[action._acId] = func;
        this._actions.push(action);
    }

    this.show = function()
    {
        if(_curShowedAlert == null)
        {
            api_showAlert(this);
            _curShowedAlert = this;
        }
    }
}


function AlertAction(title)
{
    this._acId;
    this._clsName = "AlertAction"
    this.title = title;
}


function dispatchWithAnimation(duration,func)
{
    if(api_platform() == "iOS")
        api_dispatchWithAnimation(duration,func);
}

var _vrt_httpRqe_Cache = {};
//原型func(json data,string info)
function HttpRequest(url,func)
{
    this.url = url;
    this._param = {};
    
    this.request = function(param)
    {
        if(param == null)
            this._param = {};
        else
            this._param = param;
        api_httpRequest(this);
    }
    _vrt_httpRqe_Cache[url] = func;
}

function _api_httpResponse(url,data,info)
{
    var httpReqFunc = _vrt_httpRqe_Cache[url];
    if(httpReqFunc)
    {
        httpReqFunc(data,info);
    }
}



/* TEST CODE *//* TEST CODE *//* TEST CODE *//* TEST CODE *//* TEST CODE *//* TEST CODE *//* TEST CODE */

//创建测试数据
function model4Test()
{
    this.userName = null;
}

var dataSource = new Array();

for(var i = 0; i < 10; i++)
{
    var model = new model4Test();
    model.userName = "userName " + i;
    dataSource.push(model);
}


//获取导航控制器、且获取参数
var param = getThisNavigation().getPushedParam();

//创建唯一的视图控制器
var mainVC = new ViewController(true,true);



//在视图不同阶段添加回调
mainVC.addCallBackViewDidLoad(function(){
                              // api_log("view did load");
                              });
mainVC.addCallBackViewWillAppear(function(){
                                 // api_log("view will appear");
                                 });
mainVC.addCallBackViewDidAppear(function(){
                                // api_log("view did appear");
                                });
mainVC.addCallBackViewWillDisappear(function(){
                                    // api_log("view will disappear");
                                    });

//创建一个列表视图
var list = new List();

//添加列表中cell的点击回调
list.addCallBackDidSelectRowAtIndexPath(function(section,row){
                                        api_log(" did select section : " + section + " row : " + row);
                                        });

mainVC.view.addSubView(list);
list.vrt_layout.topEqualToView(null).leftEqualToView(null).heightRatioToView(null,0.5).widthRatioToView(null,1);

//创建一个cell模版
var cell = new Cell();
//设置cell的固定高度 (当前不支持动态高度自适应)
cell.setCellFixHeight(100);


//创建一个图片视图
var imgView = new ImgView();
imgView.imageUrl = "http://114.55.84.37/anbao/img/dog.png";
cell.addSubView(imgView);
imgView.vrt_layout.topSpaceToView(cell,10).leftSpaceToView(cell,10).heightIs(60).widthEqualToHeight();

//创建一个文本显示视图
var label1 = new Label();
//因为该视图是放在cell模版中的、需要bind一个model中的key
label1.text = bindKey("userName");
label1.textColor = redColor;
label1.fontSize = 18 * kFontScale;
cell.addSubView(label1);
label1.vrt_layout.topSpaceToView(cell,10).leftSpaceToView(imgView,10).heightIs(60).widthIs(150);

//给list设置cell模版、native将根据cell模版生成合适数量的视图、并进行复用
list.setCellAtSection(0,cell);
//设置list的数据源
list.setDataSourceAtSection(0,dataSource);


var basicUrl = "http://dyba.xuebaeasy.com:8090/";
//创建一个http请求器
var reqTest = new HttpRequest(basicUrl + "user/loginToApp",function(data,info){
                              api_log(data.msg);
                              })

//创建一个基本的视图、
var view4testClick = new View();
view4testClick.backgroundColor = redColor;
mainVC.view.addSubView(view4testClick);
view4testClick.vrt_layout.topSpaceToView(list,15).centerXEqualToView(null).heightIs(30).widthEqualToHeight();
//为该视图增加一个点击事件的回调
view4testClick.addClick(view4testClick,function(){
                        reqTest.request({"userPhone":"","userPassword":""});
                        textField.text = "OK";
                        var alert = new Alert();
                        alert.title = "title";
                        alert.msg = "msg";
                        alert.addAction(new AlertAction("Black"),function(){
                            dispatchWithAnimation(0.6,function(){
                                mainVC.view.backgroundColor = blackColor;
                            })
                        });

                        alert.addAction(new AlertAction("Black"),function(){
                            dispatchWithAnimation(0.6,function(){
                                mainVC.view.backgroundColor = greenColor;
                            })
                        });
                        alert.show();
                        });

//创建一个文本输入视图
var textField = new TextField();
textField.text = "placeHolder";
textField.backgroundColor = blackColor;
textField.textColor = whiteColor;
mainVC.view.addSubView(textField);
textField.vrt_layout.topSpaceToView(list,15).leftSpaceToView(view4testClick,10).heightIs(30).widthIs(100);
textField.addCallBackDidReturn(function(text){
                               api_log("user input text: " + text);
                               });
//提交这个视图控制器
api_commitVC(mainVC);