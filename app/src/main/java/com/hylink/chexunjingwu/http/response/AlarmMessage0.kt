package com.hylink.chexunjingwu.http.response

data class AlarmMessage0(
    var portrait_img_qriginal: String? = null,
    var portrait_time: String? = null,
    var portrait_message: PortraitMessageBean? = null,
    var comparison_exception: Int = 0,
    var portrait_img: String? = null,
    var portrait_id: String? = null,
    var info: String? = null,
    var verificationPortraitDataList: List<VerificationPortraitDataListBean?>? = null
) {
    data class PortraitMessageBean(
        var portrait_count: String? = null,
        var gps_x: String? = null,
        var gps_y: String? = null,
        var portrait_img: String? = null,
        var portrait_category: String? = null,
        var threshold: String? = null,
        var portrait_imgBase64: String? = null,
        var operator: OperatorBean? = null,
        var mqttSend: String? = null,
        var portrait_img_qriginal: String? = null,
        var comparison_count: String? = null,
        var info: String? = null,
        var pad_cid: String? = null,
        var comparison_interface: List<*>? = null
    ) {
        data class OperatorBean(
            var jysfzh: String? = null,
            var jycode: String? = null,
            var jyxm: String? = null,
            var jybmbh: String? = null,
            var jybmmc: String? = null
        ) {

        }

    }

    data class VerificationPortraitDataListBean(
        var path: String? = null,
        var score: Int = 0,
        var sex: String? = null,
        var idcard: String? = null,
        var name: String? = null,
        var comparisonDataState: Int = 0,
        var comparisonData: ComparisonDataBean? = null
    ) {
        data class ComparisonDataBean(
            var comparison_id: String? = null,
            var comparison_time: String? = null,
            var comparison_exception: Int = 0,
            var comparison_img: String? = null,
            var basicInformation: List<BasicInformationBean?>? = null,
            var trajectoryInfoList: List<*>? = null,
            var Tags: List<String?>? = null,
            var tagesInfoList: List<BasicInformationBean?>? = null
        ) {

        }

    }
}
