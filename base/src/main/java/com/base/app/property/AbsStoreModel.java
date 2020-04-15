package com.base.app.property;


public abstract class AbsStoreModel<M, U> implements IStoreModel<M, U> {
    @Override
    public boolean isStoreData(M data) {
        M storeData = getStoreData();
        return data == null ? storeData == null : data.equals(storeData);
    }
}
