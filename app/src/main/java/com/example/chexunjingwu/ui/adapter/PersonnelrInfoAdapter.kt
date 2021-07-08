package com.example.chexunjingwu.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.RegexUtils
import com.example.chexunjingwu.base.BaseDataAdapter
import com.example.chexunjingwu.databinding.ItemCarinfoBinding
import com.example.chexunjingwu.http.response.BasicInformationBean

class PersonnelrInfoAdapter : BaseDataAdapter<ItemCarinfoBinding, BasicInformationBean>() {
    var REGEX_CONTAINS_LETTER = ".*?[a-zA-z]+.*?"
    override fun getBind(parent: ViewGroup?): ItemCarinfoBinding = ItemCarinfoBinding.inflate(
        LayoutInflater.from(parent?.context), parent, false
    )


    override fun showView(bind: ItemCarinfoBinding, position: Int, data: BasicInformationBean) {
        bind.chbTitle.text = data.name
        bind.chbTitle.setOnCheckedChangeListener { _, isChecked ->
            data.b = isChecked
            notifyDataSetChanged()
        }
        bind.chbTitle.isChecked = data.b
        bind.tvContent.visibility = if (data.b) View.VISIBLE else View.GONE

//        for (index in 0..data?.data!!.size) {
//            val sb = StringBuilder()
//            for ((key, value) in data.data!!.[index]) {
//                if (RegexUtils.isMatch(REGEX_CONTAINS_LETTER, key)) {
//                    continue
//                }
//                sb.append(key)
//                sb.append(":")
//                sb.append(value)
//                sb.append("\n")
//            }
//            bind.tvContent.text = sb.toString()
//        }
        if (data.data.isNullOrEmpty()) return
        val sb = StringBuilder()
        for ((key, value) in data.data!![0]) {
            if (RegexUtils.isMatch(REGEX_CONTAINS_LETTER, key)) {
                continue
            }
            sb.append(key)
            sb.append(":")
            sb.append(value)
            sb.append("\n")
        }
        bind.tvContent.text = sb.toString()
    }
}