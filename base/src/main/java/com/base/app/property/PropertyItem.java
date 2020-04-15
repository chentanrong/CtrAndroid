package com.base.app.property;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.base.BR;
import com.base.app.util.ObjectUtil;

public class PropertyItem extends BaseObservable implements Comparable<PropertyItem> {
    private String key;
    private String label;
    private Object value;
    private boolean visible;
    private boolean enable;
    private boolean notnull;
    private int index;
    private int viewType;
    private String errorMessage;
    private Class<?> valueType;
    private Object tag;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Bindable
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if (TextUtils.equals(this.label, label)) {
            return;
        }
        this.label = label;
        notifyPropertyChanged(BR.label);
    }

    @Bindable
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        if (ObjectUtil.equals(this.value, value)) {
            return;
        }
        this.value = value;
        notifyPropertyChanged(BR.value);
    }

    @Bindable
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        if (this.visible == visible) {
            return;
        }
        this.visible = visible;
        notifyPropertyChanged(BR.visible);
    }

    @Bindable
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        if (this.enable == enable) {
            return;
        }
        this.enable = enable;
        notifyPropertyChanged(BR.enable);
    }

    @Bindable
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        if (TextUtils.equals(this.errorMessage, errorMessage)) {
            return;
        }
        this.errorMessage = errorMessage;
        notifyPropertyChanged(BR.errorMessage);
    }

    public boolean validate() {
        if (isNotnull()) {
            if (isNull(getValue())) {
                setErrorMessage(getLabel() + "不能为空");
                return false;
            }
        }
        setErrorMessage(null);
        return true;
    }

    private boolean isNull(Object data) {
        if (data instanceof String) {
            return ((String) data).length() == 0;
        } else {
            return data == null;
        }
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Bindable
    public boolean isNotnull() {
        return notnull;
    }

    public void setNotnull(boolean notnull) {
        if (this.notnull == notnull) {
            return;
        }
        this.notnull = notnull;
        notifyPropertyChanged(BR.notnull);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int compareTo(@NonNull PropertyItem another) {
        return getIndex() - another.getIndex();
    }

    public Class<?> getValueType() {
        return valueType;
    }

    public void setValueType(Class<?> valueType) {
        this.valueType = valueType;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
