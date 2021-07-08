package com.example.chexunjingwu.tools

import com.example.chexunjingwu.http.response.JqDictResponse

interface OnItemClickListenerT {
    fun onItemClick(bean: JqDictResponse.ListBean, pos: Int)
}