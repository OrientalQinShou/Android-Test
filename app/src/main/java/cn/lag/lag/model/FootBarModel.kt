package cn.lag.lag.model

import android.app.Activity
import android.app.SearchManager
import android.view.View
import android.widget.LinearLayout
import cn.lag.lag.R
import cn.lag.lag.modelImpl.IFootClick
import cn.lag.lag.modelImpl.IFootModel
import kotlinx.android.synthetic.main.layout_foot_new.view.*

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag.model
 * 创建人：Heaven.li
 * 创建时间：2019-10-15 15:18
 * 备注：
 */
class FootBarModel : IFootModel , View.OnClickListener{


    private var iFootClick: IFootClick? = null
    private var activity: Activity? = null
    private var view : LinearLayout? = null
    private var footArray: BooleanArray? = null

    override fun setFootClick(activity: Activity, iFootClick: IFootClick) {
        this.activity = activity
        this.iFootClick = iFootClick
        initView()
    }

    fun initView(){
        val root = activity!!.findViewById(R.id.lsll_home_foot_new) as LinearLayout
        view = LinearLayout.inflate(activity, R.layout.layout_foot_new, null) as LinearLayout
        view?.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        root.addView(view)
        view?.foot_custom_home?.setOnClickListener(this)
        view?.foot_custom_classify?.setOnClickListener(this)
        view?.foot_custom_shopcar?.setOnClickListener(this)
        view?.foot_custom_member?.setOnClickListener(this)
        view?.foot_custom_home?.setSelected(true)
    }

    override fun changeFootPressed(vararg isPress: Boolean) {
        footArray = isPress
        view?.foot_custom_home?.setSelected(isPress[0])
        view?.foot_custom_classify?.setSelected(isPress[1])
        view?.foot_custom_shopcar?.setSelected(isPress[2])
        view?.foot_custom_member?.setSelected(isPress[3])
    }

    override fun getCurrentFootArray(): BooleanArray {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeCarNumber(Number: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentFootState(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setFootBg(url: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initFootItemBg(normalUrls: Array<String>, pressUrls: Array<String>, textColors: Array<String>, names: Array<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.foot_custom_home ->{
                changeFootPressed(true, false, false, false, false)
                iFootClick?.onFootClick(0)
            }
            R.id.foot_custom_classify ->{
                changeFootPressed(false, true, false, false, false)
                iFootClick?.onFootClick(1)
            }
            R.id.foot_custom_shopcar ->{
                changeFootPressed(false, false, true, false, false)
                iFootClick?.onFootClick(2)
            }
            R.id.foot_custom_member ->{
                changeFootPressed(false, false, false, true, false)
                iFootClick?.onFootClick(3)
            }
        }
    }
}