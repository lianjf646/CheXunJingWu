package com.example.chexunjingwu.base

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody

open class BaseRepository {
    fun <T> createRequsetBody(t: T): RequestBody {
        var body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(t)
        )
        return body;
    }
}