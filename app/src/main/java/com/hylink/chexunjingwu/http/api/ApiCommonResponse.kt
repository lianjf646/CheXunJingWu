package com.hylink.chexunjingwu.http.api

data class ApiCommonResponse<T>(val reason: ReasonBean, val result: T) {
    fun getApiData(): T {

//        if (result != null) {
//            return result;
//        }

        if (reason != null) {
            throw ApiException(reason?.code, reason?.text)
        }
        return result
//        throw ApiException("200", "请求成功")
    }

    data class ReasonBean(
        val code: String,
        val text: String
    )
}