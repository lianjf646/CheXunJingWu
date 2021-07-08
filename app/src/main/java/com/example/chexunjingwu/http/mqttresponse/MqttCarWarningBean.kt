package com.example.chexunjingwu.http.mqttresponse

data class MqttCarWarningBean(
    var comparison_id: String? = null,
    var comparison_message: ComparisonMessageDTO? = null
) {
    data class ComparisonMessageDTO(var hphm: String? = null)
}
