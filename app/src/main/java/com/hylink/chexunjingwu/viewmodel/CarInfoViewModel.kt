package com.hylink.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hylink.chexunjingwu.base.BaseViewModel
import com.hylink.chexunjingwu.http.api.HttpData
import com.hylink.chexunjingwu.http.request.GetAlarmByIdRequest
import com.hylink.chexunjingwu.http.request.GetVerificationPortraitByIdRequest
import com.hylink.chexunjingwu.http.response.GetAlarmByIdResponse
import com.hylink.chexunjingwu.http.response.GetVerificationPortraitByIdResponse
import com.hylink.chexunjingwu.repository.CarInfoRepository

class CarInfoViewModel : BaseViewModel() {

    private val repository by lazy { CarInfoRepository() }
    val chaCheLiveData: MutableLiveData<HttpData<GetAlarmByIdResponse.Data.AlarmMessage>> =
        MutableLiveData()

    val getVerificationPortraitByIdLiveData =
        MutableLiveData<HttpData<GetVerificationPortraitByIdResponse>>()

    fun getAlarmById(request: GetAlarmByIdRequest) {
        var httpData: HttpData<GetAlarmByIdResponse.Data.AlarmMessage> =
            HttpData();
        launch(block = {
            var ddd =
                repository.getAlarmById(request).data.alarm_message
            httpData.httpResponse = ddd
        }, complete = {
            httpData.httpResponseState = it;
            chaCheLiveData.value = httpData;
        })
    }

    fun getVerificationPortraitById(request: GetVerificationPortraitByIdRequest) {
        var httpData: HttpData<GetVerificationPortraitByIdResponse> =
            HttpData();
        launch(block = {
            var ddd = repository.getVerificationPortraitById(request)
            httpData.httpResponse = ddd
        }, complete = {
            httpData.httpResponseState = it;
            getVerificationPortraitByIdLiveData.value = httpData;
        })
    }
}