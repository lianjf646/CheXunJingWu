package com.hylink.chexunjingwu.base

import androidx.lifecycle.MutableLiveData
import com.hylink.chexunjingwu.http.api.ApiCommonResponse

class StateLiveData <T> : MutableLiveData<ApiCommonResponse<T>>() {


}