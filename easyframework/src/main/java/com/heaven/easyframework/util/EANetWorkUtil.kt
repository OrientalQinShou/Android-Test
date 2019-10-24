package com.heaven.easyframework.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyandroidframework.util
 * 创建人：Heaven.li
 * 创建时间：2019/10/3 16:08
 * 备注：检测网络的一个工具包
 */
class EANetWorkUtil {
    companion object {

        enum class netType {
            wifi, CMNET, CMWAP, noneNet
        }

        /**
         * 网络是否可用
         *
         * @param context
         * @return
         */
        fun isNetworkAvailable(context: Context): Boolean {
            val mgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if(mgr != null){
                val info = mgr.activeNetworkInfo
                if (info != null && info.isConnected && (info.state == NetworkInfo.State.CONNECTED)){
                    return true
                }
            }
            return false
        }

        /**
         * 判断是否有网络连接
         *
         * @param context
         * @return
         */
        fun isNetworkConnected(context: Context?): Boolean {
            if (context != null) {
                val mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val mNetworkInfo = mConnectivityManager.activeNetworkInfo
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable
                }
            }
            return false
        }

        /**
         * 判断WIFI网络是否可用
         *
         * @param context
         * @return
         */
        fun isWifiConnected(context: Context?): Boolean {
            if (context != null) {
                val mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                if (mWiFiNetworkInfo != null) {
                    return mWiFiNetworkInfo.isAvailable
                }
            }
            return false
        }

        /**
         * 判断MOBILE网络是否可用
         *
         * @param context
         * @return
         */
        fun isMobileConnected(context: Context?): Boolean {
            if (context != null) {
                val mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                if (mMobileNetworkInfo != null) {
                    return mMobileNetworkInfo.isAvailable
                }
            }
            return false
        }

        /**
         * 获取当前网络连接的类型信息
         *
         * @param context
         * @return
         */
        fun getConnectedType(context: Context?): Int {
            if (context != null) {
                val mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val mNetworkInfo = mConnectivityManager.activeNetworkInfo
                if (mNetworkInfo != null && mNetworkInfo.isAvailable) {
                    return mNetworkInfo.type
                }
            }
            return -1
        }

        /**
         *
         * 获取当前的网络状态 -1：没有网络 1：WIFI网络2：wap 网络3：net网络
         *
         * @param context
         *
         * @return
         */
        fun getAPNType(context: Context): netType {
            val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo ?: return netType.noneNet
            val nType = networkInfo.type

            if (nType == ConnectivityManager.TYPE_MOBILE) {
                return if (networkInfo.extraInfo.toLowerCase() == "cmnet") {
                    netType.CMNET
                } else {
                    netType.CMWAP
                }
            } else if (nType == ConnectivityManager.TYPE_WIFI) {
                return netType.wifi
            }
            return netType.noneNet

        }
    }
}