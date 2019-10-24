package cn.lag.lag.adapter

import android.content.Context
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.lag.lag.R
import kotlinx.android.synthetic.main.item_backdoor.view.*

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag.adapter
 * 创建人：Heaven.li
 * 创建时间：2019/10/10 10:41
 * 备注：
 */
class BackDoorAdapter(var date: List<String>, context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val context = context

    private var itemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val inflate = from(parent.context).inflate(R.layout.item_backdoor, null)
            return MyHolder(inflate)
        } else {
            val inflate = from(parent.context).inflate(R.layout.item_backdoor, null)
            return MyHolder2(inflate)
        }
    }

    override fun getItemCount(): Int {
        return date.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyHolder) {
            holder.bind(date[position], context)
        } else if (holder is MyHolder2) {
            holder.bind(date[position], context)
        }
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(position)

        }
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            //多布局
            in 1..2 ->
                return 1
            else -> return super.getItemViewType(position)
        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(date: String, context: Context) {
            itemView.txt_name.text = date
            itemView.txt_name.setTextColor(context.resources.getColor(R.color.color_6224ED, null))

        }
    }

    class MyHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(date: String, context: Context) {
            itemView.txt_name.text = date
            itemView.txt_name.setTextColor(context.resources.getColor(R.color.color_2299ee, null))
        }
    }

    fun setOnKotlinItemClickListener(itemClickListener: ((Int) -> Unit)) {
        this.itemClickListener = itemClickListener

    }

}