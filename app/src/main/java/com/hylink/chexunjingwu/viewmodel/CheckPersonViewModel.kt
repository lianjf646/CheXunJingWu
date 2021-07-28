package com.hylink.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hylink.chexunjingwu.base.BaseViewModel
import com.hylink.chexunjingwu.http.api.HttpData
import com.hylink.chexunjingwu.http.request.ChaRenRequest
import com.hylink.chexunjingwu.http.request.ChaRenResponse
import com.hylink.chexunjingwu.repository.CheckRepository

class CheckPersonViewModel : BaseViewModel() {
    private val repository by lazy { CheckRepository() }
    val chaRenLiveData: MutableLiveData<HttpData<ChaRenResponse>> = MutableLiveData()

    fun checkRY(request: ChaRenRequest) {
        var httpData = HttpData<ChaRenResponse>()
        launch(block = {
            httpData.httpResponse = repository.checkRY(request)
        }, complete = {
            httpData.httpResponseState = it
            chaRenLiveData.value = httpData
        })

    }

}