package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.JqDictRequest
import com.example.chexunjingwu.http.request.JqJqfkRequest
import com.example.chexunjingwu.http.request.JqJqfkzcDetailsRequest
import com.example.chexunjingwu.http.request.YwDictRequest
import com.example.chexunjingwu.http.response.JqDictResponse
import com.example.chexunjingwu.http.response.JqJqfkResponse
import com.example.chexunjingwu.http.response.JqJqfkzcDetailsResponse
import com.example.chexunjingwu.http.response.YwDictResponse

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