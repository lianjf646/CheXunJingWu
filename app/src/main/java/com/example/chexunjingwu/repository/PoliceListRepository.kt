package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.GetUserListRequest
import com.example.chexunjingwu.http.response.GetUserListResponse

class PoliceListRepository:BaseRepository() {
    suspend fun getUserList(request: GetUserListRequest): GetUserListResponse {
        return ApiEngine
            .getApiService()
            .getUserList(createRequsetBody(request))
            .getApiData()
    }
}