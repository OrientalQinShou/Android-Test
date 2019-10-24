package com.heaven.easyframework.okhttp

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyframework.okhttp
 * 创建人：Heaven.li
 * 创建时间：2019/10/8 15:27
 * 备注：
 */
interface HttpMethod {

    /**
     * HTTP请求--GET方法
     */
    val HTTPREQUESTMETHOD_GET: String
        get() = "GET"
    /**
     * HTTP请求--POST方法
     */
    val HTTPREQUESTMETHOD_POST: String
        get() = "POST"
}