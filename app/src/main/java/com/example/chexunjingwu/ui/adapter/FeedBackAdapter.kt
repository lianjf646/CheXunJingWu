package com.example.chexunjingwu.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.chexunjingwu.http.response.GetJqFkListResponse

class FeedBackAdapter(var context: Context? = null) : BaseAdapter() {

    var beanList: MutableList<GetJqFkListResponse.ListBean>? = null


    override fun getCount(): Int = if (beanList == null) 0 else beanList?.size!!

    override fun getItem(position: Int): GetJqFkListResponse.ListBean = beanList?.get(position)!!


    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


        return convertView!!;
    }

    class ViewHolder(itemView: View) {

    }
}