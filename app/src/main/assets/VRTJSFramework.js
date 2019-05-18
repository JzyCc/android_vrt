Object.prototype._clsName = null;

var _vrtViewIndex = 0;

const TextAlignmentCenter = "TextAlignmentCenter";
const TextAlignmentLeft = "TextAlignmentLeft";
const TextAlignmentRight = "TextAlignmentRight";

function Vec2(x, y) {
    this.x = x;
    this.y = y;
}

function Vec4(x, y, z, w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
}

var redColor = new Vec4(255, 0, 0, 255);
var greenColor = new Vec4(0, 255, 0, 255);
var blackColor = new Vec4(0, 0, 0, 255);
var whiteColor = new Vec4(255, 255, 255, 255);
var clearColor = new Vec4(0, 0, 0, 0);

function createColorWithRGBA(r, g, b, a) {
    return new Vec4(r, g, b, a);
}

function createFrameWithRect(x, y, width, height) {
    return new Vec4(x, y, width, height);
}

var _vrt_kvo_responserIndex = 0;
var _vrt_kvo_responserCache = {};
var _vrt_kvo_targetCache = {};

function vrt_kvo_bind(target, key, func) {
    var hashKey = key + " KVO " + target._vrtId;
    if (_vrt_kvo_targetCache[hashKey] == undefined) {
        var vrt_kvo_target = new Object();
        vrt_kvo_target.target = target;
        vrt_kvo_target.targetValue = target[key];
        _vrt_kvo_targetCache[hashKey] = vrt_kvo_target;
    }

    var repHashKey = hashKey + "REPINDEXMASK" + _vrt_kvo_responserIndex++;
    var vrt_kvo_responser = new Object();
    vrt_kvo_responser.callBack = func;

    if (_vrt_kvo_responserCache[repHashKey] == undefined) {
        _vrt_kvo_responserCache[repHashKey] = new Array();

        Object.defineProperty(target, key, {
            get: function () {
                return _vrt_kvo_targetCache[hashKey].targetValue;
            },
            set: function (value) {
                var tmpArray;
                if (_vrt_kvo_responserCache[repHashKey] != undefined) {
                    tmpArray = _vrt_kvo_responserCache[repHashKey];
                    tmpArray.forEach(element => {
                        element.callBack(_vrt_kvo_targetCache[hashKey].target, key, _vrt_kvo_targetCache[hashKey].targetValue, value);
                    });
                }
                _vrt_kvo_targetCache[hashKey].targetValue = value;
            }
        });
    }

    _vrt_kvo_responserCache[repHashKey].push(vrt_kvo_responser);
}





var _vrt_callback_cache = {};

function _addCallBack(key, func) {
    _vrt_callback_cache[key] = func;
}

function _api_responseBasicCallBack(key) {
    var func = _vrt_callback_cache[key];
    if (func != null && func != undefined)
        func();
}

function _api_responseListDidSelectRow(key, section, row) {
    var func = _vrt_callback_cache[key];
    if (func != null && func != undefined)
        func(section, row);
}

function _api_responseTextFieldReturn(key, text) {
    var func = _vrt_callback_cache[key];
    if (func != null && func != undefined)
        func(text);
}


/**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**/
function vrt_layout(_masterView) {

    this.masterView = _masterView;
    this.pendCenterX = null;
    this.pendCenterY = null;

    this._resolvePendCenterX = function () {
        if (this.pendCenterX != null) {
            this.masterView._x = this.pendCenterX - this.masterView._width / 2.0;
        }
    }

    this._resolvePendCenterY = function () {
        if (this.pendCenterY != null) {
            this.masterView._y = this.pendCenterY - this.masterView._height / 2.0;
        }
    }

    this.heightIs = function (height) {
        this.masterView._height = height;
        this._resolvePendCenterY();
        return this;
    }

    this.widthIs = function (width) {
        this.masterView._width = width;
        this._resolvePendCenterX();
        return this;
    }

    this.widthEqualToHeight = function () {
        this.masterView._width = this.masterView.height();
        this._resolvePendCenterX();
        return this;
    }

    this.heightEqualToWidth = function () {
        this.masterView._height = this.masterView.width();
        this._resolvePendCenterY();
        return this;
    }

    this.heightRatioToView = function (view, scale) {
        if (view == null)
            view = this.masterView.superView;
        this.masterView._height = view.height() * scale;
        this._resolvePendCenterY();
        return this;
    }

    this.widthRatioToView = function (view, scale) {
        if (view == null)
            view = this.masterView.superView;
        this.masterView._width = view.width() * scale;
        return this;
    }

    this.centerYEqualToView = function (view) {
        if (view == null)
            view = this.masterView.superView;
        if (this.masterView.superView == view)
            this.pendCenterY = view.height() / 2.0;
        else
            this.pendCenterY = view.center().y;
        return this;
    }

    this.centerYIs = function (y) {
        this.pendCenterY = y;
        return this;
    }

    this.topEqualToView = function (view) {
        if (view == null)
            view = this.masterView.superView;
        this.pendCenterY = null;
        if (this.masterView.superView == view)
            this.masterView._y = 0;
        else
            this.masterView._y = view._y;
        return this;
    }

    this.topSpaceToView = function (view, space) {
        space = space;
        if (view == null)
            view = this.masterView.superView;
        this.pendCenterY = null;
        if (this.masterView.superView == view)
            this.masterView._y = space;
        else
            this.masterView._y = space + view.bottom();
        return this;
    }

    this.centerXEqualToView = function (view) {
        if (view == null)
            view = this.masterView.superView;
        if (this.masterView.superView == view)
            this.pendCenterX = view.width() / 2.0;
        else
            this.pendCenterX = view.center().x;
        return this;
    }

    this.centerXIs = function (x) {
        this.pendCenterX = x;
        return this;
    }

    this.leftEqualToView = function (view) {
        if (view == null)
            view = this.masterView.superView;
        this.pendCenterX = null;
        if (this.masterView.superView == view)
            this.masterView._x = 0;
        else
            this.masterView._x = view.left();
        return this;
    }

    this.leftSpaceToView = function (view, space) {
        space = space;
        if (view == null)
            view = this.masterView.superView;
        this.pendCenterX = null;
        if (this.masterView.superView == view)
            this.masterView._x = space;
        else
            this.masterView._x = view.right() + space;
        return this;
    }
}

function _kvo_add_refresh_prop(view, key) {
    vrt_kvo_bind(view, key, function (target, key, oldVlaue, newValue) {
        api_refreshView(target._vrtId, key, newValue);
    });
}

function _registerRefreshView(view) {
    _kvo_add_refresh_prop(view, '_x');
    _kvo_add_refresh_prop(view, '_y');
    _kvo_add_refresh_prop(view, '_height');
    _kvo_add_refresh_prop(view, '_width');

    _kvo_add_refresh_prop(view, 'backgroundColor');
    _kvo_add_refresh_prop(view, 'cornerRadius');
}

function View() {
    this._vrtId = _vrtViewIndex++;

    this._x = null;
    this._y = null;
    this._height = null;
    this._width = null;
    this._enabledUserInteraction = false;

    this.superView = null;
    this._clsName = 'View';
    this.subViews = new Array();
    this.backgroundColor = clearColor;
    this.vrt_layout = new vrt_layout(this);

    this.cornerRadius = 0;

    _registerRefreshView(this);

    this.setFrame = function (x, y, width, height) {
        this._x = x;
        this._y = y;
        this._width = width;
        this._height = height;
    }

    this.addClick = function (func) {
        _addCallBack(this._vrtId, func);
        this._enabledUserInteraction = true;
    }

    this.addSubView = function (subView) {
        if (subView == this || subView == null) {
            return;
        }
        this.subViews.push(subView);
        subView.superView = this;
    }

    this.top = function () {
        if (this._y == null)
            this._y = this.vrt_layout._getTop();
        return this._y;
    }

    this.left = function () {
        if (this._x == null)
            this._x = this.vrt_layout._getLeft();
        return this._x;
    }

    this.right = function () {
        return this.left() + this.width();
    }

    this.bottom = function () {
        return this.top() + this.height();
    }

    this.height = function () {
        if (this._height == null)
            this._height = this.vrt_layout._getHeight();
        return this._height;
    }

    this.width = function () {
        if (this._width == null)
            this._width = this.vrt_layout._getWidth();
        return this._width;
    }

    this.center = function () {
        return new Vec2((this.left() + this.right()) / 2.0, (this.top() + this.bottom()) / 2.0);
    }
}


function ViewController(hasNavigationBar, hasTabBar) {
    this._vrtId = -1;
    this.view = new View();
    this.view.setFrame(0.0, 0.0, 750.0, 1334.0);
    this.view.backgroundColor = whiteColor;
    this.view.superView = null;
    this._clsName = 'ViewController';
    this.title = "title";

    _kvo_add_refresh_prop(this, 'title');

    this.addCallBackViewDidLoad = function (func) {
        _addCallBack("CallBackViewDidLoad", func);
    }

    this.addCallBackViewWillAppear = function (func) {
        _addCallBack("CallBackViewWillAppear", func);
    }

    this.addCallBackViewDidAppear = function (func) {
        _addCallBack("CallBackViewDidAppear", func);
    }

    this.addCallBackViewWillDisappear = function (func) {
        _addCallBack("CallBackViewWillDisappear", func);
    }
}


function Label() {
    View.call(this);
    this._clsName = 'Label';
    this.text = '';
    this.fontSize = 30;
    this.textColor = blackColor;
    this.numberOfLines = 1;
    this.textAlignment = TextAlignmentLeft;

    _kvo_add_refresh_prop(this, 'text');
    _kvo_add_refresh_prop(this, 'fontSize');
    _kvo_add_refresh_prop(this, 'textColor');
    _kvo_add_refresh_prop(this, 'numberOfLines');
    _kvo_add_refresh_prop(this, 'textAlignment');
}

function ImgView() {
    View.call(this);
    this._clsName = 'ImgView';
    this.imageUrl = null;
    _kvo_add_refresh_prop(this, 'imageUrl');
}

function List() {
    View.call(this);
    this._clsName = 'List';

    this.addCallBackDidSelectRowAtIndexPath = function (func) {
        _addCallBack(this._vrtId + "CallBackDidSelectRowAtIndexPath", func);
    }

    this.reloadData = function (numberOfSections, offset, length, rowDataAtSection) {
        api_refreshListData(this._vrtId, numberOfSections, offset, length, rowDataAtSection);
    }
}

function Cell() {
    View.call(this);
    this._clsName = 'Cell';
    this.setCellFixHeight = function (fixHeight) {
        this.setFrame(0, 0, 750, fixHeight);
    }
}

function TextField() {
    View.call(this);
    this._clsName = "TextField";
    this.text = "";
    this.fontSize = 15;
    this.textColor = blackColor;
    this.textAlignment = TextAlignmentLeft;

    _kvo_add_refresh_prop(this, 'text');
    _kvo_add_refresh_prop(this, 'fontSize');
    _kvo_add_refresh_prop(this, 'textColor');
    _kvo_add_refresh_prop(this, 'textAlignment');

    this.addCallBackDidReturn = function (func) {
        _addCallBack(this._vrtId + "CallBackDidReturn", func);
    }

    var _this = this;
    _addCallBack(this._vrtId + "CallBackDidChange", function (text) {
        var hashKey = 'text KVO ' + _this._vrtId;
        if (_vrt_kvo_targetCache[hashKey] != undefined) {
            _vrt_kvo_targetCache[hashKey].targetValue = text;
        }
    });
}




var thisNavigetion = null;
function getThisNavigation() {
    if (thisNavigetion == null) {
        var naDic = api_getThisNavigationComp();
        thisNavigetion = new Navigation();
        thisNavigetion.url = naDic.url;
    }
    return thisNavigetion;

}

function Navigation() {
    this._clsName = "Navigation";
    this.url = "";

    this.pushUrlWithParam = function (url, param) {
        api_pushUrlWithParam(url, param);
    }

    this.popThis = function () {
        api_popThis();
    }

    this.getPushedParam = function () {
        return api_getPushedParam();
    }
}


var _alertActionCache = {};
var _curShowedAlert = null;

function _api_didSelectAlertAction(acId) {
    var func = _alertActionCache[acId];
    if (func != null && func != undefined) {
        func();
    }
    _curShowedAlert = null;

}

function Alert() {
    this._clsName = "Alert";
    this.title = "";
    this.msg = "";

    this._actions = new Array();

    this.addAction = function (action, func) {
        action._acId = this._actions.length;
        _alertActionCache[action._acId] = func;
        this._actions.push(action);
    }

    this.show = function () {
        if (_curShowedAlert == null) {
            api_showAlert(this);
            _curShowedAlert = this;
        }
    }
}


function AlertAction(title) {
    this._acId;
    this._clsName = "AlertAction"
    this.title = title;
}


function dispatchWithAnimation(duration, func) {
    if (api_platform() == "iOS")
        api_dispatchWithAnimation(duration, func);
}

var _vrt_httpRqe_Cache = {};
function HttpRequest(url, func) {
    this.url = url;
    this._param = {};

    this.request = function (param) {
        if (param == null)
            this._param = {};
        else
            this._param = param;
        api_httpRequest(this);
    }

    _vrt_httpRqe_Cache[url] = func;
}

function _api_httpResponse(url, data, info) {
    var httpReqFunc = _vrt_httpRqe_Cache[url];
    if (httpReqFunc) {
        httpReqFunc(data, info);
    }
}

function api_encodeMD5B32(str) {
    return hex_md5(str);
}

//MD5 3rd
/*
 * A JavaScript implementation of the RSA Data Security, Inc. MD5 Message
 * Digest Algorithm, as defined in RFC 1321.
 * Version 2.1 Copyright (C) Paul Johnston 1999 - 2002.
 * Other contributors: Greg Holt, Andrew Kepert, Ydnar, Lostinet
 * Distributed under the BSD License
 * See http://pajhome.org.uk/crypt/md5 for more info.
 */

/*
 * Configurable variables. You may need to tweak these to be compatible with
 * the server-side, but the defaults work in most cases.
 */
var hexcase = 0;  /* hex output format. 0 - lowercase; 1 - uppercase        */
var b64pad = ""; /* base-64 pad character. "=" for strict RFC compliance   */
var chrsz = 8;  /* bits per input character. 8 - ASCII; 16 - Unicode      */

/*
 * These are the functions you'll usually want to call
 * They take string arguments and return either hex or base-64 encoded strings
 */
function hex_md5(s) { return binl2hex(core_md5(str2binl(s), s.length * chrsz)); }
function b64_md5(s) { return binl2b64(core_md5(str2binl(s), s.length * chrsz)); }
function str_md5(s) { return binl2str(core_md5(str2binl(s), s.length * chrsz)); }
function hex_hmac_md5(key, data) { return binl2hex(core_hmac_md5(key, data)); }
function b64_hmac_md5(key, data) { return binl2b64(core_hmac_md5(key, data)); }
function str_hmac_md5(key, data) { return binl2str(core_hmac_md5(key, data)); }

/*
 * Perform a simple self-test to see if the VM is working
 */
function md5_vm_test() {
    return hex_md5("abc") == "900150983cd24fb0d6963f7d28e17f72";
}

/*
 * Calculate the MD5 of an array of little-endian words, and a bit length
 */
function core_md5(x, len) {
    /* append padding */
    x[len >> 5] |= 0x80 << ((len) % 32);
    x[(((len + 64) >>> 9) << 4) + 14] = len;

    var a = 1732584193;
    var b = -271733879;
    var c = -1732584194;
    var d = 271733878;

    for (var i = 0; i < x.length; i += 16) {
        var olda = a;
        var oldb = b;
        var oldc = c;
        var oldd = d;

        a = md5_ff(a, b, c, d, x[i + 0], 7, -680876936);
        d = md5_ff(d, a, b, c, x[i + 1], 12, -389564586);
        c = md5_ff(c, d, a, b, x[i + 2], 17, 606105819);
        b = md5_ff(b, c, d, a, x[i + 3], 22, -1044525330);
        a = md5_ff(a, b, c, d, x[i + 4], 7, -176418897);
        d = md5_ff(d, a, b, c, x[i + 5], 12, 1200080426);
        c = md5_ff(c, d, a, b, x[i + 6], 17, -1473231341);
        b = md5_ff(b, c, d, a, x[i + 7], 22, -45705983);
        a = md5_ff(a, b, c, d, x[i + 8], 7, 1770035416);
        d = md5_ff(d, a, b, c, x[i + 9], 12, -1958414417);
        c = md5_ff(c, d, a, b, x[i + 10], 17, -42063);
        b = md5_ff(b, c, d, a, x[i + 11], 22, -1990404162);
        a = md5_ff(a, b, c, d, x[i + 12], 7, 1804603682);
        d = md5_ff(d, a, b, c, x[i + 13], 12, -40341101);
        c = md5_ff(c, d, a, b, x[i + 14], 17, -1502002290);
        b = md5_ff(b, c, d, a, x[i + 15], 22, 1236535329);

        a = md5_gg(a, b, c, d, x[i + 1], 5, -165796510);
        d = md5_gg(d, a, b, c, x[i + 6], 9, -1069501632);
        c = md5_gg(c, d, a, b, x[i + 11], 14, 643717713);
        b = md5_gg(b, c, d, a, x[i + 0], 20, -373897302);
        a = md5_gg(a, b, c, d, x[i + 5], 5, -701558691);
        d = md5_gg(d, a, b, c, x[i + 10], 9, 38016083);
        c = md5_gg(c, d, a, b, x[i + 15], 14, -660478335);
        b = md5_gg(b, c, d, a, x[i + 4], 20, -405537848);
        a = md5_gg(a, b, c, d, x[i + 9], 5, 568446438);
        d = md5_gg(d, a, b, c, x[i + 14], 9, -1019803690);
        c = md5_gg(c, d, a, b, x[i + 3], 14, -187363961);
        b = md5_gg(b, c, d, a, x[i + 8], 20, 1163531501);
        a = md5_gg(a, b, c, d, x[i + 13], 5, -1444681467);
        d = md5_gg(d, a, b, c, x[i + 2], 9, -51403784);
        c = md5_gg(c, d, a, b, x[i + 7], 14, 1735328473);
        b = md5_gg(b, c, d, a, x[i + 12], 20, -1926607734);

        a = md5_hh(a, b, c, d, x[i + 5], 4, -378558);
        d = md5_hh(d, a, b, c, x[i + 8], 11, -2022574463);
        c = md5_hh(c, d, a, b, x[i + 11], 16, 1839030562);
        b = md5_hh(b, c, d, a, x[i + 14], 23, -35309556);
        a = md5_hh(a, b, c, d, x[i + 1], 4, -1530992060);
        d = md5_hh(d, a, b, c, x[i + 4], 11, 1272893353);
        c = md5_hh(c, d, a, b, x[i + 7], 16, -155497632);
        b = md5_hh(b, c, d, a, x[i + 10], 23, -1094730640);
        a = md5_hh(a, b, c, d, x[i + 13], 4, 681279174);
        d = md5_hh(d, a, b, c, x[i + 0], 11, -358537222);
        c = md5_hh(c, d, a, b, x[i + 3], 16, -722521979);
        b = md5_hh(b, c, d, a, x[i + 6], 23, 76029189);
        a = md5_hh(a, b, c, d, x[i + 9], 4, -640364487);
        d = md5_hh(d, a, b, c, x[i + 12], 11, -421815835);
        c = md5_hh(c, d, a, b, x[i + 15], 16, 530742520);
        b = md5_hh(b, c, d, a, x[i + 2], 23, -995338651);

        a = md5_ii(a, b, c, d, x[i + 0], 6, -198630844);
        d = md5_ii(d, a, b, c, x[i + 7], 10, 1126891415);
        c = md5_ii(c, d, a, b, x[i + 14], 15, -1416354905);
        b = md5_ii(b, c, d, a, x[i + 5], 21, -57434055);
        a = md5_ii(a, b, c, d, x[i + 12], 6, 1700485571);
        d = md5_ii(d, a, b, c, x[i + 3], 10, -1894986606);
        c = md5_ii(c, d, a, b, x[i + 10], 15, -1051523);
        b = md5_ii(b, c, d, a, x[i + 1], 21, -2054922799);
        a = md5_ii(a, b, c, d, x[i + 8], 6, 1873313359);
        d = md5_ii(d, a, b, c, x[i + 15], 10, -30611744);
        c = md5_ii(c, d, a, b, x[i + 6], 15, -1560198380);
        b = md5_ii(b, c, d, a, x[i + 13], 21, 1309151649);
        a = md5_ii(a, b, c, d, x[i + 4], 6, -145523070);
        d = md5_ii(d, a, b, c, x[i + 11], 10, -1120210379);
        c = md5_ii(c, d, a, b, x[i + 2], 15, 718787259);
        b = md5_ii(b, c, d, a, x[i + 9], 21, -343485551);

        a = safe_add(a, olda);
        b = safe_add(b, oldb);
        c = safe_add(c, oldc);
        d = safe_add(d, oldd);
    }
    return Array(a, b, c, d);

}

/*
 * These functions implement the four basic operations the algorithm uses.
 */
function md5_cmn(q, a, b, x, s, t) {
    return safe_add(bit_rol(safe_add(safe_add(a, q), safe_add(x, t)), s), b);
}
function md5_ff(a, b, c, d, x, s, t) {
    return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
}
function md5_gg(a, b, c, d, x, s, t) {
    return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
}
function md5_hh(a, b, c, d, x, s, t) {
    return md5_cmn(b ^ c ^ d, a, b, x, s, t);
}
function md5_ii(a, b, c, d, x, s, t) {
    return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
}

/*
 * Calculate the HMAC-MD5, of a key and some data
 */
function core_hmac_md5(key, data) {
    var bkey = str2binl(key);
    if (bkey.length > 16) bkey = core_md5(bkey, key.length * chrsz);

    var ipad = Array(16), opad = Array(16);
    for (var i = 0; i < 16; i++) {
        ipad[i] = bkey[i] ^ 0x36363636;
        opad[i] = bkey[i] ^ 0x5C5C5C5C;
    }

    var hash = core_md5(ipad.concat(str2binl(data)), 512 + data.length * chrsz);
    return core_md5(opad.concat(hash), 512 + 128);
}

/*
 * Add integers, wrapping at 2^32. This uses 16-bit operations internally
 * to work around bugs in some JS interpreters.
 */
function safe_add(x, y) {
    var lsw = (x & 0xFFFF) + (y & 0xFFFF);
    var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
    return (msw << 16) | (lsw & 0xFFFF);
}

/*
 * Bitwise rotate a 32-bit number to the left.
 */
function bit_rol(num, cnt) {
    return (num << cnt) | (num >>> (32 - cnt));
}

/*
 * Convert a string to an array of little-endian words
 * If chrsz is ASCII, characters >255 have their hi-byte silently ignored.
 */
function str2binl(str) {
    var bin = Array();
    var mask = (1 << chrsz) - 1;
    for (var i = 0; i < str.length * chrsz; i += chrsz)
        bin[i >> 5] |= (str.charCodeAt(i / chrsz) & mask) << (i % 32);
    return bin;
}

/*
 * Convert an array of little-endian words to a string
 */
function binl2str(bin) {
    var str = "";
    var mask = (1 << chrsz) - 1;
    for (var i = 0; i < bin.length * 32; i += chrsz)
        str += String.fromCharCode((bin[i >> 5] >>> (i % 32)) & mask);
    return str;
}

/*
 * Convert an array of little-endian words to a hex string.
 */
function binl2hex(binarray) {
    var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
    var str = "";
    for (var i = 0; i < binarray.length * 4; i++) {
        str += hex_tab.charAt((binarray[i >> 2] >> ((i % 4) * 8 + 4)) & 0xF) +
            hex_tab.charAt((binarray[i >> 2] >> ((i % 4) * 8)) & 0xF);
    }
    return str;
}

/*
 * Convert an array of little-endian words to a base-64 string
 */
function binl2b64(binarray) {
    var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    var str = "";
    for (var i = 0; i < binarray.length * 4; i += 3) {
        var triplet = (((binarray[i >> 2] >> 8 * (i % 4)) & 0xFF) << 16)
            | (((binarray[i + 1 >> 2] >> 8 * ((i + 1) % 4)) & 0xFF) << 8)
            | ((binarray[i + 2 >> 2] >> 8 * ((i + 2) % 4)) & 0xFF);
        for (var j = 0; j < 4; j++) {
            if (i * 8 + j * 6 > binarray.length * 32) str += b64pad;
            else str += tab.charAt((triplet >> 6 * (3 - j)) & 0x3F);
        }
    }
    return str;
}