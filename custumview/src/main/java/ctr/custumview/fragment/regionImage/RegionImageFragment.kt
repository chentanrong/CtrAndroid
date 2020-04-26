package ctr.custumview.fragment.regionImage

import android.arch.lifecycle.LifecycleObserver
import android.databinding.Observable
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import ctr.custumview.R
import ctr.common.base.BaseFragment
import ctr.custumview.databinding.FragmentRegionImageBinding
import ctr.custumview.fragment.firstCustom.FirstCustomModel
import ctr.custumview.util.Config
import ctr.custumview.util.FilePathConfig
import ctr.custumview.wedget.image.RegionImageView
import java.io.File

@Route(path = Config.FRAGMENT_REGIOM_IMAGE)
class RegionImageFragment : BaseFragment(){

    var bind:FragmentRegionImageBinding?=null
    @Autowired(name="FirstCustomModel")
    lateinit var model: FirstCustomModel
    var waveView:RegionImageView?=null

    override var resId: Int =R.layout.fragment_region_image

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind = FragmentRegionImageBinding.bind(view)
        model.addOnPropertyChangedCallback(callback)
        bind?.model=model
        waveView = view.findViewById(R.id.regionImageView) as RegionImageView

        val file = File(FilePathConfig.getCamera(),"IMG_20190914_212341.jpg")
        waveView?.setInputStream(file)

    }
    private var lifecycleObserver=object :LifecycleObserver{

    }

    override fun onPause() {
        super.onPause()
        waveView?.recycle()
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
