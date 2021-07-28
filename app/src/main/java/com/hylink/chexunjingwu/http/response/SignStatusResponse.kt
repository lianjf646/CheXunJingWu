package com.hylink.chexunjingwu.http.response

data class SignStatusResponse(var data: DataBean){
    data class DataBean(
        var sign_identification: String, var carNo: String, var imei: String
    )
}

