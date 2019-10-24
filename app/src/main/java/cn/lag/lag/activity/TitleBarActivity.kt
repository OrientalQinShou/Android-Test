package cn.lag.lag.activity

import android.os.Bundle
import android.view.View
import cn.lag.lag.R
import com.heaven.easyframework.activity.base.BaseActivity
import com.heaven.easyframework.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_titlebar.*

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag.activity
 * 创建人：Heaven.li
 * 创建时间：2019-10-15 09:41
 * 备注：
 */
class TitleBarActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_titlebar)
        titlebar.run {
            setBackgroundResource(R.drawable.shape_gradient)
            setListener(object : CommonTitleBar.OnTitleBarListener{
                override fun onClicked(v: View, action: Int, extra: String?) {
                    if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                        finish()
                        overridePendingTransition(R.anim.finish_out, R.anim.finish_in)
                    }
                }
            })
        }
    }
}