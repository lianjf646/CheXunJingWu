package com.example.chexunjingwu.http.request

data class GetJqTztgListRequest(
    var currentPage: Int = 0,
    var showCount: Int = 0,
    var pd: Pd? = null
) {
    data class Pd(
        var carno: String? = null,
        var imei: String? = null,
        var mjdwdm: String? = null,
        var search: String? = null,
        var mjjh: String? = null
    )
}
