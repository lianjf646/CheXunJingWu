package com.hylink.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hylink.chexunjingwu.base.BaseViewModel
import com.hylink.chexunjingwu.http.api.HttpData
import com.hylink.chexunjingwu.http.request.GetPatrolListRequest
import com.hylink.chexunjingwu.http.request.QueryPeripheryVehicleRequest
import com.hylink.chexunjingwu.http.response.GetPatrolListResponse
import com.hylink.chexunjingwu.http.response.QueryPeripheryVehicleResponse
import com.hylink.chexunjingwu.repository.NearbyPoliceForcesRepository

class NearbyPoliceForcesViewModel : BaseViewModel() {
    private val repository by lazy { NearbyPoliceForcesRepository() }

    val queryPeripheryVehicleLD = MutableLiveData<HttpData<QueryPeripheryVehicleResponse>>()

    val getPatrolListVehicleLD = MutableLiveData<HttpData<GetPatrolListResponse>>()
    fun queryPeripheryVehicle() {
        var httpData: HttpData<QueryPeripheryVehicleResponse> = HttpData();
        var request = QueryPeripheryVehicleRequest();
        launch(block = {
            var response =
                repository.queryPeripheryVehicle(request)
            httpData.httpResponse = response
        }, complete = {
            httpData.httpResponseState = it;
            queryPeripheryVehicleLD.postValue(httpData)
        })
    }

    fun getPatrolList(idCard: String) {
        var httpData: HttpData<GetPatrolListResponse> = HttpData();
        var request = GetPatrolListRequest(idCard = idCard);
        launch(block = {
            var getPatrolListResponse = repository.getPatrolList(request)
            httpData.httpResponse = getPatrolListResponse
        }, complete = {
            httpData.httpResponseState = it;
            getPatrolListVehicleLD.postValue(httpData)
        })
    }

}