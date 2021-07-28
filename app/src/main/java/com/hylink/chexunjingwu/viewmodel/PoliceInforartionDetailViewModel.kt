package com.hylink.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hylink.chexunjingwu.base.BaseViewModel
import com.hylink.chexunjingwu.http.api.HttpData
import com.hylink.chexunjingwu.http.request.JqChangeStateRequest
import com.hylink.chexunjingwu.http.response.JqChangeStateResponse
import com.hylink.chexunjingwu.repository.PoliceInforartionDetailRepository

class PoliceInforartionDetailViewModel : BaseViewModel() {

    private val repository by lazy { PoliceInforartionDetailRepository() }
    val mutableLiveData = MutableLiveData<HttpData<JqChangeStateResponse>>()

    fun jqQs(request: JqChangeStateRequest) {
        var httpData: HttpData<JqChangeStateResponse> = HttpData();
        launch(block = {
            httpData.httpResponse = repository.jqQs(request);
        }, complete = {
            httpData.httpResponseState = it;
            mutableLiveData.value = httpData;
        })
    }

    fun jqDcqr(request: JqChangeStateRequest) {
        var httpData: HttpData<JqChangeStateResponse> = HttpData();
        launch(block = {
            httpData.httpResponse = repository.jqDcqr(request);
        }, complete = {
            httpData.httpResponseState = it;
            mutableLiveData.value = httpData;
        })
    }

    fun jqJqjs(request: JqChangeStateRequest) {
        var httpData: HttpData<JqChangeStateResponse> = HttpData();
        launch(block = {
            httpData.httpResponse = repository.jqJqjs(request);
        }, complete = {
            httpData.httpResponseState = it;
            mutableLiveData.value = httpData;
        })
    }
}