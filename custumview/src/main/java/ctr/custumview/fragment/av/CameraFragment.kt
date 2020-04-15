package ctr.custumview.fragment.av

import android.databinding.Observable
import android.graphics.Color
import android.graphics.ImageFormat
import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.common.util.concurrent.Futures.addCallback
import ctr.common.base.BaseFragment
import ctr.custumview.R
import ctr.custumview.databinding.FragmentCameraBinding
import ctr.custumview.databinding.FragmentImageSurfaceBinding
import ctr.custumview.fragment.firstCustom.FirstCustomModel

import ctr.custumview.util.Config
import java.io.IOException
import android.graphics.ImageFormat.NV21




/**
 * @User chentanrong
 * @Date 2020/4/2
 * @Desc
 */


@Route(path = Config.FRAGMENT_CARMER)
internal class CameraFragment: BaseFragment(), SurfaceHolder.Callback{
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        camera?.startPreview()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        if(holder==null){
            return
        }
        try {
            camera?.setPreviewDisplay(holder)
            camera?.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    var camera: Camera?=null
    var bind: FragmentCameraBinding?=null
    @Autowired(name="FirstCustomModel")
    lateinit var model: FirstCustomModel

    override var resId: Int = R.layout.fragment_camera


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        camera=Camera.open()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentCameraBinding.bind(view)

        model.addOnPropertyChangedCallback(callback)
        bind?.model=model

        val holer = bind?.imageSurface?.holder?:return
        holer.addCallback(this)

        val  camera=camera?:return
        camera.setDisplayOrientation(90)
        val parameters = camera.getParameters()
        parameters.previewFormat = NV21
        camera.setParameters(parameters)

        camera.setPreviewCallback { bytes, camera -> {

        }}
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }


    override fun onDestroy() {
        bind?.unbind()
        model.removeOnPropertyChangedCallback(callback)
        camera?.setPreviewCallback(null)
        camera?.release()
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
