package com.jzycc.android_vrt.utils;

import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * @author : Jzy
 * date   : 18-10-9
 */
public class ViewClickUtils {
    public static void setClickListenerForView(View view, List<String> vrtIds, final String vrtId){
        for(String _vrtId: vrtIds){
            if(vrtId.equals(_vrtId)){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("jzy111", "onClick: "+vrtId);
                    }
                });
            }
        }
    }
}
