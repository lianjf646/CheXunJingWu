package com.example.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chexunjingwu.base.BaseViewModel
import com.example.chexunjingwu.http.api.HttpData
import com.example.chexunjingwu.http.request.ChaCheRequest
import com.example.chexunjingwu.http.response.ChaCheResponse
import com.example.chexunjingwu.repository.CheckRepository

class CheckCarViewModel : BaseViewModel() {

    private val repository by lazy { CheckRepository() }
    val chaCheLiveData: MutableLiveData<HttpData<ChaCheResponse>> = MutableLiveData()

    fun checCL(request: ChaCheRequest) {
        var httpData = HttpData<ChaCheResponse>()
        launch(block = {
            httpData.httpResponse = repository.checCL(request)
        }, complete = {
            httpData.httpResponseState = it
            chaCheLiveData.value = httpData
        })

    }
}