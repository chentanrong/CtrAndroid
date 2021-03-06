package ctr.custumview.fragment.spreadWave

import android.databinding.Observable
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import ctr.custumview.R
import ctr.common.base.BaseFragment
import ctr.custumview.databinding.FragmentSpreadWaveBinding
import ctr.custumview.fragment.firstCustom.FirstCustomModel
import ctr.custumview.util.Config
import ctr.custumview.wedget.wave.SpreadView

@Route(path = Config.FRAGMENT_SPREAD_WAVE)
class SpreadWaveFragment : BaseFragment(){

    var bind:FragmentSpreadWaveBinding?=null
    @Autowired(name="FirstCustomModel")
    lateinit var model: FirstCustomModel

    override var resId: Int =R.layout.fragment_spread_wave

    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spreadView = view.findViewById(R.id.spreadView) as SpreadView?
        spreadView?:return
        bind = FragmentSpreadWaveBinding.bind(view)
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
