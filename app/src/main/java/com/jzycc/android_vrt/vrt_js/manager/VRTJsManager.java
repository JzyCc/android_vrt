package com.jzycc.android_vrt.vrt_js.manager;

import android.util.Log;
import android.view.View;

import com.jzycc.android_vrt.vrt_js.VRTJsEngine;
import com.jzycc.android_vrt.vrt_js.constant.FunctionName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jzy
 * date   : 18-10-10
 */
public class VRTJsManager {
    private VRTJsEngine vrtJsEngine;

    public VRTJsManager(VRTJsEngine vrtJsEngine) {
        this.vrtJsEngine = vrtJsEngine;
    }

    private List<String> clickableViewVrtIds = new ArrayList<>();

    public List<String> getClickableViewVrtIds() {
        return clickableViewVrtIds;
    }

    public void setClickableViewVrtIds(List<String> clickableViewVrtIds) {
        this.clickableViewVrtIds = clickableViewVrtIds;
    }


    public void setClickListenerForView(final View view, final String vrtId){
        for(String _vrtId: clickableViewVrtIds){
            if(vrtId.equals(_vrtId)){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vrtJsEngine.callFunction(FunctionName.API_RESPONSE_BASIC_CALL_BACK,new Object[]{vrtId});
                    }
                });
            }
        }
    }

    public void setClickListenerForCell(final View view, final String vrtId, final int type, final int position){
        if(view!=null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("jzy", "onClick: "+type+"，"+position);
                    vrtJsEngine.callFunction(FunctionName.API_RESPONSE_LIST_DID_SELECT_ROW,new Object[]{vrtId,type,position});
                }
            });
        }
    }
}
