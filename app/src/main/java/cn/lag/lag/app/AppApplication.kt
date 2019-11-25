package cn.lag.lag.app

import android.app.Application
import cn.lag.lag.BuildConfig
import com.heaven.framework.log.LoggerConfig

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

    override fun onCreate() {
        super.onCreate()
        LoggerConfig.setDEBUG(BuildConfig.LOG_DEBUG)
    }
}