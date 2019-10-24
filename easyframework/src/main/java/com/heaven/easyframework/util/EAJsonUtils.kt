package com.heaven.easyframework.util

import android.text.TextUtils
import com.alibaba.fastjson.JSON
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyframework.util
 * 创建人：Heaven.li
 * 创建时间：2019-10-17 10:37
 * 备注：Json解析
 */
class EAJsonUtils {


    fun <T> parseDataToList(json: String, clazz: Class<T>): List<T>? {
        var tempList: List<T>? = null
        try {
            tempList = JSON.parseArray(JSONObject(json).getString("data"),
                    clazz)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return tempList
    }

    /**
     * json转化为object
     *
     * @param json  json
     * @param clazz 类
     * @param <T>   范型
     * @return 类
    </T> */
    fun <T> parseDataToObjectApp(json: String, clazz: Class<T>): T? {
        var t: T? = null
        try {
            t = JSON.parseObject(JSONObject(json).getString("data"), clazz)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return t
    }

    /**
     * json转化为object
     *
     * @param json  json
     * @param clazz 类
     * @param <T>   范型
     * @return 值
    </T> */
    fun <T> parseDataToObject(json: String, clazz: Class<T>): T? {
        var t: T? = null
        try {
            t = JSON.parseObject(json, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return t
    }

    /**
     * json转化为object
     *
     * @param json  json
     * @param clazz 类
     * @param <T>   范型
     * @return obj
    </T> */
    fun <T> APPparseDataToObject(json: String, clazz: Class<T>): T? {
        var t: T? = null
        try {
            t = JSON.parseObject(JSONObject(json).getString("data"), clazz)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return t
    }

    /**
     * http://www.jb51.net/article/47467.htm
     * 读取json
     *
     * @param path 文件路径
     * @return string
     */
    fun readJson(path: String): String {
        val file = File(path)
        var reader: BufferedReader? = null
        val data = StringBuilder()
        try {
            reader = BufferedReader(FileReader(file))
            var temp = reader.readLine()
            while (temp != null) {
                data.append(temp)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        return "{\"data\":$data}"
    }

    fun <T> parseDataToList(json: String, keyName: String,
                            clazz: Class<T>): List<T>? {
        var tempList: List<T>? = null
        try {
            val jsonObject = JSONObject(json).getJSONObject("data")
            if (jsonObject != null) {
                val dataNameStr = jsonObject.getString(keyName)
                if (!TextUtils.isEmpty(dataNameStr)) {
                    tempList = JSON.parseArray(dataNameStr, clazz)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return tempList
    }
}