package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.GetUserListRequest
import com.hylink.chexunjingwu.http.response.GetUserListResponse

class PoliceListRepository:BaseRepository() {
    suspend fun getUserList(request: GetUserListRequest): GetUserListResponse {
        return ApiEngine
            .getApiService()
            .getUserList(createRequsetBody(request))
            .getApiData()
    }
}