package com.hylink.chexunjingwu.ui.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.hylink.chexunjingwu.base.BaseDataAdapter
import com.hylink.chexunjingwu.bean.JingYuanBean
import com.hylink.chexunjingwu.databinding.ItemFeedbackBinding
import com.hylink.chexunjingwu.http.response.GetJqFkListResponse
import com.hylink.chexunjingwu.tools.glideLoad
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class FeedBackAdapter1 :
    BaseDataAdapter<ItemFeedbackBinding, GetJqFkListResponse.ListBean>() {
    override fun getBind(parent: ViewGroup?): ItemFeedbackBinding =
        ItemFeedbackBinding.inflate(LayoutInflater.from(parent?.context), parent, false)

    override fun showView(
        bind: ItemFeedbackBinding,
        position: Int,
        data: GetJqFkListResponse.ListBean
    ) {
        bind.tvCallback.text = "反馈警员：" + data?.fkrxm
        bind.tvFksj.text = "反馈时间：" + data?.fksj
        bind.tvFwsj.text = "发生时间：" + data?.ajfssj
        bind.tvFkdbh.text = "反馈单编号：" + data?.fkdbh
        bind.tvFkbjr.text = "反馈报警人：" + data?.BJR
        bind.tvFkrzjh.text = "反馈人证件号：" + data?.bjrzjhm
        bind.tvBjrdh.text = "反馈报警电话：" + data?.bjdh
        bind.tvFkjqlb.text = "反馈警情类别：" + data?.jqfklbmc
        bind.tvFkjqxl.text = "反馈警情细类：" + data?.jqfkxlmc
        bind.tvFkjqlx.text = "反馈警情类型：" + data?.jqfklxmc
        bind.tvFsdd.text = "发生地点：" + data?.fsdd
        bind.tvCdrs.text = "出动人数：" + data?.cdrs
        bind.tvCdcs.text = "出动车船数：" + data?.cdccs
        bind.tvFscs.text = "发生场所：" + data?.fscsmc
        bind.tvCjjg.text = "处警结果：" + data?.cjjgmc
        bind.tvFknr.text = "反馈内容：" + data?.cjqk
        bind.img.text = (position + 4).toString()

        if (!TextUtils.isEmpty(data?.fkrys)) {
            val list: List<JingYuanBean> =
                Gson().fromJson(data.fkrys, object : TypeToken<List<JingYuanBean?>?>() {}.type)
            val sb = StringBuffer()
            for (bean in list) {
                sb.append(bean.mjxm)
                sb.append("  ")
            }
            bind.tvFkrys.text = "处警警员:$sb"
        }
        //其他图片，现场图片，嫌疑人图片
        val other: ArrayList<ImageView> = ArrayList()
        val site: ArrayList<ImageView> = ArrayList()
        val person: ArrayList<ImageView> = ArrayList()
        person.add(bind.imgRen1)
        person.add(bind.imgRen2)
        person.add(bind.imgRen3)
        other.add(bind.imgOther1)
        other.add(bind.imgOther2)
        other.add(bind.imgOther3)
        site.add(bind.imgSite1)
        site.add(bind.imgSite2)
        site.add(bind.imgSite3)
        forImg(site, data.xctp as ArrayList<String>)
        forImg(person, data.xyrtp as ArrayList<String>)
        forImg(other, data.qttp as ArrayList<String>)
        bind.llXianChang.visibility = if (data.xctp!!.isNullOrEmpty()) View.GONE else View.VISIBLE
        bind.llXianYiRen.visibility = if (data.xyrtp!!.isNullOrEmpty()) View.GONE else View.VISIBLE
        bind.llQiTa.visibility = if (data.qttp!!.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    private fun forImg(img: ArrayList<ImageView>, list: ArrayList<String>) {
        if (list.isNullOrEmpty()) return
        for (i in list.indices) {
            glideLoad(imageView = img[i], url = list[i])
        }
    }
}