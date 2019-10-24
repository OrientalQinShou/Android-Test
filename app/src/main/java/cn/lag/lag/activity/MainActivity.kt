package cn.lag.lag.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import cn.lag.lag.R
import cn.lag.lag.model.ModelManager
import cn.lag.lag.modelImpl.IFootClick
import cn.lag.lag.modelImpl.IFootModel
import com.heaven.easyframework.activity.base.BaseActivity
import com.heaven.easyframework.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * 项目名称：Android-Test
 * 类名：MainActivity
 * 创建人：Heaven.li
 * 创建时间：2019/9/16 17:27
 * 备注：
 */
class MainActivity : BaseActivity() {

    private val TAG = "MainActivity"

    private var exitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, BackDoorActivity::class.java)
        titlebar.run {
            setBackgroundResource(R.drawable.shape_gradient)
            setListener(object : CommonTitleBar.OnTitleBarListener{
            override fun onClicked(v: View, action: Int, extra: String?) {
                if (action == CommonTitleBar.ACTION_RIGHT_TEXT) {
                    startActivity(intent)
                    overridePendingTransition(R.anim.finish_in1, R.anim.finish_out1)
                }
            }
        })
        }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event!!.getRepeatCount() == 0){
            exit()
//            overridePendingTransition(R.anim.finish_out, R.anim.finish_in)
        }
        return false
    }
    fun exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
//            ToastUtils.showLongToast(R.string.Again_click_exit_app)
            exitTime = System.currentTimeMillis()
        } else {
            System.exit(1)
        }
    }

}
