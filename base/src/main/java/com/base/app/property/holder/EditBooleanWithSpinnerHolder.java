package com.base.app.property.holder;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import com.base.R;
import com.base.app.property.BasePropertyItemHolder;
import com.base.app.property.PropertyItem;
import com.base.app.util.ConverterUtil;


public class EditBooleanWithSpinnerHolder extends BasePropertyItemHolder<Boolean> {
    private final static CharSequence[] bools = new CharSequence[]{"是", "否"};
    private AppCompatSpinner compatSpinner;
    private SwitchCompat switchCompat;
    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            saveToPropertyItem(position == 0);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            saveToPropertyItem(isChecked);
        }
    };

    public EditBooleanWithSpinnerHolder(ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.property_boolean_with_spinner, viewGroup, false));
        compatSpinner = (AppCompatSpinner) getItemView().findViewById(R.id.spinner1);
        switchCompat = (SwitchCompat) getItemView().findViewById(R.id.switch1);
        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_spinner_item, bools);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        compatSpinner.setAdapter(arrayAdapter);
    }

    @Override
    protected void onBindModel(ViewDataBinding dataBinding, PropertyItem dataModel) {
        super.onBindModel(dataBinding, dataModel);
        compatSpinner.setOnItemSelectedListener(itemSelectedListener);
        switchCompat.setOnCheckedChangeListener(checkedChangeListener);
    }

    @Override
    protected void onRecycled() {
        compatSpinner.setOnItemSelectedListener(null);
        switchCompat.setOnCheckedChangeListener(null);
        super.onRecycled();
    }

    @Override
    protected Object convertToPropertyValue(@Nullable Boolean value) {
        return super.convertToPropertyValue(value);
    }

    @Override
    protected Boolean convertFromPropertyValue(Object value) {
        return ConverterUtil.toBoolean(value);
    }

    @Override
    protected void refreshToUI(Boolean value) {
        super.refreshToUI(value);
        boolean checked = value != null && value;
        compatSpinner.setSelection(checked ? 0 : 1, false);
        switchCompat.setChecked(checked);
    }
}
