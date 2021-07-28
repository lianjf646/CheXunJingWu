package com.hylink.chexunjingwu.tools

import com.hylink.chexunjingwu.http.response.JqDictResponse

interface OnItemClickListenerT {
    fun onItemClick(bean: JqDictResponse.ListBean, pos: Int)
}