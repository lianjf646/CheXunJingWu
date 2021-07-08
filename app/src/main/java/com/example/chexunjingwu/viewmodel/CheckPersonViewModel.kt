package com.example.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chexunjingwu.base.BaseViewModel
import com.example.chexunjingwu.http.api.HttpData
import com.example.chexunjingwu.http.request.ChaRenRequest
import com.example.chexunjingwu.http.request.ChaRenResponse
import com.example.chexunjingwu.repository.CheckRepository

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