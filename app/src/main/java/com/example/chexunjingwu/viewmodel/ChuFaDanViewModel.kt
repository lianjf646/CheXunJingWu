package com.example.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chexunjingwu.base.BaseViewModel
import com.example.chexunjingwu.http.api.HttpData
import com.example.chexunjingwu.http.request.GetAjbhResquest
import com.example.chexunjingwu.http.request.GetCfjdDetailRequest
import com.example.chexunjingwu.http.request.JqCfjdRequest
import com.example.chexunjingwu.http.request.YwDictRequest
import com.example.chexunjingwu.http.response.GetAjbhResponse
import com.example.chexunjingwu.http.response.GetCfjdDetailResponse
import com.example.chexunjingwu.http.response.JqCfjdResponse
import com.example.chexunjingwu.http.response.YwDictResponse
import com.example.chexunjingwu.repository.ChuFaDanRepository

class ChuFaDanViewModel : BaseViewModel() {

    private val chuFaDanRepository by lazy { ChuFaDanRepository() }
    val ywDictLiveData = MutableLiveData<HttpData<YwDictResponse>>()
    val getAjbhLiveData = MutableLiveData<HttpData<GetAjbhResponse>>()
    val jqCfjdLiveData = MutableLiveData<HttpData<JqCfjdResponse>>()
    val getCfjdDetailLiveData = MutableLiveData<HttpData<GetCfjdDetailResponse>>()

    fun ywDict(code: String) {
        var request = YwDictRequest(code = code);
        var httpData = HttpData<YwDictResponse>()
        launch(block = {
            httpData.httpResponse = chuFaDanRepository.ywDict(request)
        }, complete = {
            httpData.httpResponseState = it
            ywDictLiveData.value = httpData
        })
    }

    fun getAjbh() {
        var request = GetAjbhResquest();
        var httpData = HttpData<GetAjbhResponse>()
        launch(block = {
            httpData.httpResponse = chuFaDanRepository.getAjbh(request)
        }, complete = {
            httpData.httpResponseState = it
            getAjbhLiveData.value = httpData
        })
    }

    fun jqCfjd(request: JqCfjdRequest) {

        var httpData = HttpData<JqCfjdResponse>()
        launch(block = {
            httpData.httpResponse = chuFaDanRepository.jqCfjd(request)
        }, complete = {
            httpData.httpResponseState = it
            jqCfjdLiveData.value = httpData
        })
    }

    fun cfjdEdit(request: JqCfjdRequest) {

        var httpData = HttpData<JqCfjdResponse>()
        launch(block = {
            httpData.httpResponse = chuFaDanRepository.cfjdEdit(request)
        }, complete = {
            httpData.httpResponseState = it
            jqCfjdLiveData.value = httpData
        })
    }

    fun getCfjdDetail(jqbh: String?) {
        var request = GetCfjdDetailRequest(jqbh)
        var httpData = HttpData<GetCfjdDetailResponse>()
        launch(block = {
            httpData.httpResponse = chuFaDanRepository.getCfjdDetail(request)
        }, complete = {
            httpData.httpResponseState = it
            getCfjdDetailLiveData.value = httpData
        })
    }
}