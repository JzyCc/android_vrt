package com.jzycc.android_vrt.vrt.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzycc.android_vrt.Ob;
import com.jzycc.android_vrt.model.VrtViewData;
import com.jzycc.android_vrt.vrt.VrtCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Jzy
 * created by 2018/10/8
 */
public class VrtListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private VrtViewData vrtViewData;

    public VrtListAdapter(Context mContext, VrtViewData vrtViewData) {
        this.mContext = mContext;
        this.vrtViewData = vrtViewData;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(new VrtCell(mContext,vrtViewData.get_cell().get("0"),position,0));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        Log.i("jzy111", "getItemCount: "+vrtViewData.get_dataSource());
        return vrtViewData.get_dataSource().get("0").size();
        //return vrtViewData.get_dataSource().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
