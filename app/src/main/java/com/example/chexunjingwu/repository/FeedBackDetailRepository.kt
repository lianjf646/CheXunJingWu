package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.GetJqFkListRequest
import com.example.chexunjingwu.http.response.GetJqFkListResponse

class FeedBackDetailRepository : BaseRepository() {

    suspend fun getJqFkList(request: GetJqFkListRequest): GetJqFkListResponse {
        return ApiEngine
            .getApiService()
            .getJqFkList(createRequsetBody(request)).getApiData();

    }
}