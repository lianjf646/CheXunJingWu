package com.hylink.chexunjingwu.http.response

data class BasicInformationBean(
    var b: Boolean = true,
    var name: String? = null,
    var tag: String? = null,
    var v: Boolean? = false,
    var data: List<Map<String, String>>? = null
)
