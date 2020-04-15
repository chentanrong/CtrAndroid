package com.base.app.property;

public interface IStoreModel<M, U> {
    boolean isStoreData(M data);

    M getStoreData();

    U getUIData();

    U getDropDownUIData();
}
