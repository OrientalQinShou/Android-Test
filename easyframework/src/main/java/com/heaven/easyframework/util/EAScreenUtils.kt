package com.heaven.easyframework.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyframework.util
 * 创建人：Heaven.li
 * 创建时间：2019/10/9 11:20
 * 备注：屏幕参数转换
 */
class EAScreenUtils {

    companion object {
        fun dp2Px(context : Context?,dp : Float):Float{
            if (context == null){
                return -1f
            }
            return dp * density(context)
        }

        fun px2Dp(context : Context,px : Float):Float{
            if (context == null){
                return -1f
            }
            return px / density(context)
        }

        fun density(context : Context):Float{
            return context.resources.displayMetrics.density
        }

        fun dp2PxInt(context : Context,dp : Float):Int{
            if (context == null){
                return -1
            }
            return (dp2Px(context,dp)+0.5f).toInt()
        }

        fun px2DpCeilInt(context : Context,dp : Float):Int{
            if (context == null){
                return -1
            }
            return (px2Dp(context,dp)+0.5f).toInt()
        }

        fun sp2px(context : Context,spValue : Float):Int{
            if (context == null){
                return -1
            }
            var fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue*fontScale+0.5f).toInt()
        }

        fun px2sp(context : Context,spValue : Float):Int{
            if (context == null){
                return -1
            }
            var fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue/fontScale+0.5f).toInt()
        }

        fun getDisplayMetrics(context : Context): DisplayMetrics {
            val activity: Activity
            if (context !is Activity && context is ContextWrapper) {
                activity = context.baseContext as Activity
            } else {
                activity = context as Activity
            }
            val metrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(metrics)
            return metrics
        }

        //屏幕大小
        fun getScreenPixelSize(context : Context): IntArray {
            val metrics = getDisplayMetrics(context)
            return intArrayOf(metrics.widthPixels, metrics.heightPixels)
        }

        fun hideSoftInputKeyBoard(context : Context,focusView : View){
            if (context != null && focusView != null){
                var binder = focusView.windowToken
                var imd : InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (binder != null && imd != null){
                    imd.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_IMPLICIT_ONLY)
                }
            }
        }

        fun showSoftInputKeyBoard(context : Context,focusView : View){
            if (context != null && focusView != null){
                var imd : InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imd.showSoftInput(focusView, InputMethodManager.HIDE_IMPLICIT_ONLY)
            }
        }

        fun getScreenWidth(context : Context):Int{
            return context.resources.displayMetrics.widthPixels
        }

        fun getScreenHeight(context : Context):Int{
            return context.resources.displayMetrics.heightPixels
        }

        fun getStatusBarHeight(context : Context):Int{
            var statusBarHeight = 0
            try {
                val c = Class.forName("com.android.internal.R\$dimen")
                val obj = c.newInstance()
                val field = c.getField("status_bar_height")
                val x = Integer.parseInt(field.get(obj).toString())
                statusBarHeight = context.resources.getDimensionPixelSize(x)
            } catch (e1: Exception) {
                e1.printStackTrace()
            }
            return statusBarHeight
        }

        fun getAppInScreenheight(context : Context):Int{
            return getScreenHeight(context) - getStatusBarHeight(context)
        }
    }
}