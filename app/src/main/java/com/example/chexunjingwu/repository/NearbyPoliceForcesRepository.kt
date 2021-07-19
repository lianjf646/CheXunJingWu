package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.GetPatrolListRequest
import com.example.chexunjingwu.http.request.QueryPeripheryVehicleRequest
import com.example.chexunjingwu.http.response.GetPatrolListResponse
import com.example.chexunjingwu.http.response.QueryPeripheryVehicleResponse

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