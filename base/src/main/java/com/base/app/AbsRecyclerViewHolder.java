package com.base.app;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class AbsRecyclerViewHolder<ITEM> extends RecyclerView.ViewHolder {
    private ITEM dataModel;
    private Context context;

    public AbsRecyclerViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
    }

    public AbsRecyclerViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
    }

    public View getItemView() {
        return itemView;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        if (itemView == null) {
            return null;
        } else
            return itemView.findViewById(id);
    }

    @CallSuper
    protected void onBindModel(ITEM dataModel) {
        this.dataModel = dataModel;
    }

    @CallSuper
    protected void onRecycled() {
        this.dataModel = null;
    }

    public ITEM getDataModel() {
        return dataModel;
    }

    public Context getContext() {
        return context;
    }
}
