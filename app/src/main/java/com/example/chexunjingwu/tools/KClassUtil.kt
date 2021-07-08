package com.example.chexunjingwu.tools

import java.lang.reflect.ParameterizedType

class KClassUtil {
    companion object {
        inline fun <reified T> getKType(K: Any,position:Int):T {
            var parameterizedType = K::class.java.genericSuperclass as ParameterizedType
            var actualTypeArguments = parameterizedType.actualTypeArguments
            return ( actualTypeArguments[position].javaClass as Class<T>).newInstance()
        }
    }
}