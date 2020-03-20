package ctr.custumview.fragment.signture

import android.view.View
import ctr.common.base.BaseModel
import ctr.common.service.CommonPathService
import ctr.custumview.wedget.signture.SigntureView
import java.io.File

/**
 * @User chentanrong
 * @Date 2020/1/15
 * @Desc
 */
class SigntureModel : BaseModel(){
    var signtureView:SigntureView?=null
    fun clear(view: View?){
        signtureView?.clear()
    }
    fun confirm(view: View?){
        val commonPathService = CommonPathService()
        val temp = File(commonPathService.photoDir,"temp.png")
        signtureView?.confirm(temp)

    }
}
