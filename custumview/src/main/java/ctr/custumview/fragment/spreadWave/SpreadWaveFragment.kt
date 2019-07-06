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
import ctr.custumview.wedget.timer.TimeView

@Route(path = Config.FRAGMENT_SPREAD_WAVE)
class SpreadWaveFragment : BaseFragment(){

    lateinit var bind:FragmentSpreadWaveBinding
    @Autowired(name="FirstCustomModel")
    lateinit var model: FirstCustomModel

    override var resId: Int =R.layout.fragment_spread_wave

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timeView = view.findViewById<TimeView>(R.id.timeView)
        timeView.start()
        bind = FragmentSpreadWaveBinding.bind(view)
        model.addOnPropertyChangedCallback(callback)
        bind.model=model



    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind.unbind()
        model.removeOnPropertyChangedCallback(callback)
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
