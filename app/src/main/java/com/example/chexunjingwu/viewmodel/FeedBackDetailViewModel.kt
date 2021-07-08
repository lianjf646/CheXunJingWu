package com.example.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chexunjingwu.base.BaseViewModel
import com.example.chexunjingwu.http.api.HttpData
import com.example.chexunjingwu.http.request.GetJqFkListRequest
import com.example.chexunjingwu.http.response.GetJqFkListResponse
import com.example.chexunjingwu.repository.FeedBackDetailRepository
import com.example.chexunjingwu.tools.DataHelper

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