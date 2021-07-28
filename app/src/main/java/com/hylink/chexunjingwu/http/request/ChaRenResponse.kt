package com.hylink.chexunjingwu.http.request

import com.hylink.chexunjingwu.http.response.BasicInformationBean

data class ChaRenResponse(var data: List<DataBeanXX>? = null) {

    data class DataBeanXX(
        var basicInformation: List<BasicInformationBean>? = null,
        var tagesInfoList: List<BasicInformationBean>? = null,
        var comparison_exception: Int = 0,
        var Tags: List<String>? = null
    )
}