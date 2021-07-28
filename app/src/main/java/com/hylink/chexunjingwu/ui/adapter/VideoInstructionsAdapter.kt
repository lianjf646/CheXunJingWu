package com.hylink.chexunjingwu.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hylink.chexunjingwu.base.BaseDataAdapter
import com.hylink.chexunjingwu.databinding.ItemVideomessageBinding
import com.hylink.chexunjingwu.http.response.GetJqTztgListResponse
import com.hylink.chexunjingwu.tools.glideLoad

class VideoInstructionsAdapter :
    BaseDataAdapter<ItemVideomessageBinding, GetJqTztgListResponse.ListBean.VideoMessageBean>() {
    override fun getBind(parent: ViewGroup?): ItemVideomessageBinding =
        ItemVideomessageBinding.inflate(
            LayoutInflater.from(parent?.context), parent, false
        )

    override fun showView(
        bind: ItemVideomessageBinding,
        position: Int,
        data: GetJqTztgListResponse.ListBean.VideoMessageBean
    ) {
        glideLoad(data.imgPath!!, bind.videoView)
    }
}