package ctr.custumview.fragment.firstCustom

import android.databinding.Observable
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import ctr.custumview.R
import ctr.common.base.BaseFragment
import ctr.custumview.databinding.FragmentCustomFirstBinding
import ctr.custumview.util.Config
import ctr.custumview.wedget.timer.TimeView

@Route(path = Config.FRAGMENT_FIRST_CUMSTOM)
class FirstCustomFragment : BaseFragment(){

    var bind:FragmentCustomFirstBinding?=null
    @Autowired(name="FirstCustomModel")
    lateinit var model:FirstCustomModel

    override var resId: Int =R.layout.fragment_custom_first

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timeView = view.findViewById(R.id.timeView) as TimeView?
        timeView?:return
        timeView.start()
        bind = FragmentCustomFirstBinding.bind(view)
        model.addOnPropertyChangedCallback(callback)
        bind?.model=model
    }

    override fun onDestroy() {
        bind?.unbind()
        model.removeOnPropertyChangedCallback(callback)
        super.onDestroy()
    }
    private val callback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (sender is FirstCustomModel) {
                when (propertyId) {

                }
            }
        }

    }
}
