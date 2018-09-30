Object.prototype._clsName = null;

var vrtViewIndex = 0;
var currentVC = null;

function commitVC(vc)
{
    currentVC = vc;
    // native_commitVC(vc);
}


function vec2(x,y)
{
    this.x = x;
    this.y = y;
}

function vec4(x,y,z,w)
{
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
}

const redColor = new vec4(1,0,0,1);
const greenColor = new vec4(0,1,0,1);
const blackColor = new vec4(0,0,0,1);
const whiteColor = new vec4(1,1,1,1);
const clearColor = new vec4(0,0,0,0);

function createColorWithRGBA(r,g,b,a)
{
    return new vec4(r,g,b,a);
}


const vrtRectZero = new vec4(0,0,0,0);

function createFrameWithRect(x,y,width,height)
{
    return new vec4(x,y,width,height);
}

function vrt_layout(){
    
    this._leftView = null;
    this._leftSpaceToView = 0;

    this._topView = null;
    this._topSpaceToView = 0;

    this._rightView = null;
    this._rightSpaceToView = 0;

    this._bottomView = null;
    this._bootomSpaceToView = 0;

    this._height = 0;
    this._width = 0;

    this._heightView = null;
    this._widthView = null;


    this._getLeft = function(){

        if(this.superView == _leftView)
        {
            return this._leftSpaceToView;
        }

        if(this._leftSpaceToView != 0)
            return this._leftView.right() + this._leftSpaceToView;
        else 
            return this._leftView.left();
    }

    this._getTop = function()
    {
        if(this.superView == this._topView)
        {
            return this._topSpaceToView;
        }

        if(this._topSpaceToView != 0)
            return this._topView.bottom() - this._topSpaceToView;
        else 
            return this._topView.top();
    }

    this._getWidth = function()
    {
        if(this._widthView == null)
            return this._width;
        else 
            return this._widthView.width() * this._width;
    }

    this._getHeight = function()
    {
        if(this._heightView == null)
            return this._height;
        else 
            return this._heightView.height() * this._height;
    }




    this.heightIs = function(height){
        this._height = height;
        return this;
    }

    this.widthIs = function(width){
        this._width = width;
        return this;
    }

    this.widthEqualToHeight = function(){
        this._width = this._getHeight();
        return this;
    }

    this.heightEqualToWidth = function(){
        this.height = this._getWidth();
        return this;
    }

    this.heightRatioToView = function(view,scale){
        this._heightView = view;
        this._height = scale;
        return this;
    }

    this.widthRatioToView = function(view,scale){
        this._widthView = view;
        this._width = scale;
        return this;
    }

    this.leftEqualToView = function(view){
        this._leftView = view;
        return this;
    }

    this.leftSpaceToView = function(view,space){
        this._leftView = view;
        this._leftSpaceToView = space;
        return this;
    }

    this.topEqualToView = function(view){
        this._topView = view;
        return this;
    }

    this.topSpaceToView = function(view,space){
        this._topView = view;
        this._topSpaceToView = space;
        return this;
    }

    this.rightEqualToView = function(view){
        this._rightView = view;
        return this;
    }

    this.rightSpaceToView = function(view,space){
        this._rightView = view;
        this._rightSpaceToView = space;
        return this;
    }

    this.bottomEqualToView = function(view){
        this._bottomView = view;
        return this;
    }

    this.bottomSpaceToView = function(view,space){
        this._bottomView = view;
        this._bootomSpaceToView = space;
        return this;
    }
}


var _vrt_kvo_array = new Array();

function vrt_kvo_bind(target,key,func)
{
    var vrt_kvo_obj = new Object();
    vrt_kvo_obj.target = target;
    vrt_kvo_obj[key] = target[key];

    _vrt_kvo_array.push(vrt_kvo_obj);

    Object.defineProperty(target,key,{
        get : function(){
            return vrt_kvo_obj[key];
        },
        set : function(value){
            vrt_kvo_obj[key] = value;
            func(vrt_kvo_obj.target,key,value);
        }
    });
}

function view()
{
    this._vrtId = vrtViewIndex++;
    this._refreshTag = true;

    this._x = null;
    this._y = null;
    this._height = null;
    this._width = null;

    this.superView = null;
    this._clsName = 'view';
    this.subViews = new Array();
    this.backgroundColor = clearColor;
    this.vrt_layout = new vrt_layout();

    vrt_kvo_bind(this,'backgroundColor',function(target,key,value){
        // native_refreshView(target._vrtId,key,value);
    });

    this._setFrame = function(x,y,width,height)
    {
        this._x = x;
        this._y = y;
        this._width = width;
        this._height = height;
    }

    this.addSubview = function(subview){
        if(subview == this || subview == null)
        {
            return;
        }
        this.subViews.push(subview);
        subview.superView = this;
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
        return new vec2((this.left() + this.right())/2.0 , (this.top() + this.bottom())/2.0);
    }

}

function viewController()
{
    this.view = new view();
    this.view._setFrame(0,0,1024,800);
    this.view.backgroundColor = whiteColor;
    this._clsName = 'viewController';
    this.title = "title";

    this.viewDidLoad = function(){}

    this.viewWillAppear = function(){}

    this.viewDidAppear = function(){}

    this.viewWillDisappear = function(){}
}


function vrtControl()
{
    this.click = null;
    this.clsName = 'vrtControl';
}

function label()
{
    view.call(this);
    this.control = null;
    this.clsName = 'label';
    this.text = '';
    this.fontSize = 12;
}

function imgView()
{
    view.call(this);
    this.clsName = 'imgView';
    this.control = null;
    this.imageUrl = null;
}

function list()
{
    view.call(this);
    this.clsName = 'list';
    
    this.cell = null;
    this.dataSource = null;
    
    this.reloadData = function()
    {
    }
}

function cell()
{
    view.call(this);
    this.clsName = 'cell';
    
}

var dataSource = new Array();

function model4User()
{
    this.userName = '';
}

for(var i =0;i<10;i++)
{
    var model = new model4User();
    model.userName = 'userName '+i;
    
    dataSource.push(model);
}

var cell = new cell();

var label = new label();
label.text = 'bindKey:userName';

cell.addSubview(label);

var tableView = new list();
tableView.cell = cell;
tableView.dataSource = dataSource;

var vc = new viewController();


var view1 = new view();
vc.view.addSubview(view1);
view1.vrt_layout.topEqualToView(vc.view).leftEqualToView(vc.view).heightRatioToView(vc.view,0.5).widthEqualToHeight();
view1.backgroundColor = redColor;

vc.view.addSubview(tableView);

console.log(vc);
//native_commitVC(vc);
//native_commitVC 这个方法需要本地实现







