package com.heaven.easyframework.util

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.ArrayList
import java.util.concurrent.ConcurrentHashMap

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyframework.util
 * 创建人：Heaven.li
 * 创建时间：2019/10/8 13:23
 * 备注：
 */
class EARequestParams {


    private val ENCODING = "UTF-8"

    protected var urlParams: ConcurrentHashMap<String, String>? = null

    protected var fileParams: ConcurrentHashMap<String, FileWrapper>? = null

    protected var urlParamsWithArray: ConcurrentHashMap<String, ArrayList<String>>? = null

    constructor(){
        init()
    }
    constructor(source : ConcurrentHashMap<String, String>){
        init()
        for ((key, value) in source) {
            put(key, value)
        }
    }
    constructor(key : String,value : String){
        init()
        put(key, value)
    }
    constructor(vararg keysAndValues: Any){
        init()
        val len = keysAndValues.size
        if (len % 2 != 0)
            throw IllegalArgumentException("Supplied arguments must be even")
        var i = 0
        while (i < len) {
            val key = keysAndValues[i].toString()
            val `val` = keysAndValues[i + 1].toString()
            put(key, `val`)
            i += 2
        }
    }

    private fun init(){
        urlParams = ConcurrentHashMap()
        fileParams = ConcurrentHashMap()
        urlParamsWithArray = ConcurrentHashMap()
    }

    fun put(key: String?, value: String?) {
        if (key != null && value != null) {
            urlParams!![key] = value
        }
    }

    @Throws(FileNotFoundException::class)
    fun put(key: String, file: File) {
        put(key, FileInputStream(file), file.name)
    }

    fun put(key: String, stream: InputStream, fileName: String?) {
        put(key, stream, fileName!!, null)
    }

    fun put(key: String?, stream: InputStream?, fileName: String, contentType: String?) {
        if (key != null && stream != null) {
            fileParams!![key] = FileWrapper(stream, fileName, contentType!!)
        }
    }

    fun remove(key: String) {
        urlParams!!.remove(key)
        fileParams!!.remove(key)
        urlParamsWithArray!!.remove(key)
    }

    override fun toString(): String {
        val result = StringBuilder()
        urlParams!!.forEach { (key, value) ->
            if (result.length > 0)
                result.append("&")

            result.append(key)
            result.append("=")
            result.append(value)
        }

        fileParams!!.forEach { (key) ->
            if (result.length > 0)
                result.append("&")

            result.append(key)
            result.append("=")
            result.append("FILE")
        }

        urlParamsWithArray!!.forEach { (key, values) ->
            if (result.length > 0)
                result.append("&")

            for (value in values) {
                if (values.indexOf(value) != 0)
                    result.append("&")
                result.append(key)
                result.append("=")
                result.append(value)
            }
        }

        return result.toString()
    }

    class FileWrapper(inputStream: InputStream,fileName: String,contentType: String) {

        var inputStream: InputStream = inputStream
        var fileName: String = fileName
        var contentType: String = contentType

        fun getfileName(): String {
            return if (fileName != null) {
                fileName
            } else {
                "nofilename"
            }
        }
    }
}