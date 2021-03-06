package com.hylink.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hylink.chexunjingwu.base.BaseViewModel
import com.hylink.chexunjingwu.http.api.HttpData
import com.hylink.chexunjingwu.http.request.HomeLoginRequest
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.repository.LoginRepository
import com.hylink.chexunjingwu.tools.md5

class LoginViewModel : BaseViewModel() {
    private val loginRepository by lazy { LoginRepository() }

    val loginLiveData = MutableLiveData<HttpData<HomeLoginResponse>>()

    fun login(name: String, password: String) {
        var httpData: HttpData<HomeLoginResponse> = HttpData();
        val request = HomeLoginRequest(username = name, password = md5(password));
        launch(block = {
            httpData.httpResponse = loginRepository.login(request);
        }, complete = {
            httpData.httpResponseState = it;
            loginLiveData.postValue(httpData)
        })
    }


    fun login(idCard: String) {
        var httpData: HttpData<HomeLoginResponse> = HttpData();
        val request = HomeLoginRequest(idCard = idCard);
        launch(block = {

            httpData.httpResponse = loginRepository.login(request);
        }, complete = {
            httpData.httpResponseState = it;
            loginLiveData.postValue(httpData)
        })
    }
}