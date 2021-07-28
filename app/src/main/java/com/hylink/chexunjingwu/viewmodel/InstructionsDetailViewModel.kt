package com.hylink.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hylink.chexunjingwu.base.BaseViewModel
import com.hylink.chexunjingwu.http.api.HttpData
import com.hylink.chexunjingwu.http.request.GetJqTztgDetailRequest
import com.hylink.chexunjingwu.http.request.GetJqTztgFkListRequest
import com.hylink.chexunjingwu.http.request.InstructionMessageReadRequest
import com.hylink.chexunjingwu.http.response.GetJqTztgDetailResponse
import com.hylink.chexunjingwu.http.response.GetJqTztgFkListResponse
import com.hylink.chexunjingwu.http.response.InstructionMessageReadResponse
import com.hylink.chexunjingwu.repository.InstructionsDetailRepository

class InstructionsDetailViewModel : BaseViewModel() {
    private val instructionsDetailRepository by lazy { InstructionsDetailRepository() }
    val instructionMessageReadResponse : MutableLiveData<HttpData<InstructionMessageReadResponse>> = MutableLiveData()
    val getJqTztgDetailResponseResponse : MutableLiveData<HttpData<GetJqTztgDetailResponse>> = MutableLiveData()
    val  getJqTztgFkListResponse :MutableLiveData<HttpData<GetJqTztgFkListResponse>> = MutableLiveData()

    fun instructionMessageRead(request: InstructionMessageReadRequest) {
        var httpData: HttpData<InstructionMessageReadResponse> = HttpData();
        launch(block = {
            httpData.httpResponse = instructionsDetailRepository.instructionMessageRead(request)
        }, complete = {
            httpData.httpResponseState = it
            instructionMessageReadResponse.value = httpData;
        })
    }

    fun getJqTztgDetail(request: GetJqTztgDetailRequest){
        var httpData: HttpData<GetJqTztgDetailResponse> = HttpData();
        launch(block = {
            httpData.httpResponse = instructionsDetailRepository.getJqTztgDetail(request)
        }, complete = {
            httpData.httpResponseState = it
            getJqTztgDetailResponseResponse.value = httpData;
        })
    }

  fun  getJqTztgFkList(request :GetJqTztgFkListRequest){
      var httpData: HttpData<GetJqTztgFkListResponse> = HttpData();
      launch(block = {
          httpData.httpResponse = instructionsDetailRepository.getJqTztgFkList(request)
      }, complete = {
          httpData.httpResponseState = it
          getJqTztgFkListResponse.value = httpData;
      })
  }
}