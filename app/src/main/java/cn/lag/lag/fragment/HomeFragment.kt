package cn.lag.lag.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cn.lag.lag.R
import cn.lag.lag.adapter.HomeAdapter
import cn.lag.lag.bean.HomeBean
import com.heaven.easyframework.fragment.base.BaseFragment
import com.heaven.easyframework.view.RecyclerViewItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag.fragment
 * 创建人：Heaven.li
 * 创建时间：2019-10-16 14:52
 * 备注：
 */
class HomeFragment(context: Context) : BaseFragment() {

    var mContext : Context = context
    var mView : View? = null
    var data : MutableList<HomeBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        for (index in 1..10){
            var mHomeBean = HomeBean()
            mHomeBean.TitleName = "Title:"+1
            data.add(mHomeBean)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        titlebar.run {
            setBackgroundResource(R.drawable.shape_gradient)
        }
        initUI()
    }

    private fun initUI() {
        val layoutManager= LinearLayoutManager(mContext)
//        val layout=GridLayoutManager(this,2);
        if (data != null && data.size > 0){
            rcv_home.layoutManager=layoutManager
            var adapter= HomeAdapter(data,mContext)
            var mSpaceValueMap : HashMap<String,Int> = hashMapOf()
            mSpaceValueMap.put("left_decoration",20)
            mSpaceValueMap.put("top_decoration",10)
            mSpaceValueMap.put("right_decoration",20)
            mSpaceValueMap.put("bottom_decoration",10)
            rcv_home.addItemDecoration(RecyclerViewItemDecoration(mSpaceValueMap))
            rcv_home.adapter=adapter
            adapter.setOnKotlinItemClickListener {

                when(it){
                    0 -> {
//                    val intent = Intent(this, TitleBarActivity::class.java)
//                    startActivity(intent)
//                    overridePendingTransition(R.anim.finish_in1, R.anim.finish_out1)
                    }
                }


            }
        }
    }

    override fun setLayout(inflater: LayoutInflater): View {
        mView = inflater.inflate(R.layout.fragment_home, null)

        return mView as View
    }
}