package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.ChaCheRequest
import com.hylink.chexunjingwu.http.request.ChaRenRequest
import com.hylink.chexunjingwu.http.request.ChaRenResponse
import com.hylink.chexunjingwu.http.response.ChaCheResponse

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