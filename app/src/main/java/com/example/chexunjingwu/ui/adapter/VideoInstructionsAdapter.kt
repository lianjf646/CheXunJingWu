package com.example.chexunjingwu.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.chexunjingwu.base.BaseDataAdapter
import com.example.chexunjingwu.databinding.ItemVideomessageBinding
import com.example.chexunjingwu.http.response.GetJqTztgListResponse
import com.example.chexunjingwu.tools.glideLoad

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