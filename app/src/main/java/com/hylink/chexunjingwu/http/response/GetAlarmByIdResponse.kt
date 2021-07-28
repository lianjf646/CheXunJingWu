package com.hylink.chexunjingwu.http.response

data class GetAlarmByIdResponse(
    val `data`: Data,
    val reason: Reason
) {
    data class Data(
        val alarm_id: String,
        val alarm_message: AlarmMessage,
        val alarm_time: String,
        val alarm_type: Int,
        val createtime: String,
        val createuser: Any,
        val flag: String,
        val pad_cid: String,
        val patrol_record_id: Any,
        val police_unit_id: Any,
        val updatetime: String,
        val updateuser: Any,
        val vehicle_id: Any,
        val visibale: Int
    ) {
        data class AlarmMessage(
            val cameraName: String,
            val cjdz: String,
            val comparison_exception: Int,
            val comparison_interface: List<Any>,
            val info: String,
            val portrait_id: String,
            val portrait_img: String,
            val portrait_img_qriginal: String,
            val portrait_message: PortraitMessage,
            val portrait_time: String,
            val signList: List<Any>,
            val comparison_img: String? = null,
            val Tags: List<String>? = null,
            val comparison_message: ComparisonMessageBean? = null,
            val verificationPortraitDataList: List<VerificationPortraitData>,
            var basicInformation: List<BasicInformationBean>? = null,
            val tagesInfoList: List<BasicInformationBean>? = null
        ) {
            data class PortraitMessage(
                val comparison_count: String,
                val comparison_interface: List<Any>,
                val deviceId: String,
                val gps_x: String,
                val gps_y: String,
                val info: String,
                val mqttSend: String,
                val `operator`: Operator,
                val pad_cid: String,
                val portrait_category: String,
                val portrait_count: String,
                val portrait_img: String,
                val portrait_imgBase64: String,
                val portrait_img_qriginal: String,
                val threshold: String
            ) {
                data class Operator(
                    val jybmbh: String,
                    val jybmmc: String,
                    val jycode: String,
                    val jysfzh: String,
                    val jyxm: String
                )
            }

            data class ComparisonMessageBean(var hphm: String? = null)

            data class VerificationPortraitData(
                val comparisonData: ComparisonData,
                val comparisonDataState: Int,
                val idcard: String,
                val name: String,
                val path: String,
                val score: Int,
                val sex: String
            ) {
                data class ComparisonData(
                    val Tags: List<String>,
                    val basicInformation: List<BasicInformationBean>,
                    val cameraName: String,
                    val cjdz: String,
                    val comparison_exception: Int,
                    val comparison_id: String,
                    val comparison_img: String,
                    val comparison_message: ComparisonMessage,
                    val comparison_time: String,
                    val signList: List<Any>,
                    val tagesInfoList: List<BasicInformationBean>,
                    val trajectoryInfoList: List<Any>,
                ) {


                    data class ComparisonMessage(
                        val comparison_count: String,
                        val comparison_img: String,
                        val comparison_interface: List<ComparisonInterface>,
                        val comparison_type: Int,
                        val deviceId: String,
                        val gps_x: String,
                        val gps_y: String,
                        val info: String,
                        val mqttSend: String,
                        val name: String,
                        val `operator`: Operator,
                        val pad_cid: String,
                        val portrait_category: String,
                        val portrait_count: String,
                        val portrait_id: String,
                        val portrait_img: String,
                        val portrait_imgBase64: String,
                        val portrait_img_qriginal: String,
                        val sfzh: String,
                        val source_type: Int,
                        val threshold: String
                    ) {
                        data class ComparisonInterface(
                            val verification_interface_tag: String
                        )

                        data class Operator(
                            val jybmbh: String,
                            val jybmmc: String,
                            val jycode: String,
                            val jysfzh: String,
                            val jyxm: String
                        )
                    }


                }
            }
        }
    }

    data class Reason(
        val code: String,
        val text: String
    )
}