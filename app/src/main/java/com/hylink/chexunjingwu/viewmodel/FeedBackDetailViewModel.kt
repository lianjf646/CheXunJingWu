package com.hylink.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hylink.chexunjingwu.base.BaseViewModel
import com.hylink.chexunjingwu.http.api.HttpData
import com.hylink.chexunjingwu.http.request.GetJqFkListRequest
import com.hylink.chexunjingwu.http.response.GetJqFkListResponse
import com.hylink.chexunjingwu.repository.FeedBackDetailRepository
import com.hylink.chexunjingwu.tools.DataHelper

class FeedBackDetailViewModel : BaseViewModel() {

    private val repository by lazy { FeedBackDetailRepository() }

    var getJqFkListLiveData = MutableLiveData<HttpData<GetJqFkListResponse>>()

    fun getJqFkList(jqbh: String?) {
        var request = GetJqFkListRequest(
            pd = GetJqFkListRequest.PdBean(
                imei = DataHelper.imei,
                jqbh = jqbh
            )

        );
        var httpData: HttpData<GetJqFkListResponse> = HttpData();
        launch(block = {
            httpData.httpResponse= repository.getJqFkList(request)
        }, complete = {
            httpData.httpResponseState = it;
            getJqFkListLiveData.value = httpData

        })
    }

}