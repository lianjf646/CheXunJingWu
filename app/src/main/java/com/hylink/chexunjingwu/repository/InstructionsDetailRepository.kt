package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.GetJqTztgDetailRequest
import com.hylink.chexunjingwu.http.request.GetJqTztgFkListRequest
import com.hylink.chexunjingwu.http.request.InstructionMessageReadRequest
import com.hylink.chexunjingwu.http.response.GetJqTztgDetailResponse
import com.hylink.chexunjingwu.http.response.GetJqTztgFkListResponse
import com.hylink.chexunjingwu.http.response.InstructionMessageReadResponse

class InstructionsDetailRepository :BaseRepository() {



    suspend fun instructionMessageRead(request:InstructionMessageReadRequest): InstructionMessageReadResponse {
        return ApiEngine
            .getApiService()
            .instructionMessageRead(createRequsetBody(request)).getApiData();

    }

    suspend fun getJqTztgDetail(request: GetJqTztgDetailRequest): GetJqTztgDetailResponse {
        return ApiEngine
            .getApiService()
            .getJqTztgDetail(createRequsetBody(request)).getApiData();

    }

    suspend fun getJqTztgFkList(request : GetJqTztgFkListRequest): GetJqTztgFkListResponse {
        return ApiEngine
            .getApiService()
            .getJqTztgFkList(createRequsetBody(request)).getApiData();

    }



}