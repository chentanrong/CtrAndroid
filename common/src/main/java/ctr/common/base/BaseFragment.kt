package ctr.common.base


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter


open class BaseFragment : Fragment() {


    open var  resId:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val  id=resId
        return if (id > 0) {
            inflater.inflate(id, container, false)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }
}
