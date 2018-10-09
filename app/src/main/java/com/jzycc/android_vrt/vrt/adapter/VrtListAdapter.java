package com.jzycc.android_vrt.vrt.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jzycc.android_vrt.model.VrtViewData;
import com.jzycc.android_vrt.vrt.VrtCell;

/**
 * @author Jzy
 * created by 2018/10/8
 */
public class VrtListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private VrtViewData vrtViewData;
    private int nowPosition = 0;

    public VrtListAdapter(Context mContext, VrtViewData vrtViewData) {
        this.mContext = mContext;
        this.vrtViewData = vrtViewData;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new VrtCell(mContext,vrtViewData.get_cell().get("0"),vrtViewData.get_dataSource().get("0").get(nowPosition)));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return vrtViewData.get_dataSource().get("0").size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        nowPosition = position;
        return super.getItemViewType(position);
    }
}
