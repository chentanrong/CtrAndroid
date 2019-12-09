package com.base.app

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcel
import android.os.Parcelable
import ctr.common.BR

open class BaseModel() : BaseObservable(), Parcelable {

    @get :Bindable
    var name: String? = null
        set(value) {
            if (field == value) {
                return
            }
            field = value
            notifyPropertyChanged(BR.name)
        }

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseModel> {
        override fun createFromParcel(parcel: Parcel): BaseModel {
            return BaseModel(parcel)
        }

        override fun newArray(size: Int): Array<BaseModel?> {
            return arrayOfNulls(size)
        }
    }


}
