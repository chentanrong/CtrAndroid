package ctr.custumview.fragment.av

import android.databinding.Observable
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import ctr.common.base.BaseFragment
import ctr.custumview.R
import ctr.custumview.databinding.FragmentAudioRecordBinding
import ctr.custumview.fragment.firstCustom.FirstCustomModel

import ctr.custumview.util.Config
import android.view.TextureView
import java.io.IOException
import java.lang.Exception


/**
 * @User chentanrong
 * @Date 2019/11/16
 * @Desc
 */


@Route(path = Config.AUDIO_RECORD_FRAGMENT)
class AudioRecordFragment: BaseFragment(), TextureView.SurfaceTextureListener {


    override var resId: Int = R.layout.fragment_audio_record
    var bind:FragmentAudioRecordBinding?=null
    @Autowired(name="FirstCustomModel")
    lateinit var firsModel: FirstCustomModel
    lateinit var model:AudioRecordModelJava
    var textureView: TextureView? = null
    var camera: Camera? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentAudioRecordBinding.bind(view)
        model= AudioRecordModelJava()
        model.context=context
        model.addOnPropertyChangedCallback(callback)
        bind?.model=model
        textureView =  view.findViewById(R.id.texture_view) as TextureView
        textureView?.surfaceTextureListener=this
        camera?.stopPreview()
        camera?.release()

        camera?.setDisplayOrientation(90)
    }
    override fun onDestroy() {
        bind?.unbind()
        model.removeOnPropertyChangedCallback(callback)
        super.onDestroy()
    }
    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        camera?.release()
        return false
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        camera = Camera.open()
        try {
//            camera?.setPreviewTexture(surface)
            camera?.startPreview()
        }catch (e:Exception){
            e.printStackTrace()
        }
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
