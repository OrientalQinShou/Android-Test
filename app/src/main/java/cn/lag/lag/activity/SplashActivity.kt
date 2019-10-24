package cn.lag.lag.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import cn.lag.lag.R

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag
 * 创建人：Heaven.li
 * 创建时间：2019/9/16 17:35
 * 备注：
 */
class SplashActivity : AppCompatActivity() {

    private val TAG = "BaseActivity"
    private val handler = Handler();

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler.postDelayed({
            redirectTo()
        }, 3000)
    }

    fun redirectTo() {
        val intent = Intent(this, MainFragmentActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.finish_in1, R.anim.finish_out1)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }
}