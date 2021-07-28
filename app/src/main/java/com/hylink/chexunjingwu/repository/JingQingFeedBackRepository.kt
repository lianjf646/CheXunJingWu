package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.JqDictRequest
import com.hylink.chexunjingwu.http.request.JqJqfkRequest
import com.hylink.chexunjingwu.http.request.JqJqfkzcDetailsRequest
import com.hylink.chexunjingwu.http.request.YwDictRequest
import com.hylink.chexunjingwu.http.response.JqDictResponse
import com.hylink.chexunjingwu.http.response.JqJqfkResponse
import com.hylink.chexunjingwu.http.response.JqJqfkzcDetailsResponse
import com.hylink.chexunjingwu.http.response.YwDictResponse

class JingQingFeedBackRepository : BaseRepository() {


    suspend fun jqJqfkzcDetails(request: JqJqfkzcDetailsRequest): JqJqfkzcDetailsResponse {
        return ApiEngine
            .getApiService()
            .jqJqfkzcDetails(createRequsetBody(request)).getApiData();

    }

    suspend fun jqDict(request: JqDictRequest): JqDictResponse {
        return ApiEngine
            .getApiService()
            .jqDict(createRequsetBody(request)).getApiData();

    }

    suspend fun ywDict(request: YwDictRequest): YwDictResponse {
        return ApiEngine
            .getApiService()
            .ywDict(createRequsetBody(request)).getApiData();

    }

    suspend fun jqJqfk(request: JqJqfkRequest): JqJqfkResponse {
        return ApiEngine
            .getApiService()
            .jqJqfk(createRequsetBody(request)).getApiData();

    }
}