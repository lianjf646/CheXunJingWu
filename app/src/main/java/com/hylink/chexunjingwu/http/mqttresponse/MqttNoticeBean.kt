package com.hylink.chexunjingwu.http.mqttresponse

data class MqttNoticeBean(
    var xhl: Int = 0,
    var notice_id: String? = null,
    var bt: String? = null,
    var fbdwmc: String? = null,
    var fbrbm: String? = null,
    var fbrxm: String? = null,
    var fbsj: String? = null,
    var fsdwbm: String? = null,
    var fsdwdj: String? = null,
    var fslx: String? = null,
    var isread: String? = null,
    var nr: String? = null,
    var fbdwbm: String? = null,
    var visibale: Int = 0,
    var attachment: String? = null,
    var fsdwmc: String? = null,
    var fsjc_message: String? = null,
    var fsjcs: String? = null,
    var audio_message: String? = null,
    var video_message: String? = null,
    var createuser: String? = null
)
