package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.IfTimeOutRequest
import com.hylink.chexunjingwu.http.request.LoginRequest
import com.hylink.chexunjingwu.http.request.SignStatusRequest
import com.hylink.chexunjingwu.http.response.IfTimeOutResponse
import com.hylink.chexunjingwu.http.response.LoginResponse
import com.hylink.chexunjingwu.http.response.SignStatusResponse

class HomeFragmentRepository : BaseRepository() {
    suspend fun ifTimeOut(request: IfTimeOutRequest): IfTimeOutResponse {
        return ApiEngine
            .getApiService()
            .ifTimeOut(createRequsetBody(request)).getApiData();
    }

    suspend fun login(request: LoginRequest): LoginResponse {
        return ApiEngine
            .getApiService()
            .login(createRequsetBody(request)).getApiData();
    }

    suspend fun signStatus(request: SignStatusRequest): SignStatusResponse {
        return ApiEngine
            .getApiService()
            .signStatus(createRequsetBody(request)).getApiData();
    }
}