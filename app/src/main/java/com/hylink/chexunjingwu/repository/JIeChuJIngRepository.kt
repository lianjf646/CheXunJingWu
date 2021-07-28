package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.GetJqListRequest
import com.hylink.chexunjingwu.http.request.JqJqfkzcListRequest
import com.hylink.chexunjingwu.http.response.GetJqListResponse
import com.hylink.chexunjingwu.http.response.JqJqfkzcListResponse

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