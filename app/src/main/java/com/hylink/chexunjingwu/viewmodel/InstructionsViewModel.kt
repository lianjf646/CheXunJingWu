package com.hylink.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hylink.chexunjingwu.base.BaseViewModel
import com.hylink.chexunjingwu.http.api.HttpData
import com.hylink.chexunjingwu.http.request.GetJqTztgListRequest
import com.hylink.chexunjingwu.http.response.GetJqTztgListResponse
import com.hylink.chexunjingwu.repository.InstructionsRepository

class InstructionsViewModel : BaseViewModel() {
    private val instructionsRepository by lazy { InstructionsRepository() }
    val getJqTztgListLiveData : MutableLiveData<HttpData<GetJqTztgListResponse>> = MutableLiveData()


    fun getJqTztgList(request: GetJqTztgListRequest) {
        var httpData: HttpData<GetJqTztgListResponse> = HttpData();
        launch(block = {
            httpData.httpResponse = instructionsRepository.getJqTztgList(request)
        }, complete = {
            httpData.httpResponseState = it;
            getJqTztgListLiveData.value = httpData;
        })

    }
}