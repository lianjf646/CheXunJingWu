package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.GetJqTztgListRequest
import com.hylink.chexunjingwu.http.response.GetJqTztgListResponse

class InstructionsRepository :BaseRepository() {


    suspend fun getJqTztgList(request: GetJqTztgListRequest): GetJqTztgListResponse {
        return ApiEngine
            .getApiService()
            .getJqTztgList(createRequsetBody(request)).getApiData();
    }

}