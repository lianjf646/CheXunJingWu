package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.ChaCheRequest
import com.example.chexunjingwu.http.request.ChaRenRequest
import com.example.chexunjingwu.http.request.ChaRenResponse
import com.example.chexunjingwu.http.response.ChaCheResponse

class CheckRepository : BaseRepository() {

    suspend fun checkRY(request: ChaRenRequest): ChaRenResponse {
        return ApiEngine
            .getApiService()
            .checkRY(createRequsetBody(request)).getApiData();

    }

    suspend fun checCL(request: ChaCheRequest): ChaCheResponse {
        return ApiEngine
            .getApiService()
            .checCL(createRequsetBody(request)).getApiData();

    }
}