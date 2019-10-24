package com.heaven.easyframework.util

import android.util.Base64
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.*
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.framework.util
 * 创建人：Heaven.li
 * 创建时间：2019/10/8 17:26
 * 备注：加密工具
 */
class EAEncryptUtil {


    private var key : String
    private var iv : String

    constructor(key : String,iv : String){
        this.key = key
        this.iv = iv
    }

    /**
     * 解密
     *
     * @param content 需解密的base64数据
     * @return 原文
     */
    fun decrypt(content: String): String {
        try {
            val decode = Base64.decode(content, Base64.DEFAULT)
            val cipher = getCipher(Cipher.DECRYPT_MODE)
            val doFinal = cipher!!.doFinal(decode)
            return String(doFinal)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 加密
     *
     * @param content 要加密的字符串
     * @return 加密后的base64数据
     */
    fun encrypt(content: String): String {

        try {
            val cipher = getCipher(Cipher.ENCRYPT_MODE)
            val doFinal = cipher!!.doFinal(content.toByteArray(charset("utf-8")))
            return Base64.encodeToString(doFinal, Base64.DEFAULT)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 获取加密对象
     *
     * @param cipherType
     * @return
     */
    private fun getCipher(cipherType: Int): Cipher? {
        Security.addProvider(BouncyCastleProvider())
        val keySpec = SecretKeySpec(key.toByteArray(), "AES")
        val ivParame = IvParameterSpec(iv.toByteArray())
        var cipher: Cipher? = null
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC")
            cipher!!.init(cipherType, keySpec, ivParame)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchProviderException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        }

        return cipher
    }
}