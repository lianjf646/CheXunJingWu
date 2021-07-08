package com.example.chexunjingwu.http.response

data class ChaCheResponse(var data: List< DataBeanXX>? = null) {
    data class DataBeanXX(
        var basicInformation: List<BasicInformationBean >? = null,
        var tagesInfoList: List<BasicInformationBean >? = null,
        var Tags: List<String?>? = null,
        var comparison_exception: Int = 0
    )
}
