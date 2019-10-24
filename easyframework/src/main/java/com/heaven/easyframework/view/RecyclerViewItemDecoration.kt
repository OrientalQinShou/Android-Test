package com.heaven.easyframework.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyframework.view
 * 创建人：Heaven.li
 * 创建时间：2019-10-17 11:20
 * 备注：RecyclerView间距设置
 */
open class RecyclerViewItemDecoration(mSpaceValueMap : HashMap<String,Int>)  : RecyclerView.ItemDecoration() {

    var mSpaceValueMap = mSpaceValueMap

    val TOP_DECORATION =  "top_decoration"
    val BOTTOM_DECORATION =  "bottom_decoration"
    val LEFT_DECORATION =  "left_decoration"
    val RIGHT_DECORATION =  "right_decoration"

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        if (mSpaceValueMap.get(TOP_DECORATION) != null) {
            outRect.top = mSpaceValueMap.get(TOP_DECORATION)!!
        }
        if (mSpaceValueMap.get(LEFT_DECORATION) != null) {
            outRect.left = mSpaceValueMap.get(LEFT_DECORATION)!!
        }
        if (mSpaceValueMap.get(RIGHT_DECORATION) != null) {
            outRect.right = mSpaceValueMap.get(RIGHT_DECORATION)!!
        }
        if (mSpaceValueMap.get(BOTTOM_DECORATION) != null) {
            outRect.bottom = mSpaceValueMap.get(BOTTOM_DECORATION)!!
        }
    }
}