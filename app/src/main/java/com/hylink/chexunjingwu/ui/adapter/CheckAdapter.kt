package com.hylink.chexunjingwu.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.blankj.utilcode.util.RegexUtils
import com.hylink.chexunjingwu.base.BaseDataAdapter
import com.hylink.chexunjingwu.databinding.ItemCarinfoBinding
import com.hylink.chexunjingwu.http.response.BasicInformationBean

class CheckAdapter : BaseDataAdapter<ItemCarinfoBinding, BasicInformationBean>() {
    override fun getBind(parent: ViewGroup?): ItemCarinfoBinding =
        ItemCarinfoBinding.inflate(LayoutInflater.from(parent?.context), parent, false)

    override fun showView(
        bind: ItemCarinfoBinding,
        position: Int,
        basicInformationBean: BasicInformationBean
    ) {
        bind.chbTitle.text = basicInformationBean.name
        bind.chbTitle.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            basicInformationBean.b = isChecked
            notifyDataSetChanged()
        })

        bind.chbTitle.isChecked = basicInformationBean.b
        if (basicInformationBean?.b) {
            bind.tvContent.visibility = View.VISIBLE
        } else {
            bind.tvContent.visibility = View.GONE
        }

        for (i in 0 until basicInformationBean?.data?.size!!) {
//            Map<String, Object> stringObjectMap = BeanUtil.convertToMap(basicInformationBean.getData().get(i));
            val sb = StringBuilder()
            for ((key, value) in basicInformationBean.data?.get(i)?.entries!!) {
                if (RegexUtils.isMatch(".*?[a-zA-z]+.*?", key)) {
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
}