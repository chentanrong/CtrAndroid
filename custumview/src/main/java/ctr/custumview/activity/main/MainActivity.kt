package ctr.custumview.activity.main

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import ctr.custumview.R
import ctr.common.base.BaseActivity
import ctr.common.base.PermissionActivity
import ctr.custumview.databinding.ActivityMainBinding
import ctr.custumview.fragment.firstCustom.FirstCustomModel
import ctr.custumview.fragment.listMenu.LeftMenuFragment
import ctr.custumview.util.Config
import java.lang.ref.WeakReference


@Route(path = Config.ACTIVITY_MAIN)
class MainActivity : PermissionActivity() {

    companion object {

        class MyLeftFragmentCallback(var refer: WeakReference<MainActivity>) :LeftMenuFragment.LeftFragmentCallback{
            override fun leftCallback(position: Int, path: String, name: String) {
                val firstCustomModel = FirstCustomModel()
                firstCustomModel.name =name
                val f = ARouter.getInstance()
                    .build(path)
                    .withParcelable("FirstCustomModel", firstCustomModel)
                    .navigation() as Fragment?
                if(f!=null)
                refer.get()?.commitFra(f)
            }

        }
    }

    lateinit var binding: ActivityMainBinding
    lateinit var model: MainModel
    var menuFragment :LeftMenuFragment?=null
    override fun onPermissionSuccess() {
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        model= MainModel()
        model.text="跳转"
        binding.model=model

        menuFragment = LeftMenuFragment()
        menuFragment?.callback= MyLeftFragmentCallback(WeakReference(this))

        supportFragmentManager.beginTransaction()
            .add(menuFragment!!, menuFragment!!.tag)
            .replace(R.id.leftFrameLayout, menuFragment!!)
            .commitAllowingStateLoss()
    }



    fun commitFra( f:Fragment){
            supportFragmentManager.beginTransaction()
                .add(f, f.tag)
                .replace(R.id.mainFrameLayout, f)
                .commitAllowingStateLoss()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
        menuFragment?.callback=null
        menuFragment=null

    }
}
