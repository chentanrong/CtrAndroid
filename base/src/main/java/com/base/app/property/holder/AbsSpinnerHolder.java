package com.base.app.property.holder;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.base.R;
import com.base.app.property.BasePropertyItemHolder;
import com.base.app.property.IStoreDataAdapter;
import com.base.app.property.PropertyItem;


public abstract class AbsSpinnerHolder<S> extends BasePropertyItemHolder<S> implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private IStoreDataAdapter<S> adapter;

    public AbsSpinnerHolder(ViewGroup viewGroup) {
        super(viewGroup, R.layout.property_spinner);
        spinner = (Spinner) getItemView().findViewById(R.id.p_spinner1);
        adapter = onCreateSpinnerAdapter();
        spinner.setAdapter(adapter);
    }

    public IStoreDataAdapter<S> getAdapter() {
        return adapter;
    }

    public abstract IStoreDataAdapter<S> onCreateSpinnerAdapter();

    @Override
    protected void onBindModel(ViewDataBinding dataBinding, PropertyItem dataModel) {
        super.onBindModel(dataBinding, dataModel);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    protected void onRecycled() {
        spinner.setOnItemSelectedListener(null);
        super.onRecycled();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        S value = adapter.getStoreData(position);
        saveToPropertyItem(value);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected Object convertToPropertyValue(@Nullable S value) {
        return value;
    }

    @Override
    protected void refreshToUI(S value) {
        int index = adapter.indexOfStoreData(value);
        if (index < 0) index = 0;
        spinner.setSelection(index, false);
    }
}
