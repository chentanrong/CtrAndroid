package com.base.app.property;

import android.content.Context;

import java.util.List;

public abstract class AbsStoreDataAdapter<S, U, MODEL extends IStoreModel<S, U>> extends AbsBaseSpinnerAdapter<MODEL> implements IStoreDataAdapter<S> {
    public AbsStoreDataAdapter(Context context) {
        super(context);
    }

    private MODEL headerModel = null;

    public MODEL getHeaderModel() {
        return headerModel;
    }

    public void setHeaderModel(MODEL headerModel) {
        this.headerModel = headerModel;
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        if (headerModel != null)
            count++;
        return count;
    }

    @Override
    public MODEL getItem(int position) {
        if (headerModel != null) {
            if (position == 0)
                return headerModel;
            else return super.getItem(position - 1);
        }
        return super.getItem(position);
    }

    @Override
    public int indexOfStoreData(S data) {
        int index = -1;
        List<MODEL> list = getData();
        if (list == null) {
            index = -1;
        } else {
            int count = list.size();
            for (int i = 0; i < count; i++) {
                MODEL m = list.get(i);
                if (m == null) {
                    if (data == null) {
                        index = i;
                        break;
                    }
                } else if (m.isStoreData(data)) {
                    index = i;
                    break;
                }
            }
        }
        if (headerModel != null) {
            index++;
        }
        return index;
    }

    @Override
    public S getStoreData(int index) {
        MODEL m = getItem(index);
        return m == null ? null : m.getStoreData();
    }
}
