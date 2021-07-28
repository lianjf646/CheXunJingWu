package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.GetJqFkListRequest
import com.hylink.chexunjingwu.http.response.GetJqFkListResponse

class FeedBackDetailRepository : BaseRepository() {

    suspend fun getJqFkList(request: GetJqFkListRequest): GetJqFkListResponse {
        return ApiEngine
            .getApiService()
            .getJqFkList(createRequsetBody(request)).getApiData();

    }
}