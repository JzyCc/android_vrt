package com.jzycc.android_vrt.model;

/**
 * @author : Jzy
 * date   : 18-10-11
 */
public class VrtRefreshMsg {
    private String _vrtId;
    private String _key;
    private Object _newValue;

    public String get_vrtId() {
        return _vrtId;
    }

    public void set_vrtId(String _vrtId) {
        this._vrtId = _vrtId;
    }

    public String get_key() {
        return _key;
    }

    public void set_key(String _key) {
        this._key = _key;
    }

    public Object get_newValue() {
        return _newValue;
    }

    public void set_newValue(Object _newValue) {
        this._newValue = _newValue;
    }
}
