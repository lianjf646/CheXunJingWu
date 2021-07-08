package com.example.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chexunjingwu.base.BaseViewModel
import com.example.chexunjingwu.http.api.HttpData
import com.example.chexunjingwu.http.request.JTztgFkRequest
import com.example.chexunjingwu.http.request.YwDictRequest
import com.example.chexunjingwu.http.response.JTztgFkResponse
import com.example.chexunjingwu.http.response.YwDictResponse
import com.example.chexunjingwu.repository.InstructionsFeedBackRepository

class InstructionsFeedBackViewModel : BaseViewModel() {
    private val reedBackRepository by lazy { InstructionsFeedBackRepository() }
    val reedBackLiveData: MutableLiveData<HttpData<YwDictResponse>> = MutableLiveData()
    val jTztgFkLiveData: MutableLiveData<HttpData<JTztgFkResponse>> = MutableLiveData()
    fun ywDict() {
        var request = YwDictRequest(appCode = "106305", code = "305005")
        var httpData: HttpData<YwDictResponse> = HttpData();
        launch(block = {
            httpData.httpResponse = reedBackRepository.ywDict(request)
        }, complete = {
            httpData.httpResponseState = it;
            reedBackLiveData.value = httpData;
        })

    }

    fun jTztgFk(request: JTztgFkRequest) {

        var httpData: HttpData<JTztgFkResponse> = HttpData();
        launch(block = {
            httpData.httpResponse = reedBackRepository.jTztgFk(request)
        }, complete = {
            httpData.httpResponseState = it;
            jTztgFkLiveData.value = httpData;
        })
    }

}