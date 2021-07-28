package com.hylink.chexunjingwu.http.api

enum class HttpResponseState {

    STATE_SUCCESS, //成功
    STATE_FAILED,  //接口请求成功但是服务器返回error
    STATE_ERROR,   //请求异常
    STATE_UNKNOWN  //未知
}