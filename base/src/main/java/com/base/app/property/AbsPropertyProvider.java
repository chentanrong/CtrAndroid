package com.base.app.property;

import android.content.Context;
import android.databinding.Observable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class AbsPropertyProvider {
    private final List<PropertyItem> propertyItems = new ArrayList<>();
    private boolean install;
    private boolean trigger;
    private OnItemChangedListener onItemChangedListener;
    private OnDataStatusChangedListener onDataStatusChangedListener;
    private PropertyRecyclerAdapter recyclerAdapter;
    private Context context;
    private boolean dataChanged;
    private String notifyKey;
    private RecyclerView recyclerView;

    public AbsPropertyProvider(Context context) {
        this.context = context;
        recyclerAdapter = new PropertyRecyclerAdapter(context);
    }


    private Observable.OnPropertyChangedCallback innerOnPropertyChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int what) {
            if (install) {
                return;
            }

            PropertyItem propertyItem = (PropertyItem) observable;
            String key = propertyItem.getKey();

            boolean needSet = !trigger;
            if (notifyKey == null) {
                notifyKey = key;
            } else {
                needSet = !notifyKey.equals(key);
            }
            if (needSet) {
                setDataChanged(isDataChanged() | onItemDataChanged(key, what, propertyItem));
            }
            if (onItemChangedListener != null) {
                onItemChangedListener.onChanged(propertyItem, what);
            }
            notifyKey = null;
        }
    };

    protected abstract boolean onItemDataChanged(String key, int what, PropertyItem propertyItem);

    public abstract Object getValue(String key);

    protected void installPropertyItem(int what, boolean needNotify) {
        install = true;
        for (PropertyItem item : propertyItems) {
            item.setValue(getValue(item.getKey()));
        }
        install = false;
        trigger = true;
        dispatchAllItemChanged(what);
        trigger = false;
        if (needNotify) {
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    public PropertyItem getPropertyItem(String key) {
        for (PropertyItem item : propertyItems) {
            if (item.getKey().equals(key)) {
                return item;
            }
        }
        return null;
    }

    public void setModelValue(String key, Object value) {
        for (PropertyItem item : propertyItems) {
            if (item.getKey().equals(key)) {
                item.setValue(value);
                break;
            }
        }
    }

    public void setModelValue(String rowKey, String fillKey, boolean isReplace) {
        Object value2=null;
        for (PropertyItem item : propertyItems) {
            if (item.getKey().equals(rowKey)) {
                value2=item.getValue();
                break;
            }
        }
        if(value2==null)return;
        for (PropertyItem item : propertyItems) {
            if (item.getKey().equals(fillKey)) {
                if (item.getValue() == null) {
                    item.setValue(value2);
                } else if (isReplace) {
                    item.setValue(value2);
                }
                break;
            }
        }
    }

    private void dispatchAllItemChanged(int what) {
        if (onItemChangedListener != null) {
            for (PropertyItem item : propertyItems) {
                onItemChangedListener.onChanged(item, what);
            }
        }
    }

    public void setPropertyItems(List<PropertyItem> propertyItems) {
        clear();
        if (propertyItems != null) {
            this.propertyItems.addAll(propertyItems);
            for (PropertyItem item : this.propertyItems) {
                item.addOnPropertyChangedCallback(innerOnPropertyChangedCallback);
            }
            Collections.sort(this.propertyItems);
        }
        recyclerAdapter.setData(this.propertyItems);
    }

    public void clear() {
        for (PropertyItem item : propertyItems) {
            item.removeOnPropertyChangedCallback(innerOnPropertyChangedCallback);
        }
        propertyItems.clear();
    }

    public List<PropertyItem> getPropertyItems() {
        return propertyItems;
    }

    public void setOnItemChangedListener(OnItemChangedListener onItemChangedListener) {
        this.onItemChangedListener = onItemChangedListener;
    }

    public void setOnDataStatusChangedListener(OnDataStatusChangedListener onDataStatusChangedListener) {
        this.onDataStatusChangedListener = onDataStatusChangedListener;
    }

    public PropertyRecyclerAdapter getRecyclerAdapter() {
        return recyclerAdapter;
    }

    public boolean isDataChanged() {
        return dataChanged;
    }

    public void setDataChanged(boolean dataChanged) {
        if (this.dataChanged == dataChanged) {
            return;
        }
        this.dataChanged = dataChanged;
        if (onDataStatusChangedListener != null) {
            onDataStatusChangedListener.onChanged(dataChanged);
        }
    }

    public boolean validate() {
        boolean flag = true;
        for (PropertyItem item : propertyItems) {
            flag &= item.validate();
        }
        return flag;
    }

    public Context getContext() {
        return context;
    }

    public void initRecycleView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(getRecyclerAdapter());
        this.recyclerView=recyclerView;
    }
    public void setGridRecycleView(int colCount){
        if(this.recyclerView==null)return;
        if(colCount>0){
            this.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),colCount));
            this.recyclerView.setAdapter(getRecyclerAdapter());
        }
    }

    public interface OnItemChangedListener {
        void onChanged(PropertyItem item, int what);
    }

    public interface OnDataStatusChangedListener {
        void onChanged(boolean changed);
    }
}
