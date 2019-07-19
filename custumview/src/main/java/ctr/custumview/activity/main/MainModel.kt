package ctr.custumview.activity.main

import android.databinding.BaseObservable
import android.databinding.Bindable
import ctr.custumview.BR

class MainModel : BaseObservable() {

    @get:Bindable
    var text: String? = null
        set(value) {
            if (field == value) {
                return
            }
            field = value
            notifyPropertyChanged(BR.text)
        }


}
