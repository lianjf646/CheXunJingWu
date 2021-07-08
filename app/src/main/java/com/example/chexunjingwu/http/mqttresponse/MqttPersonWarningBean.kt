package com.example.chexunjingwu.http.mqttresponse

data class MqttPersonWarningBean(
    var verificationPortraitDataList: List<VerificationPortraitDataListDTO>? = null,
    var portrait_id: String? = null
) {
    data class VerificationPortraitDataListDTO(var name: String? = null)
}
