package cn.lag.lag.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import cn.lag.lag.R
import cn.lag.lag.activity.BackDoorActivity
import com.heaven.easyframework.fragment.base.BaseFragment
import com.heaven.easyframework.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_server.*
import kotlinx.android.synthetic.main.fragment_server.titlebar

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag.fragment
 * 创建人：Heaven.li
 * 创建时间：2019-10-16 14:52
 * 备注：
 */
class ServerFragment : BaseFragment() {

    var mView : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val intent = Intent(activity, BackDoorActivity::class.java)
        titlebar.run {
            setBackgroundResource(R.drawable.shape_gradient)
            setListener(object : CommonTitleBar.OnTitleBarListener{
                override fun onClicked(v: View, action: Int, extra: String?) {
                    if (action == CommonTitleBar.ACTION_RIGHT_TEXT) {
                        startActivity(intent)
                        activity?.overridePendingTransition(R.anim.finish_in1, R.anim.finish_out1)
                    }
                }
            })
        }
    }

    override fun setLayout(inflater: LayoutInflater): View {
        mView = inflater.inflate(R.layout.fragment_server, null)

        return mView as View
    }
}