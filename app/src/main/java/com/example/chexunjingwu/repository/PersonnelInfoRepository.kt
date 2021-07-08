package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.GetAlarmByIdRequest
import com.example.chexunjingwu.http.request.GetVerificationPortraitByIdRequest
import com.example.chexunjingwu.http.response.GetAlarmByIdResponse
import com.example.chexunjingwu.http.response.GetVerificationPortraitByIdResponse

class PersonnelInfoRepository : BaseRepository() {
    suspend fun getAlarmById(request: GetAlarmByIdRequest): GetAlarmByIdResponse {
        return ApiEngine
            .getApiService()
            .getAlarmById(createRequsetBody(request)).getApiData();
    }

    suspend fun getVerificationPortraitById(request: GetVerificationPortraitByIdRequest): GetVerificationPortraitByIdResponse {
        return ApiEngine
            .getApiService()
            .getVerificationPortraitById(createRequsetBody(request)).getApiData();
    }
}