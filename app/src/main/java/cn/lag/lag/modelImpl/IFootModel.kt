package cn.lag.lag.modelImpl

import android.app.Activity

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag.modelImpl
 * 创建人：Heaven.li
 * 创建时间：2019-10-15 15:15
 * 备注：底部footer 接口
 */
interface IFootModel {

    /**
     * 设置点击回调
     *
     * @param activity
     * @param iFootClick
     */
    fun setFootClick(activity: Activity, iFootClick: IFootClick)

    /**
     * 更改显示效果
     *
     * @param isPress
     */
    fun changeFootPressed(vararg isPress: Boolean)

    /**
     * 获取当前所有foot（选择）状态
     */
    fun getCurrentFootArray(): BooleanArray

    /**
     * 更改购物车按钮数量
     *
     * @param isPress
     */
    fun changeCarNumber(Number: String)

    /**
     * 获取当前foot（选择）选中状态
     */
    fun getCurrentFootState(): Int

    /**
     * 设置底部bar颜色
     */
    fun setFootBg(url: String)

    /**     设置单独icon背景
     * @param normalUrls 默认图url
     * @param PressUrls 按下图nurl
     * @param textColors    文字默认、按下 色值
     * @param names 名字
     */
    fun initFootItemBg(normalUrls: Array<String>, pressUrls: Array<String>, textColors: Array<String>, names: Array<String>)
}