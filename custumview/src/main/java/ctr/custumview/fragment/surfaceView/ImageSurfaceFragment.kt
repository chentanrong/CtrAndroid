package ctr.custumview.fragment.surfaceView

import android.databinding.Observable
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import ctr.custumview.R
import ctr.common.base.BaseFragment
import ctr.custumview.databinding.FragmentImageSurfaceBinding
import ctr.custumview.fragment.firstCustom.FirstCustomModel
import ctr.custumview.util.Config
import android.graphics.BitmapFactory
import android.graphics.Paint
import ctr.custumview.util.FilePathConfig
import java.io.File
import android.R.attr.authorities
import android.graphics.Color
import android.net.Uri
import android.support.v4.content.FileProvider
import android.os.Build
import ctr.custumview.wedget.timer.TimeView


@Route(path = Config.FRAGMENT_IMAGE_SURFACE)
class ImageSurfaceFragment : BaseFragment(){


    var bind:FragmentImageSurfaceBinding?=null
    @Autowired(name="FirstCustomModel")
    lateinit var model: FirstCustomModel

    override var resId: Int =R.layout.fragment_image_surface

    var paint: Paint=Paint().apply {
        this.isAntiAlias=true
        this.style=Paint.Style.STROKE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageSurface = view.findViewById(R.id.imageSurface) as SurfaceView ?:return

        bind = FragmentImageSurfaceBinding.bind(view)
        model.addOnPropertyChangedCallback(callback)
        bind?.model=model

        imageSurface.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
                if(holder==null){
                    return
                }
                val lockCanvas = holder.lockCanvas()
                lockCanvas.drawColor(Color.GRAY)

                val file = File(FilePathConfig.getCamera(),"IMG_20190914_212341.jpg")
                val decodeFile = BitmapFactory.decodeFile(file.absolutePath)?:return

                lockCanvas.drawBitmap(decodeFile,0f,0f,null)
                holder.unlockCanvasAndPost(lockCanvas)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
            }

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
