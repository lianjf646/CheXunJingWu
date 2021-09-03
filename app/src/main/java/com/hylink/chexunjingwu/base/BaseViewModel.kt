package com.hylink.chexunjingwu.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.hylink.chexunjingwu.http.api.ApiException
import com.hylink.chexunjingwu.http.api.HttpResponseState
import com.hylink.chexunjingwu.tools.showError
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import kotlin.Unit as Unit1

typealias Error = suspend (e: Exception) -> Unit1

open class BaseViewModel : ViewModel() {


    protected open fun launch(
        block: suspend () -> Unit1,
        error: Error? = null,
        complete: suspend (httpResponseState: HttpResponseState) -> Unit1

    ): Job {
        var httpResponseState: HttpResponseState = HttpResponseState.STATE_UNKNOWN;
        return viewModelScope.launch(
            Dispatchers.Main
        ) {
            try {
                block()
                httpResponseState = HttpResponseState.STATE_SUCCESS
            } catch (e: Exception) {
                httpResponseState = parseError(e)
                error?.invoke(e) // 捕获ApiCommonResponse类中的异常信息
            } finally {
                complete(httpResponseState)
            }
        }
    }


    private fun parseError(e: Exception): HttpResponseState {
        when (e) {
            is ApiException -> {
                when (e.errorCode) {
                    "420" -> {
                        showError(e.errorMsg)

                    }
                    "1001" -> {
                        showError(e.errorMsg)
                    }
                    else -> {
                        showError(e.errorMsg)
                    }
                }
                return HttpResponseState.STATE_FAILED
            }
            is HttpException -> {
                // TODO http异常
                var code = e?.code()
                var message = e?.message()
                var str = e.response()?.errorBody()?.string()
                showError(code?.toString() + message?.toString() + str?.toString())
                return HttpResponseState.STATE_ERROR
            }


            is ConnectException -> {
                // TODO 连接失败
                showError("连接失败")
                return HttpResponseState.STATE_ERROR
            }

            is SocketTimeoutException -> {
                // TODO 请求超时
                showError("请求超时")
                return HttpResponseState.STATE_ERROR
            }

            is SocketException -> {
                // TODO 请求异常（超时）
                showError("请求异常（超时）" + e.message)
                return HttpResponseState.STATE_ERROR
            }

            is JsonParseException -> {
                // TODO JSON解析错误
                showError("JSON解析错误" + e.message)
                return HttpResponseState.STATE_ERROR
            }
            else -> {
                // TODO 其他错误
                e.message?.let {
                    Log.e(">>>>>", "其他错误: $it")
                    showError("其他错误$it")
                }
                return HttpResponseState.STATE_UNKNOWN
            }
        }
    }


    protected fun <T> async(block: suspend () -> T): Deferred<T> {
        return viewModelScope.async { block() }
    }
}