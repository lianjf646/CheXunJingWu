package com.example.chexunjingwu.ui.adapter


import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dylanc.viewbinding.BindingViewHolder
import com.example.chexunjingwu.R
import com.example.chexunjingwu.databinding.ItemPoliceNumBinding
import com.example.chexunjingwu.http.response.GetUserListResponse
import com.example.chexunjingwu.tools.OnItemClickListener

class GetUserListAdapter(var onItemClickListenerT: OnItemClickListener) :
    ListAdapter<GetUserListResponse.DataDTO.ListDTO, RecyclerView.ViewHolder>(DiffCallbackaa()) {
    lateinit var context: Context

    class DiffCallbackaa : DiffUtil.ItemCallback<GetUserListResponse.DataDTO.ListDTO>() {
        /**  areItemsTheSame提供了两个对象，需你提供这个两个对象是否是同一个对象。在User对象中有一个userId,
         *其代表了唯一性，所以这里我就使用了oldItem?.userId == newItem?.userId。这个可根据实际情况自行判断。
         **/
        override fun areItemsTheSame(
            oldItem: GetUserListResponse.DataDTO.ListDTO,
            newItem: GetUserListResponse.DataDTO.ListDTO

        ): Boolean {
            Log.e(">>>>>>", "areItemsTheSame: " + oldItem.toString() + "" + newItem.toString())
            return oldItem == newItem
        }

        /**
         * areContentsTheSame也提供了两个对象，然后需要你提供这个两个对象的内容是否一致，
         * 如果不一致，那么 它就将对列表进行重绘和动画加载，反之，表示你已经显示了这个对象的内容并且没有任何的变化， 那么将不做任何的操作。
         */
        override fun areContentsTheSame(
            oldItem: GetUserListResponse.DataDTO.ListDTO,
            newItem: GetUserListResponse.DataDTO.ListDTO
        ): Boolean {
            Log.e(">>>>>>", "areContentsTheSame: " + oldItem.toString() + "" + newItem.toString())
//            return false
            return false
        }
    }

    override fun submitList(list: MutableList<GetUserListResponse.DataDTO.ListDTO>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context;
        return BindingViewHolder<ItemPoliceNumBinding>(parent)
    }


    public override fun getItem(position: Int): GetUserListResponse.DataDTO.ListDTO {
        return super.getItem(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as BindingViewHolder<ItemPoliceNumBinding>
        var bean = getItem(position)
        if (bean.choice!!) {
            holder.binding.ivIcon.setImageResource(R.drawable.icon_yuan_t)
        } else {
            holder.binding.ivIcon.setImageResource(R.drawable.icon_yuan_f)
        }

        holder.binding.tvName.text = bean.name
        holder.binding.tvName.setOnClickListener {
            onItemClickListenerT.onItemClick(position)
        }
    }
}