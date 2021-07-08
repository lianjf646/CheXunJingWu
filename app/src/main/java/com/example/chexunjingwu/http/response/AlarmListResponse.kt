package com.example.chexunjingwu.http.response

data class AlarmListResponse(
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
            var imei: String? = null,
            var isNow: String? = null,
            var pad_cid: String? = null,
            var alarm_types: List<Int>? = null
        )
    }

    data class ListBean(
        var xh: Int = 0,
        var alarm_id: String? = null,
        var alarm_type: Int = 0,
        var alarm_message: AlarmMessageBean? = null,
        var vehicle_id: Any? = null,
        var police_unit_id: Any? = null,
        var patrol_record_id: Any? = null,
        var pad_cid: String? = null,
        var alarm_time: String? = null,
        var createuser: Any? = null,
        var createtime: String? = null,
        var updateuser: Any? = null,
        var updatetime: Any? = null,
        var visibale: Int = 0,
        var flag: String? = null,
        var tagesInfoList: List<BasicInformationBean?>? = null,
        private var verificationPortraitDataList: List<AlarmMessage0.VerificationPortraitDataListBean?>? = null
    ) {

        data class AlarmMessageBean(
            var portrait_img_qriginal: String? = null,
            var portrait_time: String? = null,
            var portrait_message: AlarmMessage0.PortraitMessageBean? = null,
            var comparison_exception: Int = 0,
            var portrait_img: String? = null,
            var portrait_id: String? = null,
            var info: String? = null,
            var verificationPortraitDataList: List<AlarmMessage0.VerificationPortraitDataListBean?>? = null,
            var comparison_message: AlarmMessage1.ComparisonMessageBean? = null,
            var comparison_id: String? = null,
            var comparison_img_qriginal: String? = null,
            var comparison_time: String? = null,
            var verificationPd: AlarmMessage1.VerificationPdBean? = null,
            var comparison_img: String? = null,
            var basicInformation: List<BasicInformationBean?>? = null,
            var Tags: List<String?>? = null,
            var tagesInfoList: List<BasicInformationBean?>? = null
        ) {

        }
    }
}