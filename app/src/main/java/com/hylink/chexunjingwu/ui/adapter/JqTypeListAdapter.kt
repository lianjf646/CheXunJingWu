package com.hylink.chexunjingwu.ui.adapter


import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dylanc.viewbinding.BindingViewHolder
import com.hylink.chexunjingwu.databinding.ItemJqTypeBinding
import com.hylink.chexunjingwu.http.response.JqDictResponse
import com.hylink.chexunjingwu.tools.OnClickViewListener
import com.hylink.chexunjingwu.tools.OnItemClickListenerT

class JqTypeListAdapter(var onItemClickListenerT: OnItemClickListenerT) :
    ListAdapter<JqDictResponse.ListBean, RecyclerView.ViewHolder>(DiffCallback()) {
    lateinit var context: Context

    class DiffCallback : DiffUtil.ItemCallback<JqDictResponse.ListBean>() {
        override fun areItemsTheSame(
            oldItem: JqDictResponse.ListBean,
            newItem: JqDictResponse.ListBean
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: JqDictResponse.ListBean,
            newItem: JqDictResponse.ListBean
        ) = oldItem == newItem
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context;
        return BindingViewHolder<ItemJqTypeBinding>(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as BindingViewHolder<ItemJqTypeBinding>
        var bean = currentList[position]
        holder.binding.tvName.text = bean.dictvalue
        holder.binding.tvName.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                onItemClickListenerT.onItemClick(bean, position)
            }
        })
    }
}