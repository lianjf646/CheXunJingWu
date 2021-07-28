package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.GetPatrolListRequest
import com.hylink.chexunjingwu.http.request.QueryPeripheryVehicleRequest
import com.hylink.chexunjingwu.http.response.GetPatrolListResponse
import com.hylink.chexunjingwu.http.response.QueryPeripheryVehicleResponse

class NearbyPoliceForcesRepository : BaseRepository() {
    suspend fun queryPeripheryVehicle(request: QueryPeripheryVehicleRequest): QueryPeripheryVehicleResponse {
        return ApiEngine
            .getApiService()
            .queryPeripheryVehicle(createRequsetBody(request)).getApiData();
    }

    suspend fun getPatrolList(request: GetPatrolListRequest): GetPatrolListResponse {
        return ApiEngine
            .getApiService()
            .getPatrolList(createRequsetBody(request)).getApiData();
    }
}