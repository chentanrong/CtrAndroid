package ctr.custumview.fragment.sweepStack

import android.databinding.Observable
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import ctr.custumview.R
import ctr.common.base.BaseFragment
import ctr.custumview.databinding.FragmentSweepStackBinding
import ctr.custumview.fragment.firstCustom.FirstCustomModel
import ctr.custumview.util.Config
import ctr.custumview.wedget.wave.SweepStackView

/**
 * 摇奖
 */
@Route(path = Config.FRAGMENT_SWEEP_STACK)
class SweepStackFragment : BaseFragment(){

    var bind:FragmentSweepStackBinding?=null
    @Autowired(name="FirstCustomModel")
    lateinit var model: FirstCustomModel

    override var resId: Int =R.layout.fragment_sweep_stack

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind = FragmentSweepStackBinding.bind(view)
        model.addOnPropertyChangedCallback(callback)
        bind?.model=model
        val waveView = view.findViewById(R.id.sweepStack) as SweepStackView? ?: return
        waveView.setMlistener ({ position, msg ->
            toast(msg)
        })
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
