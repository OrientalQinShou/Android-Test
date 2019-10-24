package com.heaven.easyframework

import com.heaven.easyframework.okhttp.AsyncHttpResponseHandler
import com.heaven.easyframework.util.EAEncryptUtil
import com.heaven.easyframework.util.EARequestParams
import okhttp3.Response
import org.json.JSONObject

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyframework
 * 创建人：Heaven.li
 * 创建时间：2019/10/8 13:06
 * 备注：网络请求
 */
class AcyncHttpClient {

    companion object {
        private var instance: AcyncHttpClient? = null
            get() {
                if (field == null) {
                    field = AcyncHttpClient()
                }
                return field
            }

        fun get(): AcyncHttpClient {
            return instance!!
        }
    }

    // 加密解密公钥
    val ENPUBLICKEY = "596a96cc7bf9108c"

    val ENPRIVATEKEY = "d896f33c44aedc8a"
    /**
     * 是否需要自动加cookie
     */
    var isNeedCookie: Boolean = false
    /**
     * 是否需要自动加密(加密自动加json头包含一系列信息)
     */
    var isNeedEncrypt: Boolean = false

    val encryptUtil: EAEncryptUtil by lazy {
        EAEncryptUtil(ENPUBLICKEY, ENPRIVATEKEY)
    }

    constructor()

    constructor(isNeedCookie: Boolean) {
        this.isNeedCookie = isNeedCookie
    }

    constructor(isNeedCookie: Boolean, isNeedEncrypt: Boolean) {
        this.isNeedCookie = isNeedCookie
        this.isNeedEncrypt = isNeedEncrypt
    }

    enum class netHttp {
        GET, POST
    }

    fun getMethod(typr: netHttp, uil: String, paramsEA: EARequestParams) {
        when (typr) {
            netHttp.GET -> {
                getHttp()
            }
            netHttp.POST -> {
                postHttp()
            }
        }
    }

    private fun getHttp() {

    }

    private fun postHttp() {

    }


    /**
     * 响应报文返回
     */
    private val mResponseHandler = object : AsyncHttpResponseHandler() {

        override fun onResponse(response: Response) {
            try {
                val content = response.body()!!.string()
                if (!parseResponseCode(content)) {
                    //登录已失效
                    //清除本地已有cookie
//                    LemallLoginUtil.clearLocalCookieInfo(AppApplication.getContext())
//                    onFailure(Throwable(AppApplication.getContext().getString(R.string.logong_lose)), AppApplication.getContext().getString(R.string.logong_lose))
                    return
                }
//                if (mTempResponseHandler != null) {
//                    //因为之前response已被消耗，此时返回reponse会直接回调onFailure方法。
//                    //因此此处需要重新build以下reponse
//                    val responseBody = response.body()
//                    val contentType = responseBody!!.contentType()
//                    val newBody = ResponseBody.create(contentType, content)
//                    val newResponse = response.newBuilder().body(newBody).build()
//                    if (myHandler != null) {
//                        myHandler.post(Runnable { mTempResponseHandler.onResponse(newResponse) })
//                    }
//                }
            } catch (e: Exception) {
                EALogger.exception(e)
//                onFailure(Throwable(AppApplication.getContext().getString(R.string.logong_lose)), AppApplication.getContext().getString(R.string.logong_lose))
            }

        }

//        override fun onSuccess(content: String) {
//            //            EALogger.i("AsyncHttpResponseHandler", "onSuccess===>" + content);
//            if (!parseResponseCode(content)) {
//                //登录已失效
//                //清除本地已有cookie
//                LemallLoginUtil.clearLocalCookieInfo(AppApplication.getContext())
//                onFailure(Throwable(AppApplication.getContext().getString(R.string.logong_lose)), AppApplication.getContext().getString(R.string.logong_lose))
//                return
//            }
//            if (mTempResponseHandler != null) {
//                if (myHandler != null) {
//                    myHandler.post(Runnable { mTempResponseHandler.onSuccess(content) })
//                }
//            }
//        }
//
//        override fun onSuccess(statusCode: Int, content: String) {
//            EALogger.i("AsyncHttpResponseHandler", "onSuccess statusCode===>$content")
//            if (!parseResponseCode(content)) {
//                //登录已失效
//                //清除本地已有cookie
//                LemallLoginUtil.clearLocalCookieInfo(AppApplication.getContext())
//                onFailure(Throwable(AppApplication.getContext().getString(R.string.logong_lose)), AppApplication.getContext().getString(R.string.logong_lose))
//                return
//            }
//            if (mTempResponseHandler != null) {
//                if (myHandler != null) {
//                    myHandler.post(Runnable { mTempResponseHandler.onSuccess(content) })
//                }
//            }
//        }
//
//        override fun onSuccess(statusCode: Int, headers: Array<Header>, content: String) {
//            EALogger.i("AsyncHttpResponseHandler", "onSuccess headers===>$content")
//            if (!parseResponseCode(content)) {
//                //登录已失效
//                //清除本地已有cookie
//                LemallLoginUtil.clearLocalCookieInfo(AppApplication.getContext())
//                onFailure(Throwable(AppApplication.getContext().getString(R.string.logong_lose)), AppApplication.getContext().getString(R.string.logong_lose))
//                return
//            }
//            if (mTempResponseHandler != null) {
//                if (myHandler != null) {
//                    myHandler.post(Runnable { mTempResponseHandler.onSuccess(statusCode, headers, content) })
//                }
//            }
//        }
//
//        override fun onFailure(error: Throwable) {
//            EALogger.i("AsyncHttpResponseHandler", "onFailure Throwable")
//            if (mTempResponseHandler != null) {
//                if (myHandler != null) {
//                    myHandler.post(Runnable { mTempResponseHandler.onFailure(error) })
//                }
//            }
//        }
//
//        override fun onFailure(error: Throwable, content: String) {
//            EALogger.i("AsyncHttpResponseHandler", "onFailure Throwable===>")
//            if (mTempResponseHandler != null) {
//                if (myHandler != null) {
//                    myHandler.post(Runnable { mTempResponseHandler.onFailure(error, content) })
//                }
//            }
//        }
//
//        override fun onFinish() {
//            if (mTempResponseHandler != null) {
//                if (myHandler != null) {
//                    myHandler.post(Runnable { mTempResponseHandler.onFinish() })
//                }
//            }
//        }
    }

    /**
     * 判断本次响应是否有效
     *
     * @param content 响应内容
     * @return true=有效，false=无效
     */
    private fun parseResponseCode(content: String): Boolean {
        var content = content
        //        EALogger.i("parseResponseCode报文", "===>" + content);
        try {
            if (isNeedEncrypt) {
                content = getDecrypt(content)
            }
            val contentJson = JSONObject(content)
            val status = contentJson.optString("status")

            val statusCode = Integer.parseInt(status)
            when (statusCode) {
                51//cookie里面cookie_session_id缺失或者不合法
                    , 52//cookie里面cookie_user_id缺失或者不合法
                    , 53//cookie里面cookie_token_date缺失或者不合法
                    , 54//cookie里面cookie_token_id_n缺失或者不合法
                    , 61//cookie里面的信息已经过期了(30天)，需要重新登录
                    , 62//cookie是伪造的，不是登录是张波产生的
                    , 69//系统错误。一般不会出现，因为调用张波的服务器获取混淆码发生了异常
                    , 71//解析token时失败没有返回useid与时间戳
                    , 72//商城本地设置的token过期时间通过expireDays参数配置
                    , 73//失效的token，是在失效库中查询到token无效（用户改密码）
                -> {
                    EALogger.i("失效报文", "===>$content")
                    if (true) {
//                        if (!BuildConfig.isInlay) {
//                            CommonUtil.showToast(AppApplication.getContext(), AppApplication.getContext().getString(R.string.logong_lose))
//                        }
                    }
                    //                    CommonUtil.showToast(AppApplication.getContext(), "cookie出现错误，缺失或不合法");
                    return false
                }
                else -> return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }

    /**
     * 解密传入的 数据
     */
    fun getDecrypt(strJson: String): String {
        val result = encryptUtil.decrypt(strJson)
        return result
    }
}