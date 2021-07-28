package com.hylink.chexunjingwu.base

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseDataAdapter<Bind : ViewBinding, Data : Any> : BaseAdapter() {

    var dataList: List<Data>? = null
    override fun getCount(): Int =
        if (dataList.isNullOrEmpty())
            0
        else
            dataList!!.size

    override fun getItem(position: Int): Data = dataList!![position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder<Bind>?
        val view: View
        if (convertView == null) {
            var bind = getBind(parent)
            view = bind.root;
            viewHolder = ViewHolder(bind);
            view!!.tag = viewHolder
        } else {
            view = convertView
            viewHolder = convertView.tag as ViewHolder<Bind>?;

        }
        showView(viewHolder!!.bind, position, dataList!![position])
        return view;
    }

    abstract fun getBind(parent: ViewGroup?): Bind

    abstract fun showView(bind: Bind, position: Int, data: Data)


    class ViewHolder<Bind : ViewBinding>(viewBinding: Bind) {
        var bind: Bind = viewBinding

    }
}
