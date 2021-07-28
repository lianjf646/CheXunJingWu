package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.JqChangeStateRequest
import com.hylink.chexunjingwu.http.response.JqChangeStateResponse

class PoliceInforartionDetailRepository : BaseRepository() {
    suspend fun jqQs(request: JqChangeStateRequest): JqChangeStateResponse {
        return ApiEngine
            .getApiService()
            .jqQs(createRequsetBody(request)).getApiData();
    }


    suspend fun jqDcqr(request: JqChangeStateRequest): JqChangeStateResponse {
        return ApiEngine
            .getApiService()
            .jqDcqr(createRequsetBody(request)).getApiData();
    }

    suspend fun jqJqjs(request: JqChangeStateRequest): JqChangeStateResponse {
        return ApiEngine
            .getApiService()
            .jqJqjs(createRequsetBody(request)).getApiData();
    }
}