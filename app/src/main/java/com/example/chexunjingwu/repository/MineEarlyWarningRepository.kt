package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.AlarmListRequest
import com.example.chexunjingwu.http.response.AlarmListResponse

class MineEarlyWarningRepository : BaseRepository() {


    suspend fun alarmList(request: AlarmListRequest): AlarmListResponse {
        return ApiEngine
            .getApiService()
            .alarmList(createRequsetBody(request)).getApiData();
    }
}