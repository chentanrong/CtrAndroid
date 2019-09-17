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


@Route(path = Config.FRAGMENT_IMAGE_SURFACE)
class ImageSurfaceFragment : BaseFragment(){

    lateinit var bind:FragmentImageSurfaceBinding
    @Autowired(name="FirstCustomModel")
    lateinit var model: FirstCustomModel

    override var resId: Int =R.layout.fragment_image_surface

    lateinit var paint: Paint

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageSurface = view.findViewById<SurfaceView>(R.id.imageSurface)

        paint.isAntiAlias=true
        paint.style=Paint.Style.STROKE

        bind = FragmentImageSurfaceBinding.bind(view)
        model.addOnPropertyChangedCallback(callback)
        bind.model=model

        imageSurface.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
                if(holder==null){
                    return
                }

                val file = File(FilePathConfig.getCamera().absolutePath, "20190730_181802.jpg")
                val decodeFile = BitmapFactory.decodeFile(file.absolutePath)?:return
                val lockCanvas = holder.lockCanvas()

                lockCanvas.drawBitmap(decodeFile,0f,0f,paint)
                holder.unlockCanvasAndPost(lockCanvas)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
            }

        })

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
