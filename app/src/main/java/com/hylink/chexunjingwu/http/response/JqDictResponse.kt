package com.hylink.chexunjingwu.http.response

data class JqDictResponse(var list: List<ListBean>, var dictid: String) {
    data class ListBean(
        var dictvalue: String,
        val dictkey: String
    )
}
