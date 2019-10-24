package cn.lag.lag.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import cn.lag.lag.R
import com.heaven.easyframework.fragment.base.BaseFragment

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag.fragment
 * 创建人：Heaven.li
 * 创建时间：2019-10-16 14:52
 * 备注：
 */
class NullFragment : BaseFragment() {

    var mView : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setLayout(inflater: LayoutInflater): View {
        mView = inflater.inflate(R.layout.fragment_null, null)

        return mView as View
    }
}