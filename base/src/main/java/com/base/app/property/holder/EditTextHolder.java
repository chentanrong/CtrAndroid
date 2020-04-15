package com.base.app.property.holder;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.base.R;
import com.base.app.property.BasePropertyItemHolder;
import com.base.app.property.PropertyItem;
import com.base.app.util.TextViewUtil;


public class EditTextHolder extends BasePropertyItemHolder<String> implements TextWatcher {
    protected EditText editText;

    public EditTextHolder(ViewGroup viewGroup, @LayoutRes int id) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(id, viewGroup, false));
        editText = (EditText) getItemView().findViewById(R.id.p_edit_text);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        saveToPropertyItem(s.toString());
    }

    public EditTextHolder(ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.property_edittext, viewGroup, false));
        editText = (EditText) getItemView().findViewById(R.id.p_edit_text);
    }

    @Override
    protected void onBindModel(ViewDataBinding dataBinding, PropertyItem dataModel) {
        super.onBindModel(dataBinding, dataModel);
        if (editText != null)
            editText.addTextChangedListener(this);
    }

    @Override
    protected void onRecycled() {
        if (editText != null)
            editText.removeTextChangedListener(this);
        super.onRecycled();
    }

    public EditText getEditText() {
        return editText;
    }

    @Override
    public Object convertToPropertyValue(@Nullable String value) {
        return super.convertToPropertyValue(value);
    }

    @Override
    public String convertFromPropertyValue(Object data) {
        return data == null ? null : data.toString();
    }

    @Override
    protected void refreshToUI(String value) {
        super.refreshToUI(value);
        if (editText != null) {
            TextViewUtil.setText(editText, value);
        }
    }
}
