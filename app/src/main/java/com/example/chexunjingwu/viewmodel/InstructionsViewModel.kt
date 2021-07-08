package com.example.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chexunjingwu.base.BaseViewModel
import com.example.chexunjingwu.http.api.HttpData
import com.example.chexunjingwu.http.request.GetJqTztgListRequest
import com.example.chexunjingwu.http.response.GetJqTztgListResponse
import com.example.chexunjingwu.repository.InstructionsRepository

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