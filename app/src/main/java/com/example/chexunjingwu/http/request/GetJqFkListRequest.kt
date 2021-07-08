package com.example.chexunjingwu.http.request

data class GetJqFkListRequest(
    var currentPage: Int = 1,
    var showCount: Int = 999,
    var pd: PdBean? = null
) {
    data class PdBean(var jqbh: String? = null, var imei: String? = null)
}
