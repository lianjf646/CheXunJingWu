package com.hylink.chexunjingwu.http.api

data class ApiCommonResponse<T>(val reason: ReasonBean, val result: T) {
    fun getApiData(): T {
        if (reason == null) return result
        else throw ApiException(reason?.code, reason?.text)
    }

    data class ReasonBean(
        val code: String,
        val text: String
    )
}