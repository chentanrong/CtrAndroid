package com.base.app.property;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.base.app.AbsRecyclerViewHolder;


public class BaseBindingViewHolder<MODEL> extends AbsRecyclerViewHolder<MODEL> {
    private ViewDataBinding dataBinding;

    public BaseBindingViewHolder(View itemView) {
        this(itemView.getContext(), itemView);
    }

    public BaseBindingViewHolder(Context context, View itemView) {
        super(context, itemView);
        setDataBinding(DataBindingUtil.bind(itemView));
    }

    @Override
    protected void onBindModel(MODEL dataModel) {
        super.onBindModel(dataModel);
        ViewDataBinding dataBinding = getDataBinding();
        if (dataBinding != null) {
            onBindModel(dataBinding, dataModel);
        }
    }

    @Override
    protected void onRecycled() {
        if (dataBinding != null)
            dataBinding.unbind();
        super.onRecycled();
    }

    protected void onBindModel(ViewDataBinding dataBinding, MODEL dataModel) {
    }

    public ViewDataBinding getDataBinding() {
        return dataBinding;
    }

    public void setDataBinding(ViewDataBinding dataBinding) {
        this.dataBinding = dataBinding;
    }
}
