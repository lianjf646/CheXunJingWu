package com.hylink.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hylink.chexunjingwu.base.BaseViewModel
import com.hylink.chexunjingwu.http.api.HttpData
import com.hylink.chexunjingwu.http.request.IfTimeOutRequest
import com.hylink.chexunjingwu.http.request.LoginRequest
import com.hylink.chexunjingwu.http.request.SignStatusRequest
import com.hylink.chexunjingwu.http.response.SignStatusResponse
import com.hylink.chexunjingwu.repository.HomeFragmentRepository
import com.hylink.chexunjingwu.tools.showNormal
import kotlinx.coroutines.delay
import java.util.*

class HomeFragmentViewModel : BaseViewModel() {

    private val homeFragmentRep by lazy { HomeFragmentRepository() }

    val signStatusLiveData: MutableLiveData<HttpData<SignStatusResponse>> = MutableLiveData()
    fun ifTimeOut(uuid: String, idCard: String) {
        val request = IfTimeOutRequest(uuid = uuid);
        launch(block = {
            var ifTimeOutResponse = homeFragmentRep.ifTimeOut(request);
            if (ifTimeOutResponse?.code != "200") {
                showNormal(ifTimeOutResponse?.msg)
                return@launch
            }
            var loginResponse = homeFragmentRep.login(
                LoginRequest(
                    uuid = uuid, idCard = idCard
                )
            )
            if (loginResponse != null) {
                if (loginResponse?.code != "200") {
                    showNormal(loginResponse?.msg)
                    return@launch
                }
            }
        }, complete = {

        })
    }

    fun signStatus(idCard: String) {
        var httpData: HttpData<SignStatusResponse> = HttpData();
        async {
            while (true) {
                launch(block = {
                    httpData.httpResponse =
                        homeFragmentRep.signStatus(SignStatusRequest(idCard = idCard))
                },
                    complete = {
                        httpData.httpResponseState = it;
                        signStatusLiveData.postValue(httpData)

                    })
                delay(10 * 1000)
//                break
            }
        }

    }
}