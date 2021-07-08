package com.example.chexunjingwu.http.api

import java.lang.Exception


class HttpData<T> {
    lateinit var httpResponseState: HttpResponseState
    var httpResponse: T? = null
    lateinit var exception: Exception
}