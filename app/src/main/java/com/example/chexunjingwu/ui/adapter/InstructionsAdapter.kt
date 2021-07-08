package com.example.chexunjingwu.ui.adapter

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dylanc.viewbinding.BindingViewHolder
import com.example.chexunjingwu.R
import com.example.chexunjingwu.databinding.ItemInstructionsBinding
import com.example.chexunjingwu.http.response.GetJqTztgListResponse
import com.example.chexunjingwu.tools.OnClickViewListener
import com.example.chexunjingwu.tools.OnItemClickListener

class InstructionsAdapter(var onItemClickListener: OnItemClickListener) :
    ListAdapter<GetJqTztgListResponse.ListBean, RecyclerView.ViewHolder>(
        DiffCallback()
    ) {
    lateinit var context: Context

    class DiffCallback : DiffUtil.ItemCallback<GetJqTztgListResponse.ListBean>() {
        override fun areItemsTheSame(
            oldItem: GetJqTztgListResponse.ListBean,
            newItem: GetJqTztgListResponse.ListBean
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: GetJqTztgListResponse.ListBean,
            newItem: GetJqTztgListResponse.ListBean
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context;
        return BindingViewHolder<ItemInstructionsBinding>(parent)
    }

    override fun submitList(list: MutableList<GetJqTztgListResponse.ListBean>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder as BindingViewHolder<ItemInstructionsBinding>
        var bean = currentList[position]
        when {
            bean.level == 1 -> {
                holder.binding.tvZllx.setText("普通指令")
                holder.binding.tvZllx.setTextColor(Color.BLUE)
            }
            bean.level === 2 -> {
                holder.binding.tvZllx.setText("红色指令")
                holder.binding.tvZllx.setTextColor(Color.RED)
            }
            else -> {
                holder.binding.tvZllx.setText("普通指令")
                holder.binding.tvZllx.setTextColor(Color.BLUE)
            }
        }

        holder.binding.tvFbnr.text = bean.nr
        holder.binding.tvTime.text = bean.fbsj
        holder.binding.tvContent.text = bean.fbdwmc
        holder.binding.ll.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                onItemClickListener.onItemClick(position)
            }
        })
        if (!TextUtils.isEmpty(bean.feedback_status)) {
            var feedback_status = bean.feedback_status;
            when (feedback_status) {
                "0" -> {
                    holder.binding.tvState.text = "未确认"
                    holder.binding.tvState.setTextColor(
                        context.resources.getColor(R.color.red)
                    )
                    holder.binding.tvState.setBackgroundResource(R.drawable.style_red)
                }
                "1" -> {
                    holder.binding.tvState.text = "已确认"
                    holder.binding.tvState.setTextColor(
                        context.resources.getColor(R.color.green)
                    )
                    holder.binding.tvState.setBackgroundResource(R.drawable.style_green)
                }
                "2" -> {
                    holder.binding.tvState.text = "已反馈"
                    holder.binding.tvState.setTextColor(
                        context.resources.getColor(R.color.color_0FA8CE)
                    )
                    holder.binding.tvState.setBackgroundResource(R.drawable.style_lan)
                }
            }
        }
    }
}