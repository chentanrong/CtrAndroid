package ctr.custumview.fragment.signture

import android.databinding.Observable
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import ctr.custumview.R
import ctr.common.base.BaseFragment
import ctr.custumview.databinding.FragmentSigntureBinding
import ctr.custumview.fragment.firstCustom.FirstCustomModel
import ctr.custumview.fragment.signture.SigntureModel
import ctr.custumview.util.Config
import ctr.custumview.wedget.signture.SigntureView

@Route(path = Config.FRAGMENT_SIGNTURE)
class SigntureFragment : BaseFragment(){

    var bind:FragmentSigntureBinding?=null

    lateinit var model: SigntureModel

    override var resId: Int =R.layout.fragment_signture

    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timeView = view.findViewById(R.id.signtureView) as SigntureView?
        timeView?:return
        model=SigntureModel()

        model.signtureView=timeView
        bind = FragmentSigntureBinding.bind(view)
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
