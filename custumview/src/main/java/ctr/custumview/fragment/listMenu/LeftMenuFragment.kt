package ctr.custumview.fragment.listMenu

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import ctr.custumview.R
import ctr.custumview.util.Config
import android.widget.ArrayAdapter
import android.widget.ListView


@Route(path = Config.FRAGMENT_FIRST_CUMSTOM)
class LeftMenuFragment : ListFragment() {
    var callback: LeftFragmentCallback ?= null
    var datalist = arrayOf("自定义时钟", "水波纹", "贝塞尔波浪","摇奖","流式布局","Surface图像","大图加载","AudioRecord","手签板","自定义相机")
    var pathlist = arrayOf(
        Config.FRAGMENT_FIRST_CUMSTOM,
        Config.FRAGMENT_SPREAD_WAVE,
        Config.FRAGMENT_WAVE_CUSTOM,
        Config.FRAGMENT_SWEEP_STACK,
        Config.FRAGMENT_FLOW_LAYOUT,
        Config.FRAGMENT_IMAGE_SURFACE,
        Config.FRAGMENT_REGIOM_IMAGE,
        Config.AUDIO_RECORD_FRAGMENT,
        Config.FRAGMENT_SIGNTURE,
        Config.FRAGMENT_CARMER
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_manu_fragment, container, false);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter = ArrayAdapter<String>(
            activity,
            android.R.layout.simple_list_item_1, datalist
        )
    }


    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
            callback?.leftCallback(position,pathlist[position],datalist[position])
    }

    interface LeftFragmentCallback {
        fun leftCallback(position: Int,path:String,name:String)
    }
}
