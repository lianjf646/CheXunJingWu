package com.example.chexunjingwu.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.chexunjingwu.base.BaseDataAdapter
import com.example.chexunjingwu.databinding.ItemPictureBinding
import com.example.chexunjingwu.tools.glideLoad

class PictureAdapter : BaseDataAdapter<ItemPictureBinding, String>() {
    override fun getBind(parent: ViewGroup?): ItemPictureBinding = ItemPictureBinding.inflate(
        LayoutInflater.from(parent?.context), parent, false
    )

    override fun showView(bind: ItemPictureBinding, position: Int, data: String) {
        glideLoad(data, bind.ivItem)
    }
}