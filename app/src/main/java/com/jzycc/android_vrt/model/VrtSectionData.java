package com.jzycc.android_vrt.model;

import java.util.List;
import java.util.Map;

public class VrtSectionData {
    private Integer numberOfSections;
    private Map<String, List<VrtViewData>> rowDataAtSection;
    private String _vrtId;

    public VrtSectionData(Integer numberOfSections, Map<String, List<VrtViewData>> rowDataAtSection, String _vrtId) {
        this.numberOfSections = numberOfSections;
        this.rowDataAtSection = rowDataAtSection;
        this._vrtId = _vrtId;
    }

    public Integer getNumberOfSections() {
        return numberOfSections;
    }

    public void setNumberOfSections(Integer numberOfSections) {
        this.numberOfSections = numberOfSections;
    }

    public Map<String, List<VrtViewData>> getRowDataAtSection() {
        return rowDataAtSection;
    }

    public void setRowDataAtSection(Map<String, List<VrtViewData>> rowDataAtSection) {
        this.rowDataAtSection = rowDataAtSection;
    }

    public String get_vrtId() {
        return _vrtId;
    }

    public void set_vrtId(String _vrtId) {
        this._vrtId = _vrtId;
    }
}
