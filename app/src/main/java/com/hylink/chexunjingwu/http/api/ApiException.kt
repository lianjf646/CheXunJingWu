package com.hylink.chexunjingwu.http.api

import java.lang.RuntimeException

class ApiException(val errorCode: String, val errorMsg: String) : RuntimeException()