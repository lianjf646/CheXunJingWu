package com.example.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chexunjingwu.base.BaseViewModel
import com.example.chexunjingwu.http.api.HttpData
import com.example.chexunjingwu.http.request.AlarmListRequest
import com.example.chexunjingwu.http.response.AlarmListResponse
import com.example.chexunjingwu.repository.MineEarlyWarningRepository

class MineEarlyWarningViewModel : BaseViewModel() {

    private val mineEarlyWarningRepository by lazy { MineEarlyWarningRepository() }

    val alarmListLiveData = MutableLiveData<HttpData<AlarmListResponse>>()

    fun alarmList(request: AlarmListRequest) {
        var httpData: HttpData<AlarmListResponse> = HttpData();

        launch(block = {
            httpData.httpResponse = mineEarlyWarningRepository.alarmList(request)
        }, complete = {
            httpData.httpResponseState = it;
            alarmListLiveData.value = httpData;
        })


    }
}