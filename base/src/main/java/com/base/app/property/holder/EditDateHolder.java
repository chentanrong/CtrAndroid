package com.base.app.property.holder;

import android.app.DatePickerDialog;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;


import com.base.app.property.PropertyItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class EditDateHolder extends EditTextHolder {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            showPicker();
        }
    };
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            Date date = calendar.getTime();
            saveToPropertyItem(dateFormat.format(date));
        }
    };

    private void showPicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getItemView().getContext(), onDateSetListener, year, monthOfYear,
                dayOfMonth);
        datePickerDialog.show();
    }

    public EditDateHolder(ViewGroup viewGroup) {
        super(viewGroup);
    }


    @Override
    protected void onBindModel(ViewDataBinding dataBinding, PropertyItem dataModel) {
        super.onBindModel(dataBinding, dataModel);
        EditText editText = getEditText();
        if (editText != null) {
            editText.setFocusable(false);
            editText.setOnClickListener(onClickListener);
        }
    }


    @Override
    protected void onRecycled() {
        EditText editText = getEditText();
        if (editText != null) {
            editText.setOnClickListener(null);
        }
        super.onRecycled();
    }

    @Override
    public String convertFromPropertyValue(Object data) {
        if (data instanceof Date) {
            return dateFormat.format((Date) data);
        } else if (data instanceof String) {
            return (String) data;
        }
        return null;
    }

    @Override
    public Object convertToPropertyValue(String value) {
        Class<?> valueType = getValueType();
        if (valueType == null) {
            return null;
        }
        if (Date.class.isAssignableFrom(valueType)) {
            try {
                return dateFormat.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else if (String.class.isAssignableFrom(valueType)) {
            return value;
        }
        return null;
    }
}
