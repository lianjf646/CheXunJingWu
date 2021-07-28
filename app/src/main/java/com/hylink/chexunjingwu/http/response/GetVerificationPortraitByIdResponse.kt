package com.hylink.chexunjingwu.http.response

data class GetVerificationPortraitByIdResponse(var data: DataDTO? = null) {
    data class DataDTO(
        var tags: List<String?>? = null,
        var comparison_img: String? = null,
        var basicInformation: List<BasicInformationBean>? = null,
        var tagesInfoList: List<BasicInformationBean>? = null
    )
}
