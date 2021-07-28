package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.HomeLoginRequest
import com.hylink.chexunjingwu.http.response.HomeLoginResponse

class LoginRepository : BaseRepository() {
    suspend fun login(homeLoginRequest: HomeLoginRequest): HomeLoginResponse {
        return ApiEngine
            .getApiService()
            .homeLogin(createRequsetBody(homeLoginRequest)).getApiData();
    }
}