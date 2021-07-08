package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.IfTimeOutRequest
import com.example.chexunjingwu.http.request.LoginRequest
import com.example.chexunjingwu.http.request.SignStatusRequest
import com.example.chexunjingwu.http.response.IfTimeOutResponse
import com.example.chexunjingwu.http.response.LoginResponse
import com.example.chexunjingwu.http.response.SignStatusResponse

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