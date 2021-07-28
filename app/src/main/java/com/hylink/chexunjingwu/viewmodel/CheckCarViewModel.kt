package com.hylink.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hylink.chexunjingwu.base.BaseViewModel
import com.hylink.chexunjingwu.http.api.HttpData
import com.hylink.chexunjingwu.http.request.ChaCheRequest
import com.hylink.chexunjingwu.http.response.ChaCheResponse
import com.hylink.chexunjingwu.repository.CheckRepository

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