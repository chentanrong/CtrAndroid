package com.base.app.property;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsRecyclerAdapter<MODEL, VH extends AbsRecyclerViewHolder<MODEL>> extends RecyclerView.Adapter<VH> {
    private Context context;
    private LayoutInflater inflater;
    private List<MODEL> data;
    private List<MODEL> rawData;
    private IModelFilter<MODEL> modelFilter;

    public AbsRecyclerAdapter(@NonNull Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    public Context getContext() {
        return context;
    }

    @Nullable
    public List<MODEL> getData() {
        return data;
    }

    public void setData(@Nullable List<MODEL> data) {
        this.data = this.rawData = data;
        applyFilter();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public MODEL getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return onCreateViewHolder(getView(parent, viewType));
    }

    public VH onCreateViewHolder(View view) {
        return null;
    }

    protected View getView(ViewGroup parent, int viewType) {
        int id = getViewResId(viewType);
        if (id > 0) {
            return inflater.inflate(id, parent, false);
        }
        return null;
    }

    @LayoutRes
    protected int getViewResId(int viewType) {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        MODEL model = getItem(position);
        holder.onBindModel(model);
    }

    @Override
    public void onViewRecycled(@NonNull VH holder) {
        super.onViewRecycled(holder);
        holder.onRecycled();
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public IModelFilter<MODEL> getModelFilter() {
        return modelFilter;
    }

    public void setModelFilter(IModelFilter<MODEL> modelFilter) {
        this.modelFilter = modelFilter;
        applyFilter();
    }

    private void applyFilter() {
        List<MODEL> data = this.rawData;
        if (modelFilter != null && data != null) {
            List<MODEL> filteredData = new ArrayList<>();
            for (MODEL model : data) {
                if (modelFilter.filter(model)) {
                    filteredData.add(model);
                }
            }
            this.data = filteredData;
        }
    }

}
