package com.example.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chexunjingwu.base.BaseViewModel
import com.example.chexunjingwu.http.api.HttpData
import com.example.chexunjingwu.http.request.GetAlarmByIdRequest
import com.example.chexunjingwu.http.request.GetVerificationPortraitByIdRequest
import com.example.chexunjingwu.http.response.GetAlarmByIdResponse
import com.example.chexunjingwu.http.response.GetVerificationPortraitByIdResponse
import com.example.chexunjingwu.repository.PersonnelInfoRepository

class PersonnelInfoViewModel : BaseViewModel() {
    private val repository by lazy { PersonnelInfoRepository() }
    val getAlarmByIdLiveData =
        MutableLiveData<HttpData<GetAlarmByIdResponse.Data.AlarmMessage.VerificationPortraitData>>()

    val getVerificationPortraitByIdLiveData =
        MutableLiveData<HttpData<GetVerificationPortraitByIdResponse>>()


    fun getAlarmById(request: GetAlarmByIdRequest, pos: Int) {
        var httpData: HttpData<GetAlarmByIdResponse.Data.AlarmMessage.VerificationPortraitData> =
            HttpData();
        launch(block = {
            var ddd =
                repository.getAlarmById(request).data.alarm_message.verificationPortraitDataList[pos]
            httpData.httpResponse = ddd
        }, complete = {
            httpData.httpResponseState = it;
            getAlarmByIdLiveData.value = httpData;
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