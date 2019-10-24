package com.heaven.easyframework.okhttp

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.heaven.easyframework.EALogger
import okhttp3.Response
import okhttp3.internal.http2.Header
import java.io.IOException

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyframework.okhttp
 * 创建人：Heaven.li
 * 创建时间：2019/10/8 15:35
 * 备注：接收请求结果，一般重写onSuccess及onFailure接收请求成功或失败的消息，还有onStart，onFinish等消息
 */
open class AsyncHttpResponseHandler {

    protected val SUCCESS_MESSAGE = 4

    protected val FAILURE_MESSAGE = 1

    protected val START_MESSAGE = 2

    protected val FINISH_MESSAGE = 3

    protected val PROGRESS_MESSAGE = 0

    private var handler: Handler? = null

    constructor(){
        if (Looper.myLooper() != null) {
            handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    this.handleMessage(msg)
                }
            }
        }
    }

    fun onStart() {}

    open fun onFinish() {}

    open fun onResponse(response: Response) {
        try {
            sendSuccessMessage(200, null, response.body()!!.string())
        } catch (e: Exception) {
            EALogger.exception(e)
        }

    }

    open fun onSuccess(content: String) {

    }

    fun onProgress(totalSize: Long, currentSize: Long, speed: Long) {

    }

    open fun onSuccess(statusCode: Int, headers: Array<Header>, content: String) {
        onSuccess(statusCode, content)
    }

    open fun onSuccess(statusCode: Int, content: String) {
        onSuccess(content)
    }

    open fun onFailure(error: Throwable) {

    }

    open fun onFailure(error: Throwable, content: String) {

        onFailure(error)
    }

    protected fun sendSuccessMessage(statusCode: Int, headers: Array<Header>?, responseBody: String?) {
        sendMessage(obtainMessage(SUCCESS_MESSAGE, arrayOf<Any>(statusCode, headers!!, responseBody!!)))
    }

    protected fun sendFailureMessage(e: Throwable, responseBody: String) {
        sendMessage(obtainMessage(FAILURE_MESSAGE, arrayOf<Any>(e, responseBody)))
    }

    protected fun sendFailureMessage(e: Throwable, responseBody: ByteArray) {
        sendMessage(obtainMessage(FAILURE_MESSAGE, arrayOf<Any>(e, responseBody)))
    }

    protected fun sendStartMessage() {
        sendMessage(obtainMessage(START_MESSAGE, null))
    }

    protected fun sendFinishMessage() {
        sendMessage(obtainMessage(FINISH_MESSAGE, null))
    }

    protected fun handleSuccessMessage(statusCode: Int, headers: Array<Header>, responseBody: String) {
        onSuccess(statusCode, headers, responseBody)
    }

    protected fun handleFailureMessage(e: Throwable, responseBody: String) {
        onFailure(e, responseBody)
    }

    protected fun handleProgressMessage(totalSize: Long, currentSize: Long, speed: Long) {
        onProgress(totalSize, currentSize, speed)
    }

    protected fun handleMessage(msg: Message) {
        val response: Array<Any>

        when (msg.what) {
            PROGRESS_MESSAGE -> {
                response = msg.obj as Array<Any>
                handleProgressMessage(response[0] as Long, response[1] as Long, response[2] as Long)
            }
            SUCCESS_MESSAGE -> {
                response = msg.obj as Array<Any>
                handleSuccessMessage((response[0] as Int).toInt(), response[1] as Array<Header>, response[2] as String)
            }
            FAILURE_MESSAGE -> {
                response = msg.obj as Array<Any>
                handleFailureMessage(response[0] as Throwable, response[1] as String)
            }
            START_MESSAGE -> onStart()
            FINISH_MESSAGE -> onFinish()
        }
    }

    protected fun sendMessage(msg: Message) {
        if (handler != null) {
            handler!!.sendMessage(msg)
        } else {
            handleMessage(msg)
        }
    }

    protected fun obtainMessage(responseMessage: Int, response: Any?): Message {
        var msg: Message? = null
        if (handler != null) {
            msg = this.handler!!.obtainMessage(responseMessage, response)
        } else {
            msg = Message.obtain()
            msg!!.what = responseMessage
            msg.obj = response
        }
        return msg
    }
}