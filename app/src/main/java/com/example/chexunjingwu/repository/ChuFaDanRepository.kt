package com.example.chexunjingwu.repository

import com.example.chexunjingwu.base.BaseRepository
import com.example.chexunjingwu.http.api.ApiEngine
import com.example.chexunjingwu.http.request.GetAjbhResquest
import com.example.chexunjingwu.http.request.GetCfjdDetailRequest
import com.example.chexunjingwu.http.request.JqCfjdRequest
import com.example.chexunjingwu.http.request.YwDictRequest
import com.example.chexunjingwu.http.response.GetAjbhResponse
import com.example.chexunjingwu.http.response.GetCfjdDetailResponse
import com.example.chexunjingwu.http.response.JqCfjdResponse
import com.example.chexunjingwu.http.response.YwDictResponse

class ChuFaDanRepository :BaseRepository(){
    suspend fun ywDict(request: YwDictRequest): YwDictResponse {
        return ApiEngine
            .getApiService()
            .ywDict(createRequsetBody(request)).getApiData();

    }

    suspend fun getAjbh(request: GetAjbhResquest): GetAjbhResponse {
        return ApiEngine
            .getApiService()
            .getAjbh(createRequsetBody(request)).getApiData();

    }

    suspend fun jqCfjd(request: JqCfjdRequest): JqCfjdResponse {
        return ApiEngine
            .getApiService()
            .jqCfjd(createRequsetBody(request)).getApiData();

    }

    suspend fun cfjdEdit(request: JqCfjdRequest): JqCfjdResponse {
        return ApiEngine
            .getApiService()
            .cfjdEdit(createRequsetBody(request)).getApiData();

    }

    suspend fun getCfjdDetail(request: GetCfjdDetailRequest): GetCfjdDetailResponse {
        return ApiEngine
            .getApiService()
            .getCfjdDetail(createRequsetBody(request)).getApiData();

    }

}