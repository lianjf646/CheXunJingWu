package com.example.chexunjingwu.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dylanc.viewbinding.BindingViewHolder
import com.example.chexunjingwu.databinding.ItemJqjqfkzcBinding
import com.example.chexunjingwu.http.response.JqJqfkzcListResponse
import com.example.chexunjingwu.tools.OnClickViewListener
import com.example.chexunjingwu.tools.OnItemClickListener
import com.example.chexunjingwu.tools.getStrings

class JqJqfkzcListAdapter(var onItemClickListener: OnItemClickListener) :
    ListAdapter<JqJqfkzcListResponse.ListDTO, RecyclerView.ViewHolder>(DiffCallback()) {
    lateinit var context: Context

    class DiffCallback : DiffUtil.ItemCallback<JqJqfkzcListResponse.ListDTO>() {
        override fun areItemsTheSame(
            oldItem: JqJqfkzcListResponse.ListDTO,
            newItem: JqJqfkzcListResponse.ListDTO
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: JqJqfkzcListResponse.ListDTO,
            newItem: JqJqfkzcListResponse.ListDTO
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context;
        return BindingViewHolder<ItemJqjqfkzcBinding>(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as BindingViewHolder<ItemJqjqfkzcBinding>
        var bean = currentList[position]
        holder.binding.tvJqbh.text = getStrings(bean.police_alarm_message.jqbh)
        holder.binding.tvTime.text = getStrings(bean.police_alarm_message.bjsj)
        holder.binding.tvLoc.text = getStrings(bean.police_alarm_message.afdd)
        holder.binding.tvContent.text = getStrings(bean.police_alarm_message.bjnr)
        holder.binding.tvType.text = getStrings(bean.police_alarm_message.bjlbmc)
        holder.binding.ll.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                onItemClickListener.onItemClick(position)
            }
        })
    }
}