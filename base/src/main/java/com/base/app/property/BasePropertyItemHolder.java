package com.base.app.property;

import android.content.Context;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.BR;
import com.base.app.util.ConverterUtil;



public abstract class BasePropertyItemHolder<V> extends BaseBindingViewHolder<PropertyItem> {

    public BasePropertyItemHolder(View itemView) {
        super(itemView);
    }

    public BasePropertyItemHolder(ViewGroup viewGroup, @LayoutRes int contentRes) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(contentRes, viewGroup, false));
    }

    public BasePropertyItemHolder(Context context, ViewGroup viewGroup, @LayoutRes int contentRes) {
        super(context, LayoutInflater.from(context).inflate(contentRes, viewGroup, false));
    }

    private Observable.OnPropertyChangedCallback changedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            PropertyItem propertyItem = getDataModel();
            if (observable == propertyItem) {
                onPropertyItemChanged(propertyItem, i);
            }
        }
    };

    protected void onPropertyItemChanged(@Nullable PropertyItem propertyItem, int i) {
        if (i == BR.value) {
            onPropertyItemValueChanged(propertyItem == null ? null : propertyItem.getValue());
        }
    }

    public Class<?> getValueType() {
        PropertyItem dataModel = getDataModel();
        return dataModel == null ? null : dataModel.getValueType();
    }

    protected void onPropertyItemValueInstall(@Nullable Object value) {
        onPropertyItemValueChanged(value);
    }

    protected void onPropertyItemValueChanged(@Nullable Object value) {
        V v = convertFromPropertyValue(value);
        refreshToUI(v);
    }

    protected Object convertToPropertyValue(@Nullable V value) {
        Object saveValue = value;
        Class<?> valueType = getValueType();
        if (value != null && valueType != null) {
            saveValue = ConverterUtil.to(valueType, value);
        }
        return saveValue;
    }

    protected V convertFromPropertyValue(Object value) {
        return (V) value;
    }

    protected void saveToPropertyItem(@Nullable V value) {
        Object saveValue = convertToPropertyValue(value);
        PropertyItem propertyItem = getDataModel();
        if (propertyItem != null) {
            propertyItem.setValue(saveValue);
        }
    }

    protected void refreshToUI(V value) {

    }

    @Override
    protected void onBindModel(ViewDataBinding dataBinding, PropertyItem propertyItem) {
        super.onBindModel(dataBinding, propertyItem);
        dataBinding.setVariable(BR.propertyItem, propertyItem);
        if (propertyItem != null) {
            Object value = propertyItem.getValue();
            onPropertyItemValueInstall(value);
            propertyItem.addOnPropertyChangedCallback(changedCallback);
        }
    }

    @Override
    protected void onRecycled() {
        PropertyItem propertyItem = getDataModel();
        if (propertyItem != null) {
            propertyItem.removeOnPropertyChangedCallback(changedCallback);
        }
        super.onRecycled();
    }
}
