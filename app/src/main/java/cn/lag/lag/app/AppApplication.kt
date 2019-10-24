package cn.lag.lag.app

import android.app.Application

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag.app
 * 创建人：Heaven.li
 * 创建时间：2019-10-17 14:33
 * 备注：
 */
open class AppApplication : Application() {

    companion object {
        private var instance: AppApplication? = null
            get() {
                if (field == null) {
                    field = AppApplication()
                }
                return field
            }

        fun get(): AppApplication {
            return instance!!
        }
    }
}