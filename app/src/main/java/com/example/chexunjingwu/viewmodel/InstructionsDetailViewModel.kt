package com.example.chexunjingwu.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.chexunjingwu.base.BaseViewModel
import com.example.chexunjingwu.http.api.HttpData
import com.example.chexunjingwu.http.request.GetJqTztgDetailRequest
import com.example.chexunjingwu.http.request.GetJqTztgFkListRequest
import com.example.chexunjingwu.http.request.InstructionMessageReadRequest
import com.example.chexunjingwu.http.response.GetJqTztgDetailResponse
import com.example.chexunjingwu.http.response.GetJqTztgFkListResponse
import com.example.chexunjingwu.http.response.InstructionMessageReadResponse
import com.example.chexunjingwu.repository.InstructionsDetailRepository

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