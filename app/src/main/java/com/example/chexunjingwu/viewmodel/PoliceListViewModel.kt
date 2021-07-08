package com.example.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chexunjingwu.base.BaseViewModel
import com.example.chexunjingwu.http.api.HttpData
import com.example.chexunjingwu.http.request.GetUserListRequest
import com.example.chexunjingwu.http.response.GetUserListResponse
import com.example.chexunjingwu.repository.PoliceListRepository

class PoliceListViewModel : BaseViewModel() {

    private val repository by lazy { PoliceListRepository() }
    val getUserListLiveData = MutableLiveData<HttpData<GetUserListResponse>>()

    fun getUserList(code: String) {
        var httpData = HttpData<GetUserListResponse>();
        launch(block = {
            httpData.httpResponse = repository.getUserList(GetUserListRequest(code = code))
        }, complete = {
            httpData.httpResponseState = it;
            getUserListLiveData.value = httpData
        })
    }

}