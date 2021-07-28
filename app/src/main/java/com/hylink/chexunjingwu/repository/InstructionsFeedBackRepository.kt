package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.JTztgFkRequest
import com.hylink.chexunjingwu.http.request.YwDictRequest
import com.hylink.chexunjingwu.http.response.JTztgFkResponse
import com.hylink.chexunjingwu.http.response.YwDictResponse

class InstructionsFeedBackRepository : BaseRepository() {

    suspend fun ywDict(request: YwDictRequest): YwDictResponse {
        return ApiEngine
            .getApiService()
            .ywDict(createRequsetBody(request)).getApiData();

    }

    suspend fun jTztgFk(request: JTztgFkRequest): JTztgFkResponse {
        return ApiEngine
            .getApiService()
            .jTztgFk(createRequsetBody(request)).getApiData();

    }

}