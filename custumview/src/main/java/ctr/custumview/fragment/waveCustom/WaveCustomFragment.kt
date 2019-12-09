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

    lateinit var bind:FragmentCustomWaveBinding
    @Autowired(name="FirstCustomModel")
    lateinit var model: FirstCustomModel

    override var resId: Int =R.layout.fragment_custom_wave

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind = FragmentCustomWaveBinding.bind(view)
        model.addOnPropertyChangedCallback(callback)
        bind.model=model
        val waveView = view.findViewById(R.id.waveView) as WaveView
        waveView.startAnimation()
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
