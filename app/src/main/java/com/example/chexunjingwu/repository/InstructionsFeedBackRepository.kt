package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.JTztgFkRequest
import com.example.chexunjingwu.http.request.YwDictRequest
import com.example.chexunjingwu.http.response.JTztgFkResponse
import com.example.chexunjingwu.http.response.YwDictResponse

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