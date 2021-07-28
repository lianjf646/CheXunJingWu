package com.hylink.chexunjingwu.repository

import com.hylink.chexunjingwu.base.BaseRepository
import com.hylink.chexunjingwu.http.api.ApiEngine
import com.hylink.chexunjingwu.http.request.GetAjbhResquest
import com.hylink.chexunjingwu.http.request.GetCfjdDetailRequest
import com.hylink.chexunjingwu.http.request.JqCfjdRequest
import com.hylink.chexunjingwu.http.request.YwDictRequest
import com.hylink.chexunjingwu.http.response.GetAjbhResponse
import com.hylink.chexunjingwu.http.response.GetCfjdDetailResponse
import com.hylink.chexunjingwu.http.response.JqCfjdResponse
import com.hylink.chexunjingwu.http.response.YwDictResponse

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