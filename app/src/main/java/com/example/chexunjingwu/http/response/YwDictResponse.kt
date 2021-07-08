package com.example.chexunjingwu.http.response

data class YwDictResponse(var data: List<DataBean>? = null) {

    data class DataBean(
        var code: String? = null,
        var level: Int = 0,
        var name: String? = null,
        var pid: String? = null,
        var remark: String? = null,
        var id: String? = null,
        var sort: Int = 0,
        var appCode: String? = null,
        var value: String? = null,
    )
}
