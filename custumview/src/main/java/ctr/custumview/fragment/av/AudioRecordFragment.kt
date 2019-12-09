package ctr.custumview.fragment.av

import android.databinding.Observable
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import ctr.common.base.BaseFragment
import ctr.custumview.R
import ctr.custumview.databinding.FragmentAudioRecordBinding
import ctr.custumview.fragment.firstCustom.FirstCustomModel

import ctr.custumview.util.Config

/**
 * @User chentanrong
 * @Date 2019/11/16
 * @Desc
 */


@Route(path = Config.AUDIO_RECORD_FRAGMENT)
class AudioRecordFragment: BaseFragment(){
    override var resId: Int = R.layout.fragment_audio_record
    lateinit var bind:FragmentAudioRecordBinding
    @Autowired(name="FirstCustomModel")
    lateinit var firsModel: FirstCustomModel
    lateinit var model:AudioRecordModelJava


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentAudioRecordBinding.bind(view)
        model= AudioRecordModelJava()
        model.context=context
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
            if (sender is AudioRecordModelJava) {
                when (propertyId) {

                }
            }
        }

    }
}
