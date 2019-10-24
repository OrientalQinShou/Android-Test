package cn.lag.lag.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import cn.lag.lag.R
import com.heaven.easyframework.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_enterprise.*
import kotlinx.android.synthetic.main.fragment_enterprise.titlebar
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag.fragment
 * 创建人：Heaven.li
 * 创建时间：2019-10-16 14:52
 * 备注：
 */
class EnterpriseFragment : BaseFragment() {

    var mView : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        titlebar.run {
            setBackgroundResource(R.drawable.shape_gradient)
        }
    }

    override fun setLayout(inflater: LayoutInflater): View {
        mView = inflater.inflate(R.layout.fragment_enterprise, null)

        return mView as View
    }
}