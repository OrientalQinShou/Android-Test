package cn.lag.lag.adapter

import android.content.Context
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.lag.lag.R
import cn.lag.lag.bean.HomeBean
import kotlinx.android.synthetic.main.item_backdoor.view.*

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag.adapter
 * 创建人：Heaven.li
 * 创建时间：2019/10/17 09:49
 * 备注：
 */
class HomeAdapter(var date: MutableList<HomeBean>, context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val context = context

    private var itemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = from(parent.context).inflate(R.layout.item_home, null)
        return MyHolder(inflate)
    }

    override fun getItemCount(): Int {
        return date.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyHolder) {
            holder.bind(date[position], context)
        }
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(position)

        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(date: HomeBean, context: Context) {
//            itemView.txt_name.text = date.TitleName
//            itemView.txt_name.setTextColor(context.resources.getColor(R.color.color_6224ED, null))

        }
    }

    fun setOnKotlinItemClickListener(itemClickListener: ((Int) -> Unit)) {
        this.itemClickListener = itemClickListener

    }

}