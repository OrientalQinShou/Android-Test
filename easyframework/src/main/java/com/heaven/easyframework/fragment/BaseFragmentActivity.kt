package com.heaven.easyframework.fragment

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyframework.fragment
 * 创建人：Heaven.li
 * 创建时间：2019-10-15 14:41
 * 备注：FragmentActivity基类
 */
open class BaseFragmentActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}