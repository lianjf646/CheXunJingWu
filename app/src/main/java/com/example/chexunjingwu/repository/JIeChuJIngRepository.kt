package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.GetJqListRequest
import com.example.chexunjingwu.http.request.JqJqfkzcListRequest
import com.example.chexunjingwu.http.response.GetJqListResponse
import com.example.chexunjingwu.http.response.JqJqfkzcListResponse

class JIeChuJIngRepository : BaseRepository() {

    suspend fun getJqList(request: GetJqListRequest): GetJqListResponse {
        return ApiEngine
            .getApiService()
            .getJqList(createRequsetBody(request)).getApiData();
    }

    suspend fun jqJqfkzcList(request: JqJqfkzcListRequest): JqJqfkzcListResponse {

        return ApiEngine
            .getApiService()
            .jqJqfkzcList(createRequsetBody(request)).getApiData();
    }
}