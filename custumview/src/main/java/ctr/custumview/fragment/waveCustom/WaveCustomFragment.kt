package ctr.custumview.fragment.waveCustom

import android.databinding.Observable
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import ctr.custumview.R
import ctr.common.base.BaseFragment
import ctr.custumview.databinding.FragmentCustomWaveBinding
import ctr.custumview.fragment.firstCustom.FirstCustomModel
import ctr.custumview.util.Config
import ctr.custumview.wedget.wave.WaveView

@Route(path = Config.FRAGMENT_WAVE_CUSTOM)
class WaveCustomFragment : BaseFragment(){

    var bind:FragmentCustomWaveBinding?=null
    @Autowired(name="FirstCustomModel")
    lateinit var model: FirstCustomModel
    var   waveView:WaveView?=null

    override var resId: Int =R.layout.fragment_custom_wave

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind = FragmentCustomWaveBinding.bind(view)
        model.addOnPropertyChangedCallback(callback)
        bind?.model=model
         waveView = view.findViewById(R.id.waveView) as WaveView
        waveView?.startAnimation()
    }

    override fun onDestroy() {
        bind?.unbind()
        model.removeOnPropertyChangedCallback(callback)
        waveView?.cancel()
        waveView=null
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
