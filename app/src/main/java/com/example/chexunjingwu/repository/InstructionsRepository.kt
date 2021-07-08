package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.GetJqTztgListRequest
import com.example.chexunjingwu.http.response.GetJqTztgListResponse

class InstructionsRepository :BaseRepository() {


    suspend fun getJqTztgList(request: GetJqTztgListRequest): GetJqTztgListResponse {
        return ApiEngine
            .getApiService()
            .getJqTztgList(createRequsetBody(request)).getApiData();
    }

}