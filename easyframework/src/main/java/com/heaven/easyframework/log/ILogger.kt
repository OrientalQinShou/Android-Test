package com.heaven.framework.log

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyandroidframework.log
 * 创建人：Heaven.li
 * 创建时间：2019/10/3 15:42
 * 备注：ILogger是一个日志的接口
 */
interface ILogger {
    fun v(tag: String, message: String)

    fun d(tag: String, message: String)

    fun i(tag: String, message: String)

    fun w(tag: String, message: String)

    fun e(tag: String, message: String)

    fun open()

    fun close()

    fun println(priority: Int, tag: String, message: String)
}