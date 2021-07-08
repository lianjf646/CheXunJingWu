package com.example.chexunjingwu.http.response

data class GetJqFkListResponse(
    var page: PageBean? = null,
    var list: List<ListBean>? = null
) {
    data class PageBean(
        var showCount: Int = 0,
        var totalPage: Int = 0,
        var totalResult: Int = 0,
        var currentPage: Int = 0,
        var currentResult: Int = 0,
        var entityOrField: Boolean = false,
        var pageStr: String? = null,
        var pd: PdBean? = null
    ) {
        data class PdBean(
            var jqbh: String? = null,
            var imei: String? = null,
            var jjdzt: String? = null
        )
    }

    data class ListBean(
        var xctp: List<String >? = null,
        var xyrtp: List<String >? = null,
        var qttp: List<String >? = null,
        var xh: Int = 0,
        var jqbh: String? = null,
        var BJR: String? = null,
        var ajfssj_y: String? = null,
        var ajjssj_y: String? = null,
        var BJDH: String? = null,
        var bjdh: String? = null,
        var cdccs: String? = null,
        var cdrs: String? = null,
        var fkrxm: String? = "",
        var jqfklbmc: String? = null,
        var jqfklxmc: String? = null,
        var jqfkxlmc: String? = null,
        var ajfssj: String? = null,
        var ajjssj: String? = null,
        var cjqk: String? = null,
        var fsdd: String? = null,
        var fksj: String? = null,
        var ssrs: String? = null,
        var zhrs: String? = null,
        var swrs: String? = null,
        var bjrzjhm: String? = null,
        var fkdbh: String? = null,
        var fscs // 发生场所,
        : String? = null,
        var cjjg //处警结果,
        : String? = null,
        var fscsmc: String? = null,
        var cjjgmc: String? = null,
        var fkrys: String? = null
    )
}
