package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.AlarmListRequest
import com.hylink.chexunjingwu.http.response.AlarmListResponse

class MineEarlyWarningRepository : BaseRepository() {


    suspend fun alarmList(request: AlarmListRequest): AlarmListResponse {
        return ApiEngine
            .getApiService()
            .alarmList(createRequsetBody(request)).getApiData();
    }
}