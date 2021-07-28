package com.hylink.chexunjingwu.http.response

data class GetJqTztgFkListResponse(
    var list: List<ListDTO>? = null
) {
    data class ListDTO(
        var xh: Int = 0,
        var notice_id: String? = null,
        var feedback_id: String? = null,
        var feedback_superior_id: Any? = null,
        var feedback_type: String? = null,
        var feedback_message: FeedbackMessageDTO? = null,
        var feedback_imei: String? = null,
        var feedback_time: String? = null,
        var feedback_status: String? = null,
        var visiable: Int = 0,
        var createuser: Any? = null,
        var createtime: Long = 0,
        var updateuser: Any? = null,
        var updatetime: Long = 0
    ) {
        data class FeedbackMessageDTO(var fknr: String? = null)
    }
}
