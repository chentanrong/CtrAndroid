package com.base.app.property;

import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

public interface IStoreDataAdapter<S> extends ListAdapter, SpinnerAdapter {
    int indexOfStoreData(S data);

    S getStoreData(int index);
}
