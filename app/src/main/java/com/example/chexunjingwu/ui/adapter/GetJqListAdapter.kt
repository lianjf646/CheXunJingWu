package com.example.chexunjingwu.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dylanc.viewbinding.BindingViewHolder
import com.example.chexunjingwu.R
import com.example.chexunjingwu.databinding.ItemPoliceInformationBinding
import com.example.chexunjingwu.http.response.GetJqListResponse
import com.example.chexunjingwu.tools.OnItemClickListener

class GetJqListAdapter(var onItemClickListener: OnItemClickListener) :
    ListAdapter<GetJqListResponse.ListBean, RecyclerView.ViewHolder>(DiffCallback()) {
    lateinit var context: Context

    class DiffCallback : DiffUtil.ItemCallback<GetJqListResponse.ListBean>() {
        override fun areItemsTheSame(
            oldItem: GetJqListResponse.ListBean,
            newItem: GetJqListResponse.ListBean
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: GetJqListResponse.ListBean,
            newItem: GetJqListResponse.ListBean
        ) = oldItem == newItem
    }


    override fun submitList(list: MutableList<GetJqListResponse.ListBean>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context;
        return BindingViewHolder<ItemPoliceInformationBinding>(parent)
    }

    public override fun getItem(position: Int): GetJqListResponse.ListBean {
        return super.getItem(position)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as BindingViewHolder<ItemPoliceInformationBinding>
        var bean = currentList[position];
        with(holder.binding) {
            tvJqbh.text = bean?.jqbh
            tvTime.text = bean?.bjsj
            tvLoc.text = bean?.afdd
            tvContent.text = bean?.bjnr
            tvType.text = bean?.bjlbmc

            var jjdztName = ""
            var tv_textColor = 0
            var tv_bg = 0
            when (bean.jjdzt) {
                "01" -> {
                    jjdztName = "待签收"
                    tv_bg = R.drawable.bg_radius_yellow_5
                    tv_textColor = R.color.text_color_E69022
                }
                "02" -> {
                    tv_bg = R.drawable.bg_radius_yellow_5
                    tv_textColor = R.color.text_color_E69022
                    jjdztName = "待到场"
                }
                "03" -> {
                    jjdztName = "待结束"
                    tv_bg = R.drawable.bg_radius_yellow_5
                    tv_textColor = R.color.text_color_E69022
                }
                "04" -> {
                    jjdztName = "待反馈"
                    tv_bg = R.drawable.bg_radius_blue_5
                    tv_textColor = R.color.text_color_E5D8DF6
                }
                "05" -> {
                    jjdztName = "已反馈"
                    tv_bg = R.drawable.bg_radius_green_5
                    tv_textColor = R.color.text_color_5EBB4F
                }
                else -> {
                    jjdztName = ""
                    tv_bg = 0
                }
            }
            tvState.text = jjdztName
            if (tv_bg != 0) {
                val color: Int = context.resources.getColor(tv_textColor)
                tvState.setTextColor(color)
                tvState.setBackgroundResource(tv_bg)
            }
            ll.setOnClickListener {
                onItemClickListener.onItemClick(position)
            }
        }
    }

}


