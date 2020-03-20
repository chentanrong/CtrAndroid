package ctr.custumview.fragment.flowLayout

import android.databinding.Observable
import android.graphics.Color
import android.graphics.SurfaceTexture
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import ctr.custumview.R
import ctr.common.base.BaseFragment
import ctr.custumview.databinding.FragmentFlowLayoutBinding
import ctr.custumview.fragment.firstCustom.FirstCustomModel
import ctr.custumview.util.Config
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import ctr.custumview.layout.FlowLayout
import ctr.custumview.wedget.timer.TimeView


@Route(path = Config.FRAGMENT_FLOW_LAYOUT)
class FlowLayoutFragment : BaseFragment() {

    var bind: FragmentFlowLayoutBinding?=null
    @Autowired(name = "FirstCustomModel")
    lateinit var model: FirstCustomModel

    override var resId: Int = R.layout.fragment_flow_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind = FragmentFlowLayoutBinding.bind(view)
        model.addOnPropertyChangedCallback(callback)
        bind?.model = model
        val flowLayout = view.findViewById(R.id.flowLayout) as FlowLayout?
        flowLayout ?: return
        val list = arrayListOf<String>(
            "上家喝一杯", "下家喝一杯", "本人喝一杯", "跳过", "真心话", "大冒险", "唱首歌吧", "跳个舞吧",
            "上家唱首歌", "下家唱首歌", "上家跳个舞", "下家跳个舞", "上家大冒险", "下家大冒险"
        )
        val layoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(10, 5, 10, 5)
        flowLayout.removeAllViews()

        for (i in 0 until list.size) {
            val tv = CheckBox(context)
            tv.text = list[i]
            tv.id = i
            tv.setSingleLine()
            tv.setButtonDrawable(ColorDrawable(Color.TRANSPARENT))
            tv.layoutParams = layoutParams
            tv.setBackgroundResource(R.drawable.tag_bg)
            flowLayout.addView(tv, layoutParams)
            tv.setOnClickListener { v ->
                toast(v.id.toString() + tv.text)
            }
        }

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
