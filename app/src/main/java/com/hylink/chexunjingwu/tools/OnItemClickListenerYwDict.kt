package com.hylink.chexunjingwu.tools

import com.hylink.chexunjingwu.http.response.YwDictResponse

interface OnItemClickListenerYwDict {
    fun onItemClick(bean: YwDictResponse.DataBean, pos: Int)
}