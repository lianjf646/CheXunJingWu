package com.hylink.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hylink.chexunjingwu.base.BaseViewModel
import com.hylink.chexunjingwu.http.api.HttpData
import com.hylink.chexunjingwu.http.request.GetUserListRequest
import com.hylink.chexunjingwu.http.response.GetUserListResponse
import com.hylink.chexunjingwu.repository.PoliceListRepository

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