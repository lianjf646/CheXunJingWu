package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.HomeLoginRequest
import com.example.chexunjingwu.http.response.HomeLoginResponse

class LoginRepository : BaseRepository() {
    suspend fun login(homeLoginRequest: HomeLoginRequest): HomeLoginResponse {
        return ApiEngine
            .getApiService()
            .homeLogin(createRequsetBody(homeLoginRequest)).getApiData();
    }
}