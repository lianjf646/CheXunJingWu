package com.hylink.chexunjingwu.http.request

data class AlarmListRequest(
    var currentPage: Int = 1,
    var currentResult: Int = 0,
    var entityOrField: Boolean = true,
    var pageStr: String? = "string",
    var pd: PdBean? = null,
    var showCount: Int = 10,
    var totalPage: Int = 0,
    var totalResult: Int = 0,
) {
    data class PdBean(var imei: String?)
}

