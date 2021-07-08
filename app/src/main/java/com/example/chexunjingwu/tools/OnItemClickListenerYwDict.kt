package com.example.chexunjingwu.tools

import com.example.chexunjingwu.http.response.YwDictResponse

interface OnItemClickListenerYwDict {
    fun onItemClick(bean: YwDictResponse.DataBean, pos: Int)
}