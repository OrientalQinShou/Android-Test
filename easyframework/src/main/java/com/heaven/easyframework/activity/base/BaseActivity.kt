package com.heaven.easyframework.activity.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyframework.activity.base
 * 创建人：Heaven.li
 * 创建时间：2019-10-15 14:53
 * 备注：Activity基类
 */
open class BaseActivity : AppCompatActivity() {

    private val TAG = "BaseActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}