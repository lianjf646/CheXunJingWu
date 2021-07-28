package com.hylink.chexunjingwu.ui.adapter


import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dylanc.viewbinding.BindingViewHolder
import com.hylink.chexunjingwu.databinding.ItemJqTypeBinding
import com.hylink.chexunjingwu.http.response.YwDictResponse
import com.hylink.chexunjingwu.tools.OnClickViewListener
import com.hylink.chexunjingwu.tools.OnItemClickListenerYwDict

class YwDictListAdapter(var onItemClickListenerYwDict: OnItemClickListenerYwDict) :
    ListAdapter<YwDictResponse.DataBean, RecyclerView.ViewHolder>(DiffCallback()) {
    lateinit var context: Context

    class DiffCallback : DiffUtil.ItemCallback<YwDictResponse.DataBean>() {
        override fun areItemsTheSame(
            oldItem: YwDictResponse.DataBean,
            newItem: YwDictResponse.DataBean
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: YwDictResponse.DataBean,
            newItem: YwDictResponse.DataBean
        ) = oldItem == newItem
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context;
        return BindingViewHolder<ItemJqTypeBinding>(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as BindingViewHolder<ItemJqTypeBinding>
        var bean = currentList[position]
        holder.binding.tvName.text = bean.name
        holder.binding.tvName.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                onItemClickListenerYwDict.onItemClick(bean, position)
            }
        })
    }
}