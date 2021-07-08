package com.example.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chexunjingwu.base.BaseViewModel
import com.example.chexunjingwu.http.api.HttpData
import com.example.chexunjingwu.http.request.IfTimeOutRequest
import com.example.chexunjingwu.http.request.LoginRequest
import com.example.chexunjingwu.http.request.SignStatusRequest
import com.example.chexunjingwu.http.response.SignStatusResponse
import com.example.chexunjingwu.repository.HomeFragmentRepository
import com.example.chexunjingwu.tools.showNormal
import kotlinx.coroutines.delay
import java.util.*

class HomeFragmentViewModel : BaseViewModel() {

    private val homeFragmentRep by lazy { HomeFragmentRepository() }

    val signStatusLiveData: MutableLiveData<HttpData<SignStatusResponse>> = MutableLiveData()
    fun ifTimeOut(uuid: String, idCard: String) {
        val request = IfTimeOutRequest(uuid = uuid);
        launch(block = {
            var ifTimeOutResponse = homeFragmentRep.ifTimeOut(request);
            if (!ifTimeOutResponse?.code.equals("200")) {
                showNormal(ifTimeOutResponse?.msg)
                return@launch
            }
            var loginResponse = homeFragmentRep.login(
                LoginRequest(
                    uuid = uuid, idCard = idCard
                )
            )
            if (loginResponse != null) {
                if (!loginResponse?.code.equals("200")) {
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
                        signStatusLiveData.value = httpData;

                    })
                delay(10 * 1000)
//                break
            }
        }

    }
}