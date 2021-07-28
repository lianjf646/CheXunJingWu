package com.hylink.chexunjingwu.http.response

data class AlarmMessage1(
    var comparison_message: ComparisonMessageBean? = null,
    var comparison_id: String? = null,
    var comparison_img_qriginal: String? = null,
    var comparison_time: String? = null,
    var verificationPd:  VerificationPdBean? = null,
    var comparison_exception: Int = 0,
    var comparison_img: String? = null,
    var basicInformation: List<BasicInformationBean?>? = null,
    var Tags: List<String?>? = null,
    var tagesInfoList: List<BasicInformationBean?>? = null
) {
    data class ComparisonMessageBean(
        var comparison_img_qriginal: String? = null,
        var gps_x: String? = null,
        var plate_color: String? = null,
        var gps_y: String? = null,
        var comparison_type: String? = null,
        var hpzl: String? = null,
        var source_type: String? = null,
        var comparison_img: String? = null,
        var operator: OperatorBean? = null,
        var byVehicleType: String? = null,
        var hpys: String? = null,
        var hphm: String? = null,
        var info: String? = null,
        var pad_cid: String? = null,
        var comparison_interface: List<ComparisonInterfaceBean?>? = null
    ) {
        data class OperatorBean(
            var jysfzh: String? = null,
            var jycode: String? = null,
            var jyxm: String? = null,
            var jybmbh: String? = null,
            var jybmmc: String? = null,
        )

        data class ComparisonInterfaceBean(var verification_interface_tag: String? = null)


    }
    data class VerificationPdBean(
        var csys: String? = null,
        var clpp: String? = null,
        var cllx: String? = null,
        var hphm: String? = null
    )
}
