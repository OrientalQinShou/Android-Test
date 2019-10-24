package cn.lag.lag.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cn.lag.lag.R
import cn.lag.lag.adapter.BackDoorAdapter
import com.heaven.easyframework.activity.base.BaseActivity
import com.heaven.easyframework.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_backdoor.*

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag.activity
 * 创建人：Heaven.li
 * 创建时间：2019/10/9 17:49
 * 备注：
 */
class BackDoorActivity : BaseActivity() {

    var list=mutableListOf<String>()
    var data= listOf(
            "快速预览TitleBar")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backdoor)
        backdoor_titlebar.run {
            setBackgroundResource(R.drawable.shape_gradient)
            setListener(object : CommonTitleBar.OnTitleBarListener{
                override fun onClicked(v: View, action: Int, extra: String?) {
                    if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                        finish()
                        overridePendingTransition(R.anim.finish_out, R.anim.finish_in)
                    }
                }
            })
        }
        initUI()
    }

    private fun initUI() {
        val layoutManager= LinearLayoutManager(this)
//        val layout=GridLayoutManager(this,2);
        backdoor_recyclelistview.layoutManager=layoutManager
        var adapter= BackDoorAdapter(data,this)
        backdoor_recyclelistview.adapter=adapter
        adapter.setOnKotlinItemClickListener {

            when(it){
                0 -> {
                    val intent = Intent(this, TitleBarActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.finish_in1, R.anim.finish_out1)
                }
            }


        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event!!.getRepeatCount() == 0){
            finish()
            overridePendingTransition(R.anim.finish_out, R.anim.finish_in)
        }
        return false
    }
}