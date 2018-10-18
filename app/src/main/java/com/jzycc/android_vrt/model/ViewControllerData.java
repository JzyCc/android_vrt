package com.jzycc.android_vrt.model;

/**
 * @author Jzy
 * created by 2018/10/2
 */
public class ViewControllerData {
    protected String _vrtId;
    protected VrtViewData view;
    protected String _clsName;
    protected String title;

    public String get_vrtId() {
        return _vrtId;
    }

    public void set_vrtId(String _vrtId) {
        this._vrtId = _vrtId;
    }

    public VrtViewData getView() {
        return view;
    }

    public void setView(VrtViewData view) {
        this.view = view;
    }

    public String get_clsName() {
        return _clsName;
    }

    public void set_clsName(String _clsName) {
        this._clsName = _clsName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
