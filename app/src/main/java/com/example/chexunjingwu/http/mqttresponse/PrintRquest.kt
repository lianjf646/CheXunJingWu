package com.example.chexunjingwu.http.mqttresponse

data class PrintRquest(
    var type: String? = null,
    var content: String? = null,
    var printStateTopic: String? = null,
    var code: String? = null
)
