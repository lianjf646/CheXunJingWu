package com.hylink.chexunjingwu.http.response

data class JqJqfkzcListResponse(var list: List<ListDTO>? = null) {

    data class ListDTO(
        var police_alarm_flow_id: String? = null,
        var jqbh: String?,
        var ajfssj: String? = null,
        var bJR: String = "",
        var bjdh: String? = null,
        var bjrzjhm: String? = null,
        var jqlbmc: String? = null,
        var jqlxmc: String? = null,
        var jqxlmc: String? = null,
        var fsdd: String? = null,
        var cjqk: String? = null,
        var cdccs: String? = null,
        var jqlb: String? = null,
        var jqlx: String? = null,
        var jqxl: String? = null,
        var fkrys: String? = null,
        var police_alarm_message: PoliceAlarmMessageDTO
    ) {
        data class PoliceAlarmMessageDTO(
            var bjlbmc: String? = null,
            var gxdwdm: String? = null,
            var jqbh: String? = null,
            var bjsj: String? = null,
            var afdd: String? = null,
            var bjnr: String? = null,

        )
    }

}
