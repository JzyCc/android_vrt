Object.prototype._clsName = null;

var _vrtViewIndex = 0;

var kHeightScale = 0;
var kWidthScale = 0;
var kFontScale = 0;

const TextAlignmentCenter = "TextAlignmentCenter";
const TextAlignmentLeft = "TextAlignmentLeft";
const TextAlignmentRight = "TextAlignmentRight";

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

function _addCallBack(key,func)
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
function vrt_layout(_masterView){
    
    this.masterView = _masterView;
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
        this.masterView._height = height * kHeightScale;
        this._resolvePendCenterY();
        return this;
    }
    
    this.widthIs = function(width){
        this.masterView._width = width * kWidthScale;
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
        this.pendCenterY = y * kHeightScale;
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
        space = space * kHeightScale;
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
        this.pendCenterX = x * kWidthScale;
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
        space = space * kWidthScale;
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
    this.vrt_layout = new vrt_layout(this);
    
    this.cornerRadius = 0;
    
    _registerRefreshView(this);
    
    this.setFrame = function(x,y,width,height)
    {
        this._x = x;
        this._y = y;
        this._width = width;
        this._height = height;
    }
    
    this.addClick = function(func)
    {
        _addCallBack(this._vrtId,func);
        api_addViewClick(this._vrtId);
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
    this.view.setFrame(0,0,api_getBaseViewWidth(),api_getBaseViewHeight(false,false));
    kHeightScale = this.view._height/667.0;
    kWidthScale = this.view._width/375.0;
    kFontScale = kWidthScale;
    this.view.backgroundColor = whiteColor;
    this.view.superView = null;
    this._clsName = 'ViewController';
    this.title = "title";

    _kvo_add_refresh_prop(this,'title');

    this.addCallBackViewDidLoad = function(func){
        _addCallBack(this._vrtId + "CallBackViewDidLoad",func);
    }

    this.addCallBackViewWillAppear = function(func){
        _addCallBack(this._vrtId + "CallBackViewWillAppear",func);
    }

    this.addCallBackViewDidAppear = function(func){
        _addCallBack(this._vrtId + "CallBackViewDidAppear",func);
    }

    this.addCallBackViewWillDisappear = function(func){
        _addCallBack(this._vrtId + "CallBackViewWillDisappear",func);
    }
}


function Label()
{
    View.call(this);
    this._clsName = 'Label';
    this.text = '';
    this.fontSize = 15 * kFontScale;
    this.textColor = blackColor;
    this.numberOfLines = 1;
    this.textAlignment = TextAlignmentLeft;

    _kvo_add_refresh_prop(this,'text');
    _kvo_add_refresh_prop(this,'fontSize');
    _kvo_add_refresh_prop(this,'textColor');
    _kvo_add_refresh_prop(this,'numberOfLines');
    _kvo_add_refresh_prop(this,'textAlignment');
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

    this.addCallBackDidSelectRowAtIndexPath = function(func)
    {
        _addCallBack(this._vrtId + "CallBackDidSelectRowAtIndexPath",func);
    }

    this.reloadData = function(numberOfSections, offset, length, rowDataAtSection)
    {
        api_refreshListData(this._vrtId,numberOfSections, offset, length, rowDataAtSection);
    }
}

function Cell()
{
    View.call(this);
    this._clsName = 'Cell';
    this.setCellFixHeight = function(fixHeight)
    {
        this.setFrame(0,0,api_getBaseViewWidth(),fixHeight);
    }
}

function TextField()
{
    View.call(this);
    this._clsName = "TextField";
    this.text = "";
    this.fontSize = 15 * kFontScale;
    this.textColor = blackColor;
    this.textAlignment = TextAlignmentLeft;

    _kvo_add_refresh_prop(this,'text');
    _kvo_add_refresh_prop(this,'fontSize');
    _kvo_add_refresh_prop(this,'textColor');
    _kvo_add_refresh_prop(this,'textAlignment');

    this.addCallBackDidReturn = function(func)
    {
        _addCallBack(this._vrtId + "CallBackDidReturn",func);
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
