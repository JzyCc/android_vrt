package com.jzycc.android_vrt.vrt.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jzycc.android_vrt.model.VrtViewData;
import com.jzycc.android_vrt.vrt.VrtCell;
import com.jzycc.android_vrt.vrt_js.VRTJsEngine;
import com.jzycc.android_vrt.vrt_js.manager.VRTJsManager;

/**
 * @author Jzy
 * created by 2018/10/8
 */
public class VrtListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private VrtViewData vrtViewData;
    private int nowPosition = 0;
    private VRTJsManager vrtJsManager;

    public VrtListAdapter(Context mContext, VRTJsManager vrtJsManager, VrtViewData vrtViewData) {
        this.mContext = mContext;
        this.vrtViewData = vrtViewData;
        this.vrtJsManager = vrtJsManager;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new VrtCell(mContext,vrtJsManager,vrtViewData.get_cell().get(viewType),vrtViewData.get_dataSource().get(viewType).get(nowPosition),viewType,nowPosition));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return getCellCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        nowPosition = getPositionForJs(position);
        return getCellType(position);
    }

    /**
     * @param position
     * @return jsPosition
     * get position for JS because low b iOS
     */
    private int getPositionForJs(int position){
        int jsPosition = 0;
        int allPosition = 0;
        for(int i = 0; i < vrtViewData.get_cell().size(); i++){
            int nowCellCount = vrtViewData.get_dataSource().get(i).size();
            if(position <= (allPosition + nowCellCount -1)){
                jsPosition = position-allPosition;
                break;
            }
            allPosition = allPosition + nowCellCount-1;
        }
        return jsPosition;
    }

    /**
     * @param position
     * @return itemType
     * get itemview type
     */
    private int getCellType(int position){
        int type = 0;
        int allCellCount = 0;
        for(int i = 0; i < vrtViewData.get_cell().size(); i++){
            allCellCount += allCellCount;
            if(position < allCellCount){
                type = i;
                break;
            }
        }
        return type;
    }

    /**
     * @return itemCount
     * get itemview count
     */
    private int getCellCount(){
        int count = 0;
        for(int i = 0; i < vrtViewData.get_cell().size(); i++){
            count+=vrtViewData.get_dataSource().get(i).size();
        }
        return count;
    }
}
