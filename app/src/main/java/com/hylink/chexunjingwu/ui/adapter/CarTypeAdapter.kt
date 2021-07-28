package com.hylink.chexunjingwu.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.BaseDataAdapter
import com.hylink.chexunjingwu.bean.DialogChoiceCarBean
import com.hylink.chexunjingwu.databinding.ItemCarTypeBinding

class CarTypeAdapter : BaseDataAdapter<ItemCarTypeBinding, DialogChoiceCarBean>(), SpinnerAdapter {
    override fun getBind(parent: ViewGroup?): ItemCarTypeBinding =
        ItemCarTypeBinding.inflate(LayoutInflater.from(parent?.context), parent, false)


    override fun showView(bind: ItemCarTypeBinding, position: Int, data: DialogChoiceCarBean) {
        bind.tv.text = data.text
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var spinnerViewHolder: SpinnerViewHolder
        var view: View
        if (convertView == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.item_car_type1, null)
            spinnerViewHolder = SpinnerViewHolder(view)
            view?.tag = spinnerViewHolder
        } else {
            view = convertView
            spinnerViewHolder = convertView.tag as SpinnerViewHolder
        }
        spinnerViewHolder.tv_text.text = dataList?.get(position)?.text
        return view
    }


    class SpinnerViewHolder(itemView: View) {
        var tv_text: TextView = itemView.findViewById(R.id.tv)

    }
}