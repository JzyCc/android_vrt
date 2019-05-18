package com.jzycc.android_vrt.vrt.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jzycc.android_vrt.model.VrtSectionData;
import com.jzycc.android_vrt.model.VrtViewData;
import com.jzycc.android_vrt.vrt.VrtCell;
import com.jzycc.android_vrt.vrt_js.VRTJsEngine;
import com.jzycc.android_vrt.vrt_js.manager.VRTJsManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Jzy
 * created by 2018/10/8
 */
public class VrtListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private int nowPosition = 0;
    private VRTJsManager vrtJsManager;
    private List<VrtViewData> mList;
    private static int typeNumber;
    private String listId;

    public VrtListAdapter(Context mContext, VRTJsManager vrtJsManager, String listId,  List<VrtViewData> mList) {
        this.mContext = mContext;
        this.vrtJsManager = vrtJsManager;
        this.mList = mList;
        this.listId = listId;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new VrtCell(mContext,vrtJsManager, listId, mList.get(nowPosition),nowPosition));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        nowPosition = position;
        return 0;
    }

    public static List<VrtViewData> getList(VrtSectionData vrtSectionData){
        List<VrtViewData> list = new ArrayList<>();
        typeNumber = vrtSectionData.getNumberOfSections();
        for (String key : vrtSectionData.getRowDataAtSection().keySet()){
            list.addAll(vrtSectionData.getRowDataAtSection().get(key));
        }

        return list;
    }
}
