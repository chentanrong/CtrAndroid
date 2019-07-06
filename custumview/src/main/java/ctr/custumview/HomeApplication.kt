package ctr.custumview

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter


class HomeApplication : Application() {

    private  val   isDebugArouter=true
    override fun onCreate() {
        super.onCreate()
        if(isDebugArouter){
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()

    }

}
