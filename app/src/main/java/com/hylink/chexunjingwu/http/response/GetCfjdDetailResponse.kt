package com.hylink.chexunjingwu.http.response

data class GetCfjdDetailResponse(var data: DataDTO? = null) {
    data class DataDTO(
        var wfid: String? = null,
        var jqbh: String? = null,
        var fddbr: String? = null,
        var bcfr: String? = null,
        var ajbh: String? = null,
        var sjwpqd: String? = null,
        var sjsj: String? = null,
        var xm: String? = null,
        var xb: Int = 0,
        var nl: Int = 0,
        var csrq: String? = null,
        var xzz: String? = null,
        var sfzh: String? = null,
        var cmsj: String? = null,
        var zj: String? = null,
        var gjfk: String? = null,
        var cfjg: String? = null,
        var zxfs: Int = 0,
        var jfyh: String? = null,
        var ssfyjg: String? = null,
        var ssssfy: String? = null,
        var cfdd: String? = null,
        var bamj: String? = null,
        var cfsj: String? = null,
        var createtime: String? = null,
        var createuser: Any? = null,
        var updatetime: String? = null,
        var updateuser: Any? = null,
        var visiable: Int = 0,
        var flag: Any? = null
    )
}