package com.base.app.property.holder;

import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;

import org.apache.commons.lang3.ObjectUtils;
public class EditNumberHolder extends EditTextHolder {

    public EditNumberHolder(ViewGroup viewGroup) {
        super(viewGroup);
        EditText editText = getEditText();
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    @Override
    public Object convertToPropertyValue(String value) {
        return super.convertToPropertyValue(value);
    }

    @Override
    protected void onPropertyItemValueChanged(@Nullable Object value) {
        EditText editText = getEditText();
        if (editText != null) {
            String s = editText.getText().toString();
            Object o = convertToPropertyValue(s);
            if (ObjectUtils.equals(value, o)) {
                return;
            }
        }
        super.onPropertyItemValueChanged(value);
    }

    @Override
    public String convertFromPropertyValue(Object data) {
        return super.convertFromPropertyValue(data);
    }
}
