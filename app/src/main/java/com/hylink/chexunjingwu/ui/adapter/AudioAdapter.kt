package com.hylink.chexunjingwu.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.App
import com.hylink.chexunjingwu.base.BaseDataAdapter
import com.hylink.chexunjingwu.databinding.ItemAudioBinding
import com.hylink.chexunjingwu.http.response.GetJqTztgListResponse
import com.hylink.chexunjingwu.tools.VoicePlayer

class AudioAdapter :
    BaseDataAdapter<ItemAudioBinding, GetJqTztgListResponse.ListBean.AudioMessageBean>() {
    override fun getBind(parent: ViewGroup?): ItemAudioBinding =
        ItemAudioBinding.inflate(LayoutInflater.from(parent?.context), parent, false)

    var posStart = -1
    var isAudioLoading = false
    override fun showView(
        bind: ItemAudioBinding,
        position: Int,
        data: GetJqTztgListResponse.ListBean.AudioMessageBean
    ) {

        if (data.playTime == 0) {
            bind.tvTime.visibility = View.GONE
        } else {
            bind.tvTime.visibility = View.VISIBLE
        }
        bind.tvTime.text = getTime(data.playTime)
        bind.viewRed.visibility = if (data.isread == 0) View.VISIBLE else View.GONE
        Glide.with(App.app)
            .load(R.drawable.bofang_dd)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(bind.ivStart)

        if (posStart == position) {
            if (VoicePlayer.isPlaying()) {
                bind.ivStart.visibility = View.VISIBLE
                bind.ivStop.visibility = View.GONE
            } else {
                if (isAudioLoading) {
                    bind.ivStart.visibility = View.VISIBLE
                    bind.ivStop.visibility = View.GONE
                } else {
                    bind.ivStart.visibility = View.GONE
                    bind.ivStop.visibility = View.VISIBLE
                }
            }
        } else {
            bind.ivStart.visibility = View.GONE
            bind.ivStop.visibility = View.VISIBLE
        }
    }

    private fun getTime(date: Int): String? {
        return if (date < 60) {
            date.toString() + "秒"
        } else if (date > 60 && date < 3600) {
            val m = date / 60
            val s = date % 60
            m.toString() + "分" + s + "秒"
        } else {
            val h = date / 3600
            val m = date % 3600 / 60
            val s = date % 3600 % 60
            h.toString() + "小时" + m + "''" + s + "秒"
        }
    }

}