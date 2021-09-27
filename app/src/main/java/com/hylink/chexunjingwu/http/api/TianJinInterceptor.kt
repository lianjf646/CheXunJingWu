package com.hylink.chexunjingwu.http.api

import okhttp3.Interceptor
import okhttp3.Response

class TianJinInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val serviceID = "4daff2597d634ab28b089f178228dfff"
        val secretKey = "5a50078e2abf459bacd0b77e0e7a931f"
        val requestType = "service"
        val request = chain
            .request()
            .newBuilder()
            .addHeader("serviceID", serviceID)
            .addHeader("secretKey", secretKey)
            .addHeader("requestType", requestType)
            .build()
        return chain.proceed(request)
    }
}