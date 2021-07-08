package com.example.chexunjingwu.base

import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.request.BaseRequestOptions
import com.example.chexunjingwu.http.api.ApiCommonResponse

class StateLiveData <T> : MutableLiveData<ApiCommonResponse<T>>() {


}