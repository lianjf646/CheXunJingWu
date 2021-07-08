package com.example.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chexunjingwu.base.BaseViewModel
import com.example.chexunjingwu.http.api.HttpData
import com.example.chexunjingwu.http.request.GetJqListRequest
import com.example.chexunjingwu.http.request.JqJqfkzcListRequest
import com.example.chexunjingwu.http.response.GetJqListResponse
import com.example.chexunjingwu.http.response.HomeLoginResponse
import com.example.chexunjingwu.http.response.JqJqfkzcListResponse
import com.example.chexunjingwu.repository.JIeChuJIngRepository
import com.example.chexunjingwu.tools.DataHelper

class JieChuJingViewModel : BaseViewModel() {

    private val jieChuJIngRepository by lazy { JIeChuJIngRepository() }
    val getJqListLiveData = MutableLiveData<HttpData<GetJqListResponse>>()
    val jqJqfkzcListResponseLiveData = MutableLiveData<HttpData<JqJqfkzcListResponse>>()
    var userInfo: HomeLoginResponse.Data.Data.User =
        DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User;

    var carno = ""
    var jjdzt = ""

    fun getJqList(currentPage: Int) {
        var request = GetJqListRequest(
            currentPage = currentPage, showCount = 999, GetJqListRequest.PdBean(
                gxdwdm = userInfo?.group?.code,
                imei = DataHelper.imei,
                isgx = 1,
                isnow = 0,
                mjdwmc = userInfo?.group?.name,
                carno = carno,
                jjdzt = jjdzt,
            )
        )
        var httpData: HttpData<GetJqListResponse> = HttpData();
        launch(block = {
            httpData.httpResponse = jieChuJIngRepository.getJqList(request)
        }, complete = {
            httpData.httpResponseState = it;
            getJqListLiveData.value = httpData;
        })
    }

    fun jqJqfkzcList() {
        var request = JqJqfkzcListRequest(imei = DataHelper.imei)

        var httpData: HttpData<JqJqfkzcListResponse> = HttpData();
        launch(block = {
            httpData.httpResponse = jieChuJIngRepository.jqJqfkzcList(request)
        }, complete = {
            httpData.httpResponseState = it;
            jqJqfkzcListResponseLiveData.value = httpData;
        })
    }
}