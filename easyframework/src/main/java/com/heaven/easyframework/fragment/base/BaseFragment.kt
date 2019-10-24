package com.heaven.easyframework.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyframework.fragment.base
 * 创建人：Heaven.li
 * 创建时间：2019-10-15 14:52
 * 备注：Fragment基类
 */
abstract class BaseFragment : Fragment() {

    var mRootView : View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = setLayout(inflater)
        return mRootView
    }

    protected abstract fun setLayout(inflater: LayoutInflater): View
}