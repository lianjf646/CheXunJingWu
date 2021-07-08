package com.example.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chexunjingwu.base.BaseViewModel
import com.example.chexunjingwu.http.api.HttpData
import com.example.chexunjingwu.http.request.HomeLoginRequest
import com.example.chexunjingwu.http.response.HomeLoginResponse
import com.example.chexunjingwu.repository.LoginRepository

class LoginViewModel : BaseViewModel() {
    private val loginRepository by lazy { LoginRepository() }

    val loginLiveData = MutableLiveData<HttpData<HomeLoginResponse>>()

    fun login(name: String, password: String) {
        var httpData: HttpData<HomeLoginResponse> = HttpData();
        val request = HomeLoginRequest(username = name, password = password);
        launch(block = {
            httpData.httpResponse = loginRepository.login(request);
        }, complete = {
            httpData.httpResponseState = it;
            loginLiveData.value = httpData;
        })
    }
}