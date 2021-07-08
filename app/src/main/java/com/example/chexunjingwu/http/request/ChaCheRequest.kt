package com.example.chexunjingwu.http.request

data class ChaCheRequest(
    var gps_x: String? = null,
    var gps_y: String? = null,
    var cl_fdjh: String? = null,
    var cl_hphm: String? = null,
    var cl_hpzl: String? = null,
    var cl_sbdm: String? = null,
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
