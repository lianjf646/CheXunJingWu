package com.hylink.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hylink.chexunjingwu.base.BaseViewModel
import com.hylink.chexunjingwu.http.api.HttpData
import com.hylink.chexunjingwu.http.request.GetJqListRequest
import com.hylink.chexunjingwu.http.request.JqJqfkzcListRequest
import com.hylink.chexunjingwu.http.response.GetJqListResponse
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.http.response.JqJqfkzcListResponse
import com.hylink.chexunjingwu.repository.JIeChuJIngRepository
import com.hylink.chexunjingwu.tools.DataHelper

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