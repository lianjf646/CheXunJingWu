package com.example.chexunjingwu.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chexunjingwu.R
import com.example.chexunjingwu.http.response.AlarmListResponse
import com.example.chexunjingwu.tools.OnClickViewListener
import com.example.chexunjingwu.tools.glideLoad
import com.example.chexunjingwu.ui.activity.CarInfoActivity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

class MineEarlyWarningAdapter :
    ListAdapter<AlarmListResponse.ListBean, RecyclerView.ViewHolder>(DiffCallback()) {
    var context: Context? = null

    class DiffCallback : DiffUtil.ItemCallback<AlarmListResponse.ListBean>() {
        override fun areItemsTheSame(
            oldItem: AlarmListResponse.ListBean,
            newItem: AlarmListResponse.ListBean
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: AlarmListResponse.ListBean,
            newItem: AlarmListResponse.ListBean
        ) = oldItem == newItem
    }

    override fun submitList(list: MutableList<AlarmListResponse.ListBean>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 0)
            PersonViewHolder(parent.context, inflater.inflate(R.layout.item_prople, parent, false))
        else
            CaronViewHolder(inflater.inflate(R.layout.item_caron, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PersonViewHolder) {
            glideLoad(currentList[position].alarm_message?.portrait_img!!, holder.iv_heard_icon!!)
            holder.tv_time?.text = currentList[position].createtime
            holder.adapter?.byId = currentList[position].alarm_id
            holder.adapter?.submitList(currentList[position].alarm_message?.verificationPortraitDataList)

        } else if (holder is CaronViewHolder) {
            glideLoad(currentList[position].alarm_message?.comparison_img!!, holder.ivCaron!!)
            holder.tvCaron?.text = currentList[position].alarm_message?.comparison_message?.hphm
            holder.tvCreateTime?.text = currentList[position].createtime
            holder.id_flowlayout?.adapter =
                object : TagAdapter<String>(currentList[position]?.alarm_message?.Tags) {
                    override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                        var tv = LayoutInflater.from(context).inflate(R.layout.tv, null) as TextView
                        tv.text = t
                        return tv
                    }
                }
            holder.ll?.setOnClickListener(object : OnClickViewListener() {
                override fun onClickSuc(v: View?) {
                    var intent = Intent();
                    intent.setClass(context!!, CarInfoActivity::class.java);
                    intent.putExtra("byId", currentList[position]?.alarm_id);
                    context!!.startActivity(intent);
                }

            })

        }
    }

    override fun getItemViewType(position: Int): Int = currentList[position].alarm_type

    class PersonViewHolder(context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_heard_icon: ImageView? = null
        var tv_time: TextView? = null
        var iv_heard_1: ImageView? = null
        var tv_be_similar: TextView? = null
        var linear1: LinearLayout? = null
        var recycler: RecyclerView? = null
        var adapter: PortraitChildAdapter? = null;

        init {
            iv_heard_icon = itemView.findViewById(R.id.iv_heard_icon)
            tv_time = itemView.findViewById(R.id.tv_time)
            iv_heard_1 = itemView.findViewById(R.id.iv_heard_1)
            tv_be_similar = itemView.findViewById(R.id.tv_be_similar)
            linear1 = itemView.findViewById(R.id.linear1)
            recycler = itemView.findViewById(R.id.recycler)
            adapter = PortraitChildAdapter()
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            recycler?.layoutManager = linearLayoutManager
            recycler?.adapter = adapter
        }
    }

    class CaronViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCaron: TextView? = null
        var ivCaron: ImageView? = null
        var tvCreateTime: TextView? = null
        var tvCaronInfo: TextView? = null
        var id_flowlayout: TagFlowLayout? = null
        var ll: LinearLayout? = null

        init {
            tvCaron = itemView.findViewById(R.id.tv_caron)
            ivCaron = itemView.findViewById(R.id.iv_caron)
            tvCreateTime = itemView.findViewById(R.id.tv_create_time)
            tvCaronInfo = itemView.findViewById(R.id.tv_caron_info)
            id_flowlayout = itemView.findViewById(R.id.id_flowlayout)
            ll = itemView.findViewById(R.id.ll)
        }
    }
}

