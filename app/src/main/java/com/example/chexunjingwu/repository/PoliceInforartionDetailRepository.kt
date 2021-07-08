package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.JqChangeStateRequest
import com.example.chexunjingwu.http.response.JqChangeStateResponse

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