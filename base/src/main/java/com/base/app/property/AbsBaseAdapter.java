
package com.base.app.property;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.base.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public abstract class AbsBaseAdapter<MODEL> extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<MODEL> data;
    private IModelFilter<MODEL> modelFilter;
    private List<MODEL> rawData;

    public AbsBaseAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public MODEL getItem(int position) {
        if (data == null) {
            return null;
        }
        return data.get(position);
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    @NotNull
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = getNewView(inflater, position, parent);
            onViewCreated(view);
        } else {
            onViewRecycled(view);
        }
        bindView(position, view);
        return view;
    }

    protected void onViewCreated(@NotNull View view) {
        IViewHolder<MODEL> viewHolder = getNewHolder(view);
        view.setTag(R.id.tag_holder, viewHolder);
    }

    protected void onViewRecycled(@NotNull View view) {
        IViewHolder<MODEL> holder = findHolder(view);
        if (holder != null) {
            holder.unbind();
        }
    }

    @SuppressWarnings("unchecked")
    private IViewHolder<MODEL> findHolder(@NotNull View view) {
        Object object = view.getTag(R.id.tag_holder);
        if (object instanceof IViewHolder<?>) {
            return (IViewHolder<MODEL>) object;
        }
        return null;
    }

    public List<MODEL> getRawData() {
        return rawData;
    }

    public List<MODEL> getData() {
        return data;
    }

    public void setData(List<MODEL> data) {
        this.data = this.rawData = data;
        applyFilter();
    }

    @NotNull
    protected View getNewView(LayoutInflater inflater, int position, ViewGroup parent) {
        int id = getViewResId(position);
        if (id > 0) {
            return inflater.inflate(id, parent, false);
        }
        throw new IllegalArgumentException("resid must be greater than 0");
    }

    @LayoutRes
    protected int getViewResId(int position) {
        return 0;
    }

    protected void bindView(int position, View view) {
        IViewHolder<MODEL> holder = findHolder(view);
        if (holder != null) {
            MODEL model = getItem(position);
            holder.bind(position, model);
        }
    }

    protected IViewHolder<MODEL> getNewHolder(@NotNull View view) {
        return null;
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

    public boolean checkPosition(int position) {
        return position >= 0 && position < getCount();
    }

    public interface IViewHolder<MODEL> {
        void bind(int position, MODEL model);

        void unbind();
    }
}
