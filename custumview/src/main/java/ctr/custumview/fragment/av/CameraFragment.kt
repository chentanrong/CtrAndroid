package ctr.custumview.fragment.av

import android.databinding.Observable
import android.graphics.ImageFormat
import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import ctr.common.base.BaseFragment
import ctr.custumview.R
import ctr.custumview.databinding.FragmentCameraBinding
import ctr.custumview.fragment.firstCustom.FirstCustomModel

import ctr.custumview.util.Config
import java.io.IOException
import android.graphics.ImageFormat.NV21
import ctr.custumview.util.FilePathConfig
import java.io.File


/**
 * @User chentanrong
 * @Date 2020/4/2
 * @Desc
 */

@Route(path = Config.FRAGMENT_CARMER)
internal class CameraFragment : BaseFragment(), SurfaceHolder.Callback ,Camera.PreviewCallback{


    var camera: Camera? = null
    var bind: FragmentCameraBinding? = null
    var width = 1280
    var height = 720
    var framerate = 30
    var encoder: H264EncoderService? = null
    @Autowired(name = "FirstCustomModel")
    lateinit var model: FirstCustomModel

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        camera?.startPreview()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        camera?.setPreviewCallback(null)
        camera?.stopPreview()
        camera = null
        encoder?.stopEncoder()
        encoder=null

    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        if (holder == null) {
            return
        }
        camera = Camera.open() ?: return
        val camera=camera?:return
        camera.setDisplayOrientation(90)
        val parameters = camera.getParameters()
        parameters.previewFormat = getPreviewFormats()?:return

        val previewSize = getPreviewSize()?:return

        parameters.setPreviewSize(previewSize.width,previewSize.height )
        camera.setParameters(parameters)

        encoder = H264EncoderService(previewSize.width, previewSize.height, framerate, File(FilePathConfig.getCom(),"test.mp4"),parameters.previewFormat)
        encoder?.startEncoder()
        try {
            camera.setPreviewCallback(this)
            camera.setPreviewDisplay(holder)
            camera.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }


    }

    fun getPreviewSize():Camera.Size?{
        val parameters1 = camera?.parameters?:return null
        val supportedPreviewSizes = parameters1.supportedPreviewSizes?:return null
        val last = supportedPreviewSizes.first()
        return last
    }

    fun getPreviewFormats():Int?{
        val parameters1 = camera?.parameters?:return null
        val previewFormats = parameters1.supportedPreviewFormats?:return null
        val yv12 = previewFormats.indexOf(ImageFormat.YV12)
        if(yv12!=-1)
            return ImageFormat.YV12
        val nv21 = previewFormats.indexOf(ImageFormat.NV21)
        if(nv21!=-1){
            return ImageFormat.NV21
        }
        return -1
    }

    override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {
        encoder?.putData(data)
    }
    override var resId: Int = R.layout.fragment_camera


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentCameraBinding.bind(view)

        model.addOnPropertyChangedCallback(callback)
        bind?.model = model

        val holer = bind?.imageSurface?.holder ?: return
        holer.addCallback(this)


    }



    override fun onDestroy() {
        bind?.unbind()
        model.removeOnPropertyChangedCallback(callback)
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
