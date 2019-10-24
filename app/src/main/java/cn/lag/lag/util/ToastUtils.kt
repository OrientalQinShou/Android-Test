package cn.lag.lag.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Toast
import cn.lag.lag.app.AppApplication

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag.util
 * 创建人：Heaven.li
 * 创建时间：2019-10-17 14:21
 * 备注：吐司
 */
class ToastUtils {

    companion object{

        var toast : Toast? = null

        fun showShortToast(mContext : Context,text : String){
            initToast(mContext,text, Toast.LENGTH_SHORT)
        }
        fun showLongToast(mContext : Context,text : String){
            initToast(mContext,text, Toast.LENGTH_LONG)
        }
        fun showShortToast(mContext : Context,resId : Int){
            initToast(mContext,resId, Toast.LENGTH_SHORT)
        }
        fun showLongToast(mContext : Context,resId : Int){
            initToast(mContext,resId, Toast.LENGTH_LONG)
        }
        @SuppressLint("ShowToast")
        private fun initToast(mContext : Context,resId: Int, duration: Int) {
            if (toast == null) {
                toast = Toast.makeText(mContext, resId, duration)
            } else {
                toast?.setDuration(duration)
                toast?.setText(resId)
            }
            toast?.show()
        }

        @SuppressLint("ShowToast")
        private fun initToast(mContext : Context,content: String, duration: Int) {
            if (toast == null) {
                toast = Toast.makeText(mContext, content, duration)
            } else {
                toast?.setDuration(duration)
                toast?.setText(content)
            }
            toast?.show()
        }

        fun showLongToastOnUI(activity: Activity, resId: Int) {
            try {
                activity.runOnUiThread { Toast.makeText(activity, resId, Toast.LENGTH_LONG).show() }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}