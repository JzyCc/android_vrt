package com.jzycc.android_vrt.model;

import java.util.HashMap;
import java.util.List;

/**
 * @author Jzy
 * created by 2018/10/2
 */
public class VrtViewData {
    private String _vrtId;
    private float _x;
    private float _y;
    private float _height;
    private float _width;
    private String _clsName;
    private List<VrtViewData> subViews;
    private VrtColor backgroundColor;
    private float cornerRadius;

    //EditView TextField Label
    private String text;
    private Float fontSize;
    private VrtColor textColor;
    private Integer numberOfLines;
    //ImageView
    private String imageUrl;
    //Item
    private HashMap<Integer,VrtViewData> _cell;
    private HashMap<Integer, List<Object>> _dataSource;

    public String get_vrtId() {
        return _vrtId;
    }

    public void set_vrtId(String _vrtId) {
        this._vrtId = _vrtId;
    }

    public float get_x() {
        return _x;
    }

    public void set_x(float _x) {
        this._x = _x;
    }

    public float get_y() {
        return _y;
    }

    public void set_y(float _y) {
        this._y = _y;
    }

    public float get_height() {
        return _height;
    }

    public void set_height(float _height) {
        this._height = _height;
    }

    public float get_width() {
        return _width;
    }

    public void set_width(float _width) {
        this._width = _width;
    }

    public String get_clsName() {
        return _clsName;
    }

    public void set_clsName(String _clsName) {
        this._clsName = _clsName;
    }

    public List<VrtViewData> getSubViews() {
        return subViews;
    }

    public void setSubViews(List<VrtViewData> subViews) {
        this.subViews = subViews;
    }

    public VrtColor getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(VrtColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Float getFontSize() {
        return fontSize;
    }

    public void setFontSize(Float fontSize) {
        this.fontSize = fontSize;
    }

    public VrtColor getTextColor() {
        return textColor;
    }

    public void setTextColor(VrtColor textColor) {
        this.textColor = textColor;
    }

    public Integer getNumberOfLines() {
        return numberOfLines;
    }

    public void setNumberOfLines(Integer numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public HashMap<Integer, VrtViewData> get_cell() {
        return _cell;
    }

    public void set_cell(HashMap<Integer, VrtViewData> _cell) {
        this._cell = _cell;
    }

    public HashMap<Integer, List<Object>> get_dataSource() {
        return _dataSource;
    }

    public void set_dataSource(HashMap<Integer, List<Object>> _dataSource) {
        this._dataSource = _dataSource;
    }
}
