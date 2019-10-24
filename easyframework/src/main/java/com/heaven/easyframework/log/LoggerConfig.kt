package com.heaven.framework.log

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyandroidframework.log
 * 创建人：Heaven.li
 * 创建时间：2019/10/3 16:02
 * 备注：日志打印配置类
 */
class LoggerConfig {
    companion object {
        // 调试开关
        private var DEBUG = true

        /**
         * @return the dEBUG
         */
        fun isDEBUG(): Boolean {
            return DEBUG
        }

        /**
         * @param dEBUG the dEBUG to set
         */
        fun setDEBUG(dEBUG: Boolean) {
            DEBUG = dEBUG
        }
    }
}