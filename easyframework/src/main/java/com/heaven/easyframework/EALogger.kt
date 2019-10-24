package com.heaven.easyframework

import com.heaven.framework.log.EAPrintToLogCatLogger
import com.heaven.framework.log.ILogger
import com.heaven.framework.log.LoggerConfig
import java.util.HashMap

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyandroidframework.util
 * 创建人：Heaven.li
 * 创建时间：2019/10/3 15:40
 * 备注：日志打印类
 */
class EALogger {
    companion object {

        /**
         * Priority constant for the println method; use EALogger.v.
         */
        val VERBOSE = 2

        /**
         * Priority constant for the println method; use EALogger.d.
         */
        val DEBUG = 3

        /**
         * Priority constant for the println method; use EALogger.i.
         */
        val INFO = 4

        /**
         * Priority constant for the println method; use EALogger.w.
         */
        val WARN = 5

        /**
         * Priority constant for the println method; use EALogger.e.
         */
        val ERROR = 6

        /**
         * Priority constant for the println method.
         */
        val ASSERT = 7

        private val loggerHashMap = HashMap<String, ILogger>()

        private val defaultLogger = EAPrintToLogCatLogger()

        fun addLogger(logger: ILogger) {
            val loggerName = logger.javaClass.name
            val defaultLoggerName = defaultLogger.javaClass.name
            if (!loggerHashMap.containsKey(loggerName) && !defaultLoggerName.equals(loggerName, ignoreCase = true)) {
                logger.open()
                loggerHashMap[loggerName] = logger
            }

        }

        fun removeLogger(logger: ILogger) {
            val loggerName = logger.javaClass.name
            if (loggerHashMap.containsKey(loggerName)) {
                logger.close()
                loggerHashMap.remove(loggerName)
            }

        }

        fun d(`object`: Any, message: String) {

            printLoger(DEBUG, `object`, message)

        }

        fun e(`object`: Any, message: String) {

            printLoger(ERROR, `object`, message)

        }

        fun i(`object`: Any, message: String) {

            printLoger(INFO, `object`, message)

        }

        fun v(`object`: Any, message: String) {

            printLoger(VERBOSE, `object`, message)

        }

        fun w(`object`: Any, message: String) {

            printLoger(WARN, `object`, message)

        }

        fun d(tag: String, message: String) {

            printLoger(DEBUG, tag, message)

        }

        fun e(tag: String, message: String) {

            printLoger(ERROR, tag, message)

        }

        fun i(tag: String, message: String) {

            printLoger(INFO, tag, message)

        }

        fun v(tag: String, message: String) {

            printLoger(VERBOSE, tag, message)

        }

        fun w(tag: String, message: String) {

            printLoger(WARN, tag, message)

        }

        fun println(priority: Int, tag: String, message: String) {
            printLoger(priority, tag, message)
        }

        private fun printLoger(priority: Int, `object`: Any, message: String) {
            val cls = `object`.javaClass
            var tag = cls.name
            val arrays = tag.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            tag = arrays[arrays.size - 1]
            printLoger(priority, tag, message)
        }

        private fun printLoger(priority: Int, tag: String, message: String) {
            if (LoggerConfig.isDEBUG()) {
                printLoger(defaultLogger, priority, tag, message)
                val iter = loggerHashMap.entries.iterator()
                while (iter.hasNext()) {
                    val entry = iter.next()
                    val logger = entry.value
                    if (logger != null) {
                        printLoger(logger, priority, tag, message)
                    }
                }
            }
        }

        private fun printLoger(logger: ILogger, priority: Int, tag: String, message: String) {

            when (priority) {
                VERBOSE -> logger.v(tag, message)
                DEBUG -> logger.d(tag, message)
                INFO -> logger.i(tag, message)
                WARN -> logger.w(tag, message)
                ERROR -> logger.e(tag, message)
                else -> {
                }
            }
        }

        /**
         * 根据debug模式输出Exception
         *
         * @param ex
         */
        fun exception(ex: Exception?) {
            if (LoggerConfig.isDEBUG() && ex != null) {
                ex.printStackTrace()
            }
        }
    }
}