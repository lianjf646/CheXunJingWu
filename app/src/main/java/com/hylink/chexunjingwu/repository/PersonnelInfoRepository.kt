package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.GetAlarmByIdRequest
import com.hylink.chexunjingwu.http.request.GetVerificationPortraitByIdRequest
import com.hylink.chexunjingwu.http.response.GetAlarmByIdResponse
import com.hylink.chexunjingwu.http.response.GetVerificationPortraitByIdResponse

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