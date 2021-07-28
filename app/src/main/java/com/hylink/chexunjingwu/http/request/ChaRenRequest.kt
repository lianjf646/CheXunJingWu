package com.hylink.chexunjingwu.http.request

data class ChaRenRequest(
    var gps_x: String? = null,
    var gps_y: String? = null,
    var ry_sfzh: String? = null,
    var username: String? = null,
    var pad_cid: String? = null,
    var operator: OperatorBean? = null
) {
    data class OperatorBean(
        var jybmbh: String? = null,
        var jycode: String? = null,
        var jysfzh: String? = null,
        var jyxm: String? = null,
        var jybmmc: String? = null
    )
}
