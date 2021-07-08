package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.GetJqTztgDetailRequest
import com.example.chexunjingwu.http.request.GetJqTztgFkListRequest
import com.example.chexunjingwu.http.request.InstructionMessageReadRequest
import com.example.chexunjingwu.http.response.GetJqTztgDetailResponse
import com.example.chexunjingwu.http.response.GetJqTztgFkListResponse
import com.example.chexunjingwu.http.response.InstructionMessageReadResponse

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