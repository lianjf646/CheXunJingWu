package com.hylink.chexunjingwu.http.request

data class GetJqListRequest(
    var currentPage: Int? = null,
    var showCount: Int? = null,
    var pd: PdBean? = null
) {
    data class PdBean(
        var carno: String? = null,
        var gxdwdm: String? = null,
        var mjdwmc: String? = null,
        var imei: String? = null,
        var isgx: Int = 0,
        var isnow: Int = 0,
        var jjdzt: String? = null,
        var sfdqjq: Int? = null,
    )
}