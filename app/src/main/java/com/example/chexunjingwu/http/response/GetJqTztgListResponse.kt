package com.example.chexunjingwu.http.response

data class GetJqTztgListResponse(
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
            var carno: String? = null,
            var imei: String? = null,
            var mjdwdm: String? = null,
            var mjjh: String? = null,
            var search: String? = null
        )
    }

    data class ListBean(
        var xhl: Int = 0,
        var notice_id: String? = null,
        var feedback_id: String? = null,
        var feedback_superior_id: Any? = null,
        var feedback_type: String? = null,
        var feedback_message: Any? = null,
        var feedback_imei: String? = null,
        var feedback_time: Any? = null,
        var feedback_status: String? = null,
        var visiable: Int = 0,
        var createuser: Any? = null,
        var createtime: Long = 0,
        var updateuser: Any? = null,
        var updatetime: Long = 0,
        var bt: String? = null,
        var fbdwmc: String? = null,
        var fbrbm: String? = null,
        var fbrxm: String? = null,
        var fbsj: String? = null,
        var nr: String? = null,
        var attachment: List<String>? = null,
        var audio_message: List<AudioMessageBean>? = null,
        var video_message: List<VideoMessageBean>? = null,
        var level: Int = 0
    ) {
        data class AudioMessageBean(
            var path: String? = null,
            var isread: Int = 0,
            var playTime: Int = 0,
            var id: String? = null,
        )

        data class VideoMessageBean(
            var path: String? = null,
            var isread: Int = 0,
            var imgPath: String? = null,
            var id: String? = null
        )
    }
}
