package com.example.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chexunjingwu.base.BaseViewModel
import com.example.chexunjingwu.http.api.HttpData
import com.example.chexunjingwu.http.request.JqDictRequest
import com.example.chexunjingwu.http.request.JqJqfkRequest
import com.example.chexunjingwu.http.request.JqJqfkzcDetailsRequest
import com.example.chexunjingwu.http.request.YwDictRequest
import com.example.chexunjingwu.http.response.JqDictResponse
import com.example.chexunjingwu.http.response.JqJqfkResponse
import com.example.chexunjingwu.http.response.JqJqfkzcDetailsResponse
import com.example.chexunjingwu.http.response.YwDictResponse
import com.example.chexunjingwu.repository.JingQingFeedBackRepository

class JingQingFeedBackViewModel : BaseViewModel() {

    private val jingQingFeedBackRepository by lazy { JingQingFeedBackRepository() }

    val jqJqfkzcDetailsLiveData = MutableLiveData<HttpData<JqJqfkzcDetailsResponse>>()
    val jqDictLiveData = MutableLiveData<HttpData<JqDictResponse>>()
    val ywDictLiveData = MutableLiveData<HttpData<YwDictResponse>>()
    val jqJqfkLiveData = MutableLiveData<HttpData<JqJqfkResponse>>()

    fun jqJqfkzcDetails(jqbh: String) {
        var httpData = HttpData<JqJqfkzcDetailsResponse>();
        launch(block = {
            httpData.httpResponse =
                jingQingFeedBackRepository.jqJqfkzcDetails(JqJqfkzcDetailsRequest(jqbh = jqbh));
        }, complete = {
            httpData.httpResponseState = it;
            jqJqfkzcDetailsLiveData.value = httpData
        })
    }

    fun jqDict(dictid: String, dictkey: String, mjjh: String) {
        var httpData = HttpData<JqDictResponse>();
        var request = JqDictRequest(dictid = dictid, dictkey = dictkey, mjjh = mjjh);
        launch(block = {
            httpData.httpResponse = jingQingFeedBackRepository.jqDict(request)
            httpData.httpResponse?.dictid = dictid
        }, complete = {
            httpData.httpResponseState = it;
            jqDictLiveData.value = httpData
        })
    }

    fun ywDict() {
        var request = YwDictRequest();
        var httpData = HttpData<YwDictResponse>()
        launch(block = {
            httpData.httpResponse = jingQingFeedBackRepository.ywDict(request)
        }, complete = {
            httpData.httpResponseState = it
            ywDictLiveData.value = httpData
        })
    }

    fun jqJqfk(request: JqJqfkRequest) {

        var httpData = HttpData<JqJqfkResponse>()
        launch(block = {
            httpData.httpResponse = jingQingFeedBackRepository.jqJqfk(request)
        }, complete = {
            httpData.httpResponseState = it
            jqJqfkLiveData.value = httpData
        })
    }

}