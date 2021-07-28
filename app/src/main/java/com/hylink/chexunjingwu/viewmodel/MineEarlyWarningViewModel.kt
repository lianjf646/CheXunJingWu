package com.hylink.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hylink.chexunjingwu.base.BaseViewModel
import com.hylink.chexunjingwu.http.api.HttpData
import com.hylink.chexunjingwu.http.request.AlarmListRequest
import com.hylink.chexunjingwu.http.response.AlarmListResponse
import com.hylink.chexunjingwu.repository.MineEarlyWarningRepository

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